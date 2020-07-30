package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class ElectionEntity implements Election{

    public static  final int MAXIMUM_NAME_LENGTH = 20;

    static ElectionEntity getElectionByName(String electionName){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        ElectionEntity election = null;
        try {
            election = session.bySimpleNaturalId(ElectionEntity.class).load(electionName);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load Voter " + electionName + ". " + exception.getMessage());
        }
        return election;
    }

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
    private List<QuestionEntity> questions;

    public ElectionEntity(String name, LocalDate startTime, LocalDate endTime, boolean status) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        questions = new ArrayList<>();
    }

    public ElectionEntity() {
    }

    @Override
    public String getElectionName() {
        return name;
    }

    @Override
    public List<QuestionEntity> getAssociatedQuestions() {
        return questions;
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
