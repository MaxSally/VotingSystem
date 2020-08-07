package edu.unl.cse.csce361.voting_system_logic;

import edu.unl.cse.csce361.testTemplate.TestTemplate;
import edu.unl.cse.csce361.voting_system.backend.*;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;
import javafx.util.Pair;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GetAllQuestionAndAnswerOptionTest extends TestTemplate {

    @Test
    public void testGetAllQuestionAndAnswers(){
        int expectedSize = 6;
        String firstQuestion = "Who is the next mayor?";
        String electionName = "Nov2020";
        String voterSSN = "83948032O";
        DataLogic.getInstance().setElection(electionName);
        DataLogic.getInstance().setCurrentVoter(voterSSN);
        List<QuestionAnswer> lstQA = DataLogic.getInstance().getAllQuestionsAndAnswers();
        assertEquals(expectedSize, lstQA.size());
        assertEquals(firstQuestion, lstQA.get(0).getQuestionText());
        for(QuestionAnswer questionAnswer : lstQA){
            System.out.println(questionAnswer.getQuestionText());
            for(String answer : questionAnswer.getAnswerText()){
                System.out.println(answer);
            }
        }
    }
}
