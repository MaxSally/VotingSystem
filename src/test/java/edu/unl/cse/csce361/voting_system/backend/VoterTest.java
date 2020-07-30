package edu.unl.cse.csce361.voting_system.backend;

import edu.unl.cse.csce361.voting_system.backend.Backend;
import edu.unl.cse.csce361.voting_system.backend.DatabasePopulator;
import edu.unl.cse.csce361.voting_system.backend.HibernateUtil;
import edu.unl.cse.csce361.voting_system.backend.Voter;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class VoterTest {

    Backend backend;

    @Before
    public void setUp() {
        backend = Backend.getInstance();
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
    public void testLogIn() {
        String name = "A";
        String ssn = "123456789";

         boolean logInResult = Backend.getInstance().logIn(name, ssn);
         assertTrue(logInResult);
    }

    @Test
    public void testLogInSSN11() {
        String name = "A";
        String ssn = "12345678901";

        boolean logInResult = Backend.getInstance().logIn(name, ssn);
        assertFalse(logInResult);
    }

    @Test
    public void testRegisterVoter() {
        String name = "Chloe";
        String ssn = "121212121";

        Voter voter = Backend.getInstance().registerToVote(name, ssn);
        assertEquals(voter.getName(), name);
    }

    @Test
    public void testRegisterVoterInvalidSSN() {
        String name = "Max";
        String ssn = "12121212100";

        Voter voter = Backend.getInstance().registerToVote(name, ssn);
        assertNull(voter);
    }

    @Test
    public void testGetAllQuestionsByElection() {
        String electionName = "Nov2020";
        String expectedQuestion1 = "Who is the next mayor?";
        String expectedQuestion2 = "Who is the next city council?";
        String expectedQuestion3 = "Who is the next Sheriff?";
        int expectedSize = 6;
        
        List<QuestionEntity> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertTrue(questions.size() == expectedSize);
        assertEquals(questions.get(0).getQuestionText(), expectedQuestion1);
        assertEquals(questions.get(1).getQuestionText(), expectedQuestion2);
        assertEquals(questions.get(2).getQuestionText(), expectedQuestion3);
    }
}
