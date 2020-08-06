package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;

public class FinalResultTest {
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

    /*

    This test does not have assert since it is easier right now to look at the result.
     */
    @Test
    public void testGetAllFinalResult(){
        String electionName = "Nov2020";
        Admin admin = new AdminEntity("hello", "idk");
        Map<String, Map<String, Long>> finalResult = Backend.getInstance().getFinalResult(admin, electionName);
        for(Map.Entry<String, Map<String, Long>> questions : finalResult.entrySet()){
            System.out.println("Question: " + questions.getKey());
            for(Map.Entry<String, Long> answers : questions.getValue().entrySet()){
                System.out.format("Answer: %s: %d\n", answers.getKey(), answers.getValue());
            }
        }
    }

    @Test
    public void testGetAllVoterVoteResult(){
        String electionName = "Nov2020";
        Admin admin = new AdminEntity("hello", "idk");
        List<Map<String, String>> allVoterResult = admin.getAllVoterVoteResult(electionName);
        for(Map<String, String> result : allVoterResult){
            for(Map.Entry<String, String> choice: result.entrySet()){
                System.out.println(choice.getKey() + " " + choice.getValue());
            }
        }
    }
}
