!-- This is the max votes scenario
!-- Remember that this "MaxVotes" algorithm name is expressed in this library as RankByVotes

Scenario:  Assign a single seat to multiple candidates
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 200 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is MULTIPLE
Then result has 1 seats
Then result seat #0 is CandidateA

Scenario: Assign two seats to two candidates
Given empty scenario
Given tally has candidate CandidateA with 1 votes
Given tally has candidate CandidateB with 0 votes
Given algorithm has property numberOfSeats set to 2
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result has 2 seats
Then result type is MULTIPLE
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB

Scenario: Assign two seats to two tied candidates
Given empty scenario
Given tally has candidate CandidateA with 1 votes
Given tally has candidate CandidateB with 1 votes
Given algorithm has property numberOfSeats set to 2
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB

Scenario: Assign two seats to three candidates
Given empty scenario
Given tally has candidate CandidateA with 1 votes
Given tally has candidate CandidateB with 1 votes
Given tally has candidate CandidateC with 2 votes
Given algorithm has property numberOfSeats set to 2
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB

Scenario: Assign one seat to three candidates, from which two are tied non-winners
Given empty scenario
Given tally has candidate CandidateA with 1 votes
Given tally has candidate CandidateB with 1 votes
Given tally has candidate CandidateC with 2 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes method
!-- Unsure behaviour on this result type - shouldn't it be WINNER (MULTIPLE)?
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB

Scenario: Assign one seat to three candidates, from which two are tied non-winners
Given empty scenario
Given tally has candidate CandidateA with 1 votes
Given tally has candidate CandidateB with 1 votes
Given tally has candidate CandidateC with 2 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to false
When process with RankByVotes method
!-- Unsure behaviour on this result type - shouldn't it be WINNER (MULTIPLE)?
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB