/**
 * $Id$
 *
 * @author alvgarcia
 * @date 19/10/2015 10:11
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 * All rights reserved.
 */

package org.jseats.unit;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.methods.DHondtExtendedMethod;
import org.jseats.model.tie.RandomTieBreaker;
import org.jseats.model.tie.TieBreaker;
import org.jseats.model.tie.TieScenario;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

public class DHondtExtendedShould extends DHondtShould {

	@Override
	public void setUpSut() {
		sut = new DHondtExtendedMethod();
	}

	@Test
	public void pass_with_millions_of_votes() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "5");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_BOOZE, 400_000_000),
						new Candidate(CANDIDATE_NAME_ROYALTY, 700_000_000),
						new Candidate(CANDIDATE_NAME_ROCK, 300_000_000),
						new Candidate(CANDIDATE_NAME_POLITICS, 200_000_000));

		Result result = sut.process(tally, properties, new RandomTieBreaker());

		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_BOOZE), 1);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROYALTY), 3);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROCK), 1);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_POLITICS), 0);
	}

	@Test
	public void pass_with_non_relevant_tie_v1() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "4");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_BOOZE, 100),
						new Candidate(CANDIDATE_NAME_ROYALTY, 20),
						new Candidate(CANDIDATE_NAME_ROCK, 30),
						new Candidate(CANDIDATE_NAME_POLITICS, 100));

		Result result = sut.process(tally, properties, null);

		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_BOOZE), 2);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROYALTY), 0);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROCK), 0);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_POLITICS), 2);
	}

	@Test
	public void pass_with_non_relevant_tie_v2() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "5");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_BOOZE, 100),
						new Candidate(CANDIDATE_NAME_ROYALTY, 70),
						new Candidate(CANDIDATE_NAME_ROCK, 30),
						new Candidate(CANDIDATE_NAME_POLITICS, 100));

		Result result = sut.process(tally, properties, null);

		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_BOOZE), 2);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROYALTY), 1);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROCK), 0);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_POLITICS), 2);
	}

	@Test
	public void pass_with_non_relevant_tie_v3() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "7");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_BOOZE, 100),
						new Candidate(CANDIDATE_NAME_ROYALTY, 70),
						new Candidate(CANDIDATE_NAME_ROCK, 100),
						new Candidate(CANDIDATE_NAME_POLITICS, 100));

		Result result = sut.process(tally, properties, null);

		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_BOOZE), 2);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROYALTY), 1);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_ROCK), 2);
		assertEquals(result.getNumberOfSeatsForCandidate(CANDIDATE_NAME_POLITICS), 2);
	}

	@Test
	public void pass_with_relevant_tie_v1() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "1");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_BOOZE, 100),
						new Candidate(CANDIDATE_NAME_ROYALTY, 100),
						new Candidate(CANDIDATE_NAME_ROCK, 30),
						new Candidate(CANDIDATE_NAME_POLITICS, 100));

		Result expectedResult = new Result(Result.ResultType.TIE);
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_BOOZE, 100));
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_ROYALTY, 100));
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_POLITICS, 100));

		Result result = sut.process(tally, properties, null);

		assertEquals(Result.ResultType.TIE, result.getType());
		assertEquals(expectedResult.getSeats(), result.getSeats());
	}

	@Test
	public void pass_with_relevant_tie_v2() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "3");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_BOOZE, 150),
						new Candidate(CANDIDATE_NAME_ROYALTY, 100),
						new Candidate(CANDIDATE_NAME_ROCK, 100),
						new Candidate(CANDIDATE_NAME_POLITICS, 100));

		Result expectedResult = new Result(Result.ResultType.TIE);
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_ROCK, 100));
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_POLITICS, 100));

		TieBreaker mockedTieBreaker = Mockito.mock(TieBreaker.class);
		List<Candidate> mockedTiedCandidates = new ArrayList<>();
		mockedTiedCandidates.add(new Candidate(CANDIDATE_NAME_ROYALTY, 100));
		mockedTiedCandidates.add(new Candidate(CANDIDATE_NAME_ROCK, 100));
		mockedTiedCandidates.add(new Candidate(CANDIDATE_NAME_POLITICS, 100));

		List<Candidate> mockedWinners = new ArrayList<>();
		mockedWinners.add(new Candidate(CANDIDATE_NAME_ROYALTY, 100));

		doReturn(new TieScenario(mockedWinners, true))
				.when(mockedTieBreaker).breakTie(mockedTiedCandidates);

		Result result = sut.process(tally, properties, mockedTieBreaker);

		assertEquals(Result.ResultType.TIE, result.getType());
		assertEquals(expectedResult.getSeats(), result.getSeats());
	}

	@Test
	public void pass_with_relevant_tie_v3() throws SeatAllocationException {
		properties.put(org.jseats.Properties.NUMBER_OF_SEATS, "3");
		tally =
				getTallySheetWith(
						new Candidate(CANDIDATE_NAME_BOOZE, 20),
						new Candidate(CANDIDATE_NAME_ROYALTY, 60),
						new Candidate(CANDIDATE_NAME_ROCK, 120),
						new Candidate(CANDIDATE_NAME_POLITICS, 80));

		Result expectedResult = new Result(Result.ResultType.TIE);
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_ROCK, 120));
		expectedResult.addSeat(new Candidate(CANDIDATE_NAME_ROYALTY, 60));

		Result result = sut.process(tally, properties, null);

		assertEquals(Result.ResultType.TIE, result.getType());
		assertEquals(expectedResult.getSeats(), result.getSeats());
	}


}
