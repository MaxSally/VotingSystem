package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabasePopulator {

    public static Set<Voter> createVoters() {
        System.out.println("Creating Voter.....");
        return Set.of(
                new VoterEntity("A", "123456789"),
                new VoterEntity("B", "123879456"),
                new VoterEntity("C", "83948032O"),
                new VoterEntity("D", "273832943"),
                new VoterEntity("E", "987678987"),
                new VoterEntity("F", "353812739")
        );
    }

    public static Set<Admin> createAdmin() {
        System.out.println("Creating Admin.....");
        return Set.of(
                new AdminEntity("superuser 999", "this is my password"),
                new AdminEntity("superrobot 800", "password101")
        );
    }

    public static Set<Election> createElection() {
        System.out.println("Creating election .... Hooray");
        return Set.of(
                new ElectionEntity("Nov2020", LocalDate.of(2020, 7, 9), LocalDate.of(2020, 11, 9), false),
                new ElectionEntity("Nov2021", LocalDate.of(2021, 7, 9), LocalDate.of(2021, 11, 9), false)
        );
    }

    public static List<Question> createQuestion() {
        System.out.println("Creating Question.....");
        return List.of(
                new QuestionEntity("Who is the next mayor?", "Nov2020"),
                new QuestionEntity("Who is the next city council?", "Nov2020"),
                new QuestionEntity("Who is the next Sheriff?", "Nov2020"),
                new QuestionEntity("Shall there be a 25¢ tax on cherries?", "Nov2020"),
                new QuestionEntity("Shall liquor licenses be required for electronic bars?", "Nov2020"),
                new QuestionEntity("Shall electronic race tracks be held liable for electronic car crashes?",
                        "Nov2020"),
                new QuestionEntity("How are you doing?", "Nov2021")
        );
    }

    public static List<AnswerOption> createAnswerOption() {
        System.out.println("Create answer options ......");
        return List.of(
                new AnswerOptionEntity("Who is the next mayor?", "Pat Mann"),
                new AnswerOptionEntity("Who is the next mayor?", "Dawn Keykong"),
                new AnswerOptionEntity("Who is the next city council?", "Inky"),
                new AnswerOptionEntity("Who is the next city council?", "Blinky"),
                new AnswerOptionEntity("Who is the next Sheriff?", "Q. Burte"),
                new AnswerOptionEntity("Shall there be a 25¢ tax on cherries?", "Yes"),
                new AnswerOptionEntity("Shall there be a 25¢ tax on cherries?", "No"),
                new AnswerOptionEntity("Shall liquor licenses be required for electronic bars?", "Yes"),
                new AnswerOptionEntity("Shall liquor licenses be required for electronic bars?", "No"),
                new AnswerOptionEntity("Shall electronic race tracks be held liable for electronic car crashes?", "Yes"),
                new AnswerOptionEntity("Shall electronic race tracks be held liable for electronic car crashes?", "No"),
                new AnswerOptionEntity("How are you doing?", "Meh"),
                new AnswerOptionEntity("How are you doing?", "Bueno"),
                new AnswerOptionEntity("How are you doing?", "Alright")
        );
    }

    public static Set<VoterChoice> createVoterChoice() {
        System.out.println("Create voter choice/selection..........");
        return Set.of(
                new VoterChoiceEntity("123456789", 1L),
                new VoterChoiceEntity("123456789", 4L),
                new VoterChoiceEntity("123456789", 5L),
                new VoterChoiceEntity("123456789", 7L),
                new VoterChoiceEntity("123456789", 8L),
                new VoterChoiceEntity("123879456", 0L)
        );
    }

    public static void setVoterStatus() {
        Session session = HibernateUtil.getSession();
        try {
            Voter voter = VoterEntity.getVoterBySSN("123456789");
            voter.setVoterStatus(true);
            session.beginTransaction();
            session.saveOrUpdate(voter);
            session.getTransaction().commit();
            voter = VoterEntity.getVoterBySSN("123879456");
            voter.setVoterStatus(true);
            session.beginTransaction();
            session.saveOrUpdate(voter);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            System.err.println("Encounter hibernate exception while setting voter status in db populator :v:" + exception);
            session.getTransaction().rollback();
        }
    }

    public static void depopulateTables(Session session) {
        System.out.println("Emptying tables...");
        session.createQuery("delete from VoterChoiceEntity").executeUpdate();
        session.createQuery("delete from AnswerOptionEntity").executeUpdate();
        session.createQuery("delete from QuestionEntity").executeUpdate();
        session.createQuery("delete from ElectionEntity").executeUpdate();
        session.createQuery("delete from VoterEntity").executeUpdate();
    }

    public static void main(String[] args) {
        System.out.println("Creating Hibernate session...");
        Session session = HibernateUtil.getSession();
        try {
            System.out.println("Starting Hibernate transaction to clear tables...");
            session.beginTransaction();
            depopulateTables(session);
            System.out.println("Concluding Hibernate transaction...");
            session.getTransaction().commit();
            System.out.println("Starting Hibernate transaction to populate tables");
            session.beginTransaction();
            createVoters().forEach(session::saveOrUpdate);
            createAdmin().forEach(session::saveOrUpdate);
            createElection().forEach(session::saveOrUpdate);
            createQuestion().forEach(session::saveOrUpdate);
            createAnswerOption().forEach(session::saveOrUpdate);
            createVoterChoice().forEach(session::saveOrUpdate);
            session.getTransaction().commit();
            setVoterStatus();
            System.out.println("Concluding Hibernate transaction...");
            System.out.println("Success! The database has been populated.");
        } catch (MappingException mappingException) {
            System.err.println("Problem encountered when creating a table. The most likely problem is a missing\n" +
                    "    <mapping class=\"...\"/> tag in hibernate.cfg.xml, but it's possible the\n" +
                    "    problem is that the programmer is attempting to load an interface instead\n" +
                    "    of an entity.");
            StackTraceElement[] trace = mappingException.getStackTrace();
            System.err.println("  " + trace[trace.length - 1]);
            System.err.println("  " + mappingException.getMessage());
            session.getTransaction().rollback();
        } catch (PersistenceException persistenceException) {
            System.err.println("Problem encountered when populating or depopulating a table. It's not clear why\n" +
                    "    this would happen unless it's a network or server failure. But it's probably\n" +
                    "    something completely unexpected.");
            StackTraceElement[] trace = persistenceException.getStackTrace();
            System.err.println("  " + trace[trace.length - 1]);
            System.err.println("  " + persistenceException.getMessage());
            System.err.println("  " + persistenceException.getCause().getCause().getMessage());
            session.getTransaction().rollback();
        } catch (Exception exception) {
            System.err.println("Problem encountered that (probably) has nothing to do with creating and\n" +
                    "    (de)populating tables. This is (most likely) is a plain, old-fashioned\n" +
                    "    programming boo-boo.");
            StackTraceElement[] trace = exception.getStackTrace();
            System.err.println("  " + trace[trace.length - 1]);
            System.err.println("  " + exception.getMessage());
            session.getTransaction().rollback();
        } finally {
            HibernateUtil.closeSession(session);
        }
    }
}
