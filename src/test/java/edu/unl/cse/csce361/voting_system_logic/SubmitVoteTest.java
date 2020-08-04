package edu.unl.cse.csce361.voting_system_logic;

import edu.unl.cse.csce361.voting_system.backend.*;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;
import javafx.util.Pair;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SubmitVoteTest {
    DataLogic dataLogic;
    List<Long> answerOptionIndex;

    @Before
    public void setUp() {
        dataLogic = DataLogic.getInstance();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.depopulateTables(session);
        DatabasePopulator.createVoters().forEach(session::saveOrUpdate);
        DatabasePopulator.createAdmin().forEach(session::saveOrUpdate);
        DatabasePopulator.createElection().forEach(session::saveOrUpdate);
        DatabasePopulator.createQuestion().forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        List<AnswerOption> answerOptions = DatabasePopulator.createAnswerOption();
        session.beginTransaction();
        for(AnswerOption answerOption: answerOptions){
            session.saveOrUpdate(answerOption);
        }
        session.getTransaction().commit();
        answerOptionIndex = DatabasePopulator.getAnswerOptionIndex();
        session.beginTransaction();
        DatabasePopulator.createVoterChoice(answerOptionIndex).forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        DatabasePopulator.setVoterStatus();
    }

    @After
    public void tearDown() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.depopulateTables(session);
        session.getTransaction().commit();
    }

    @Test
    public void submitVote(){
        String electionName = "Nov2020";
        String voterSSN = "83948032O";
        DataLogic.getInstance().setElection(electionName);
        DataLogic.getInstance().setCurrentVoter(voterSSN);
        Map<String, String> userSelections = new HashMap<>();
        userSelections.put("Who is the next mayor?", "Pat Mann");
        userSelections.put("Who is the next city council?", "Inky");
        userSelections.put("Shall there be a 25¢ tax on cherries?", "No");
        userSelections.put("Shall liquor licenses be required for electronic bars?", "Yes");
        userSelections.put("Shall electronic race tracks be held liable for electronic car crashes?", "No");
        boolean success = DataLogic.getInstance().submitVote(userSelections);
        assertTrue(success);
    }
}
