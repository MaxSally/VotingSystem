package edu.unl.cse.csce361.voting_system.backend;

import edu.unl.cse.csce361.testTemplate.TestTemplate;
import javafx.util.Pair;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class VoterTest extends TestTemplate {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testLogIn() {
        String name = "A";
        String ssn = "25f9e794323b453885f5181f1b624d0b";

        Voter voter = Backend.getInstance().voterLogIn(name, ssn);
        assertNotNull(voter);
    }

    @Test
    public void testLogInSSN11() {
        String name = "A";
        String ssn = "25f9e794323b453885f5181f1b624d489";

        Voter voter = Backend.getInstance().voterLogIn(name, ssn);
        assertNull(voter);
    }

    @Test
    public void testRegisterVoter() {
        String name = "Chloe";
        String ssn = "28f673f31cdd6af50d1f0b8e2b71b9e5";//121212121

        Voter voter = Backend.getInstance().registerToVote(name, ssn);
        assertEquals(voter.getName(), name);
    }

    @Test
    public void testGetAllQuestionsByElection() {
        String electionName = "Nov2020";
        String expectedQuestion1 = "Who is the next mayor?";
        String expectedQuestion2 = "Who is the next city council?";
        String expectedQuestion3 = "Who is the next Sheriff?";
        int expectedSize = 6;
        
        List<String> questions = Backend.getInstance().getAllQuestionsByElection(electionName);
        assertEquals(questions.size(), expectedSize);
        assertEquals(expectedQuestion1, questions.get(0));
        assertEquals(expectedQuestion2, questions.get(1));
        assertEquals(expectedQuestion3, questions.get(2));
    }

    @Test
    public void testGetAllAnswersByQuestion() {
        String questionName = "Who is the next mayor?";
        String expectedAnswer1 = "Pat Mann";
        String expectedAnswer2 = "Dawn Keykong";
        String electionName = "Nov2020";
        int expectedSize = 3;

        List<Pair<String, Long>> answers = Backend.getInstance().getAllAnswersByQuestion(questionName, electionName);
        assertEquals(answers.size(), expectedSize);
        boolean expectedAnswer1Exists = false;
        boolean expectedAnswer2Exists = false;
        for(Pair<String, Long> answer : answers){
            if(answer.getKey().equals(expectedAnswer1)){
                expectedAnswer1Exists = true;
            }else if(answer.getKey().equals(expectedAnswer2)){
                expectedAnswer2Exists = true;
            }
        }
        assertTrue(expectedAnswer1Exists);
        assertTrue(expectedAnswer2Exists);
    }

    @Test
    public void testGetAnswerIndex() {
        String questionText = "Shall there be a 25¢ tax on cherries?";
        String answerText = "No";
        Long expectedID = getAnswerOptionIndex().get(3);
        Long actualID = AnswerOptionEntity.getAnswerOptionIndexByName(questionText, answerText);
        assertSame(expectedID, actualID);
    }

    @Test
    public void testGetVoterSelection() {
        String voterSSN = "b24454431f08deb4d5ee6747bd55f3be";
        Voter voter = VoterEntity.getVoterBySSN(voterSSN);
        String electionName = "Nov2020";
        String question = "Who is the next mayor?";
        Map<String, String> result = Backend.getInstance().getVoterVoteResult(voter, electionName);
        assertTrue(result.containsKey(question));
    }

    @Test
    public void testGetAllVoterStatus() {
        String electionName = "Nov2020";
        String voter1Name = "A";
        String voter2Name = "B";
        String voter3Name = "C";
        Admin adminEntity = new AdminEntity();
        Map<String, String> result = adminEntity.getAllVoterStatus(electionName);
        assertTrue(result.containsKey(voter1Name));
        assertTrue(result.containsKey(voter2Name));
        assertTrue(result.containsKey(voter3Name));
    }
}
