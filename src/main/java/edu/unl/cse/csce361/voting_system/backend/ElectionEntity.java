package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class ElectionEntity implements Election{

    public static  final int MAXIMUM_NAME_LENGTH = 20;
    @Id
    @GeneratedValue
    private Long electionId;

    @NaturalId
    @Column(length = MAXIMUM_NAME_LENGTH)
    private String name;

    @Column
    private LocalDate startTime;

    @Column
    private LocalDate endTime;

    @Column
    private boolean status;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL)
    private Set<QuestionEntity> questions;

    public ElectionEntity(String name, LocalDate startTime, LocalDate endTime, boolean status) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        questions = new HashSet<>();
    }

    public ElectionEntity() {
    }


    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public List<Question> getAssociatedQuestions() {
        return null;
    }

    public void addElection(Question question){
        if (question instanceof QuestionEntity) {
            QuestionEntity questionEntity = (QuestionEntity) question;
            questions.add(questionEntity);
            questionEntity.setElection(this);
        } else {
            throw new IllegalArgumentException("Expected question, got " + question.getClass().getSimpleName());
        }
    }
}
