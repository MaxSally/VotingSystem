#   Grading notes for Sprint 2 - Team 2

## Meeting Minutes

4/4

-   All good.

### Scrum Practices

5/5

-   Looks good.

### Class Diagram

3/3

-   Auto-generated; assumed to be consistent with code.

### Design

14/14

-   Looks good.

### Code Style

10/13

From Sprint 1 grading:

>   QuestionEntity.getQuestionsByName uses break to exit a loop

-    Resolved.

>   Indentation is a mix of tabs & spaces, sometimes on the same line

-    Not addressed. (-0.5)

>   There appear to be four unresovled fx:id references in confirm_screen.fxml,
    2 in login.fxml, and 1 in user_info.fxml -- better check on those

-    Resolved.

>   30 unused imports

-    Resolved.
>   assertTrue(lstQA.size() == expectedSize) in
    GetAllQuestionAndAnswerOptionTest would be better expressed as
    assertEquals(expectedSize, lstQA.size()) so that you can see the difference
    if the test fails
>   - Similary, VoterTest has 5 assertTrues that can be better expressed as
    assertNotNull, assertNull, assertEquals, or assertSame  

-    Resolved.

>   VoterEntity.logIn() will always return true because it's testing whether
    this.name is equal to itself

-    Seems to be resolved.

>   VoterTest.testGetAnswerIndex() uses == instead of .equals() to compare two
    boxed Longs; normally I'd say use .equals() or, better yet, make expectedId
    & actualID unboxed longs -- but in this case, if you replace assertTrue
    with assertEquals then it won't matter

-    Resolved.

New problems:
-   Slightly inconsistent bracing style: In some cases you have
    `void method() {`, in others `void method(){`. Either is fine, but pick one
    and stick with it.
-   Several methods in UpdateElectionController are declared with
    `throws IOException`, but would not actually throw an Exception (at least
    not an IO one.) (-0.5)
-   DataLogic.isElectionOfficial() has completely unnecessary conditionals.
    Just `return Backend.getInstance().isElectionOfficial();`. (-1.0)
-   In DataLogic.submitVote() you have _three_ nested `for`-loops, the inner
    two of which have a `continue` condition, and the innermost of which has
    _two_ conditions that `return false`. I'd like you to think long and hard
    about how that smells. :)

##  Commits

### Messages

4/5

-   Non-descriptive/poorly-descriptive messages
    -   "Integrated Admin login" (817453b0) (-0.25)
    -   "Implement listview for editing election" (fd6841d5) (-0.25)
    -   "fix current admin issue" (c92a83f5) (-0.25)
    -   "Commiting for debug" (471e8981) (-0.25)
    -   0/118 (0%) sprint 2 commits are malformed

    -   Formatting problems across full project:
    ```
    50 commits on the master branch
    261 commits among all branches
        On the master branch:
            30 merges, 2 reverts, 214 well-formatted commits, and 4 malformatted commits
            2020-08-03 13:19:54-05:00 Commit a0fc1036 has 1 line too long. (Quan Nguyen and Chloe Galinsky)
            2020-08-03 15:58:06-05:00 Commit c21d4a35 has 1 line too long. (Quan Nguyen and Chloe Galinsky)
            2020-08-03 17:32:39-05:00 Commit eb2f3741 has 1 line too long. (Quan Nguyen and Chloe Galinsky)
            2020-08-03 17:32:40-05:00 Commit cfa39b50 has 1 line too long. (Quan Nguyen and Chloe Galinsky)
        On all other branches:
            1 merges, 0 reverts, 10 well-formatted commits, and 0 malformatted commits

        Unresolved merge conflicts on the master branch:
            2 out of 30 merges have unresolved merge conflicts:
                2020-08-09 19:34:17-05:00 c851b8b8 (syeap2)
                2020-08-11 14:37:45-05:00 e2f6232d (syeap2)
        Unresolved merge conflicts on all other branches:
            0 out of 1 merges have unresolved merge conflicts:
    ```

### Summary

