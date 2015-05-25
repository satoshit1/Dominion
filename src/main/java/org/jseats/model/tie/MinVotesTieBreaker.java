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

package org.jseats.model.tie;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jseats.model.Candidate;

public class MinVotesTieBreaker extends BaseTieBreaker {

	@Override
	public String getName() {
		return "MinVotes Tie Breaker";
	}

	@Override
	public List<Candidate> innerBreakTie(List<Candidate> candidates) {
		// TODO MMP: Test case when they both contain same votes value
		final Comparator<Candidate> candidateComparator = (c1, c2) -> Integer.compare(c1.getVotes(), c2.getVotes());
		Collections.sort(candidates, candidateComparator);
		return candidates;
	}

}
