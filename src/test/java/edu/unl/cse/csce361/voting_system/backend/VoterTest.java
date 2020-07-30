package edu.unl.cse.csce361.voting_system.backend;

import edu.unl.cse.csce361.voting_system.backend.Backend;
import edu.unl.cse.csce361.voting_system.backend.DatabasePopulator;
import edu.unl.cse.csce361.voting_system.backend.HibernateUtil;
import edu.unl.cse.csce361.voting_system.backend.Voter;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VoterTest {

    Backend backend;

    @Before
    public void setUp() {
        backend = Backend.getInstance();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.depopulateTables(session);
        DatabasePopulator.createVoters().forEach(session::saveOrUpdate);
        DatabasePopulator.createAdmin().forEach(session::saveOrUpdate);
        DatabasePopulator.createElection().forEach(session::saveOrUpdate);
        DatabasePopulator.createQuestion().forEach(session::saveOrUpdate);
        DatabasePopulator.createAnswerOption().forEach(session::saveOrUpdate);
        DatabasePopulator.createVoterChoice().forEach(session::saveOrUpdate);
        session.getTransaction().commit();
    }

    @After
    public void tearDown() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.depopulateTables(session);
        session.getTransaction().commit();
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
}
