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

public class LastOccurenceTieBreaker extends BaseTieBreaker {

	@Override
	public String getName() {
		return "Last Occurence Tie Breaker";
	}

	@Override
	public Candidate innerBreakTie(List<Candidate> candidates) {
		return candidates.get(0);
	}

}