-   Sprint 2 only
```
Contributions by each partner to CSCE 361 / summer2020 / 36team2 on the master branch:
    ('MaxSally', 'qnguyen16@huskers.unl.edu')
     1472 total line changes in  26 commits
            fxml      1 file changes
            java   1278 line changes
            png       3 file changes
            uml       6 file changes
            xml       4 line changes
        First commit:  2020-08-04 22:09:59-05:00
        Median commit: 2020-08-08 16:42:59-05:00
        Last commit:   2020-08-12 20:31:37-05:00
    ('syeap2', 'syeap2@huskers.unl.edu')
     3567 total line changes in  41 commits
            fxml     45 file changes
            java   2707 line changes
        First commit:  2020-08-07 14:02:55-05:00
        Median commit: 2020-08-09 20:14:30-05:00
        Last commit:   2020-08-12 20:27:12-05:00
    ('Chloe', 'chloe.galinsky@huskers.unl.edu')
      426 total line changes in  10 commits
            java     74 line changes
            md      352 line changes
        First commit:  2020-08-05 17:25:37-07:00
        Median commit: 2020-08-08 16:42:59-05:00
        Last commit:   2020-08-11 15:38:55-07:00
    ('Quan Nguyen and Chloe Galinsky', 'qnguyen16cgalinsky2@dev.edu')
       21 total line changes in   1 commits
            java     21 line changes
        First commit:  2020-08-11 13:24:17-05:00
        Median commit: 2020-08-11 13:24:17-05:00
        Last commit:   2020-08-11 13:24:17-05:00
    ('cody.bye', 'cody.bye@huskers.unl.edu')
      903 total line changes in  13 commits
            fxml     16 file changes
            java    554 line changes
        First commit:  2020-08-07 13:54:14-05:00
        Median commit: 2020-08-08 14:19:18-05:00
        Last commit:   2020-08-10 23:21:58-05:00
    ('Soon Leong and Cody Bye', 'syeap2cody.bye@huskers.unl.edu')
      204 total line changes in   2 commits
            fxml      2 file changes
            java    170 line changes
        First commit:  2020-08-07 23:56:47-05:00
        Median commit: 2020-08-08 14:50:23-05:00
        Last commit:   2020-08-08 14:50:23-05:00
    ('matthew.hedberg', 'matthew.hedberg@huskers.unl.edu')
       67 total line changes in   1 commits
            java     67 line changes
        First commit:  2020-08-08 14:45:59-05:00
        Median commit: 2020-08-08 14:45:59-05:00
        Last commit:   2020-08-08 14:45:59-05:00
    ('Quan Nguyen and Chloe Galinsky', 'cgalinsky2qnguyen16@dev.null')
     1131 total line changes in  16 commits
            java   1131 line changes
        First commit:  2020-08-05 19:19:28-05:00
        Median commit: 2020-08-06 11:20:41-07:00
        Last commit:   2020-08-07 11:23:00-07:00
Contributions by each partner to CSCE 361 / summer2020 / 36team2 on 20 branches:
    ('MaxSally', 'qnguyen16@huskers.unl.edu')
     1472 total line changes in  26 commits
            fxml           1 file changes
            java        1278 line changes
            png            3 file changes
            uml            6 file changes
            xml            4 line changes
        First commit:  2020-08-04 22:09:59-05:00
        Median commit: 2020-08-08 16:42:59-05:00
        Last commit:   2020-08-12 20:31:37-05:00
    ('syeap2', 'syeap2@huskers.unl.edu')
     3691 total line changes in  42 commits
            fxml          46 file changes
            java        2826 line changes
        First commit:  2020-08-05 20:00:22-05:00
        Median commit: 2020-08-09 20:14:30-05:00
        Last commit:   2020-08-12 20:27:12-05:00
    ('Chloe', 'chloe.galinsky@huskers.unl.edu')
      434 total line changes in  11 commits
            java          82 line changes
            md           352 line changes
        First commit:  2020-08-05 17:25:37-07:00
        Median commit: 2020-08-08 16:34:56-05:00
        Last commit:   2020-08-11 15:38:55-07:00
    ('Quan Nguyen and Chloe Galinsky', 'qnguyen16cgalinsky2@dev.edu')
       21 total line changes in   1 commits
            java          21 line changes
        First commit:  2020-08-11 13:24:17-05:00
        Median commit: 2020-08-11 13:24:17-05:00
        Last commit:   2020-08-11 13:24:17-05:00
    ('cody.bye', 'cody.bye@huskers.unl.edu')
     1232 total line changes in  18 commits
            fxml          22 file changes
            java         744 line changes
        First commit:  2020-08-05 00:19:54-05:00
        Median commit: 2020-08-07 13:55:18-05:00
        Last commit:   2020-08-10 23:21:58-05:00
    ('Soon Leong and Cody Bye', 'syeap2cody.bye@huskers.unl.edu')
      204 total line changes in   2 commits
            fxml           2 file changes
            java         170 line changes
        First commit:  2020-08-07 23:56:47-05:00
        Median commit: 2020-08-08 14:50:23-05:00
        Last commit:   2020-08-08 14:50:23-05:00
    ('matthew.hedberg', 'matthew.hedberg@huskers.unl.edu')
       67 total line changes in   1 commits
            java          67 line changes
        First commit:  2020-08-08 14:45:59-05:00
        Median commit: 2020-08-08 14:45:59-05:00
        Last commit:   2020-08-08 14:45:59-05:00
    ('Quan Nguyen and Chloe Galinsky', 'cgalinsky2qnguyen16@dev.null')
     1248 total line changes in  18 commits
            java        1248 line changes
        First commit:  2020-08-05 19:19:28-05:00
        Median commit: 2020-08-06 11:29:16-07:00
        Last commit:   2020-08-07 16:39:47-07:00
```

