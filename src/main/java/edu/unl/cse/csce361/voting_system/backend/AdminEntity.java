package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class AdminEntity implements  Admin {

    static AdminEntity getAdminByUsername(String username) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        AdminEntity admin = null;
        try {
            admin = session.bySimpleNaturalId(AdminEntity.class).load(username);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load Admin Account " + username + ". " + exception.getMessage());
        }
        return admin;
    }

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column
    private String username;

    @Column
    private String password;

    public AdminEntity() {
    }

    public AdminEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean logIn(String password) {
        return this != null && this.password.equals(password);
    }

    public Map<String, String> getAllVoterStatus(String electionName){
        Session session = HibernateUtil.getSession();
        Map<String, String> voterStatus = new HashMap<>();
        List<VoterEntity> voters = new ArrayList<>();
        try {
            session.beginTransaction();
            voters = session.createQuery("SELECT voter From VoterEntity voter", VoterEntity.class).getResultList();
            session.getTransaction().commit();
            for(VoterEntity voterEntity : voters) {
                voterStatus.put(voterEntity.getName(), (voterEntity.hasVoted(electionName)?"yes" : "no"));
            }
        } catch (HibernateException exception) {
            System.err.println("Could not load all Voters " + exception.getMessage());
        }
        return voterStatus;
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

    @Override
    public Map<QuestionEntity, Map<AnswerOptionEntity, Long>> getFinalResult(String electionName){
        if(!endElection(electionName)){
            System.err.println("cannot end election " + electionName);
            return null;
        }
        List<QuestionEntity> questions = ElectionEntity.getElectionByName(electionName).getAssociatedQuestions();
        Map<QuestionEntity, Map<AnswerOptionEntity, Long>> finalResult = new HashMap<>();
        for(QuestionEntity question : questions){
            Map<AnswerOptionEntity, Long> finalResultEachQuestion = getFinalResultForEachQuestion(electionName, question);
            finalResult.put(question, finalResultEachQuestion);
        }
        return finalResult;
    }

    public Map<AnswerOptionEntity, Long> getFinalResultForEachQuestion(String electionName, QuestionEntity question){
        List<AnswerOptionEntity> answerOptions = question.getAssociatedAnswerOption();
        Map<AnswerOptionEntity, Long> answerToQuestionVoteCount = new HashMap<>();
        Long mostVoteCount = 0L;
        AnswerOptionEntity mostVoteSelection = null;
        for(AnswerOptionEntity answerOptionEntity : answerOptions){
            Session session = HibernateUtil.getSession();
            try{
                session.beginTransaction();
                Integer voteCount = session.createQuery("From VoterChoiceEntity where answerOption_id = " + answerOptionEntity.getId(),
                        VoterChoiceEntity.class).list().size();
                answerToQuestionVoteCount.put(answerOptionEntity, (long) voteCount);
                /*
                pending for settling tie.
                 */
                session.getTransaction().commit();
            }catch(HibernateException exception){
                System.err.println("Encounter problems while counting votes " + exception);
                session.getTransaction().rollback();
            }
        }
        return answerToQuestionVoteCount;
    }
}
