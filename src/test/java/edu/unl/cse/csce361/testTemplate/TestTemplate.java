package edu.unl.cse.csce361.testTemplate;

import edu.unl.cse.csce361.voting_system.backend.*;
import org.hibernate.Session;
import org.hsqldb.Database;
import org.junit.After;
import org.junit.Before;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Set;

public class TestTemplate {

    List<Long> answerOptionIndex;

    @Before
    public void setUp() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.depopulateTables(session);
        session.getTransaction().commit();
        session.beginTransaction();
        DatabasePopulator.createVoters().forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        session.beginTransaction();
        DatabasePopulator.createAdmin().forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        session.beginTransaction();
        DatabasePopulator.createElectionOfficial().forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        Set<Election> elections = DatabasePopulator.createElection();
        session.beginTransaction();
        elections.forEach(session::saveOrUpdate);
        session.getTransaction().commit();
        List<Question> questions = DatabasePopulator.createQuestion();
        session.beginTransaction();
        questions.forEach(session::saveOrUpdate);
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
        Backend.getInstance().startElection(new ElectionOfficialEntity("Hello", "hooy"), "Nov2020");
    }

    @After
    public void tearDown() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.depopulateTables(session);
        session.getTransaction().commit();
    }

    public List<Long> getAnswerOptionIndex() {
        return answerOptionIndex;
    }
}
