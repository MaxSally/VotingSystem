package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ElectionEntity implements Election{

    @Id
    @GeneratedValue
    private Long electionId;

    @NaturalId
    private String name;

    @Column
    private LocalDate startTime;

    @Column
    private LocalDate endTime;

    @Column
    private boolean status;

    public ElectionEntity(String name, Long electionId, LocalDate startTime, LocalDate endTime, boolean status) {
        this.name = name;
        this.electionId = electionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
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
}
