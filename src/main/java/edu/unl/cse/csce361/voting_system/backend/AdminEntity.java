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
    public Map<QuestionEntity, Map<AnswerOptionEntity, Long>> getFinalResult(String electionName) {
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
                session.getTransaction().commit();
            }catch(HibernateException exception){
                System.err.println("Encounter problems while counting votes " + exception);
                session.getTransaction().rollback();
            }
        }
        return answerToQuestionVoteCount;
    }

    @Override
    public List<Map<String, String>> getAllVoterVoteResult(String electionName) {
        List<Map<String, String>> allVoterVoteResults = new ArrayList<>();
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            List<VoterEntity> voters = session.createQuery("SELECT voter From VoterEntity voter", VoterEntity.class).getResultList();
            session.getTransaction().commit();
            for(VoterEntity voterEntity : voters) {
                if(voterEntity.hasVoted(electionName))
                    allVoterVoteResults.add(voterEntity.getVoterVoteResult(electionName));
            }
        } catch (HibernateException exception) {
            System.err.println("Could not load all Voters " + exception.getMessage());
        }
        return allVoterVoteResults;
    }

    @Override
    public Map<String, List<String>> getFinalWinner(String electionName){
        Map<QuestionEntity, Map<AnswerOptionEntity, Long>> finalResult = getFinalResult(electionName);
        Map<String, List<String>> winners = new HashMap<>();
        for(Map.Entry<QuestionEntity, Map<AnswerOptionEntity, Long>> question: finalResult.entrySet()){
            List<String> winnerByQuestion = new ArrayList<>();
            Long maxVote = 0L;
            for(Map.Entry<AnswerOptionEntity, Long> answers: question.getValue().entrySet()){
                if(maxVote < answers.getValue()){
                    maxVote = answers.getValue();
                    winnerByQuestion.clear();
                    winnerByQuestion.add(answers.getKey().getAnswerText());
                }else if(maxVote == answers.getValue()){
                    winnerByQuestion.add(answers.getKey().getAnswerText());
                }
            }
            winners.put(question.getKey().getQuestionText(), winnerByQuestion);
        }
        return winners;
    }
}
