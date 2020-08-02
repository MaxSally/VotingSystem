package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

@Entity
public class VoterEntity implements Voter {

    public static int REQUIRED_SSN_LENGTH= 9;

    static VoterEntity getVoterBySSN(String ssn){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        VoterEntity voter = null;
        try {
            voter = session.bySimpleNaturalId(VoterEntity.class).load(ssn);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load Voter " + ssn + ". " + exception.getMessage());
        }
        return voter;
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long voterId;

    @NaturalId
    @Column(unique = true)
    private String SSN;
    @Column
    private String name;
    @Column
    private boolean hasVoted;

    @OneToMany(mappedBy = "voter", cascade = CascadeType.ALL)
    private List<VoterChoiceEntity> voterChoices;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="electionVotedIn", joinColumns={@JoinColumn(referencedColumnName="ID")}
            , inverseJoinColumns={@JoinColumn(referencedColumnName="ID")})
    private Set<ElectionEntity> electionVotedIn;

    public VoterEntity() {
        super();
    }

    public VoterEntity(String name, String SSN) {
        if(validateSSN(SSN)) {
            this.SSN= SSN;
        } else {
            this.SSN = "";
        }
        this.name = name;
        voterChoices = new ArrayList<>();
        electionVotedIn = new HashSet<>();
        hasVoted = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean logIn(String name, String ssn) {
        VoterEntity voterEntity = null;
        try {
            voterEntity = HibernateUtil.getSession().bySimpleNaturalId(VoterEntity.class).load(ssn);
        } catch (Exception e) {
            System.err.println("Error while loading Voter: either the required Java class is not a mapped entity\n" +
                    "    (unlikely), or the entity does not have a simple natural ID (also unlikely).");
            System.err.println("  " + e.getMessage());
            System.err.println("Please inform the the developer that the error occurred in\n" +
                    "    VoterEntity.logIn(String, String).");
            voterEntity = null;
            System.err.println("Resuming, leaving " + this.toString() + " without an assigned Election.");
            return false;
        }
        return voterEntity != null && voterEntity.getName().equals(name);
    }

    @Override
    public boolean vote(Long answerOptionIndex) {
        Session session = HibernateUtil.getSession();
        try{
            session.beginTransaction();
            VoterChoiceEntity newVote = new VoterChoiceEntity(SSN, answerOptionIndex);
            session.saveOrUpdate(newVote);
            session.getTransaction().commit();
            return true;
        }catch (HibernateException exception){
            System.err.println("Encounter exception while accessing db. " + exception);
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean hasVoted(String electionName) {
        return electionVotedIn.contains(ElectionEntity.getElectionByName(electionName));
    }

    @Override
    public Map<String, String> getPastVotingDescription(String electionName) {
        Set<QuestionEntity> questions = new HashSet<>();
        questions.addAll(ElectionEntity.getElectionByName(electionName).getAssociatedQuestions());

        Map<String, String> voterSelections = new HashMap<>();
        for(VoterChoiceEntity voterChoiceEntity : voterChoices) {
            if(questions.contains(voterChoiceEntity.getAnswerOption().getQuestion())) {
                voterSelections.put(voterChoiceEntity.getAnswerOption().getQuestion().getQuestionText(), voterChoiceEntity.getAnswerOption().getAnswerText());
            }
        }

        for(QuestionEntity questionEntity : questions) {
            if(!voterSelections.containsKey(questionEntity.getQuestionText())) {
                voterSelections.put(questionEntity.getQuestionText(), AnswerOptionEntity.NO_VOTE);
            }
        }

        return voterSelections;
    }

    public void addVoter(VoterChoice voterChoice){
        if(voterChoice instanceof VoterChoiceEntity){
            VoterChoiceEntity voterChoiceEntity = (VoterChoiceEntity) voterChoice;
            voterChoices.add(voterChoiceEntity);
            voterChoiceEntity.setVoter(this);
        }else {
            throw new IllegalArgumentException("Expected VoterChoice, got " + voterChoice.getClass().getSimpleName());
        }
    }

    static boolean validateSSN(String ssn) {
        if(ssn.length() != REQUIRED_SSN_LENGTH) {
            throw new IllegalArgumentException("Invalid SSN length. The required length is " + REQUIRED_SSN_LENGTH);
        } else {
            return true;
        }
    }

    @Override
    public void setVoterStatus(String electionName) {
        electionVotedIn.add(ElectionEntity.getElectionByName(electionName));
        Session session = HibernateUtil.getSession();
        try{
            session.beginTransaction();
            session.saveOrUpdate(this);
            session.getTransaction().commit();
        } catch (HibernateException exception){
            System.err.println("Encounter hibernate exception while setting voter status: " + exception);
            session.getTransaction().rollback();
        }
        ElectionEntity.getElectionByName(electionName).addVoter(this);
    }

    @Override
    public void addVotedElection(String electionName) {
        electionVotedIn.add(ElectionEntity.getElectionByName(electionName));
        Session session = HibernateUtil.getSession();
        try{
            session.beginTransaction();
            session.saveOrUpdate(this);
            session.getTransaction().commit();
        } catch (HibernateException exception){
            System.err.println("Encounter hibernate exception while adding election to list of voted election: " + exception);
            session.getTransaction().rollback();
        }
    }
}
