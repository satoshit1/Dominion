package org.jseats.unit;

import org.jseats.model.Candidate;
import org.jseats.model.SeatAllocationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CandidateModelTest {

	@Test
	public void hasVotes() {

		Candidate candidate = new Candidate("A");

		assertFalse(candidate.hasVotes());

		candidate.setVotes(100);

		assertTrue(candidate.hasVotes());

		candidate.hasVotes(false);

		assertFalse(candidate.hasVotes());
	}

	@Test
	public void hasVotesFromConstructor() {

		Candidate candidate = new Candidate("A", 100);

		assertTrue(candidate.hasVotes());

		candidate.hasVotes(false);

		assertFalse(candidate.hasVotes());
	}

	@Test
	public void setsProperties() throws SeatAllocationException {

		Candidate candidate = new Candidate("A", 100);
		
		candidate.setProperty("gender", "male");
		
		assertEquals("male", candidate.getProperty("gender"));

		candidate = Candidate.fromString("B:200:gender=woman");
		assertEquals("woman", candidate.getProperty("gender"));

		candidate = Candidate.fromString("B:200:gender=");
		assertEquals(null, candidate.getProperty("gender"));

		candidate = Candidate.fromString("B:200:gender=alien:minority=yes");
		assertEquals("alien", candidate.getProperty("gender"));
		assertEquals("yes", candidate.getProperty("minority"));

		candidate = new Candidate("C", 300);
		candidate.setProperty("minority", "no");
		assertEquals("C:300:minority=no", candidate.toString());
		candidate.setProperty("gender", "man");
		assertEquals("C:300:gender=man:minority=no", candidate.toString());

		candidate.setProperty("minority", null);
		assertEquals("C:300:gender=man", candidate.toString());
		assertTrue(candidate.propertyIs("gender", "man"));
	}

	@Test
	public void compareTo(){
		assertEquals(new Candidate("Booze", 100).compareTo(new Candidate("Politics", 100)), -14);
		assertFalse(new Candidate("Booze", 100).equals(new Candidate("Politics", 100)));
		assertTrue(new Candidate("Booze", 100).equals(new Candidate("Booze", 50)));
	}
}
