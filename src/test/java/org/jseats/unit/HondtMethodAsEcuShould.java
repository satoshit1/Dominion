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
import org.jseats.model.tie.FirstOccurenceTieBreaker;
import org.junit.Test;

public class HondtMethodAsEcuShould {

	@Test
	public void allocate_two_candidates_and_15_seats() throws SeatAllocationException {
		SeatAllocatorProcessor jSeatsProcessorHondt = new SeatAllocatorProcessor();
		jSeatsProcessorHondt.setMethodByName("DHondt");
		jSeatsProcessorHondt.setTieBreaker(new FirstOccurenceTieBreaker());
		jSeatsProcessorHondt.setProperty("groupSeatsPerCandidate", "true");

		Tally tally = new Tally();
		tally.addCandidate(new Candidate("Winner", 50));
		tally.addCandidate(new Candidate("NonWinner", 25));
		jSeatsProcessorHondt.setProperty("numberOfSeats", "15");

		jSeatsProcessorHondt.setTally(tally);

		Result result = jSeatsProcessorHondt.process();
		assertThat(result.getType(), is(Result.ResultType.MULTIPLE));

		System.out.println(result.getSeats());

		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(10));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(5));
	}

	@Test
	public void allocate_two_candidates_and_three_seats() throws SeatAllocationException {
		SeatAllocatorProcessor jSeatsProcessorHondt = new SeatAllocatorProcessor();
		jSeatsProcessorHondt.setMethodByName("DHondt");
		jSeatsProcessorHondt.setTieBreaker(new FirstOccurenceTieBreaker());
		jSeatsProcessorHondt.setProperty("groupSeatsPerCandidate", "true"); // Indexes does not mather for now

		Tally tally = new Tally();
		tally.addCandidate(new Candidate("Winner", 50));
		tally.addCandidate(new Candidate("NonWinner", 25));
		jSeatsProcessorHondt.setProperty("numberOfSeats", "3");

		jSeatsProcessorHondt.setTally(tally);

		Result result = jSeatsProcessorHondt.process();
		assertThat(result.getType(), is(Result.ResultType.MULTIPLE));

		System.out.println(result.getSeats());

		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(2));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(1));
	}

	@Test
	public void allocate_two_candidates_and_two_seats() throws SeatAllocationException {
		SeatAllocatorProcessor jSeatsProcessorHondt = new SeatAllocatorProcessor();
		jSeatsProcessorHondt.setMethodByName("DHondt");
		jSeatsProcessorHondt.setTieBreaker(new FirstOccurenceTieBreaker());
		jSeatsProcessorHondt.setProperty("groupSeatsPerCandidate", "true");

		Tally tally = new Tally();
		tally.addCandidate(new Candidate("Winner", 50));
		tally.addCandidate(new Candidate("NonWinner", 25));
		jSeatsProcessorHondt.setProperty("numberOfSeats", "2");

		jSeatsProcessorHondt.setTally(tally);

		Result result = jSeatsProcessorHondt.process();
		assertThat(result.getType(), is(Result.ResultType.MULTIPLE));

		System.out.println(result.getSeats());

		assertThat(result.getNumberOfSeatsForCandidate("Winner"), is(1));
		assertThat(result.getNumberOfSeatsForCandidate("NonWinner"), is(1));
	}

}
