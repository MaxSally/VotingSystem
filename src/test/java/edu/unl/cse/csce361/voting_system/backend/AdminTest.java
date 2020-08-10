package edu.unl.cse.csce361.voting_system.backend;

<<<<<<< HEAD
=======
import edu.unl.cse.csce361.testTemplate.TestTemplate;
>>>>>>> c2230c6f4b14f154144c654cd2114fce302d110c
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

<<<<<<< HEAD
public class AdminTest {

    Backend backend;
    List<Long> answerOptionIndex;

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
=======
public class AdminTest extends TestTemplate {
>>>>>>> c2230c6f4b14f154144c654cd2114fce302d110c

    @Test
    public void testAdminLogIn() {
        String username = "superuser 999";
        String password = "this is my password";

        Admin admin = Backend.getInstance().adminLogIn(username, password);
        assertNotNull(admin);
    }

    @Test
    public void testCreateAdminAccount() {
        String username = "chloe";
        String password = "testaccount";

        Admin admin = Backend.getInstance().registerAdminAccount(username, password);
        assertNotNull(admin);
    }

<<<<<<< HEAD
=======
    @Test
    public void getAllInactiveElection(){
        List<Election> elections = Backend.getInstance().getAllInactiveElections();
        int expectedSize = 1;
        String expectedName = "Nov2021";
        assertEquals(expectedSize, elections.size());
        assertTrue(elections.get(0).isAvailableForEdit());
        assertEquals(expectedName, elections.get(0).getElectionName());
    }

>>>>>>> c2230c6f4b14f154144c654cd2114fce302d110c
}
