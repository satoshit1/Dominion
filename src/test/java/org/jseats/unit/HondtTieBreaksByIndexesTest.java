/**
 * $Id$
 * @author mmiranda
 * @date   26/1/2015 10:31:30
 *
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */

package org.jseats.unit;

import static org.junit.Assert.assertSame;

import org.jseats.SeatAllocatorProcessor;
import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.jseats.model.tie.FirstOccurrenceTieBreaker;
import org.jseats.model.tie.LastOccurrenceTieBreaker;
import org.jseats.model.tie.MaxVotesTieBreaker;
import org.jseats.model.tie.MinVotesTieBreaker;
import org.jseats.model.tie.TieBreaker;
import org.junit.Test;

//TODO MMP: Integrate both HondtTieBreak tests in a proper way
public class HondtTieBreaksByIndexesTest {

	@Test
	public void testFirstOccurenceTieBreaker() throws SeatAllocationException {
		// Default in previous projects
		testTieBWins(runTestCase(new FirstOccurrenceTieBreaker()));
	}

	@Test
	public void testLastOccurenceTieBreaker() throws SeatAllocationException {
		testTieAWins(runTestCase(new LastOccurrenceTieBreaker()));
	}

	@Test
	public void testMaxVotesTieBreaker() throws SeatAllocationException {
		testTieAWins(runTestCase(new MaxVotesTieBreaker()));
	}

	@Test
	public void testMinVotesTieBreaker() throws SeatAllocationException {
		testTieBWins(runTestCase(new MinVotesTieBreaker()));
	}

	private void testTieAWins(Result result) {
		assertSame(result.getSeatAt(0).getName(), "A");
		assertSame(result.getSeatAt(1).getName(), "A");
		assertSame(result.getSeatAt(2).getName(), "B");
	}

	private void testTieBWins(Result result) {
		assertSame(result.getSeatAt(0).getName(), "A");
		assertSame(result.getSeatAt(1).getName(), "B");
		assertSame(result.getSeatAt(2).getName(), "A");
	}

	private Result runTestCase(TieBreaker tieBreaker) throws SeatAllocationException {
		SeatAllocatorProcessor tallier = new SeatAllocatorProcessor();
		tallier.setMethodByName("DHondt");
		tallier.setProperty("groupSeatsPerCandidate", "false");// Indexes do matter by our testcase
		tallier.setProperty("numberOfSeats", "3");
		tallier.setTieBreaker(tieBreaker);

		Tally tally = new Tally();
		tally.addCandidate(new Candidate("A", 30));
		tally.addCandidate(new Candidate("B", 15));
		tallier.setTally(tally);
		return tallier.process();
	}
}
