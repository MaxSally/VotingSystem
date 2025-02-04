package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;
import java.util.*;

import static edu.unl.cse.csce361.voting_system.backend.EncryptionUtil.encrypt;

@Entity
public class VoterEntity implements Voter {

    public static int REQUIRED_SSN_LENGTH= 9;

    static VoterEntity getVoterBySSN(String ssn) {
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

    static Long getVoterCountVotedInElection(String electionName) {
        Long voterCount = 0L;
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            List<VoterEntity> voters = session.createQuery("SELECT voter From VoterEntity voter", VoterEntity.class).getResultList();
            session.getTransaction().commit();
            for(VoterEntity voter: voters) {
                if(voter.hasVoted(electionName)) {
                    voterCount++;
                }
            }

        } catch (HibernateException exception) {
            System.err.println("Could not load all Voters " + exception.getMessage());
            session.getTransaction().rollback();
        }
        return voterCount;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="electionVotedIn", joinColumns={@JoinColumn(referencedColumnName="ID")}
            , inverseJoinColumns={@JoinColumn(referencedColumnName="ID")})
    private Set<ElectionEntity> electionVotedIn;

    public VoterEntity() {
        super();
    }

    public VoterEntity(String name, String SSN) {
    	this.SSN = SSN;
        this.name = name;
        electionVotedIn = new HashSet<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean logIn(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean vote(Long answerOptionIndex) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            VoterChoiceEntity newVote = new VoterChoiceEntity(SSN, answerOptionIndex);
            session.saveOrUpdate(newVote);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exception) {
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
    public Map<String, String> getVoterVoteResult(String electionName) {
        Set<Question> questions = new HashSet<>();
        questions.addAll(ElectionEntity.getElectionByName(electionName).getAssociatedQuestions());

        Map<String, String> voterSelections = new HashMap<>();
        String encryptedSSN = encrypt(SSN);
        List<VoterChoiceEntity> voterChoices = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            voterChoices = session.createQuery("From VoterChoiceEntity  where voter = '" + encryptedSSN + "'", VoterChoiceEntity.class).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException exception){
            System.out.println("Encounter hibernate problems " + exception);
            session.getTransaction().rollback();
        }
        for(VoterChoiceEntity voterChoiceEntity : voterChoices) {
            if(questions.contains(voterChoiceEntity.getAnswerOption().getQuestion())) {
                voterSelections.put(voterChoiceEntity.getAnswerOption().getQuestion().getQuestionText(), voterChoiceEntity.getAnswerOption().getAnswerText());
            }
        }

        for(Question question : questions) {
            if(!voterSelections.containsKey(question.getQuestionText())) {
                voterSelections.put(question.getQuestionText(), AnswerOptionEntity.ABSTAIN_VOTE);
            }
        }
        return voterSelections;
    }

    public void addVoter(VoterChoice voterChoice) { 
        if(voterChoice instanceof VoterChoiceEntity) {
            VoterChoiceEntity voterChoiceEntity = (VoterChoiceEntity) voterChoice;
            voterChoiceEntity.setVoter(this);
        }
        else {
            throw new IllegalArgumentException("Expected VoterChoice, got " + voterChoice.getClass().getSimpleName());
        }
    }

    @Override
    public void addVotedElection(String electionName) {
        electionVotedIn.add(ElectionEntity.getElectionByName(electionName));
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(this);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Encounter hibernate exception while adding election to list of voted election: " + exception);
            session.getTransaction().rollback();
        }
        ElectionEntity.getElectionByName(electionName).addVoter(this);
    }

    public String getSSN(){
        return SSN;
    }
}
