# Voting System

The city of Pacopolis wants you to develop an electronic voting system. While
the mayoral race between Pat Mann and Dawn Keykong is the most visible
election, there are other elections and issues that will also need to be
decided. And, of course, the electronic voting system must be usable in future
elections, too.

-   The ballot for the November 2020 election consists of:
    -   Mayor: Pat Mann vs. Dawn Keykong
    -   City Council seat: Inky vs. Blinky
    -   Sheriff: Q. Burte, no opponent
    -   Proposition 1: Shall there be a 25¢ tax on cherries?
    -   Proposition 2: Shall liquor licenses be required for electronic bars?
    -   Proposition 3: Shall electronic race tracks be held liable for
        electronic car crashes?

-   A non-technical person must be able to create the ballot for a new
    election.

The system shall allow a voter to identify themselves through authentication,
after which they shall be presented with the ballot. After making their
selections, the voter shall be offered the opportunity to review and change
their selections. Once submitted, the voter's choices shall be recorded. The
system shall prohibit a voter from voting more than once in the same election.
At the end of the voting day, the system shall determine the winner of each
election and the outcome of each issue.

While it shall be possible for a voter to later view their recorded vote to
confirm that it was recorded correctly, and it shall be possible for someone
such as an auditor to determine *whether* a particular voter voted, it shall
be impossible for anyone other than the voter to determine *how* any
particular voter voted.

-   "Anyone other than the voter" includes database administrators and
    system administrators.

The system shall allow an unlimited number of voters to vote from their own
computers or from a shared computer at a polling location.

-   The system may be implemented in text-mode or with a GUI.