-   Across full project
```
Contributions by each partner to CSCE 361 / summer2020 / 36team2 on the master branch:
    ('MaxSally', 'qnguyen16@huskers.unl.edu')
     2084 total line changes in  46 commits
            fxml      6 file changes
            gitignore 1 line changes
            java   1725 line changes
            png       3 file changes
            uml       7 file changes
            xml      56 line changes
            xml_temp 54 line changes
        First commit:  2020-07-31 16:43:18-05:00
        Median commit: 2020-08-07 14:34:46-05:00
        Last commit:   2020-08-12 20:31:37-05:00
    ('syeap2', 'syeap2@huskers.unl.edu')
     3988 total line changes in  53 commits
            fxml     55 file changes
            java   3010 line changes
            png       1 file changes
            xml       3 line changes
            xml-TEMPLATE 48 line changes
        First commit:  2020-08-01 22:01:07-05:00
        Median commit: 2020-08-08 14:50:23-05:00
        Last commit:   2020-08-12 20:27:12-05:00
    ('Chloe', 'chloe.galinsky@huskers.unl.edu')
      756 total line changes in  14 commits
            java     74 line changes
            md      682 line changes
        First commit:  2020-07-28 16:50:29-07:00
        Median commit: 2020-08-07 14:20:38-07:00
        Last commit:   2020-08-11 15:38:55-07:00
    ('Quan Nguyen and Chloe Galinsky', 'qnguyen16cgalinsky2@dev.edu')
       21 total line changes in   1 commits
            java     21 line changes
        First commit:  2020-08-11 13:24:17-05:00
        Median commit: 2020-08-11 13:24:17-05:00
        Last commit:   2020-08-11 13:24:17-05:00
    ('cody.bye', 'cody.bye@huskers.unl.edu')
     1304 total line changes in  19 commits
            fxml     25 file changes
            java    796 line changes
            md        2 line changes
            png       1 file changes
        First commit:  2020-07-31 17:04:58-05:00
        Median commit: 2020-08-07 13:55:12-05:00
        Last commit:   2020-08-10 23:21:58-05:00
    ('Soon Leong and Cody Bye', 'syeap2cody.bye@huskers.unl.edu')
      204 total line changes in   2 commits
            fxml      2 file changes
            java    170 line changes
        First commit:  2020-08-07 23:56:47-05:00
        Median commit: 2020-08-08 14:50:23-05:00
        Last commit:   2020-08-08 14:50:23-05:00
    ('matthew.hedberg', 'matthew.hedberg@huskers.unl.edu')
       87 total line changes in   3 commits
            java     87 line changes
        First commit:  2020-08-03 15:01:40-05:00
        Median commit: 2020-08-03 17:32:39-05:00
        Last commit:   2020-08-08 14:45:59-05:00
    ('Quan Nguyen and Chloe Galinsky', 'cgalinsky2qnguyen16@dev.null')
     3866 total line changes in  61 commits
            fxml      1 file changes
            gitignore 2 line changes
            java   3581 line changes
            properties 70 line changes
            txt       0 line changes
            xml     121 line changes
            xml-TEMPLATE 144 line changes
        First commit:  2020-07-29 12:49:22-05:00
        Median commit: 2020-08-01 09:24:55-07:00
        Last commit:   2020-08-07 11:23:00-07:00
    ('Quan Nguyen and Chloe Galinsky', 'qnguyen16cgalinsky2@dev.null')
      146 total line changes in   2 commits
            java    146 line changes
        First commit:  2020-08-02 15:42:50-05:00
        Median commit: 2020-08-03 17:32:39-05:00
        Last commit:   2020-08-03 17:32:39-05:00
Contributions by each partner to CSCE 361 / summer2020 / 36team2 on 20 branches:
    ('MaxSally', 'qnguyen16@huskers.unl.edu')
     2084 total line changes in  46 commits
            fxml           6 file changes
            gitignore      1 line changes
            java        1725 line changes
            png            3 file changes
            uml            7 file changes
            xml           56 line changes
            xml_temp      54 line changes
        First commit:  2020-07-31 16:43:18-05:00
        Median commit: 2020-08-07 14:34:46-05:00
        Last commit:   2020-08-12 20:31:37-05:00
    ('syeap2', 'syeap2@huskers.unl.edu')
     4112 total line changes in  54 commits
            fxml          56 file changes
            java        3129 line changes
            png            1 file changes
            xml            3 line changes
            xml-TEMPLATE  48 line changes
        First commit:  2020-08-01 22:01:07-05:00
        Median commit: 2020-08-08 14:50:23-05:00
        Last commit:   2020-08-12 20:27:12-05:00
    ('Chloe', 'chloe.galinsky@huskers.unl.edu')
      764 total line changes in  15 commits
            java          82 line changes
            md           682 line changes
        First commit:  2020-07-28 16:50:29-07:00
        Median commit: 2020-08-07 14:20:38-07:00
        Last commit:   2020-08-11 15:38:55-07:00
    ('Quan Nguyen and Chloe Galinsky', 'qnguyen16cgalinsky2@dev.edu')
       21 total line changes in   1 commits
            java          21 line changes
        First commit:  2020-08-11 13:24:17-05:00
        Median commit: 2020-08-11 13:24:17-05:00
        Last commit:   2020-08-11 13:24:17-05:00
    ('cody.bye', 'cody.bye@huskers.unl.edu')
     1633 total line changes in  24 commits
            fxml          31 file changes
            java         986 line changes
            md             2 line changes
            png            1 file changes
        First commit:  2020-07-31 17:04:58-05:00
        Median commit: 2020-08-07 13:54:57-05:00
        Last commit:   2020-08-10 23:21:58-05:00
    ('Soon Leong and Cody Bye', 'syeap2cody.bye@huskers.unl.edu')
      204 total line changes in   2 commits
            fxml           2 file changes
            java         170 line changes
        First commit:  2020-08-07 23:56:47-05:00
        Median commit: 2020-08-08 14:50:23-05:00
        Last commit:   2020-08-08 14:50:23-05:00
    ('matthew.hedberg', 'matthew.hedberg@huskers.unl.edu')
       87 total line changes in   3 commits
            java          87 line changes
        First commit:  2020-08-03 15:01:40-05:00
        Median commit: 2020-08-03 17:32:39-05:00
        Last commit:   2020-08-08 14:45:59-05:00
    ('Quan Nguyen and Chloe Galinsky', 'cgalinsky2qnguyen16@dev.null')
     4031 total line changes in  64 commits
            fxml           1 file changes
            gitignore      2 line changes
            java        3698 line changes
            properties    70 line changes
            txt            0 line changes
            xml          121 line changes
            xml-TEMPLATE 192 line changes
        First commit:  2020-07-29 12:49:22-05:00
        Median commit: 2020-08-01 10:15:55-07:00
        Last commit:   2020-08-07 16:39:47-07:00
    ('Quan Nguyen and Chloe Galinsky', 'qnguyen16cgalinsky2@dev.null')
      146 total line changes in   2 commits
            java         146 line changes
        First commit:  2020-08-02 15:42:50-05:00
        Median commit: 2020-08-03 17:32:39-05:00
        Last commit:   2020-08-03 17:32:39-05:00
```

