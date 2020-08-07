package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ElectionOfficialEntity extends AdminEntity implements ElectionOfficial {

    @Id
    @GeneratedValue
    private Long id;

    public ElectionOfficialEntity(String username, String password) {
        super(username, password);
    }

    public ElectionOfficialEntity() {

    }

    @Override
    public boolean createQuestion(String electionName, String questionText) {
        if(ElectionEntity.getElectionByName(electionName).getAvailability()) {
            return false;
        }
        if(QuestionEntity.getQuestionsByName(questionText, electionName) == null) {
            Question question = new QuestionEntity(questionText, electionName);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean createAnswer(Question question, String answerText) {
        if(question == null) {
            System.err.println("that question does not exist");
            return false;
        }
        if(question.getElection().getAvailability()) {
            return false;
        }
        if(AnswerOptionEntity.getAnswerOptionIndexByName(question.getQuestionText(), answerText) == null) {
            AnswerOption answer = new AnswerOptionEntity(question.getElection().getElectionName(), question.getQuestionText(),
                    answerText);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean createElection(String name, LocalDate startTime, LocalDate endTime, boolean status) {
        if(ElectionEntity.getElectionByName(name) == null) {
            Election election = new ElectionEntity(name, startTime, endTime, status, false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateQuestion(String electionName, String originalQuestionText, String updatedQuestionText) {
        if(ElectionEntity.getElectionByName(electionName).getAvailability()) {
            return false;
        }
        QuestionEntity question = QuestionEntity.getQuestionsByName(originalQuestionText, electionName);
        if(question == null) {
            return false;
        }
        question.setQuestionText(updatedQuestionText);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            session.saveOrUpdate(question);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exception) {
            System.err.println("Could not update Question " + originalQuestionText + ". " + exception.getMessage());
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean updateAnswer(Question question, String originalAnswerText, String updatedAnswerText) {
        if(question == null) {
            return false;
        }
        if(question.getElection().getAvailability()) {
            return false;
        }
        AnswerOptionEntity answer = AnswerOptionEntity.getAnswerOptionByQuestionAndAnswerOptionName(question.getQuestionText(),
                originalAnswerText);
        answer.setAnswerText(updatedAnswerText);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            session.saveOrUpdate(answer);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exception) {
            System.err.println("Could not update answer " + originalAnswerText + ". " + exception.getMessage());
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean updateElectionName(String originalElectionName, String updatedElectionName) {
        Election election = ElectionEntity.getElectionByName(originalElectionName);
        if(election == null) {
            return false;
        }
        if(election.getAvailability()) {
            return false;
        }
        election.setElectionName(updatedElectionName);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            session.saveOrUpdate(election);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exception) {
            System.err.println("Could not update election name " + originalElectionName + ". " + exception.getMessage());
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean removeQuestion(String electionName, String questionText) {
        Election election = ElectionEntity.getElectionByName(electionName);
        if(election == null || election.getAvailability()) {
            return false;
        }
        Question question = QuestionEntity.getQuestionsByName(questionText, electionName);
        if(question == null) {
            return false;
        }
        List<AnswerOptionEntity> correspondingAnswers = question.getAssociatedAnswerOption();
        for(AnswerOptionEntity answerOptionEntity : correspondingAnswers) {
            if(!removeAnswer(question, answerOptionEntity.getAnswerText())) {
                return false;
            }
        }
        question.setStatus(false);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            session.saveOrUpdate(question);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exception) {
            System.err.println("Could not remove question " + questionText + ". " + exception.getMessage());
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean removeAnswer(Question question, String answerText) {
        if(question == null) {
            return false;
        }
        if(question.getElection().getAvailability()) {
            return false;
        }
        AnswerOptionEntity answer = AnswerOptionEntity.getAnswerOptionByQuestionAndAnswerOptionName(question.getQuestionText(), answerText);
        answer.setStatus(false);
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(answer);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exception) {
            System.err.println("Could not remove answer " + answerText + ". " + exception.getMessage());
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean removeElection(String electionName) {
        ElectionEntity election = ElectionEntity.getElectionByName(electionName);
        if(election == null || election.getAvailability()) {
            return false;
        }
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        for(String question : questions) {
            if(!removeQuestion(electionName, question)) {
                return false;
            }
        }
        election.setRemoved(true);
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(election);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exception) {
            System.err.println("Could not remove election " + electionName + ". " + exception.getMessage());
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean startElection(String electionName) {
        ElectionEntity election = ElectionEntity.getElectionByName(electionName);
        if(election != null) {
            election.setStatus(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean endElection(String electionName) {
        ElectionEntity election = ElectionEntity.getElectionByName(electionName);
        if(election != null) {
            election.setStatus(false);
            return true;
        }
        return false;
    }


}
