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
public class ElectionEntity implements Election {

    static ElectionEntity getElectionByName(String electionName) {
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

    static List<Election> getAllInactiveElection() {
        List<Election> inactiveElections = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            List<ElectionEntity> allElections= session.createQuery("SELECT election From ElectionEntity election", ElectionEntity.class).getResultList();
            session.getTransaction().commit();
            for(ElectionEntity election: allElections) {
                if(election.isAvailableForEdit()){
                    inactiveElections.add(election);
                }
            }
        } catch (HibernateException exception){
            System.err.println("Could not load all elections " + exception.getMessage());
            session.getTransaction().rollback();
        }
        return inactiveElections;
    }
    
    static List<Election> getAllInProgressElection() {
        List<Election> inProgressElections = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            List<ElectionEntity> allElections= session.createQuery("SELECT election From ElectionEntity election", ElectionEntity.class).getResultList();
            session.getTransaction().commit();
            for(ElectionEntity election: allElections) {
                if(election.getStatus().equals(VOTING_PHASE) && !election.isRemoved()) {
                	inProgressElections.add(election);
                }
            }
        } catch (HibernateException exception) {
            System.err.println("Could not load all elections " + exception.getMessage());
            session.getTransaction().rollback();
        }
        return inProgressElections;
    }

    static List<Election> getAllElection() {
        List<Election> elections = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            List<ElectionEntity> allElections = session.createQuery("SELECT election From ElectionEntity election", ElectionEntity.class).getResultList();
            session.getTransaction().commit();
            for(ElectionEntity electionEntity : allElections) {
                elections.add(electionEntity);
            }
        } catch (HibernateException exception) {
            System.err.println("Could not load all elections " + exception.getMessage());
            session.getTransaction().rollback();
        }
        return elections;
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
    private String status;

    @Column
    private boolean isRemoved;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;

    @ManyToMany(mappedBy = "electionVotedIn", cascade = CascadeType.ALL)
    private Set<VoterEntity> voters;

    public ElectionEntity(String name, LocalDate startTime, LocalDate endTime) {
        this.name = name;
        if(startTime.isBefore(endTime)) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
        status = PREPARE_PHASE;
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
    public List<Question> getAssociatedQuestions() {
        List<Question> availableQuestion = new ArrayList<>();
        for(Question question: questions){
            if(question.getStatus()){
                availableQuestion.add(question);
            }
        }
        return availableQuestion;
    }

    public void addElection(Question question) {
        if (question instanceof QuestionEntity) {
            QuestionEntity questionEntity = (QuestionEntity) question;
            questions.add(questionEntity);
            questionEntity.setElection(this);
        } 
        else {
            throw new IllegalArgumentException("Expected question, got " + question.getClass().getSimpleName());
        }
    }

    @Override
    public void addVoter(VoterEntity voter) {
        voters.add(voter);
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(this);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Encounter hibernate exception while adding voter to election: " + exception);
            session.getTransaction().rollback();
        }
    }

    @Override
    public boolean isAvailableForEdit() {
        return status.equals(PREPARE_PHASE) && !isRemoved;
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
    
    public String getStatus() {
    	return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public LocalDate getStartTime() {
        return startTime;
    }

    @Override
    public LocalDate getEndTime() {
        return endTime;
    }
    
    @Override
    public boolean isInProgress() {
    	return status == VOTING_PHASE;
    }
}
