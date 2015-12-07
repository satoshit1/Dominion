/**
 * $Id$
 * @author lcappuccio
 * @date   Jan 22, 2015 10:44:25 AM
 *
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package org.jseats.unit;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.jseats.model.methods.by.votes.rank.ByVotesRankExtendedMethod;
import org.jseats.model.tie.MaxVotesTieBreaker;
import org.jseats.model.tie.RandomTieBreaker;
import org.jseats.model.tie.TieBreaker;
import org.jseats.model.tie.TieScenario;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ByVotesRankExtendedShould {

	private static final String CANDIDATE_NAME_A = "AAAA";

	private static final String CANDIDATE_NAME_B = "BBBB";

	private static final String CANDIDATE_NAME_C = "CCCC";

	private static final String CANDIDATE_NAME_D = "DDDD";

	private static final String CANDIDATE_NAME_E = "EEEE";

	private static final String CANDIDATE_NAME_F = "FFFF";

	private static final String CANDIDATE_NAME_G = "GGGG";

	private static final String CANDIDATE_NAME_H = "HHHH";

	private static final String CANDIDATE_NAME_I = "IIII";

	private static final String CANDIDATE_NAME_J = "JJJJ";

	private static final String CANDIDATE_NAME_K = "KKKK";

	ByVotesRankExtendedMethod sut = new ByVotesRankExtendedMethod();

	private Tally tally;

	private Properties properties;

	@Before
	public void setUp() {
		tally = new Tally();
		properties = new Properties();
	}

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

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

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
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "-2");
		tally.addCandidate(mock(Candidate.class));

		expectedException.expect(SeatAllocationException.class);
		expectedException.expectMessage(equalTo("numberOfSeats is negative: -2"));

		sut.process(tally, properties, mock(TieBreaker.class));
	}

	@Test
	public void elect_candidateA() throws SeatAllocationException {
		Candidate candidateA = new Candidate("candidateA", 10);
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "1");
		RandomTieBreaker tieBreaker = new RandomTieBreaker();
		tally.addCandidate(candidateA);
		tally.addCandidate(new Candidate("candidateB", 0));
		assertEquals(candidateA, sut.process(tally, properties, tieBreaker).getSeatAt(0));
	}

	@Test
	public void pick_random_candidate_on_tie() throws SeatAllocationException {
		Candidate candidateF = new Candidate("candidateF", 10);
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "1");
		RandomTieBreaker tieBreaker = new RandomTieBreaker();
		tieBreaker.injectRandom(new Random(5));
		tally.addCandidate(new Candidate("candidateA", 10));
		tally.addCandidate(new Candidate("candidateB", 10));
		tally.addCandidate(new Candidate("candidateC", 10));
		tally.addCandidate(new Candidate("candidateD", 10));
		tally.addCandidate(new Candidate("candidateE", 10));
		tally.addCandidate(candidateF);
		assertThat(sut.process(tally, properties, tieBreaker).getSeatAt(0), equalTo(candidateF));
	}

	@Test
	public void not_fail_when_there_are_more_seats_than_candidates() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "4");
		Candidate candidateA = new Candidate("candidateA", 100);
		Candidate candidateB = new Candidate("candidateB", 200);
		tally.addCandidate(candidateA);
		tally.addCandidate(candidateB);

		Result result = sut.process(tally, properties, new MaxVotesTieBreaker());
		assertEquals(result.getNumerOfSeats(), tally.getNumberOfCandidates());
		assertThat(result.getSeatAt(0), equalTo(candidateB));
		assertThat(result.getSeatAt(1), equalTo(candidateA));
	}

	@Test
	public void be_ok_when_there_are_more_candidates_than_seats() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "1");
		Candidate candidateA = new Candidate("candidateA", 1000);
		tally.addCandidate(candidateA);
		tally.addCandidate(new Candidate("candidateB", 200));
		tally.addCandidate(new Candidate("candidateC", 300));
		tally.addCandidate(new Candidate("candidateD", 400));
		tally.addCandidate(new Candidate("candidateE", 500));

		Result result = sut.process(tally, properties, new MaxVotesTieBreaker());
		assertEquals(1, result.getNumerOfSeats());
		assertThat(result.getSeatAt(0), equalTo(candidateA));
	}

	@Test
	public void be_ok_when_same_amount_of_candidates_and_seats() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "5");
		Candidate candidateA = new Candidate("candidateA", 1000);
		tally.addCandidate(candidateA);
		tally.addCandidate(new Candidate("candidateB", 200));
		tally.addCandidate(new Candidate("candidateC", 300));
		tally.addCandidate(new Candidate("candidateD", 400));
		tally.addCandidate(new Candidate("candidateE", 500));

		Result result = sut.process(tally, properties, new MaxVotesTieBreaker());
		assertEquals(tally.getNumberOfCandidates(), result.getNumerOfSeats());
		assertThat(result.getSeatAt(0), equalTo(candidateA));
	}

	@Test
	public void support_ties_of_n_candidates_v1() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "1");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_D, 100),
						new Candidate(CANDIDATE_NAME_C, 100),
						new Candidate(CANDIDATE_NAME_A, 100),
						new Candidate(CANDIDATE_NAME_B, 100));

		Result expectedResult = new Result(Result.ResultType.TIE);
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_A, 100));
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_B, 100));
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_C, 100));
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_D, 100));

		Result result = sut.process(tally, properties, null);

		assertEquals(Result.ResultType.TIE, result.getType());
		assertEquals(expectedResult.getSeats(), result.getSeats());
	}

	@Test
	public void support_ties_of_n_candidates_v2() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "1");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_D, 200),
						new Candidate(CANDIDATE_NAME_C, 100),
						new Candidate(CANDIDATE_NAME_A, 100),
						new Candidate(CANDIDATE_NAME_B, 100));

		Result expectedResult = new Result(Result.ResultType.MULTIPLE);
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_D, 100));

		Result result = sut.process(tally, properties, null);

		assertEquals(Result.ResultType.MULTIPLE, result.getType());
		assertEquals(expectedResult.getSeats(), result.getSeats());
	}

	@Test
	public void support_ties_of_n_candidates_v3() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "2");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_D, 200),
						new Candidate(CANDIDATE_NAME_C, 100),
						new Candidate(CANDIDATE_NAME_A, 100),
						new Candidate(CANDIDATE_NAME_B, 100));

		Result expectedResult = new Result(Result.ResultType.TIE);
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_A, 100));
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_B, 100));
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_C, 100));

		Result result = sut.process(tally, properties, null);

		assertEquals(Result.ResultType.TIE, result.getType());
		assertEquals(expectedResult.getSeats(), result.getSeats());
	}

	@Test
	public void support_ties_of_n_candidates_v4() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "3");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_D, 150),
						new Candidate(CANDIDATE_NAME_B, 100),
						new Candidate(CANDIDATE_NAME_C, 100),
						new Candidate(CANDIDATE_NAME_A, 100));

		TieBreaker mockedTieBreaker = Mockito.mock(TieBreaker.class);
		List<Candidate> mockedTiedCandidates = new ArrayList<>();
		mockedTiedCandidates.add(new Candidate(CANDIDATE_NAME_A, 100));
		mockedTiedCandidates.add(new Candidate(CANDIDATE_NAME_B, 100));
		mockedTiedCandidates.add(new Candidate(CANDIDATE_NAME_C, 100));

		List<Candidate> mockedWinners = new ArrayList<>();
		mockedWinners.add(new Candidate(CANDIDATE_NAME_C, 100));

		doReturn(new TieScenario(mockedWinners, true))
				.when(mockedTieBreaker).breakTie(mockedTiedCandidates);

		Result expectedResult = new Result(Result.ResultType.TIE);
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_A, 100));
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_B, 100));

		Result result = sut.process(tally, properties, mockedTieBreaker);

		assertEquals(Result.ResultType.TIE, result.getType());
		assertEquals(expectedResult.getSeats(), result.getSeats());
	}

	private Tally getTallySheetWith(Candidate... candidates) {
		Tally tally = new Tally();
		Arrays.asList(candidates).stream().forEach(tally::addCandidate);
		return tally;
	}

}
