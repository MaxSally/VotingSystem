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

import static edu.unl.cse.csce361.voting_system.backend.AnswerOptionEntity.ABSTAIN_VOTE;


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
        return this.password.equals(password);
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
    public Map<String, Map<String, Long>> getFinalResult(String electionName) {
        List<QuestionEntity> questions = ElectionEntity.getElectionByName(electionName).getAssociatedQuestions();
        Map<String, Map<String, Long>> finalResult = new HashMap<>();
        for(QuestionEntity question : questions){
            //can be simplified to one line but hard to debug
            Map<String , Long> finalResultEachQuestion = getFinalResultForEachQuestion(electionName, question);
            finalResult.put(question.getQuestionText(), finalResultEachQuestion);
        }
        return finalResult;
    }

    public Map<String, Long> getFinalResultForEachQuestion(String electionName, QuestionEntity question){
        List<AnswerOptionEntity> answerOptions = question.getAssociatedAnswerOption();
        Map<String, Long> answerToQuestionVoteCount = new HashMap<>();
        for(AnswerOptionEntity answerOptionEntity : answerOptions){
            Session session = HibernateUtil.getSession();
            try{
                session.beginTransaction();
                int voteCount = session.createQuery("From VoterChoiceEntity where answerOption_id = " + answerOptionEntity.getId(),
                        VoterChoiceEntity.class).list().size();
                answerToQuestionVoteCount.put(answerOptionEntity.getAnswerText(), (long) voteCount);
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
        Map<String, Map<String, Long>> finalResult = getFinalResult(electionName);
        Map<String, List<String>> winners = new HashMap<>();
        for(Map.Entry<String, Map<String, Long>> question: finalResult.entrySet()){
            List<String> winnerByQuestion = new ArrayList<>();
            Long maxVote = 0L;
            for(Map.Entry<String, Long> answers: question.getValue().entrySet()){
                if(answers.getKey().equals(ABSTAIN_VOTE)){
                    continue;
                }
                if(maxVote < answers.getValue()){
                    maxVote = answers.getValue();
                    winnerByQuestion.clear();
                    winnerByQuestion.add(answers.getKey());
                }else if(maxVote.equals(answers.getValue())){
                    winnerByQuestion.add(answers.getKey());
                }
            }
            winners.put(question.getKey(), winnerByQuestion);
        }
        return winners;
    }
}