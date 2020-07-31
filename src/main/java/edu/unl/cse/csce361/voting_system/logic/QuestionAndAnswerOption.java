package edu.unl.cse.csce361.voting_system.logic;

public class QuestionAndAnswerOption {
    private String electionName;
    private String questionText;
    private String answerOptionText;
    private int answerOptionId;

    public QuestionAndAnswerOption() {
    }

    public QuestionAndAnswerOption(String electionName, String questionText, String answerOptionText, int answerOptionId) {
        this.electionName = electionName;
        this.questionText = questionText;
        this.answerOptionText = answerOptionText;
        this.answerOptionId = answerOptionId;
    }

    @Override
    public String toString() {
        return "AnswerOptionChoice{" +
                "electionName='" + electionName + '\'' +
                ", questionText='" + questionText + '\'' +
                ", answerOptionText='" + answerOptionText + '\'' +
                '}';
    }

    public String getElectionName() {
        return electionName;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswerOptionText() {
        return answerOptionText;
    }

    public int getAnswerOptionId() {
        return answerOptionId;
    }

    public boolean isEqual(Object o){
        if(o instanceof QuestionAndAnswerOption){
            return this.electionName.equals(((QuestionAndAnswerOption) o).getElectionName())
                    && this.questionText.equals(((QuestionAndAnswerOption) o).getQuestionText())
                    && this.answerOptionText.equals(((QuestionAndAnswerOption) o).getAnswerOptionText());
        }
        return false;
    }
}
