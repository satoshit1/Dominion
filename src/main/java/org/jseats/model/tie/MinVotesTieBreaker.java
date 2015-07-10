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

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import org.jseats.model.Candidate;

public class MinVotesTieBreaker extends ByVotesTieBreaker {

	@Override
	public String getName() {
		return "MinVotes Tie Breaker";
	}

	@Override
	public Optional<Candidate> filterFunction(Stream<Candidate> candidatesStream, Comparator<Candidate> candidateComparator) {
		return candidatesStream.min(candidateComparator);
	}
}