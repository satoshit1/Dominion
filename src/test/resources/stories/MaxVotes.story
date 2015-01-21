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
Then result has 1 seats
Then result seat #0 is CandidateA