/**
 * $Id$
 *
 * @author alvgarcia
 * @date 19/10/2015 10:11
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 * All rights reserved.
 */

package org.jseats.unit;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.methods.DHondtExtendedMethod;
import org.jseats.model.tie.RandomTieBreaker;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DHondtExtendedShould extends DHondtShould {

	@Override
	public void setUpSut() {
		sut = new DHondtExtendedMethod();
	}

	@Test
	public void pass_with_millions_of_votes() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "5");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_BOOZE, 400000000),
						new Candidate(CANDIDATE_NAME_ROYALTY, 700000000),
						new Candidate(CANDIDATE_NAME_ROCK, 300000000),
						new Candidate(CANDIDATE_NAME_POLITICS, 200000000));

		Result result = sut.process(tally, properties, new RandomTieBreaker());

		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_BOOZE), 1);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROYALTY), 3);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROCK), 1);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_POLITICS), 0);
	}

}
