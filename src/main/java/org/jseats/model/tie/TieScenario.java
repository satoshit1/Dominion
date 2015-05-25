/**
 * $Id$
 *
 * @author alvgarcia
 * @date 25/05/2015 16:12
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 * All rights reserved.
 */

package org.jseats.model.tie;

import org.jseats.model.Candidate;

import java.util.ArrayList;
import java.util.List;

public class TieScenario extends ArrayList<Candidate>  {
	public static final boolean SOLVED = true;
	public static final boolean TIED = !SOLVED;
	private boolean tied;

	public TieScenario(List<Candidate> candidates, boolean isSolved) {
		super(candidates);
		this.tied = !isSolved;
	}

	public boolean isTied() {
		return tied;
	}
}
