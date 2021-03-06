AbsoluteMajority story

Narrative:
In order to select a candidate by an absolute majority
As a development team
I want to use AbsoluteMajorityAlgorithm

Scenario: Candidate list has no candidate with enouth votes to reach absolute majority.
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given tally has 1000 potential votes
!-- Absolute majority minimum votes is 501
When process with AbsoluteMajority algorithm
Then result type is UNDECIDED
Then result has 0 seats

Scenario: Candidate list has one candidate with enouth votes to reach exact absolute majority.
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 501 votes
Given tally has candidate CandidateD with 125 votes
Given tally has 1000 potential votes
!-- Absolute majority minimum votes is 501
When process with AbsoluteMajority algorithm
Then result type is SINGLE
Then result has 1 seat
Then result seat #0  is CandidateC
Then result seat #0  isn't CandidateA
Then result seat #0  isn't CandidateB
Then result seat #0  isn't CandidateD


Scenario: Candidate list has one candidate with enouth votes to pass absolute majority.
Given empty scenario
Given tally has candidate CandidateA with 100 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 600 votes
Given tally has candidate CandidateD with 125 votes
Given tally has 1000 potential votes
!-- Absolute majority minimum votes is 501
When process with AbsoluteMajority algorithm
Then result type is SINGLE
Then result has 1 seat
Then result seat #0  is CandidateC
Then result seat #0  isn't CandidateA
Then result seat #0  isn't CandidateB
Then result seat #0  isn't CandidateD