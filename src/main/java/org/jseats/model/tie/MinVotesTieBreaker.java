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

public class MinVotesTieBreaker extends BaseTieBreaker {

	@Override
	public String getName() {
		return "MinVotes Tie Breaker";
	}

	@Override
	public Candidate innerBreakTie(List<Candidate> candidates) {
		return candidates.stream().min((c1, c2) -> Integer.compare(c1.getVotes(), c2.getVotes())).get();
	}

}
