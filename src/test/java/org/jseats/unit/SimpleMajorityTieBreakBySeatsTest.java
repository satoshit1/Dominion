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
import org.junit.Test;

/*
 * Simple Majority is exclusively a SINGLE result type method. It ignores the number of requested seats, there is always a single winner.
 * 
 */
//TODO MMP: Integrate with both HondtTieBreak tests in a good way
public class SimpleMajorityTieBreakBySeatsTest {

	@Test
	public void allocate_two_candidates_and_fifteen_seats() throws SeatAllocationException {
		Result result = execute_testcase(15);

		assertThat(result.getType(), is(Result.ResultType.SINGLE));
		assertThat(result.getNumerOfSeats(), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(0));
	}

	@Test
	public void allocate_two_candidates_and_three_seats() throws SeatAllocationException {
		Result result = execute_testcase(3);

		assertThat(result.getType(), is(Result.ResultType.SINGLE));
		assertThat(result.getNumerOfSeats(), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(0));
	}

	@Test
	public void allocate_two_candidates_and_two_seats() throws SeatAllocationException {
		Result result = execute_testcase(2);

		assertThat(result.getType(), is(Result.ResultType.SINGLE));
		assertThat(result.getNumerOfSeats(), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(0));
	}

	@Test
	public void allocate_two_candidates_and_one_seat() throws SeatAllocationException {
		Result result = execute_testcase(1);

		assertThat(result.getType(), is(Result.ResultType.SINGLE));
		assertThat(result.getNumerOfSeats(), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(0));
	}

	private Result execute_testcase(int numberOfSeats) throws SeatAllocationException {
		SeatAllocatorProcessor jSeatsProcessorHondt = new SeatAllocatorProcessor();
		jSeatsProcessorHondt.setMethodByName("SimpleMajority");
		jSeatsProcessorHondt.setTieBreaker(new FirstOccurrenceTieBreaker());
		jSeatsProcessorHondt.setProperty("groupSeatsPerCandidate", "true"); // Indexes do not matter by our testcase, but
																			// anyway
																			// there is never further than the first
		Tally tally = new Tally();
		tally.addCandidate(new Candidate("Winner", 50));
		tally.addCandidate(new Candidate("NonWinner", 50));

		jSeatsProcessorHondt.setProperty("numberOfSeats", String.valueOf(numberOfSeats));
		jSeatsProcessorHondt.setTally(tally);

		Result result = jSeatsProcessorHondt.process();
		return result;
	}

}
