package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;

@Entity
public class AdminEntity implements  Admin{

    @Id
    @GeneratedValue
    private Long id;

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
    public boolean logIn(String username, String password) {
        return true;
    }

    @Override
    public String getFinalResult() {
        return null;
    }

    @Override
    public boolean createQuestion(String questionText, List<String> answerOption) {
        return false;
    }

    @Override
    public boolean updateAnswerOption(Question question, List<String> answerOption) {
        return false;
    }

    @Override
    public boolean removeAnswerOption(Question question, AnswerOption answerOption) {
        return false;
    }

    @Override
    public boolean updateQuestion(Question question, String newQuestionText) {
        return false;
    }

    public Map<String, String> getAllVoterStatus(String electionName){
        Session session = HibernateUtil.getSession();
        Map<String, String> voterStatus = new HashMap<>();
        List<VoterEntity> voters = new ArrayList<>();
        try {
            session.beginTransaction();
            voters = session.createNativeQuery("Select * From VoterEntity;").getResultList();
            session.getTransaction().commit();
            for(VoterEntity voterEntity : voters) {
                voterStatus.put(voterEntity.getName(), (voterEntity.hasVoted(electionName)?"yes" : "no"));
            }
        } catch (HibernateException exception) {
            System.err.println("Could not load all Voters " + exception.getMessage());
        }
        return voterStatus;
    }
}
