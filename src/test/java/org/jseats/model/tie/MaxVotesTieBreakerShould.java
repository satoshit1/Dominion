package org.jseats.model.tie;

import org.hamcrest.MatcherAssert;
import org.jseats.model.Candidate;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * $Id$
 *
 * @author alvgarcia
 * @date 25/05/2015 14:17
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 * All rights reserved.
 */
public class MaxVotesTieBreakerShould {

	@Test
	public void spike1() {

		MaxVotesTieBreaker sut = new MaxVotesTieBreaker();
		Candidate candidateA = new Candidate("A",50);
		Candidate candidateB = new Candidate("B",100);
		List<Candidate> sortedCandidates = sut.innerBreakTie(
				Arrays.asList(
						candidateB
						,
						candidateA
				));

		MatcherAssert.assertThat(sortedCandidates.get(0), is(candidateB));
		MatcherAssert.assertThat(sortedCandidates.get(1), is(candidateA));
	}

}