##   Voting System Functionality

14/15

-   There is no way of going back from viewing election results (as an admin)
    without logging out. ~~(-0.5)~~

### Original Assignment Specification (includes clarifications on [Piazza](https://piazza.com/class/kavslp56w7g7d4?cid=125_f1)) (11 points)
-   Elections
    -   [x] Supports November 2020 election (0.5)
        -   Two 2-way races
        -   One uncontested race
        -   Three propositions
    -   [x] Supports additional elections (0.5)
        -   A non-technical person must be able to create the ballot for new
            elections
        -   Once an election has started, the ballot cannot be edited
    -   [x] At the end of the voting day, the winner of each race & outcome of
        each issue shall be determined (1.0)
        -   On the "overview", where it seems to want to display the winner, if
            there's a tie, it just displays all tied options.
    -   [x] No limits on the number of voters (0.5)
-   Voting
    -   [x] Voters identify through authentication, then presented with ballot
        (1.0)
    -   [x] After making their selections, voter can review & change selections
        (0.5)
    -   [x] Once submitted, the voter's choices shall be recorded (1.0)
    -   [x] Once submitted, the voter's choices cannot be changed (1.0)
    -   [x] A voter cannot vote more than once in the same election (1.0)
    -   [x] A voter can choose not to cast a vote for a particular race or
        issue, recorded as an abstention (on Piazza, I used the unfortunate
        term "no vote" which could be confused with selecting "no" for a
        proposition) (0.5)
