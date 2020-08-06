package edu.unl.cse.csce361.voting_system.backend;

import javafx.util.Pair;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RemoveQuestionAnswerElectionTest {

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

    @Test
    public void testRemoveQuestionFromActiveElection() {
        String electionName = "Nov2020";
        String questionText = "Who is the next Sheriff?";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.removeQuestion(electionName, questionText);
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertFalse(result);
        assertTrue(questions.contains(questionText));
    }

    @Test
    public void testRemoveQuestionFromInactiveElection() {
        String electionName = "Nov2021";
        String questionText = "How are you doing?";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.removeQuestion(electionName, questionText);
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertTrue(result);
        assertTrue(questions.contains(questionText));
        assertFalse(QuestionEntity.getQuestionsByName(questionText, electionName).getStatus());
        for(AnswerOptionEntity answerOptionEntity : QuestionEntity.getQuestionsByName(questionText, electionName).getAssociatedAnswerOption()) {
            assertFalse(answerOptionEntity.getStatus());
        }
    }

    @Test
    public void testRemoveAnswerFromInactiveElection() {
        String electionName = "Nov2021";
        String questionText = "How are you doing?";
        String answerText = "Meh";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.removeAnswer(QuestionEntity.getQuestionsByName(questionText, electionName), answerText);
        List<AnswerOptionEntity> answers = QuestionEntity.getQuestionsByName(questionText, electionName).getAssociatedAnswerOption();
        boolean answerTextStatus = false;
        for(AnswerOptionEntity answerOption : answers) {
            if(answerOption.getAnswerText().equals(answerText)) {
                answerTextStatus = answerOption.getStatus();
            }
        }
        assertTrue(result);
        assertFalse(answerTextStatus);
    }

    @Test
    public void testRemoveAnswerFromActiveElection() {
        String electionName = "Nov2020";
        String questionText = "Who is the next mayor?";
        String answerText = "Pat Mann";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.removeAnswer(QuestionEntity.getQuestionsByName(questionText, electionName), answerText);
        List<AnswerOptionEntity> answers = QuestionEntity.getQuestionsByName(questionText, electionName).getAssociatedAnswerOption();
        boolean answerTextStatus = false;
        for(AnswerOptionEntity answerOption : answers) {
            if(answerOption.getAnswerText().equals(answerText)) {
                answerTextStatus = answerOption.getStatus();
            }
        }
        assertFalse(result);
        assertTrue(answerTextStatus);
    }

}
