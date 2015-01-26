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

//TODO MMP: Integrate both HondtTieBreak tests in a good way
public class HondtTieBreakBySeats_withFirstOccurrenceAsPreviousProjectsTest {

	@Test
	public void allocate_two_candidates_and_fifteen_seats() throws SeatAllocationException {
		Result result = execute_testcase(15);

		assertThat(result.getType(), is(Result.ResultType.MULTIPLE));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(10));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(5));
	}

	@Test
	public void allocate_two_candidates_and_three_seats() throws SeatAllocationException {
		Result result = execute_testcase(3);

		assertThat(result.getType(), is(Result.ResultType.MULTIPLE));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(2));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(1));
	}

	@Test
	public void allocate_two_candidates_and_two_seats() throws SeatAllocationException {
		Result result = execute_testcase(2);

		assertThat(result.getType(), is(Result.ResultType.MULTIPLE));
		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(1));
	}

	private Result execute_testcase(int numberOfSeats) throws SeatAllocationException {
		SeatAllocatorProcessor jSeatsProcessorHondt = new SeatAllocatorProcessor();
		jSeatsProcessorHondt.setMethodByName("DHondt");
		jSeatsProcessorHondt.setTieBreaker(new FirstOccurrenceTieBreaker());
		jSeatsProcessorHondt.setProperty("groupSeatsPerCandidate", "true"); // Indexes do not matter by our testcase

		Tally tally = new Tally();
		tally.addCandidate(new Candidate("Winner", 50));
		tally.addCandidate(new Candidate("NonWinner", 25));

		jSeatsProcessorHondt.setProperty("numberOfSeats", String.valueOf(numberOfSeats));
		jSeatsProcessorHondt.setTally(tally);

		Result result = jSeatsProcessorHondt.process();
		return result;
	}

}
