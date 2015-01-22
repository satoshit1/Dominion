/**
 * $Id$
 * @author lcappuccio
 * @date   Jan 22, 2015 10:44:25 AM
 *
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package org.jseats.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Properties;

import org.jseats.model.Candidate;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.jseats.model.methods.ByVotesRankMethod;
import org.jseats.model.tie.RandomTieBreaker;
import org.jseats.model.tie.TieBreaker;
import org.junit.Before;
import org.junit.Test;

public class MaxVotesShould {

	ByVotesRankMethod sut = new ByVotesRankMethod();

	@Before
	public void setUp() {
	}

	@Test(expected = SeatAllocationException.class)
	public void not_fail_on_a_null_tally() throws SeatAllocationException {
		sut.process(null, mock(Properties.class), mock(TieBreaker.class));
	}

	@Test(expected = SeatAllocationException.class)
	public void not_fail_on_null_tibreaker() throws SeatAllocationException {
		sut.process(mock(Tally.class), mock(Properties.class), null);
	}
	
	@Test(expected = SeatAllocationException.class)
	public void not_fail_on_null_properties() throws SeatAllocationException {
		sut.process(mock(Tally.class), null, mock(TieBreaker.class));
	}

	@Test
	public void elect_candidateA() throws SeatAllocationException {
		Candidate candidateA = new Candidate("candidateA", 10);
		Candidate candidateB = new Candidate("candidateB", 0);
		Tally tally = new Tally();
		Properties properties = new Properties();
		properties.put("numberOfSeats", 1);
		RandomTieBreaker tieBreaker = new RandomTieBreaker();
		tally.addCandidate(candidateA);
		tally.addCandidate(candidateB);
		System.out.println();
		assertEquals(candidateA, sut.process(tally, properties, tieBreaker).getSeatAt(0));
	}
}
