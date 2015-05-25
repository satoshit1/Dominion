package org.jseats.model.tie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jseats.model.Candidate;

public abstract class BaseTieBreaker implements TieBreaker {

	@Override
	public List<Candidate> breakTie(List<Candidate> candidates) {
		return innerBreakTie(new ArrayList<>(candidates));
	}
	
	@Override
	public List<Candidate> breakTie(Candidate... candidates) {
		return innerBreakTie(new ArrayList<>(Arrays.asList(candidates)));
	}

	public abstract List<Candidate> innerBreakTie(List<Candidate> candidates);

}
