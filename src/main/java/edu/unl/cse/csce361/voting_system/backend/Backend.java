package edu.unl.cse.csce361.voting_system.backend;

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

}
