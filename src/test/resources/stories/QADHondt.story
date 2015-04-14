D'Hondt story

Narrative:
In order to assign seats according to Highest Averages with a D'Hondt divisor
As a development team
I want to use DHondt


Scenario: DHondt scenario (http://en.wikipedia.org/wiki/Largest_remainder_method)
Given empty scenario
Given tally has candidate Yellows with 47000 votes
Given tally has candidate Whites with 16000 votes
Given tally has candidate Reds with 15900 votes
Given tally has candidate Greens with 12000 votes
Given tally has candidate Blues with 5000 votes
Given tally has candidate Pinks with 3100 votes
Given tally has 100000 potential votes
Given algorithm has property numberOfSeats set to 10
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 10 seats
Then result seat #0 is Yellows
Then result seat #1 is Yellows
Then result seat #2 is Whites
Then result seat #3 is Reds
Then result seat #4 is Yellows
Then result seat #5 is Greens
Then result seat #6 is Yellows
Then result seat #7 is Yellows
Then result seat #8 is Whites
Then result seat #9 is Reds
Given algorithm has property groupSeatsPerCandidate set to true
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 10 seats
Then result seat #0 is Yellows
Then result seat #1 is Yellows
Then result seat #2 is Yellows
Then result seat #3 is Yellows
Then result seat #4 is Yellows
Then result seat #5 is Whites
Then result seat #6 is Whites
Then result seat #7 is Reds
Then result seat #8 is Reds
Then result seat #9 is Greens
Then result has 5 seats for Yellows
Then result has 2 seats for Whites
Then result has 2 seats for Reds
Then result has 1 seat for Greens
Then result seats do not contain Blues
Then result seats do not contain Pinks
Then tally has 99000 effective votes

Scenario: DHondt scenario from wikipedia (http://en.wikipedia.org/wiki/D%27Hondt_method)
Given empty scenario
Given tally has candidate Party A with 100000 votes
Given tally has candidate Party B with 80000 votes
Given tally has candidate Party C with 30000 votes
Given tally has candidate Party D with 20000 votes
Given algorithm has property numberOfSeats set to 8
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 8 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then result seat #2 is Party A
Then result seat #3 is Party B
Then result seat #4 is Party A
Then result seat #5 is Party C
Then result seat #6 is Party B
Then result seat #7 is Party A
Given algorithm has property groupSeatsPerCandidate set to true
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 8 seats
Then result seat #0 is Party A
Then result seat #1 is Party A
Then result seat #2 is Party A
Then result seat #3 is Party A
Then result seat #4 is Party B
Then result seat #5 is Party B
Then result seat #6 is Party B
Then result seat #7 is Party C
Then result has 4 seats for Party A
Then result has 3 seats for Party B
Then result has 1 seats for Party C
Then result seats do not contain Party D
Then tally has 230000 effective votes

Scenario: DHondt scenario from wikipedia (http://es.wikipedia.org/wiki/Sistema_D%27Hondt - Exemple 2)
Given empty scenario
Given tally has candidate Party A with 391000 votes
Given tally has candidate Party B with 311000 votes
Given tally has candidate Party C with 184000 votes
Given tally has candidate Party D with 73000 votes
Given tally has candidate Party E with 27000 votes
Given tally has candidate Party F with 12000 votes
Given tally has candidate Party G with 2000 votes
Given algorithm has property numberOfSeats set to 21
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 21 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then result seat #2 is Party A
Then result seat #3 is Party C
Then result seat #4 is Party B
Then result seat #5 is Party A
Then result seat #6 is Party B
Then result seat #7 is Party A
Then result seat #8 is Party C
Then result seat #9 is Party A
Then result seat #10 is Party B
Then result seat #11 is Party D
Then result seat #12 is Party A
Then result seat #13 is Party B
Then result seat #14 is Party C
Then result seat #15 is Party A
Then result seat #16 is Party B
Then result seat #17 is Party A
Then result seat #18 is Party C
Then result seat #19 is Party B
Then result seat #20 is Party A
Given algorithm has property groupSeatsPerCandidate set to true
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 21 seats
Then result seat #0 is Party A
Then result seat #1 is Party A
Then result seat #2 is Party A
Then result seat #3 is Party A
Then result seat #4 is Party A
Then result seat #5 is Party A
Then result seat #6 is Party A
Then result seat #7 is Party A
Then result seat #8 is Party A
Then result seat #9 is Party B
Then result seat #10 is Party B
Then result seat #11 is Party B
Then result seat #12 is Party B
Then result seat #13 is Party B
Then result seat #14 is Party B
Then result seat #15 is Party B
Then result seat #16 is Party C
Then result seat #17 is Party C
Then result seat #18 is Party C
Then result seat #19 is Party C
Then result seat #20 is Party D
Then result seats do not contain Party E
Then result seats do not contain Party F
Then result seats do not contain Party G
Then tally has 1000000 effective votes

Scenario: TIE on votes and last seat - DHondt scenario
!-- Results: A B A/B
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given tally has candidate Party B with 20000 votes
Given tally has candidate Party C with 3000 votes
Given tally has candidate Party D with 2500 votes
Given algorithm has property numberOfSeats set to 3
When process with DHondt algorithm
Then result type is TIE
Then result has 2 seats
Then result seats contain Party A
Then result seats contain Party B
Then tally has 45500 effective votes

Scenario: TIE on votes and BUT NOT last seat 
!-- Results: A B A B
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given tally has candidate Party B with 20000 votes
Given tally has candidate Party C with 3000 votes
Given tally has candidate Party D with 2500 votes
Given algorithm has property numberOfSeats set to 4
Given algorithm has property groupSeatsPerCandidate set to true
When process with DHondt algorithm
Then result type is TIE
Then result has 2 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then tally has 45500 effective votes

Scenario: TIE on last seat but not on votes, tie on last seat
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given tally has candidate Party B with 10000 votes
Given tally has candidate Party C with 3000 votes
Given tally has candidate Party D with 2500 votes
Given algorithm has property numberOfSeats set to 2
When process with DHondt algorithm
Then result type is TIE
Then result has 2 seats
Then result seats contain Party A
Then result seats contain Party B
Then tally has 35500 effective votes

Scenario: TIE on last seat but not on votes, don't tie on last seat
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given tally has candidate Party B with 10000 votes
Given tally has candidate Party C with 3000 votes
Given tally has candidate Party D with 2500 votes
Given algorithm has property numberOfSeats set to 3
When process with DHondt algorithm
Then result type is TIE
Then result has 2 seats
Then result seats contain Party A
Then result seats contain Party B
Then tally has 35500 effective votes

Scenario: Only one candidate
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given algorithm has property numberOfSeats set to 4
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 4 seats
Then result seat #0 is Party A
Then result seat #1 is Party A
Then result seat #2 is Party A
Then result seat #3 is Party A
Then tally has 20000 effective votes

Scenario: Effective votes > Potential votes
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given tally has candidate Party B with 9000 votes
Given tally has candidate Party C with 12000 votes
Given algorithm has property numberOfSeats set to 4
Given tally has 35000 potential votes
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 4 seats
Then result seat #0 is Party A
Then result seat #1 is Party C
Then result seat #2 is Party A
Then result seat #3 is Party B
Then tally has 41000 effective votes

Scenario: Candidates list empty
Given empty scenario
Given tally has candidate Party A with 0 votes
Given tally has candidate Party B with 0 votes
Given tally has candidate Party C with 0 votes
Given algorithm has property numberOfSeats set to 2
Given tally has 0 potential votes
When process with DHondt algorithm
Then result type is CANDIDATES_NO_VOTES
Then result has 0 seats
Then result seats do not contain Party A
Then result seats do not contain Party B
Then tally has 0 effective votes

Scenario: Tie out of chosen candidates
Given empty scenario
Given tally has candidate Party A with 220 votes
Given tally has candidate Party B with 200 votes
Given tally has candidate Party C with 100 votes
Given algorithm has property numberOfSeats set to 2
Given tally has 600 potential votes
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 2 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then tally has 520 effective votes

!-- TIE BREAKER: First Occurrence. Same scenarios but with Tie Breaker

Scenario: DHondt scenario (http://en.wikipedia.org/wiki/Largest_remainder_method)
Given empty scenario
Given tally has candidate Yellows with 47000 votes
Given tally has candidate Whites with 16000 votes
Given tally has candidate Reds with 15900 votes
Given tally has candidate Greens with 12000 votes
Given tally has candidate Blues with 5000 votes
Given tally has candidate Pinks with 3100 votes
Given tally has 100000 potential votes
Given algorithm has property numberOfSeats set to 10
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 10 seats
Then result seat #0 is Yellows
Then result seat #1 is Yellows
Then result seat #2 is Whites
Then result seat #3 is Reds
Then result seat #4 is Yellows
Then result seat #5 is Greens
Then result seat #6 is Yellows
Then result seat #7 is Yellows
Then result seat #8 is Whites
Then result seat #9 is Reds
Given algorithm has property groupSeatsPerCandidate set to true
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 10 seats
Then result seat #0 is Yellows
Then result seat #1 is Yellows
Then result seat #2 is Yellows
Then result seat #3 is Yellows
Then result seat #4 is Yellows
Then result seat #5 is Whites
Then result seat #6 is Whites
Then result seat #7 is Reds
Then result seat #8 is Reds
Then result seat #9 is Greens
Then result has 5 seats for Yellows
Then result has 2 seats for Whites
Then result has 2 seats for Reds
Then result has 1 seat for Greens
Then result seats do not contain Blues
Then result seats do not contain Pinks
Then tally has 99000 effective votes

Scenario: DHondt scenario from wikipedia (http://en.wikipedia.org/wiki/D%27Hondt_method)
Given empty scenario
Given tally has candidate Party A with 100000 votes
Given tally has candidate Party B with 80000 votes
Given tally has candidate Party C with 30000 votes
Given tally has candidate Party D with 20000 votes
Given algorithm has property numberOfSeats set to 8
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 8 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then result seat #2 is Party A
Then result seat #3 is Party B
Then result seat #4 is Party A
Then result seat #5 is Party C
Then result seat #6 is Party B
Then result seat #7 is Party A
Given algorithm has property groupSeatsPerCandidate set to true
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 8 seats
Then result seat #0 is Party A
Then result seat #1 is Party A
Then result seat #2 is Party A
Then result seat #3 is Party A
Then result seat #4 is Party B
Then result seat #5 is Party B
Then result seat #6 is Party B
Then result seat #7 is Party C
Then result has 4 seats for Party A
Then result has 3 seats for Party B
Then result has 1 seats for Party C
Then result seats do not contain Party D
Then tally has 230000 effective votes

Scenario: DHondt scenario from wikipedia (http://es.wikipedia.org/wiki/Sistema_D%27Hondt - Exemple 2)
Given empty scenario
Given tally has candidate Party A with 391000 votes
Given tally has candidate Party B with 311000 votes
Given tally has candidate Party C with 184000 votes
Given tally has candidate Party D with 73000 votes
Given tally has candidate Party E with 27000 votes
Given tally has candidate Party F with 12000 votes
Given tally has candidate Party G with 2000 votes
Given algorithm has property numberOfSeats set to 21
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 21 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then result seat #2 is Party A
Then result seat #3 is Party C
Then result seat #4 is Party B
Then result seat #5 is Party A
Then result seat #6 is Party B
Then result seat #7 is Party A
Then result seat #8 is Party C
Then result seat #9 is Party A
Then result seat #10 is Party B
Then result seat #11 is Party D
Then result seat #12 is Party A
Then result seat #13 is Party B
Then result seat #14 is Party C
Then result seat #15 is Party A
Then result seat #16 is Party B
Then result seat #17 is Party A
Then result seat #18 is Party C
Then result seat #19 is Party B
Then result seat #20 is Party A
Given algorithm has property groupSeatsPerCandidate set to true
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 21 seats
Then result seat #0 is Party A
Then result seat #1 is Party A
Then result seat #2 is Party A
Then result seat #3 is Party A
Then result seat #4 is Party A
Then result seat #5 is Party A
Then result seat #6 is Party A
Then result seat #7 is Party A
Then result seat #8 is Party A
Then result seat #9 is Party B
Then result seat #10 is Party B
Then result seat #11 is Party B
Then result seat #12 is Party B
Then result seat #13 is Party B
Then result seat #14 is Party B
Then result seat #15 is Party B
Then result seat #16 is Party C
Then result seat #17 is Party C
Then result seat #18 is Party C
Then result seat #19 is Party C
Then result seat #20 is Party D
Then result seats do not contain Party E
Then result seats do not contain Party F
Then result seats do not contain Party G
Then tally has 1000000 effective votes

Scenario: TIE on votes and last seat - DHondt scenario
!-- Results: A B A/B
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given tally has candidate Party B with 20000 votes
Given tally has candidate Party C with 3000 votes
Given tally has candidate Party D with 2500 votes
Given algorithm has property numberOfSeats set to 3
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then result seat #2 is Party A
Then tally has 45500 effective votes

Scenario: TIE on votes and BUT NOT last seat 
!-- Results: A B A B
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given tally has candidate Party B with 20000 votes
Given tally has candidate Party C with 3000 votes
Given tally has candidate Party D with 2500 votes
Given algorithm has property numberOfSeats set to 4
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 4 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then result seat #2 is Party A
Then result seat #3 is Party B
Then tally has 45500 effective votes

Scenario: TIE on last seat but not on votes, tie on last seat
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given tally has candidate Party B with 10000 votes
Given tally has candidate Party C with 3000 votes
Given tally has candidate Party D with 2500 votes
Given algorithm has property numberOfSeats set to 2
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 2 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then tally has 35500 effective votes

Scenario: TIE on last seat but not on votes, don't tie on last seat
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given tally has candidate Party B with 10000 votes
Given tally has candidate Party C with 3000 votes
Given tally has candidate Party D with 2500 votes
Given algorithm has property numberOfSeats set to 3
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 3 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then result seat #0 is Party A
Then tally has 35500 effective votes

Scenario: Only one candidate
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given algorithm has property numberOfSeats set to 4
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 4 seats
Then result seat #0 is Party A
Then result seat #1 is Party A
Then result seat #2 is Party A
Then result seat #3 is Party A
Then tally has 20000 effective votes

Scenario: Effective votes > Potential votes
Given empty scenario
Given tally has candidate Party A with 20000 votes
Given tally has candidate Party B with 9000 votes
Given tally has candidate Party C with 12000 votes
Given algorithm has property numberOfSeats set to 4
Given tally has 35000 potential votes
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 4 seats
Then result seat #0 is Party A
Then result seat #1 is Party C
Then result seat #2 is Party A
Then result seat #3 is Party B
Then tally has 41000 effective votes

Scenario: Candidates list empty
Given empty scenario
Given tally has candidate Party A with 0 votes
Given tally has candidate Party B with 0 votes
Given tally has candidate Party C with 0 votes
Given algorithm has property numberOfSeats set to 2
Given tally has 0 potential votes
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is CANDIDATES_NO_VOTES
Then result has 0 seats
Then result seats do not contain Party A
Then result seats do not contain Party B
Then tally has 0 effective votes

Scenario: Tie out of chosen candidates
Given empty scenario
Given tally has candidate Party A with 220 votes
Given tally has candidate Party B with 200 votes
Given tally has candidate Party C with 100 votes
Given algorithm has property numberOfSeats set to 2
Given tally has 600 potential votes
Given use tie breaker first-occurrence-tie-breaker
When process with DHondt algorithm
Then result type is MULTIPLE
Then result has 2 seats
Then result seat #0 is Party A
Then result seat #1 is Party B
Then tally has 520 effective votes