package org.jseats.model.tie;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.jseats.model.Candidate;

/**
 * $Id$
 *
 * @author mmiranda
 * @date 09/07/2015 14:17
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 * All rights reserved.
 */
public abstract class ByVotesTieBreakerShould {
	
	public void testTie(ByVotesTieBreaker sut) {
		Candidate candidateA = new Candidate("A",100);
		Candidate candidateB = new Candidate("B",100);
		TieScenario tieScenario = testTieBreak(sut, candidateA,candidateB);
		assertThat(tieScenario.isTied(), is(true));
		assertThat(tieScenario.size(), is(2));		
	}
	
	protected TieScenario testTieBreak(ByVotesTieBreaker sut, Candidate... tiedCandidates){
		List<Candidate> candidates = Arrays.asList(tiedCandidates);
		TieScenario tieScenario = sut.innerBreakTie(candidates);
		return tieScenario;
	}

}