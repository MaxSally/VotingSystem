package edu.unl.cse.csce361.voting_system.backend;

import edu.unl.cse.csce361.testTemplate.TestTemplate;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class FinalResultTest extends TestTemplate {

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

    @Test
    public void testGetFinalWinner(){
        String electionName = "Nov2020";
        Admin admin = new AdminEntity("hello", "idk");
        Map<String, List<String>> winners = admin.getFinalWinner(electionName);
        for(Map.Entry<String, List<String>> winnerByQuestion : winners.entrySet()){
            System.out.println(winnerByQuestion.getKey());
            for(String answers : winnerByQuestion.getValue()){
                System.out.println(answers);
            }
        }
    }
}
