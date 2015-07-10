package org.jseats.model.tie;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.jseats.model.Candidate;
import org.junit.Test;

/**
 * $Id$
 *
 * @author mmiranda
 * @date 09/07/2015 14:17
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 * All rights reserved.
 */
public class MinVotesTieBreakerShould extends ByVotesTieBreakerShould {

	private ByVotesTieBreaker minVotesTieBreaker = new MinVotesTieBreaker();
	
	@Test
	public void testWinner() {
		Candidate candidateA = new Candidate("A",50);
		Candidate candidateB = new Candidate("B",100);
		TieScenario tieScenario = testTieBreak(minVotesTieBreaker,candidateA,candidateB);
		assertThat(tieScenario.isTied(), is(false));
		assertThat(tieScenario.size(), is(1));
		assertThat(tieScenario.get(0), is(candidateA));
	}

	@Test
	public void testWinnerDisordered() {
		Candidate candidateA = new Candidate("A",100);
		Candidate candidateB = new Candidate("B",50);
		TieScenario tieScenario = testTieBreak(minVotesTieBreaker, candidateA,candidateB);
		assertThat(tieScenario.isTied(), is(false));
		assertThat(tieScenario.size(), is(1));
		assertThat(tieScenario.get(0), is(candidateB));
	}
	
	@Test
	public void testTie(){
		super.testTie(minVotesTieBreaker);
	}
}