/**
 * $Id$
 * @author mmiranda
 * @date   23/1/2015 16:16:25
 *
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package org.jseats.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jseats.SeatAllocatorProcessor;
import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.jseats.model.tie.FirstOccurrenceTieBreaker;
import org.jseats.model.tie.TieBreaker;
import org.junit.Test;

/*
 * Simple Majority is exclusively a SINGLE result type method. It ignores the number of requested seats, there is always a single winner.
 * If a TieBreakerIsNotPassed, all draw candidates are returned as winners of 1 seat no matter how many seats we request 
 * 
 */
//TODO MMP: Integrate with both HondtTieBreak tests in a proper way
public class SimpleMajorityTieBreakBySeatsTest {

	@Test
	public void allocate_two_candidates_and_fifteen_seats_without_tie_breaker() throws SeatAllocationException {
		Result result = execute_testcase(15, null);

		assertThat(result.getType(), is(Result.ResultType.TIE));
		assertThat(result.getNumerOfSeats(), is(2));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(1));
	}

	/* FirstOccurrenceTieBreaker test cases */

	@Test
	public void allocate_two_candidates_and_fifteen_seats() throws SeatAllocationException {
		Result result = execute_testcase(15, new FirstOccurrenceTieBreaker());

		assertThat(result.getType(), is(Result.ResultType.SINGLE));
		assertThat(result.getNumerOfSeats(), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(0));
	}

	@Test
	public void allocate_two_candidates_and_three_seats() throws SeatAllocationException {
		Result result = execute_testcase(3, new FirstOccurrenceTieBreaker());

		assertThat(result.getType(), is(Result.ResultType.SINGLE));
		assertThat(result.getNumerOfSeats(), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(0));
	}

	@Test
	public void allocate_two_candidates_and_two_seats() throws SeatAllocationException {
		Result result = execute_testcase(2, new FirstOccurrenceTieBreaker());

		assertThat(result.getType(), is(Result.ResultType.SINGLE));
		assertThat(result.getNumerOfSeats(), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(0));
	}

	@Test
	public void allocate_two_candidates_and_one_seat() throws SeatAllocationException {
		Result result = execute_testcase(1, new FirstOccurrenceTieBreaker());

		assertThat(result.getType(), is(Result.ResultType.SINGLE));
		assertThat(result.getNumerOfSeats(), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(0));
	}

	private Result execute_testcase(int numberOfSeats, TieBreaker tieBreaker) throws SeatAllocationException {
		SeatAllocatorProcessor jSeatsProcessorMaxVotes = new SeatAllocatorProcessor();
		jSeatsProcessorMaxVotes.setMethodByName("SimpleMajority");
		if (tieBreaker != null)
			jSeatsProcessorMaxVotes.setTieBreaker(tieBreaker);
		jSeatsProcessorMaxVotes.setProperty("groupSeatsPerCandidate", "true"); // Indexes do not matter by our testcase,
																				// but anyway there is never further than
																				// the first unless a TIE exception is
																				// thrown
		Tally tally = new Tally();
		tally.addCandidate(new Candidate("Winner", 50));
		tally.addCandidate(new Candidate("NonWinner", 50));

		jSeatsProcessorMaxVotes.setProperty("numberOfSeats", String.valueOf(numberOfSeats));
		jSeatsProcessorMaxVotes.setTally(tally);

		Result result = jSeatsProcessorMaxVotes.process();
		return result;
	}

}
