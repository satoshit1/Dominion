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
import java.util.List;

import org.jseats.model.Candidate;

public class LastOccurrenceTieBreaker extends BaseTieBreaker {

	@Override
	public String getName() {
		return "Last Occurence Tie Breaker";
	}

	@Override
	public List<Candidate> innerBreakTie(List<Candidate> candidates) {
		Collections.reverse(candidates);
		return candidates;
	}
}
