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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.jseats.model.methods.DHondtHighestAveragesMethod;
import org.jseats.model.tie.RandomTieBreaker;
import org.jseats.model.tie.TieBreaker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DHondtShould {

	private Tally tally;

	private Properties properties;

	private DHondtHighestAveragesMethod sut;

	private static final String CANDIDATE_NAME_BOOZE = "Booze";

	private static final String CANDIDATE_NAME_ROYALTY = "Royalty";

	private static final String CANDIDATE_NAME_ROCK = "Rock";

	private static final String CANDIDATE_NAME_POLITICS = "Politics";

	private static final String CANDIDATE_NAME_red = "Red";

	private static final String CANDIDATE_NAME_green = "Green";

	private static final String CANDIDATE_NAME_blue = "Blue";

	private static final String CANDIDATE_NAME_purple = "Purple";

	private static final String CANDIDATE_NAME_black = "Black";

	private static final String CANDIDATE_NAME_yellow = "Yellow";

	private static final String CANDIDATE_NAME_brown = "Brown";

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
	public void throw_an_exception_on_null_properties() throws SeatAllocationException {
		sut.process(mock(Tally.class), null, mock(TieBreaker.class));
	}

	@Test
	public void fail_on_a_unparseable_numberOfSeats_Property() throws SeatAllocationException {
		expectedException.expect(SeatAllocationException.class);
		expectedException.expectMessage(equalTo("numberOfSeats property is not a number: 'AA'"));
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "AA");
		tally.addCandidate(mock(Candidate.class));
		RandomTieBreaker tieBreaker = new RandomTieBreaker();

		sut.process(tally, properties, tieBreaker);
	}

	@Test
	public void when_not_given_a_numberOfSeats_should_assign_the_same_number_as_numberOfCandidates() throws SeatAllocationException {
		Candidate candidateA = new Candidate("candidateA", 10);
		RandomTieBreaker tieBreaker = new RandomTieBreaker();
		tally.addCandidate(candidateA);
		// Do not create a property numberOfSeats
		assertEquals(candidateA, sut.process(tally, properties, tieBreaker).getSeatAt(0));
	}

	@Test
	public void not_allow_negative_numberOfSeats() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "-2");
		tally.addCandidate(mock(Candidate.class));

		expectedException.expect(SeatAllocationException.class);
		expectedException.expectMessage(equalTo("numberOfSeats is negative: -2"));

		sut.process(tally, properties, mock(TieBreaker.class));
	}

	@Test
	public void pass_the_acceptance_test_1() throws SeatAllocationException {
		// Using test data set from US: https://redmine.scytl.net/issues/94064
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "5");
		tally =
			getTallySheetWith(new Candidate(CANDIDATE_NAME_BOOZE, 40), new Candidate(CANDIDATE_NAME_ROYALTY, 70),
				new Candidate(CANDIDATE_NAME_ROCK, 30), new Candidate(CANDIDATE_NAME_POLITICS, 20));
		Result result = sut.process(tally, properties, new RandomTieBreaker());

		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_BOOZE), 1);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROYALTY), 3);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROCK), 1);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_POLITICS), 0);
	}

	@Test
	// Using test data set from http://icon.cat/util/elections is not possible
	// due to limitations with online d'Hondt calculators and draw management
	public void pass_the_acceptance_test_2() throws SeatAllocationException {
		// Using test data set from http://icon.cat/util/elections
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "50");
		tally =
			getTallySheetWith(new Candidate(CANDIDATE_NAME_brown, 1), new Candidate(CANDIDATE_NAME_yellow, 33),
				new Candidate(CANDIDATE_NAME_black, 22), new Candidate(CANDIDATE_NAME_purple, 12), new Candidate(
					CANDIDATE_NAME_blue, 75), new Candidate(CANDIDATE_NAME_green, 15),
				new Candidate(CANDIDATE_NAME_red, 50));
		Result result = sut.process(tally, properties, getRandomTieBreaker());
		assertThat(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_brown), is(0));
		assertThat(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_yellow), is(8));
		assertThat(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_black), is(5));
		assertThat(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_purple), is(3));
		assertThat(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_blue), is(19));
		assertThat(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_green), is(3));
		assertThat(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_red), is(12));
	}

	private RandomTieBreaker getRandomTieBreaker() {
		final RandomTieBreaker tieBreaker = new RandomTieBreaker();
		tieBreaker.injectRandom(new Random(1L));
		return tieBreaker;
	}

	private Tally getTallySheetWith(Candidate... candidates) {
		Tally tally = new Tally();
		Arrays.asList(candidates).stream().forEach(tally::addCandidate);
		return tally;
	}

}
