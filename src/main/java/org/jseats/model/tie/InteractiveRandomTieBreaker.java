/**
 * $Id$
 *
 * @author alvgarcia
 * @date 25/05/2015 16:39
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 * All rights reserved.
 */

package org.jseats.model.tie;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class InteractiveRandomTieBreaker extends InteractiveTieBreaker {

	private Random random;

	public InteractiveRandomTieBreaker() {
		super();
		initializeFields();
	}


	public InteractiveRandomTieBreaker(InputStream in, Logger out) {
		super(in, out);
		initializeFields();
	}

	@Override
	protected int getChosenCandidate(int size) throws IOException {
		return random.nextInt(size);
	}

	private void initializeFields() {
		random = new Random();
	}

	public void injectRandom(Random random){
		this.random = random;
	}
}
