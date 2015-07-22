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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jseats.model.Candidate;

public abstract class ByVotesTieBreaker extends BaseTieBreaker {

	@Override
	public TieScenario innerBreakTie(List<Candidate> candidates) {
			
		final Comparator<Candidate> candidateComparator = Comparator.comparingInt(Candidate::getVotes);
			
		int filteredVotesValue = filterFunction(candidates.stream(), candidateComparator).get().getVotes();
		List<Candidate> untiedCandidates = candidates.stream().filter(candidate -> candidate.getVotes() == filteredVotesValue).collect(Collectors.toList());
		
		if(untiedCandidates.size() == 1){
			return new TieScenario(untiedCandidates, TieScenario.SOLVED);
		} else {
			return new TieScenario(untiedCandidates, TieScenario.TIED);
		}
	}
	
	public abstract Optional<Candidate> filterFunction(Stream<Candidate> candidatesStream, Comparator<Candidate> candidateComparator);

}
