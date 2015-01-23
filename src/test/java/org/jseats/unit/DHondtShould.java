/**
 * $Id$
 * @author lcappuccio
 * @date   Jan 23, 2015 10:41:19 AM
 *
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package org.jseats.unit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Properties;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.jseats.model.methods.DHondtHighestAveragesMethod;
import org.jseats.model.tie.RandomTieBreaker;
import org.jseats.model.tie.TieBreaker;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DHondtShould {

	private Tally tally;
	private Properties properties;
	private DHondtHighestAveragesMethod sut;

	@Before
	public void setUp() {
		tally = new Tally();
		properties = new Properties();
		sut = new DHondtHighestAveragesMethod();
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test(expected = SeatAllocationException.class)
	public void not_fail_on_a_null_tally() throws SeatAllocationException {
		sut.process(null, mock(Properties.class), mock(TieBreaker.class));
	}
	
	@Test
	public void fail_when_no_candidates() throws SeatAllocationException {
		expectedException.expect(SeatAllocationException.class);
		expectedException.expectMessage(equalTo("This tally contains no candidates"));
		Properties mockProperties = mock(Properties.class);
		doReturn("1").when(mockProperties).getProperty(anyString(), anyString());
		sut.process(mock(Tally.class), mockProperties, null);
	}
	
	@Test(expected = SeatAllocationException.class)
	public void not_fail_on_null_properties() throws SeatAllocationException {
		sut.process(mock(Tally.class), null, mock(TieBreaker.class));
	}

	@Test
	public void fail_on_a_unparseable_numberOfSeats_Property() throws SeatAllocationException {
		expectedException.expect(SeatAllocationException.class);
		expectedException.expectMessage(equalTo("numberOfSeats property is not a number: 'AA'"));
		properties.put("numberOfSeats", "AA");
		tally.addCandidate(mock(Candidate.class));
		RandomTieBreaker tieBreaker = new RandomTieBreaker();

		sut.process(tally, properties, tieBreaker);
	}

	@Test
	public void when_not_given_a_numberOfSeats_should_assign_the_same_number_as_numberOfCandidates()
			throws SeatAllocationException {
		Candidate candidateA = new Candidate("candidateA", 10);
		RandomTieBreaker tieBreaker = new RandomTieBreaker();
		tally.addCandidate(candidateA);
		// Do not create a property numberOfSeats
		assertEquals(candidateA, sut.process(tally, properties, tieBreaker).getSeatAt(0));
	}

	@Test
	public void not_allow_negative_numberOfSeats() throws SeatAllocationException {
		properties.put("numberOfSeats", "-2");
		tally.addCandidate(mock(Candidate.class));

		expectedException.expect(SeatAllocationException.class);
		expectedException.expectMessage(equalTo("numberOfSeats is negative: -2"));

		sut.process(tally, properties, mock(TieBreaker.class));
	}

	@Test
	@Ignore
	public void pass_the_acceptance_test_1() throws SeatAllocationException {
		// Using test data set from US: https://redmine.scytl.net/issues/94064
		properties.clear();
		properties.put("numberOfSeats", "5");
		Candidate candidateBooze = new Candidate("Booze", 40);
		Candidate candidateRoyalty = new Candidate("Royalty", 70);
		Candidate candidateRock = new Candidate("Rock",30);
		Candidate candidatePolitics = new Candidate("Politics",20);
		tally = new Tally();
		tally.addCandidate(candidateBooze);
		tally.addCandidate(candidateRoyalty);
		tally.addCandidate(candidateRock);
		tally.addCandidate(candidatePolitics);
		Result result = sut.process(tally, properties, new RandomTieBreaker());
		assertEquals(result.getNumberOfSeatsForCandidate("Booze"), 1);
		assertEquals(result.getNumberOfSeatsForCandidate("Rock"), 1);
		assertEquals(result.getNumberOfSeatsForCandidate("Royalty"), 3);
	}

	@Test
	public void pass_the_acceptance_test_2() throws SeatAllocationException {
		// Using test data set from http://icon.cat/util/elections
		properties.clear();
		properties.put("numberOfSeats", "50");
		Candidate candidateRed = new Candidate("Red", 50);
		Candidate candidateGreen = new Candidate("Green", 15);
		Candidate candidateBlue = new Candidate("Blue", 75);
		Candidate candidatePurple = new Candidate("Purple", 12);
		Candidate candidateBlack = new Candidate("Black", 22);
		Candidate candidateYellow = new Candidate("Yellow", 33);
		Candidate candidateBrown = new Candidate("Brown", 1);
		tally = new Tally();
		tally.addCandidate(candidateBrown);
		tally.addCandidate(candidateYellow);
		tally.addCandidate(candidateBlack);
		tally.addCandidate(candidatePurple);
		tally.addCandidate(candidateBlue);
		tally.addCandidate(candidateGreen);
		tally.addCandidate(candidateRed);
		Result result = sut.process(tally, properties, new RandomTieBreaker());
		System.out.println(result.getNumberOfSeatsForCandidate("Red"));
		// assertEquals(result.getNumberOfSeatsForCandidate("Red"), 12);
	}
}
