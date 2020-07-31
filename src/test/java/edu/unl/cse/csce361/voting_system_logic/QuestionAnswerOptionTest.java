package edu.unl.cse.csce361.voting_system_logic;

import edu.unl.cse.csce361.voting_system.backend.*;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionAnswerOptionTest {
    DataLogic dataLogic;

    @Before
    public void setUp() {
        dataLogic = DataLogic.getInstance();
//        Session session = HibernateUtil.getSession();
//        session.beginTransaction();
//        DatabasePopulator.depopulateTables(session);
//        DatabasePopulator.createVoters().forEach(session::saveOrUpdate);
//        DatabasePopulator.createAdmin().forEach(session::saveOrUpdate);
//        DatabasePopulator.createElection().forEach(session::saveOrUpdate);
//        DatabasePopulator.createQuestion().forEach(session::saveOrUpdate);
//        DatabasePopulator.createAnswerOption().forEach(session::saveOrUpdate);
//        DatabasePopulator.createVoterChoice().forEach(session::saveOrUpdate);
//        session.getTransaction().commit();
    }

    @After
    public void tearDown() {
//        Session session = HibernateUtil.getSession();
//        session.beginTransaction();
//        DatabasePopulator.depopulateTables(session);
//        session.getTransaction().commit();
    }

    @Test
    public void testGetAllQuestionAndAnswers(){
        int expectedSize = 6;
        String firstQuestion = "Who is the next mayor?";
        String electionName = "Nov2020";
        String voterSSN = "83948032O";
        DataLogic.getInstance().setElection(electionName);
        DataLogic.getInstance().setCurrentVoter(voterSSN);
        List<QuestionAnswer> lstQA = DataLogic.getInstance().getAllQuestionsAndAnswers();
        assertTrue(lstQA.size() == expectedSize);
        assertEquals(firstQuestion, lstQA.get(0).getQuestionText());
    }


}
