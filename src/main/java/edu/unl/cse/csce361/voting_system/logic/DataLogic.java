package edu.unl.cse.csce361.voting_system.logic;

import javax.xml.crypto.Data;

public class DataLogic {
    DataLogic instance;

    public DataLogic getInstance(){
        if(instance == null){
            instance = new DataLogic();
        }
        return instance;
    }

    public DataLogic(){

    }
}
