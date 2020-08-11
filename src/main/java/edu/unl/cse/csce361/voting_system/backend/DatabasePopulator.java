package edu.unl.cse.csce361.voting_system.backend;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static edu.unl.cse.csce361.voting_system.backend.AnswerOptionEntity.ABSTAIN_VOTE;

public class DatabasePopulator {

    public static Set<Voter> createVoters() {
        System.out.println("Creating Voter.....");
        return Set.of(
                new VoterEntity("A", "25f9e794323b453885f5181f1b624d0b"),//123456789
                new VoterEntity("B", "b24454431f08deb4d5ee6747bd55f3be"),//123879456
                new VoterEntity("C", "1719ab04795270842a9ed68c3a8064c1"),//839480320
                new VoterEntity("D", "2e779d55f26986b3dfb6dc7ba7b3b38d"),//273832943
                new VoterEntity("E", "5a7f04fcd071d7a80a25b397e59341d7"),//987678987
                new VoterEntity("F", "20b9522580886ae6830172bae7cf06ac")//353812739
        );
    }

    public static Set<Admin> createAdmin() {
        System.out.println("Creating Admin.....");
        return Set.of(
                new AdminEntity("superuser 999", "fc5e038d38a57032085441e7fe7010b0"),//helloworld
                new AdminEntity("superrobot 800", "2804df10cfd85d566a0fbb8d71f760b0") //password101
        );
    }

    public static Set<ElectionOfficial> createElectionOfficial(){
        System.out.println("Creating election officials");
        return Set.of(
                new ElectionOfficialEntity("Batman", "4b9f66817cf5ae30903c9a7bb53da984"), //Batman123
                new ElectionOfficialEntity("NuclearFusion", "ca9fbb640474b738649e52613e91b7df") //WhatIsIt
        );
    }

    public static Set<Election> createElection() {
        System.out.println("Creating election .... Hooray");
        return Set.of(
                new ElectionEntity("Nov2020", LocalDate.of(2020, 10, 9), LocalDate.of(2020, 11, 9)),
                new ElectionEntity("Nov2021", LocalDate.of(2021, 7, 9), LocalDate.of(2021, 11, 9))
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
        String activeElectionName = "Nov2020";
        return List.of(
                new AnswerOptionEntity(activeElectionName, "Who is the next mayor?", "Pat Mann"),
                new AnswerOptionEntity(activeElectionName,"Who is the next mayor?", "Dawn Keykong"),
                new AnswerOptionEntity(activeElectionName,"Who is the next city council?", "Inky"),
                new AnswerOptionEntity(activeElectionName,"Who is the next city council?", "Blinky"),
                new AnswerOptionEntity(activeElectionName,"Who is the next Sheriff?", "Q. Burte"),
                new AnswerOptionEntity(activeElectionName,"Shall there be a 25¢ tax on cherries?", "Yes"),
                new AnswerOptionEntity(activeElectionName,"Shall there be a 25¢ tax on cherries?", "No"),
                new AnswerOptionEntity(activeElectionName,"Shall liquor licenses be required for electronic bars?", "Yes"),
                new AnswerOptionEntity(activeElectionName,"Shall liquor licenses be required for electronic bars?", "No"),
                new AnswerOptionEntity(activeElectionName,"Shall electronic race tracks be held liable for electronic car crashes?", "Yes"),
                new AnswerOptionEntity(activeElectionName,"Shall electronic race tracks be held liable for electronic car crashes?", "No"),
                new AnswerOptionEntity("Nov2021","How are you doing?", "Meh"),
                new AnswerOptionEntity("Nov2021","How are you doing?", "Bueno"),
                new AnswerOptionEntity("Nov2021","How are you doing?", "Alright")
        );
    }

    public static List<Long> getAnswerOptionIndex(){
        List<Long> answerOptionIndex = new ArrayList<>();
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Who is the next mayor?", "Dawn Keykong"));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Who is the next city council?", "Blinky"));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Who is the next Sheriff?", "Q. Burte"));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Shall there be a 25¢ tax on cherries?", "No"));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Shall liquor licenses be required for electronic bars?", "Yes"));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Shall electronic race tracks be held liable for electronic car crashes?", "No"));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Who is the next mayor?", "Pat Mann"));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Who is the next city council?", ABSTAIN_VOTE));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Who is the next Sheriff?", ABSTAIN_VOTE));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Shall there be a 25¢ tax on cherries?", ABSTAIN_VOTE));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Shall liquor licenses be required for electronic bars?", ABSTAIN_VOTE));
        answerOptionIndex.add(AnswerOptionEntity.getAnswerOptionIndexByName("Shall electronic race tracks be held liable for electronic car crashes?", ABSTAIN_VOTE));
        return answerOptionIndex;
    }

    public static Set<VoterChoice> createVoterChoice( List<Long> answerOptionIndex) {
        System.out.println("Create voter choice/selection..........");
        return Set.of(
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(0)),
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(1)),
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(2)),
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(3)),
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(4)),
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(5)),
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(6)),
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(7)),
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(8)),
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(9)),
                new VoterChoiceEntity("25f9e794323b453885f5181f1b624d0b", answerOptionIndex.get(10)),
                new VoterChoiceEntity("b24454431f08deb4d5ee6747bd55f3be", answerOptionIndex.get(11))
        );
    }

    public static void setVoterStatus() {
        Session session = HibernateUtil.getSession();
        try {
            Voter voter = VoterEntity.getVoterBySSN("25f9e794323b453885f5181f1b624d0b");
            voter.addVotedElection("Nov2020");
            session.beginTransaction();
            session.saveOrUpdate(voter);
            session.getTransaction().commit();
            voter = VoterEntity.getVoterBySSN("b24454431f08deb4d5ee6747bd55f3be");
            voter.addVotedElection("Nov2020");
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
        session.createQuery("delete from AdminEntity").executeUpdate();
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
            session.getTransaction().commit();
            session.beginTransaction();
            createAdmin().forEach(session::saveOrUpdate);
            session.getTransaction().commit();
            session.beginTransaction();
            createElectionOfficial().forEach(session::saveOrUpdate);
            session.getTransaction().commit();
            Set<Election> elections = createElection();
            session.beginTransaction();
            elections.forEach(session::saveOrUpdate);
            session.getTransaction().commit();
            List<Question> questions = createQuestion();
            session.beginTransaction();
            for(Question question: questions){
            session.saveOrUpdate(question);
            }
            session.getTransaction().commit();
            List<AnswerOption> answerOptions = createAnswerOption();
            session.beginTransaction();
            for(AnswerOption answerOption: answerOptions){
                session.saveOrUpdate(answerOption);
            }
            session.getTransaction().commit();
            List<Long> answerOptionIndex = getAnswerOptionIndex();
            session.beginTransaction();
            createVoterChoice(answerOptionIndex).forEach(session::saveOrUpdate);
            session.getTransaction().commit();
            setVoterStatus();
            Backend.getInstance().startElection(new ElectionOfficialEntity("Hello", "hooy"), "Nov2020");
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
