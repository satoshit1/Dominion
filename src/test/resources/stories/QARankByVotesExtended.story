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
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 1 seat
Then result seat #0 is CandidateA

Scenario:  Assign three seats to multiple candidates
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 200 votes
Given algorithm has property numberOfSeats set to 3
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateD
Then result seat #2 is CandidateB

Scenario:  Assign five seats to multiple candidates
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 300 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 2200 votes
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 5
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 5 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateG
Then result seat #2 is CandidateA
Then result seat #3 is CandidateF
Then result seat #4 is CandidateE

Scenario:  TIE - Assign one seats to multiple candidates
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 300 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 6000 votes
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateG

Scenario:  TIE - Assign three seats to multiple candidates, tie on 2nd
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 300 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1000 votes
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 3
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateA
Then result seat #2 is CandidateG

Scenario:  TIE - Assign three seats to multiple candidates, tie on 3rd
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 3000 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1000 votes
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 3
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateG

Scenario: TIE - Assign three seats to multiple candidates, tie on 5th
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 3000 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1200 votes 
Given tally has candidate CandidateH with 800 votes
Given algorithm has property numberOfSeats set to 3
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateE
Then result seat #2 is CandidateG

Scenario: TIE - Assign one seats to multiple candidates, tie on 5th
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 3000 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1200 votes 
Given tally has candidate CandidateH with 800 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 1 seats
Then result seat #0 is CandidateD

Scenario: TIE - Assign one seats to multiple candidates, tie on first two rows
Given empty scenario
Given tally has candidate CandidateA with 3000 votes
Given tally has candidate CandidateB with 3000 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 4000 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1200 votes 
Given tally has candidate CandidateH with 90000 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 1 seats
Then result seat #0 is CandidateH

Scenario: Assign one seat to three candidates, from which two are tied non-winners
Given empty scenario
Given tally has candidate CandidateA with 3000 votes
Given tally has candidate CandidateB with 5000 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 5000 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1200 votes 
Given tally has candidate CandidateH with 90000 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 1 seats
Then result seat #0 is CandidateH

Scenario: TIE BREAKER: Minority - Assign three seats to multiple candidates, tie on 3rd, one minority
Given empty scenario
Given tally has candidate CandidateA with 1000 votes and properties minority=yes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 3000 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1000 votes and property minority=no
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 3
Given use tie breaker minority-tie-breaker
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateE
Then result seat #2 is CandidateA

Scenario: TIE BREAKER: Minority - Assign three seats to multiple candidates, tie on 3rd, one minority, other not defined
Given empty scenario
Given tally has candidate CandidateA with 1000 votes and properties minority=yes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 3000 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1000 votes
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 3
Given use tie breaker minority-tie-breaker
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateE
Then result seat #2 is CandidateA

Scenario: TIE BREAKER: Minority - Assign three seats to multiple candidates, tie on 3rd, one not minority
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 3000 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1000 votes and properties minority=no
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 3
Given use tie breaker minority-tie-breaker
When process with RankByVotes-extended method
Then result type is TIE
Then result has 2 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateG

Scenario: TIE BREAKER: Random - Assign three seats to multiple candidates, tie on 2nd
Given empty scenario
Given tally has candidate CandidateA with 1000 votes and properties minority=yes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 300 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1000 votes and property minority=no
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 3
Given use tie breaker random-tie-breaker
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is CandidateD
Then result seats contain CandidateA
Then result seats contain CandidateG
Then result seats do not contain CandidateB
Then result seats do not contain CandidateC
Then result seats do not contain CandidateE
Then result seats do not contain CandidateF
Then result seats do not contain CandidateH


Scenario: TIE BREAKER: Random - Assign three seats to multiple candidates, tie on 3rd
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 3000 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1000 votes 
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 3
Given use tie breaker random-tie-breaker
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateE
Then result seat #2 is CandidateG


Scenario: TIE BREAKER: Random - Assign three seats to multiple candidates, tie on 5th
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 6000 votes
Given tally has candidate CandidateE with 3000 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 1200 votes 
Given tally has candidate CandidateH with 800 votes
Given algorithm has property numberOfSeats set to 3
Given use tie breaker random-tie-breaker
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is CandidateD
Then result seat #1 is CandidateE
Then result seat #2 is CandidateG

Scenario:  TIE on last seat - Triple tie
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 999 votes
Given tally has candidate CandidateE with 999 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 999 votes
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 2
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is TIE
!-- Then result has 3 seats
!-- Then result seat #0 is CandidateD
!-- Then result seat #1 is CandidateE
!-- Then result seat #2 is CandidateG

Scenario:  TIE out of seats - Assign one seats to multiple candidates
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 75 votes
Given tally has candidate CandidateD with 999 votes
Given tally has candidate CandidateE with 999 votes
Given tally has candidate CandidateF with 800 votes
Given tally has candidate CandidateG with 999 votes
Given tally has candidate CandidateH with 100 votes
Given algorithm has property numberOfSeats set to 1
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 1 seats
Then result seat #0 is CandidateA

Scenario:  Not enought candidates 
!-- WRONG: Error ArrayIndexOutOfBoundsException
Given empty scenario
Given tally has candidate CandidateA with 1000 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 100 votes
Given algorithm has property numberOfSeats set to 4
Given algorithm has property groupSeatsPerCandidate set to true
When process with RankByVotes-extended method
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is CandidateA
Then result seat #1 is CandidateB
Then result seat #2 is CandidateC

