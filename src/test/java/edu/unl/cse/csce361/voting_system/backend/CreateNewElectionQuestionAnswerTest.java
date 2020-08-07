package edu.unl.cse.csce361.voting_system.backend;

import edu.unl.cse.csce361.testTemplate.TestTemplate;
import javafx.util.Pair;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class CreateNewElectionQuestionAnswerTest extends TestTemplate {

    @Test
    public void testAddQuestionToActiveElection() {
        String electionName = "Nov2020";
        String questionText = "What is your name?";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.createQuestion(electionName, questionText);
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertFalse(result);
        assertFalse(questions.contains(questionText));
    }

    @Test
    public void testAddQuestionToInactiveElection() {
        String electionName = "Nov2021";
        String questionText = "What is your name?";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.createQuestion(electionName, questionText);
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertTrue(result);
        assertTrue(questions.contains(questionText));
    }

    @Test
    public void testAddAnswerToActiveElection() {
        String questionText = "Who is the next mayor?";
        String electionName = "Nov2020";
        Question question = QuestionEntity.getQuestionsByName(questionText, electionName);
        String answerText = "Chloe Galinsky";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.createAnswer(question, answerText);
        List<Pair<String, Long>> answers = Backend.getInstance().getAllAnswersByQuestion(questionText, electionName);
        boolean hasAnswerText = false;
        for(Pair<String, Long> answerOption : answers) {
            if(answerOption.getKey().equals(answerText)) {
                hasAnswerText = true;
            }
        }
        assertFalse(result);
        assertFalse(hasAnswerText);
    }

    @Test
    public void testAddAnswerToInactiveElection() {
        String electionName = "Nov2021";
        String questionText = "How are you doing?";
        Question question = QuestionEntity.getQuestionsByName(questionText, electionName);
        String answerText = "Great";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.createAnswer(question, answerText);
        List<Pair<String, Long>> answers = Backend.getInstance().getAllAnswersByQuestion(questionText, electionName);
        boolean hasAnswerText = false;
        for(Pair<String, Long> answerOption : answers) {
            if(answerOption.getKey().equals(answerText)) {
                hasAnswerText = true;
            }
        }
        assertTrue(result);
        assertTrue(hasAnswerText);
    }

    @Test
    public void testCreateElectionAlreadyExists() {
        String electionName = "Nov2020";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        LocalDate startTime = LocalDate.of(2020,11,10);
        LocalDate endTime = LocalDate.of(2020,11,11);
        boolean result = admin.createElection(electionName, startTime, endTime, false);
        assertFalse(result);
    }

    @Test
    public void testCreateNewElection() {
        String electionName = "Nov2022";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        LocalDate startTime = LocalDate.of(2022,11,10);
        LocalDate endTime = LocalDate.of(2022,11,11);
        boolean result = admin.createElection(electionName, startTime, endTime, false);
        assertTrue(result);
    }
}
