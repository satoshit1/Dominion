MaxVotes story

Narrative:
In order to select a candidate by simple majority
As a development team
I want to use SimpleMajorityAlgorithm
					 
!-- Scenario:  Candidate list with clear winner from disk
!-- Given use SimpleMajority algorithm
!-- When load "/stories/simpleMajority/simple.tally.xml" tally
!-- When process with selected algorithm
!-- Then single result is "/stories/simpleMajority/simple.result.xml"

Scenario: Candidate list with a clear winner in votes order (27 August 2011 Singaporean presidential election results - http://en.wikipedia.org/wiki/First-past-the-post_voting)
Given empty scenario
Given tally has candidate Tony Tan Keng Yam with 745693 votes
Given tally has candidate Tan Cheng Bock with 738311 votes
Given tally has candidate Tan Jee Say with 530441 votes
Given tally has candidate Tan Kin Lian with 104095 votes
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is Tony Tan Keng Yam
Then result seat #0 isn't Tan Cheng Bock
Then result seat #0 isn't Tan Jee Say
Then result seat #0 isn't Tan Kin Lian
Then result seats contain Tony Tan Keng Yam
Then result seats do not contain Tan Cheng Bock
Then result seats do not contain Tan Jee Say
Then result seats do not contain Tan Kin Lian

Scenario: Candidate list with a clear winner in reverse votes order
Given empty scenario
Given tally has candidate Tan Kin Lian with 104095 votes
Given tally has candidate Tan Jee Say with 530441 votes
Given tally has candidate Tan Cheng Bock with 738311 votes
Given tally has candidate Tony Tan Keng Yam with 745693 votes
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is Tony Tan Keng Yam
Then result seat #0 isn't Tan Cheng Bock
Then result seat #0 isn't Tan Jee Say
Then result seat #0 isn't Tan Kin Lian
Then result seats contain Tony Tan Keng Yam
Then result seats do not contain Tan Cheng Bock
Then result seats do not contain Tan Jee Say
Then result seats do not contain Tan Kin Lian

Scenario: Candidate list with a clear winner in random votes order
Given empty scenario
Given tally has candidate Tan Cheng Bock with 738311 votes
Given tally has candidate Tony Tan Keng Yam with 745693 votes
Given tally has candidate Tan Kin Lian with 104095 votes
Given tally has candidate Tan Jee Say with 530441 votes
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is Tony Tan Keng Yam
Then result seat #0 isn't Tan Cheng Bock
Then result seat #0 isn't Tan Jee Say
Then result seat #0 isn't Tan Kin Lian
Then result seats contain Tony Tan Keng Yam
Then result seats do not contain Tan Cheng Bock
Then result seats do not contain Tan Jee Say
Then result seats do not contain Tan Kin Lian

Scenario: Candidate list with a tie on winner
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
When process with SimpleMajority algorithm
Then result type is TIE
Then result seats contain CandidateA
Then result seats contain CandidateC
Then result seats do not contain CandidateB
Then result seats do not contain CandidateD

Scenario: Candidate list with a tie on looser
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 1500 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 1250 votes
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is CandidateB
Then result seats do not contain CandidateA
Then result seats do not contain CandidateC
Then result seats do not contain CandidateD

Scenario: Candidate list with a tie on 2nd
Given empty scenario
Given tally has candidate CandidateA with 2000 votes
Given tally has candidate CandidateB with 2000 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 3000 votes
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is CandidateD
Then result seats do not contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateC

Scenario: Candidate list with a multiple tie, winner first
Given empty scenario
Given tally has candidate CandidateA with 2000 votes
Given tally has candidate CandidateB with 2000 votes
Given tally has candidate CandidateC with 2000 votes
Given tally has candidate CandidateD with 1300 votes
When process with SimpleMajority algorithm
Then result type is TIE
Then result seats contain CandidateA
Then result seats contain CandidateB
Then result seats contain CandidateC
Then result seats do not contain CandidateD

Scenario: Candidate list with a multiple tie, looser first
Given empty scenario
Given tally has candidate CandidateA with 1600 votes
Given tally has candidate CandidateB with 2000 votes
Given tally has candidate CandidateC with 2000 votes
Given tally has candidate CandidateD with 2000 votes
When process with SimpleMajority algorithm
Then result type is TIE
Then result seats contain CandidateB
Then result seats contain CandidateC
Then result seats contain CandidateD
Then result seats do not contain CandidateA

Scenario: Candidate list with a multiple tie, looser in the middle
Given empty scenario
Given tally has candidate CandidateA with 2000 votes
Given tally has candidate CandidateB with 2000 votes
Given tally has candidate CandidateC with 1600 votes
Given tally has candidate CandidateD with 2000 votes
When process with SimpleMajority algorithm
Then result type is TIE
Then result seats contain CandidateA
Then result seats contain CandidateB
Then result seats contain CandidateD
Then result seats do not contain CandidateC

Scenario: Candidate list with a full tie
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 200 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 200 votes
When process with SimpleMajority algorithm
Then result type is TIE
Then result seats contain CandidateA
Then result seats contain CandidateB
Then result seats contain CandidateC
Then result seats contain CandidateD

Scenario: Candidate list empty
Given empty scenario
Given tally has candidate CandidateA with 0 votes
Given tally has candidate CandidateB with 0 votes
Given tally has candidate CandidateC with 0 votes
Given tally has candidate CandidateD with 0 votes
When process with SimpleMajority algorithm
Then result type is CANDIDATES_NO_VOTES
Then result seats do not contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateC
Then result seats do not contain CandidateD


!-- TIE BREAKER: First Occurrence. Same scenarios but with tiebreaker FirstOccurrence

Scenario: Candidate list with a clear winner in votes order (27 August 2011 Singaporean presidential election results - http://en.wikipedia.org/wiki/First-past-the-post_voting)
Given empty scenario
Given tally has candidate Tony Tan Keng Yam with 745693 votes
Given tally has candidate Tan Cheng Bock with 738311 votes
Given tally has candidate Tan Jee Say with 530441 votes
Given tally has candidate Tan Kin Lian with 104095 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is Tony Tan Keng Yam
Then result seat #0 isn't Tan Cheng Bock
Then result seat #0 isn't Tan Jee Say
Then result seat #0 isn't Tan Kin Lian
Then result seats contain Tony Tan Keng Yam
Then result seats do not contain Tan Cheng Bock
Then result seats do not contain Tan Jee Say
Then result seats do not contain Tan Kin Lian

Scenario: Candidate list with a clear winner in reverse votes order
Given empty scenario
Given tally has candidate Tan Kin Lian with 104095 votes
Given tally has candidate Tan Jee Say with 530441 votes
Given tally has candidate Tan Cheng Bock with 738311 votes
Given tally has candidate Tony Tan Keng Yam with 745693 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is Tony Tan Keng Yam
Then result seat #0 isn't Tan Cheng Bock
Then result seat #0 isn't Tan Jee Say
Then result seat #0 isn't Tan Kin Lian
Then result seats contain Tony Tan Keng Yam
Then result seats do not contain Tan Cheng Bock
Then result seats do not contain Tan Jee Say
Then result seats do not contain Tan Kin Lian

Scenario: Candidate list with a clear winner in random votes order
Given empty scenario
Given tally has candidate Tan Cheng Bock with 738311 votes
Given tally has candidate Tony Tan Keng Yam with 745693 votes
Given tally has candidate Tan Kin Lian with 104095 votes
Given tally has candidate Tan Jee Say with 530441 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is Tony Tan Keng Yam
Then result seat #0 isn't Tan Cheng Bock
Then result seat #0 isn't Tan Jee Say
Then result seat #0 isn't Tan Kin Lian
Then result seats contain Tony Tan Keng Yam
Then result seats do not contain Tan Cheng Bock
Then result seats do not contain Tan Jee Say
Then result seats do not contain Tan Kin Lian

Scenario: Candidate list with a tie on winner
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 150 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 125 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seats contain CandidateA
Then result seats do not contain CandidateC
Then result seats do not contain CandidateB
Then result seats do not contain CandidateD

Scenario: Candidate list with a tie on looser
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 1500 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 1250 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is CandidateB
Then result seats do not contain CandidateA
Then result seats do not contain CandidateC
Then result seats do not contain CandidateD

Scenario: Candidate list with a tie on 2nd
Given empty scenario
Given tally has candidate CandidateA with 2000 votes
Given tally has candidate CandidateB with 2000 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 3000 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is CandidateD
Then result seats do not contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateC

Scenario: Candidate list with a multiple tie, winner first
Given empty scenario
Given tally has candidate CandidateA with 2000 votes
Given tally has candidate CandidateB with 2000 votes
Given tally has candidate CandidateC with 2000 votes
Given tally has candidate CandidateD with 1300 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seats contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateC
Then result seats do not contain CandidateD

Scenario: Candidate list with a multiple tie, looser first
Given empty scenario
Given tally has candidate CandidateA with 1600 votes
Given tally has candidate CandidateB with 2000 votes
Given tally has candidate CandidateC with 2000 votes
Given tally has candidate CandidateD with 2000 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seats contain CandidateB
Then result seats do not contain CandidateC
Then result seats do not contain CandidateD
Then result seats do not contain CandidateA

Scenario: Candidate list with a multiple tie, looser in the middle
Given empty scenario
Given tally has candidate CandidateA with 2000 votes
Given tally has candidate CandidateB with 2000 votes
Given tally has candidate CandidateC with 1600 votes
Given tally has candidate CandidateD with 2000 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seats contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateD
Then result seats do not contain CandidateC

Scenario: Candidate list with a full tie
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 200 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 200 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seats contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateC
Then result seats do not contain CandidateD

Scenario: Candidate list empty
Given empty scenario
Given tally has candidate CandidateA with 0 votes
Given tally has candidate CandidateB with 0 votes
Given tally has candidate CandidateC with 0 votes
Given tally has candidate CandidateD with 0 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is CANDIDATES_NO_VOTES
Then result seats do not contain CandidateA
Then result seats do not contain CandidateB
Then result seats do not contain CandidateC
Then result seats do not contain CandidateD

!-- TIE BREAKER specific: FirstOccurrence

Scenario: Candidate list with a tie at top
Given empty scenario
Given tally has candidate CandidateA with 200 votes
Given tally has candidate CandidateB with 200 votes
Given tally has candidate CandidateC with 100 votes
Given tally has candidate CandidateD with 20 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is CandidateA

Scenario: Candidate list with a tie at bottom
Given empty scenario
Given tally has candidate CandidateA with 20 votes
Given tally has candidate CandidateB with 10 votes
Given tally has candidate CandidateC with 200 votes
Given tally has candidate CandidateD with 200 votes
Given use tie breaker first-occurrence-tie-breaker
When process with SimpleMajority algorithm
Then result type is SINGLE
Then result seat #0 is CandidateC