-   Election Integrity
    -   [x] A voter can later view their recorded vote (0.5)
    -   [x] Other people (including auditor) can see *whether* a voter voted
        (1.0)
    -   [x] No one other than the voter can determine *how* a voter voted (1.0)
    -   [ ] Auditor needs to be able to see all of the ballots (1.0)
        -   Auditor can only see the currently active ballot.
        -   _(Election official can see the existence of unpublished ballots,
            but not past ones, and cannot see statistics/results of anything
            but currently active ballot.)_
    -   Handling possible election fraud after it has been detected is outside
        the system's scope.

### Elicited Requirements (4 points)
-   [x] Voters can use the system to register to vote, but the system must
    consider previously-registered voters to be legitimate voters
    (https://piazza.com/class/kavslp56w7g7d4?cid=125_f2) (0.8)
-   [x] Election officials and auditors can use the system to create their
    accounts (https://piazza.com/class/kavslp56w7g7d4?cid=125_f4) (0.8)
-   The system *may* allow users to change their personal information, but
    users must not be able to change their SSN
-   [x] The system must allow races with more than two candidates
    (https://piazza.com/class/kavslp56w7g7d4?cid=125_f6) (0.8)
    -   The system *may* allow write-in candidates
-   The system *may* allow a time-limit on placing votes (*e.g.*, a voter
    must submit their responses within 15 minutes)
-   The system *may* allow an auditor to be removed after an election is
    over / before the next election
-   [x] The system must retain the records for past elections
    (https://piazza.com/class/kavslp56w7g7d4?cid=125_f10) (0.8)
    -   Sort of.. They are still in the database, but once they're ended it is
        not possible to access them through the admin interface.
-   [x] The system must gracefully handle the case of no option receiving a
    majority; the answer in
    https://piazza.com/class/kavslp56w7g7d4?cid=125_f11 is one way to
    accomplish this, but this was posted after the deadline for new
    requirements (0.8)
    -   When there's a tie, the overview simply displays all tied options, not
        necessarily next to each other. ~~(-0.4)~~

## Software Engineering Practices

6/9

-   You named your hibernate template funny. It's fine -- you're fine -- but
    the typical way to name it is `hibernate.cfg.xml-TEMPLATE` (such that it
    has no usable file type), where you named yours
    `hibernate.cfg-TEMPLATE.xml` (letting it still be readable XML.) Funny.
    -   cb: one advantage to this name is that it allows the IDE to
        pretty-print it
-   VoterEntity.vote() outputs "Encounter exception while accessing db" to
    console if it encounters an Exception, with no message passed on to the
    user. (-0.5)
-   Other, similar Exception "handling" spread throughout. Another example is
    AnswerOptionEntity.getAnswerOptionIndexByName(). ~~(-0.5)~~ cb: same
    conceptual error
-   Uncaught `IllegalArgumentException` if trying to create an election where a
    question has a `'` in it. This is worse than causing an Exception. Because
    you aren't escaping your inputs before serving them up to your database,
    you run the risk of
    **[SQL Injection attacks](https://imgs.xkcd.com/comics/exploits_of_a_mom.png)** --
    which is always bad, but perhaps especially in a public voting system.
    (-1.0)
-   Uncaught `NullPointerException` if election official attempts to create a
    new election without setting a start and end date.
-   I can't quite seem to figure out what is causing it, but I am frequently
    getting the error `Could not load Question {question name}. No row with the
    given identifier exists:
    [edu.unl.cse.csce361.voting_system.backend.ElectionEntity#1145]` (-0.5)
-   Two of your test suites (FinalResultTest and SubmitVoteTest) have loops
    with `System.out`, and no `assert*` methods. (-1000.0)
-   Two unresolved merge conflicts committed to repo (-0.5)

## Presentation / Demonstration

9/10

-   The experience report wasn't much of an experience report
-   Exceeded time: 16:14 (-1)
    -   If you had an experience report, you would have had 8-15 minutes for
        your presentation
    -   Given how little time was spent on the "experience report" (15
        seconds), I'm considering your presentation to not include one, and so
        you had 8-13 minutes

## Bonus Credit

4/5

-   JUnit tests are present. (0.5)
-   Implemented GUI (0.5)
-   Accessibility (2.0)
-   Pair Programming
    -   4237/12456 line changes in 66/201 commits on master branch are reported
        as pair programming
        -   I'll estimate 43% of code changes were pair-programmed, as non-pair-
            programmed commits include 2527 non-code line changes
            -   In the interest of giving you maximum benefit, I am not
                subtracting out the non-code line changes that were pair
                "programmed"
    -   (1.0)
