package edu.unl.cse.csce361.voting_system.backend;

import edu.unl.cse.csce361.testTemplate.TestTemplate;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

public class AdminTest extends TestTemplate {

    @Test
    public void testAdminLogIn() {
        String username = "superuser 999";
        String password = "this is my password";

        Admin admin = Backend.getInstance().adminLogIn(username, password);
        assertNotNull(admin);
    }

    @Test
    public void testCreateAdminAccount() {
        String username = "chloe";
        String password = "testaccount";

        Admin admin = Backend.getInstance().registerAdminAccount(username, password);
        assertNotNull(admin);
    }

    @Test
    public void getAllInactiveElection(){
        List<Election> elections = Backend.getInstance().getAllInactiveElections();
        int expectedSize = 1;
        String expectedName = "Nov2021";
        assertEquals(expectedSize, elections.size());
        assertTrue(elections.get(0).isAvailableForEdit());
        assertEquals(expectedName, elections.get(0).getElectionName());
    }

}
