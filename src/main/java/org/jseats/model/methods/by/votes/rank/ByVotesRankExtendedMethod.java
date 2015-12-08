package org.jseats.model.methods.by.votes.rank;

import org.jseats.model.Candidate;
import org.jseats.model.InmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.methods.ByVotesRankMethod;
import org.jseats.model.tie.TieBreaker;
import org.jseats.model.tie.TieScenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.jseats.Properties.NUMBER_OF_SEATS;

public class ByVotesRankExtendedMethod extends ByVotesRankMethod {

	static Logger log = LoggerFactory.getLogger(ByVotesRankExtendedMethod.class);

	@Override
	public Result process(InmutableTally tally, Properties properties,
						  TieBreaker tieBreaker) throws SeatAllocationException {

		if (null == tally) {
			throw new SeatAllocationException("Received Tally was null");
		}
		if (null == properties) {
			throw new SeatAllocationException("Received Properties was null");
		}
		int numberOfCandidates = tally.getNumberOfCandidates();
		if (numberOfCandidates <= 0) {
			throw new SeatAllocationException("This tally contains no candidates");
		}

		int remainingSeats = getNumberOfSeats(properties, numberOfCandidates);
		ByVotesRankTable votesTable = ByVotesRankTable.from(tally);
		votesTable.calculate();
		log.debug("RankByVotes table calculated: " + votesTable);

		Result result = new Result(Result.ResultType.MULTIPLE);

		do  {
			Map.Entry<Integer, Set<Candidate>> maxVotesEntry = votesTable.getMaxVotesEntry();
			List<Candidate> possibleWinners = new ArrayList<>(maxVotesEntry.getValue());

			if (possibleWinners.isEmpty()) {
				throw new SeatAllocationException("There was a problem generating the RankByVotes table: " + votesTable);

			} else if (possibleWinners.size() <= remainingSeats) {
				for (Candidate winner: possibleWinners) {
					result.addSeat(winner);
					remainingSeats--;
				}
				votesTable.removeMaxQuotientEntry();

			} else {
				log.debug("Tie between: " + possibleWinners);
				if (tieBreaker == null) {
					return tieResult(possibleWinners);
				}

				log.debug("Using tie breaker: " + tieBreaker.getName());
				TieScenario breakScenario = tieBreaker.breakTie(possibleWinners);

				if (breakScenario == null || breakScenario.isTied()) {
					return tieResult(possibleWinners);
				}

				Candidate winner = breakScenario.get(0);
				log.debug("Adding candidate " + winner + " to result.");
				result.addSeat(winner);
				votesTable.removeCandidateFromMaxQuotient(winner);
				remainingSeats--;
			}

		}while(remainingSeats > 0);

		return result;
	}

	private Result tieResult(List<Candidate> tiedCandidates) {
		Result tieResult = new Result(Result.ResultType.TIE);
		tiedCandidates.forEach((tiedCandidate) -> tieResult.addSeat(tiedCandidate));
		return tieResult;
	}

	private int getNumberOfSeats(Properties properties, int numberOfCandidates) throws SeatAllocationException {
		int numberOfSeats;
		// If numberOfSeats is not defined it is set with a default value
		// to numberOfCandidates
		String numberOfSeatsString_or_NumberOfCandidates =
				properties.getProperty(org.jseats.Properties.NUMBER_OF_SEATS, Integer.toString(numberOfCandidates));
		try {
			numberOfSeats = Integer.parseInt(numberOfSeatsString_or_NumberOfCandidates);
		} catch (NumberFormatException exception) {
			throw new SeatAllocationException("numberOfSeats property is not a number: '" +
					properties.getProperty(NUMBER_OF_SEATS) + "'");
		}
		if (numberOfSeats < 0) {
			throw new SeatAllocationException("numberOfSeats is negative: " + numberOfSeats);

		} else if (numberOfSeats > numberOfCandidates) {
			numberOfSeats = numberOfCandidates;
		}
		log.debug("numberOfSeats: " + numberOfSeats);
		return numberOfSeats;
	}

}
