package edu.unl.cse.csce361.voting_system_logic;

import edu.unl.cse.csce361.testTemplate.TestTemplate;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class GetAllQuestionAndAnswerOptionTest extends TestTemplate {

    @Test
    public void testGetAllQuestionAndAnswers() {
        int expectedSize = 6;
        String electionName = "Nov2020";
        String voterSSN = "83948032O";
        DataLogic.getInstance().setCurrentElection(electionName);
        DataLogic.getInstance().setCurrentVoter(voterSSN);
        List<QuestionAnswer> lstQA = DataLogic.getInstance().getAllQuestionsAndAnswers();
        assertEquals(expectedSize, lstQA.size());
    }
}
