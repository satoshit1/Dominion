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

import java.util.List;

import org.jseats.model.Candidate;

public class MaxVotesTieBreaker extends BaseTieBreaker {

	@Override
	public String getName() {
		return "MaxVotes Tie Breaker";
	}

	@Override
	public Candidate innerBreakTie(List<Candidate> candidates) {
		// TODO MMP: Test case when they both contain same votes value
		return candidates.stream().max((c1, c2) -> Integer.compare(c1.getVotes(), c2.getVotes())).get();
	}

}
