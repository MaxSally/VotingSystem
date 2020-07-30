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

    static VoterEntity getVoterByName(String name){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        VoterEntity voter = null;
        try {
            voter = session.bySimpleNaturalId(VoterEntity.class).load(name);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Could not load Voter " + name + ". " + exception.getMessage());
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
        this.SSN = SSN;
        this.name = name;
        voterChoices = new HashSet<>();
        hasVoted = false;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean logIn(){
        return true;
    }

    @Override
    public boolean vote() {
        return false;
    }

    @Override
    public String getPastVotingDescription() {
        return "";
    }

    @Override
    public String getSSN(){
        return SSN;
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
}
