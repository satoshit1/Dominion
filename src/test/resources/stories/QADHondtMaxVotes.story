US#1764 - Tie-breaker - D'Hondt and MaxVotes story
Narrative:
In order to assign seats according to Highest Averages with a D'Hondt divisor and 
resolve ties asigning the seat to the voting option that has more votes, in case tie stills
the tie breaker resolve to first occurrence
As a development team
I want to use DHondt with MaxVotes as tie breaker
 

Scenario: #1 Tie on total votes, even number of seats 
Given empty scenario
Given tally has candidate Party A with 100 votes
Given tally has candidate Party B with 100 votes
Given tally has candidate Party C with 20 votes
Given tally has candidate Party D with 10 votes
Given algorithm has property numberOfSeats set to 4
Given tally has 400 potential votes
Given use tie breaker max-votes-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 4 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then result seat #2 is Party A
Then result seat #3 is Party B
Then tally has 230 effective votes

Scenario: #2 Tie on total votes, odd number of seats
Given empty scenario
Given tally has candidate Party A with 100 votes
Given tally has candidate Party B with 100 votes
Given tally has candidate Party C with 20 votes
Given tally has candidate Party D with 10 votes
Given algorithm has property numberOfSeats set to 3
Given tally has 400 potential votes
Given use tie breaker max-votes-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then result seat #2 is Party A


Scenario: #3 Tie on second seat, one of the ties is out
Given empty scenario
Given tally has candidate Party A with 20 votes
Given tally has candidate Party B with 50 votes
Given tally has candidate Party C with 100 votes
Given tally has candidate Party D with 10 votes
Given algorithm has property numberOfSeats set to 2
Given tally has 200 potential votes
Given use tie breaker max-votes-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 2 seats
Then result seat #0 is Party C
Then result seat #1 is Party C
Then tally has 180 effective votes

Scenario: #4 Tie on second seat, no tie breaker
Given empty scenario
Given tally has candidate Party A with 20 votes
Given tally has candidate Party B with 50 votes
Given tally has candidate Party C with 100 votes
Given tally has candidate Party D with 10 votes
Given algorithm has property numberOfSeats set to 2
Given tally has 200 potential votes
When process with DHondt algorithm
Then result type is TIE
Then result has 2 seats
Then result seat #0 is Party B
Then result seat #1 is Party C
Then tally has 180 effective votes

Scenario: #5 Tie on second seat, both of the ties are in
Given empty scenario
Given tally has candidate Party A with 35 votes
Given tally has candidate Party B with 50 votes
Given tally has candidate Party C with 100 votes
Given tally has candidate Party D with 10 votes
Given algorithm has property numberOfSeats set to 4
Given tally has 200 potential votes
Given use tie breaker max-votes-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 4 seats
Then result seat #0 is Party C
Then result seat #1 is Party C
Then result seat #2 is Party B
Then result seat #3 is Party A
Then tally has 195 effective votes

Scenario: #6 Tie on second seat, both of the ties are in, no tie-breaker
Given empty scenario
Given tally has candidate Party A with 35 votes
Given tally has candidate Party B with 50 votes
Given tally has candidate Party C with 100 votes
Given tally has candidate Party D with 10 votes
Given algorithm has property numberOfSeats set to 4
Given tally has 200 potential votes
When process with DHondt algorithm
Then result type is TIE
Then result has 2 seats
Then result seat #0 is Party B
Then result seat #1 is Party C
Then tally has 195 effective votes

Scenario: #7 Tie out of range
Given empty scenario
Given tally has candidate Party A with 15 votes
Given tally has candidate Party B with 20 votes
Given tally has candidate Party C with 60 votes
Given tally has candidate Party D with 100 votes
Given algorithm has property numberOfSeats set to 5
Given tally has 200 potential votes
Given use tie breaker max-votes-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 5 seats
Then result seat #0 is Party D
Then result seat #1 is Party C
Then result seat #2 is Party D
Then result seat #3 is Party D
Then result seat #4 is Party C
Then tally has 195 effective votes

Scenario: #8 Tie on three, number of votes
Given empty scenario
Given tally has candidate Party A with 100 votes
Given tally has candidate Party B with 20 votes
Given tally has candidate Party C with 100 votes
Given tally has candidate Party D with 100 votes
Given algorithm has property numberOfSeats set to 2
Given tally has 200 potential votes
Given use tie breaker max-votes-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 2 seats
Then result seat #0 is Party A
Then result seat #1 is Party C
Then tally has 320 effective votes

Scenario: #9 Tie on three, tie on quotient
Given empty scenario
Given tally has candidate Party A with 100 votes
Given tally has candidate Party B with 20 votes
Given tally has candidate Party C with 50 votes
Given tally has candidate Party D with 25 votes
Given algorithm has property numberOfSeats set to 8
Given tally has 200 potential votes
Given use tie breaker max-votes-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 8 seats
Then result seat #0 is Party A
Then result seat #1 is Party A
Then result seat #2 is Party C
Then result seat #3 is Party A
Then result seat #4 is Party A
Then result seat #5 is Party C
Then result seat #6 is Party D
Then result seat #7 is Party A
Then tally has 195 effective votes

Scenario: #10 Tie on quotient, decimal counts
Given empty scenario
Given tally has candidate Party A with 97 votes
Given tally has candidate Party B with 49 votes
Given tally has candidate Party C with 74 votes
Given tally has candidate Party D with 25 votes
Given algorithm has property numberOfSeats set to 10
Given tally has 200 potential votes
Given use tie breaker max-votes-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 10 seats
Then result seat #0 is Party A
Then result seat #1 is Party C
Then result seat #2 is Party B
Then result seat #3 is Party A
Then result seat #4 is Party C
Then result seat #5 is Party A
Then result seat #6 is Party D
Then result seat #7 is Party C
Then result seat #8 is Party B
Then result seat #9 is Party A
Then tally has 245 effective votes