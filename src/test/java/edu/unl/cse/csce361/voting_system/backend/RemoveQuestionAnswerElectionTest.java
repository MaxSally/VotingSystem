package edu.unl.cse.csce361.voting_system.backend;

import edu.unl.cse.csce361.testTemplate.TestTemplate;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RemoveQuestionAnswerElectionTest extends TestTemplate {

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
        for(AnswerOption answerOptionEntity : QuestionEntity.getQuestionsByName(questionText, electionName).getAssociatedAnswerOption()) {
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
        List<AnswerOption> answers = QuestionEntity.getQuestionsByName(questionText, electionName).getAssociatedAnswerOption();
        boolean answerTextStatus = false;
        for(AnswerOption answerOption : answers) {
            if(answerOption.getAnswerText().equals(answerText)) {
                answerTextStatus = answerOption.getStatus();
            }
        }
        assertTrue(result);
        assertFalse(answerTextStatus);

        //Extra
        DataLogic.getInstance().setCurrentElection(electionName);
        List<QuestionAnswer> questionAnswers = DataLogic.getInstance().getAllQuestionsAndAnswers();
        for(QuestionAnswer questionAnswer : questionAnswers){
            System.out.println(questionAnswer.getQuestionText());
            for(String answerOptions : questionAnswer.getAnswerText()){
                System.out.println(answerOptions);
            }
        }
    }

    @Test
    public void testRemoveAnswerFromActiveElection() {
        String electionName = "Nov2020";
        String questionText = "Who is the next mayor?";
        String answerText = "Pat Mann";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.removeAnswer(QuestionEntity.getQuestionsByName(questionText, electionName), answerText);
        List<AnswerOption> answers = QuestionEntity.getQuestionsByName(questionText, electionName).getAssociatedAnswerOption();
        boolean answerTextStatus = false;
        for(AnswerOption answerOption : answers) {
            if(answerOption.getAnswerText().equals(answerText)) {
                answerTextStatus = answerOption.getStatus();
            }
        }
        assertFalse(result);
        assertTrue(answerTextStatus);
    }

    @Test
    public void testRemoveInactiveElection() {
        String electionName = "Nov2021";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.removeElection(electionName);
        assertTrue(result);
        assertFalse(ElectionEntity.getElectionByName(electionName).isAvailableForEdit());
    }

    @Test
    public void testRemoveActiveElection() {
        String electionName = "Nov2020";
        ElectionOfficial admin = new ElectionOfficialEntity("test", "12345");
        boolean result = admin.removeElection(electionName);
        assertFalse(result);
        assertFalse(ElectionEntity.getElectionByName(electionName).isRemoved());
    }
}
