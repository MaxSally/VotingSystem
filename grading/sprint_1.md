#   Grading notes for Sprint 1

-   Logging in only checks SSN, doesn't check if name matches
-   If SSN isn't in DB or if SSN is of the form `123-45-6789` then the error
    message says "Something is wrong. Please contact an administrator" -- it
    would be more helpful to say that the voter wasn't found in the database,
    please talk to an official if this is in error, or to say that the SSN was
    in the wrong format
    -   If you're expecting a string of 9 digits without hyphens, you'd better
        indicate the format to the user!
    -   "C" in the VoterEntity pre-populated data has the letter `O` instead of
        the digit `0` as the last digit in their SSN

##  Meeting Minutes

4/4

##  Scrum Practices

5/5

-   All team member appear to have participated in the minimum of 3 scrum
    meetings (had they not, penalties would have been individual)
-   The "cliff" burndown is an artifact of how you closed issues and does not
    represent a true "cliff" burndown

##  Class Diagram

2.75/3

-   Auto-generated; assumed to be consistent with code.
-   Thank you for breaking it up into multiple diagrams for clarity
-   Not in pdf, jpg, or png format (-0.25)

##  Design

For Sprint 1, I am only looking at whether you have an overt architecture and
are maintaining the separation of concerns.

2/2

-   Appears to be a 3-layer architecture; partitions look reasonable

##  Code Style

For Sprint 1, I am making a cursory check for compliance with your team's code
style and running static analysis.

2.3/3

-   `QuestionEntity.getQuestionsByName` uses `break` to exit a loop (-0.25)
-   Indentation is a mix of tabs & spaces, sometimes on the same line (-0.25)
-   There appear to be four unresovled fx:id references in
    `confirm_screen.fxml`, 2 in `login.fxml`, and 1 in `user_info.fxml` --
    better check on those
-   30 unused imports (-0.1)
-   `assertTrue(lstQA.size() == expectedSize)` in
    `GetAllQuestionAndAnswerOptionTest` would be better expressed as
    `assertEquals(expectedSize, lstQA.size())` so that you can see the
    difference if the test fails (-0.1)
    -   Similary, `VoterTest` has 5 `assertTrue`s that can be better expressed as `assertNotNull`, `assertNull`, `assertEquals`, or `assertSame`
-   Probable bugs
    -   `VoterEntity.logIn()` will always return true because it's testing
        whether `this.name` is equal to itself
    -   `VoterTest.testGetAnswerIndex()` uses `==` instead of `.equals()` to
        compare two boxed Longs; normally I'd say use `.equals()` or, better
        yet, make `expectedId` & `actualID` unboxed longs -- but in this case,
        if you replace `assertTrue` with `assertEquals` then it won't matter

##  Commits

### Messages

5/5

-   Overall, good commit messages
-   4/100 (4%) commits are malformed (-0.0)
```
115 commits on the master branch
138 commits among all branches
	On the master branch:
		15 merges, 0 reverts, 96 well-formatted commits, and 4 malformatted commits
		2020-08-03 13:19:54-05:00 Commit a0fc1036 has 1 line too long. (Quan Nguyen and Chloe Galinsky)
		2020-08-03 15:58:06-05:00 Commit c21d4a35 has 1 line too long. (Quan Nguyen and Chloe Galinsky)
		2020-08-03 17:32:39-05:00 Commit eb2f3741 has 1 line too long. (Quan Nguyen and Chloe Galinsky)
		2020-08-03 17:32:40-05:00 Commit cfa39b50 has 1 line too long. (Quan Nguyen and Chloe Galinsky)
	On all other branches:
		2 merges, 0 reverts, 21 well-formatted commits, and 0 malformatted ommits

	Unresolved merge conflicts on the master branch:
		0 out of 15 merges have unresolved merge conflicts:
	Unresolved merge conflicts on all other branches:
		0 out of 2 merges have unresolved merge conflicts:
```

### Summary

```
Contributions by each partner to CSCE 361 / summer2020 / 36team2 on 13 branches:
	('Max', 'qnguyen16@huskers.unl.edu')
	  801 total line changes in  21 commits
			fxml           5 file changes
			gitignore      1 line changes
			java         447 line changes
			uml            4 file changes
			xml           52 line changes
			xml_temp      54 line changes
		First commit:  2020-07-31 16:43:18-05:00
		Median commit: 2020-08-03 16:03:57-05:00
		Last commit:   2020-08-04 22:09:59-05:00
	('syeap2', 'syeap2@huskers.unl.edu')
	  421 total line changes in  12 commits
			fxml          10 file changes
			java         303 line changes
			png            1 file changes
			xml            3 line changes
			xml-TEMPLATE  48 line changes
		First commit:  2020-08-01 22:01:07-05:00
		Median commit: 2020-08-03 20:54:12-05:00
		Last commit:   2020-08-04 18:29:53-05:00
	('cody.bye', 'cody.bye@huskers.unl.edu')
	  401 total line changes in   6 commits
			fxml           9 file changes
			java         242 line changes
			md             2 line changes
			png            1 file changes
		First commit:  2020-07-31 17:04:58-05:00
		Median commit: 2020-08-02 01:36:51-05:00
		Last commit:   2020-08-04 23:17:31+00:00
	('Chloe', 'chloe.galinsky@huskers.unl.edu')
	  330 total line changes in   4 commits
			md           330 line changes
		First commit:  2020-07-28 16:50:29-07:00
		Median commit: 2020-08-03 15:22:24-07:00
		Last commit:   2020-08-04 15:12:22-07:00
	('Chloe Galinsky and Quan Nguyen', 'cgalinsky2qnguyen16@dev.null')
	 2783 total line changes in  46 commits
			fxml           1 file changes
			gitignore      2 line changes
			java        2450 line changes
			properties    70 line changes
			txt            0 line changes
			xml          121 line changes
			xml-TEMPLATE 192 line changes
		First commit:  2020-07-29 12:49:22-05:00
		Median commit: 2020-07-31 14:30:26-05:00
		Last commit:   2020-08-04 10:44:38-07:00
	('matthew.hedberg', 'matthew.hedberg@huskers.unl.edu')
	   20 total line changes in   2 commits
			java          20 line changes
		First commit:  2020-08-03 15:01:40-05:00
		Median commit: 2020-08-03 17:32:39-05:00
		Last commit:   2020-08-03 17:32:39-05:00
	('Quan Nguyen and Chloe Galinsky', 'qnguyen16cgalinsky2@dev.null')
	  146 total line changes in   2 commits
			java         146 line changes
		First commit:  2020-08-02 15:42:50-05:00
		Median commit: 2020-08-03 17:32:39-05:00
		Last commit:   2020-08-03 17:32:39-05:00
```
