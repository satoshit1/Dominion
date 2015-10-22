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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || !(obj instanceof Seat)) {
			return false;
		}

		Seat other = (Seat) obj;

		EqualsBuilder eBuilder = new EqualsBuilder();

		eBuilder = eBuilder.append(this.candidate, other.candidate);
		eBuilder = eBuilder.append(this.seatNumber, other.seatNumber);

		return eBuilder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcBuilder = new HashCodeBuilder();
		hcBuilder.append(this.candidate);
		hcBuilder.append(this.seatNumber);
		return hcBuilder.toHashCode();
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		builder.append("candidate", this.candidate);
		builder.append("seatNumber", this.seatNumber);
		return builder.build();
	}
}
