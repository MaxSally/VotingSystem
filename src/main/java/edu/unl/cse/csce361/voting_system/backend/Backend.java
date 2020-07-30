package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import static edu.unl.cse.csce361.voting_system.backend.VoterEntity.REQUIRED_SSN_LENGTH;

public class Backend {

    private static Backend instance;

    private Backend() {
        super();
    }

    public static Backend getInstance() {
        if (instance == null) {
            instance = new Backend();
        }
        return instance;
    }

    public boolean logIn(String name, String ssn) {
        Voter currentVoter = VoterEntity.getVoterBySSN(ssn);
        return currentVoter != null && currentVoter.getName().equals(name);
    }

    public Voter registerToVote(String name, String ssn) {
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        Voter voter = null;
        try {
            if(VoterEntity.validateSSN(ssn)) {
                if(VoterEntity.getVoterBySSN(ssn) == null) {
                    session.beginTransaction();
                    voter = new VoterEntity(name, ssn);
                    session.saveOrUpdate(voter);
                    session.getTransaction().commit();
                } else {
                    System.err.println("A voter with the ssn " + ssn + " already exists");
                }
            } else {
                System.err.println("The ssn " + ssn + "is invalid. It must be " + REQUIRED_SSN_LENGTH + " digits");
            }
        } catch (HibernateException exception) {
            System.err.println("encounter hibernate problem" + exception);
            session.getTransaction().rollback();
        }
        return voter;
    }

}
