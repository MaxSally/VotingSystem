package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

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

    @Column
    private String SSN;
    @Column
    private String name;

    public VoterEntity() {
        super();
    }

    public VoterEntity(String SSN, String name) {
        this.SSN = SSN;
        this.name = name;
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

    public boolean equals(Voter voter) {
        return voter.getSSN().equals(this.SSN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voterId, SSN, getName());
    }
}
