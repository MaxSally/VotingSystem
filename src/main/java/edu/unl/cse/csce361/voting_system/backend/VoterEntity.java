package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class VoterEntity implements Voter {

    private static int REQUIRED_SSN_LENGTH= 9;

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
    private Long voterId;

    @NaturalId
    @Column(unique = true)
    private String SSN;
    @Column
    private String name;
    @Column
    private boolean hasVoted;

    @OneToMany(mappedBy = "voter", cascade = CascadeType.ALL)
    Set<VoterChoiceEntity> voterChoices;

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
        voterChoices = new HashSet<>();
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
    public boolean vote() {
        return false;
    }

    @Override
    public String getPastVotingDescription() {
        return "";
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

    private boolean validateSSN(String ssn) {
        return ssn.length() == REQUIRED_SSN_LENGTH;
    }
}
