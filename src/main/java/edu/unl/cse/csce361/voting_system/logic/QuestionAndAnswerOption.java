package edu.unl.cse.csce361.voting_system.logic;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class QuestionAndAnswerOption implements QuestionAnswer {
    private String electionName;
    private String questionText;
    private List<Pair<String, Long>> answerOptions;

    public QuestionAndAnswerOption() {
    }

    public QuestionAndAnswerOption(String electionName, String questionText) {
        this.electionName = electionName;
        this.questionText = questionText;
        answerOptions = new ArrayList<>();
    }

    public String getElectionName() {
        return electionName;
    }

    public String getQuestionText() {
        return questionText;
    }

    public boolean isEqual(Object o) {
        if(o instanceof QuestionAndAnswerOption) { 
            return this.electionName.equals(((QuestionAndAnswerOption) o).getElectionName())
                    && this.questionText.equals(((QuestionAndAnswerOption) o).getQuestionText());
        }
        return false;
    }

    @Override
    public List<String> getAnswerText() {
        List<String> answerOptionsAsString = new ArrayList<>();
        for(Pair<String, Long> answerOption : answerOptions){
            answerOptionsAsString.add(answerOption.getKey());
        }
        return answerOptionsAsString;
    }

    @Override
    public List<Pair<String, Long>> getAnswerOptionWithId() {
        return answerOptions;
    }

    public void setAnswerOptions(List<Pair<String, Long>> answerOptions) {
        this.answerOptions = answerOptions;
    }
}
