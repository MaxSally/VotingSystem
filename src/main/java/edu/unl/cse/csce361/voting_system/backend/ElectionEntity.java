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
    @Column(name = "ID")
    private Long electionId;

    @NaturalId (mutable = true)
    @Column(length = MAXIMUM_NAME_LENGTH)
    private String name;

    @Column
    private LocalDate startTime;

    @Column
    private LocalDate endTime;

    @Column
    private boolean status;

    @Column
    private boolean isRemoved;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;

    @ManyToMany(mappedBy = "electionVotedIn", cascade = CascadeType.ALL)
    private Set<VoterEntity> voters;

    public ElectionEntity(String name, LocalDate startTime, LocalDate endTime, boolean status, boolean isRemoved) {
        this.name = name;
        if(startTime.isBefore(endTime)) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
        this.status = status;
        this.isRemoved = isRemoved;
        questions = new ArrayList<>();
        voters = new HashSet<>();
    }

    public ElectionEntity() {
    }

    @Override
    public String getElectionName() {
        return name;
    }

    public Set<VoterEntity> getVoters() {
        return voters;
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

    @Override
    public String getName(){
        return name;
    }

    @Override
    public void addVoter(VoterEntity voter){
        voters.add(voter);
        Session session = HibernateUtil.getSession();
        try{
            session.beginTransaction();
            session.saveOrUpdate(this);
            session.getTransaction().commit();
        } catch (HibernateException exception){
            System.err.println("Encounter hibernate exception while adding voter to election: " + exception);
            session.getTransaction().rollback();
        }
    }

    @Override
    public boolean getAvailability() {
        return status || isRemoved;
    }

    @Override
    public void setElectionName(String updatedElectionName) {
        name = updatedElectionName;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }
}
