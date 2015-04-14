/**
 * $Id$
 *
 * @author lcappuccio
 * @date 09/04/2015 17:25
 *
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package org.jseats.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Seat implements Comparable<Seat> {
	
	private Candidate candidate;
	@XmlAttribute
	private int seatNumber;

	@SuppressWarnings("unused")
	//Used by Unmarshaller
	private Seat() {
	}

	public Seat(Candidate candidate, int seatNumber) {
		if (candidate == null) {
			throw new IllegalArgumentException("Candidate cannot be null");
		}
		this.candidate = candidate;
		this.seatNumber = seatNumber;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public int getSeatNumber() {
		return seatNumber;
	}
	
	public int getVotes() {
		return this.candidate.getVotes();
	}

	public String getName() {
		return this.candidate.getName();
	}

	@Override
	public int compareTo(Seat o) {
		if(o == null)
			return -1;
		return seatNumber - o.seatNumber;
	}
}
