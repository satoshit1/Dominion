/**
 * $Id$
 *
 * @author alvgarcia
 * @date 19/10/2015 10:18
 * Copyright (C) 2015 Scytl Secure Electronic Voting SA
 * All rights reserved.
 */

package org.jseats.model.methods;

import org.jseats.model.Candidate;
import org.jseats.model.InmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.methods.dhondt.Quotient;
import org.jseats.model.methods.dhondt.QuotientsTable;
import org.jseats.model.tie.TieBreaker;
import org.jseats.model.tie.TieScenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.jseats.Properties.NUMBER_OF_SEATS;

public class DHondtExtendedMethod extends DHondtHighestAveragesMethod {

	static Logger log = LoggerFactory.getLogger(DHondtExtendedMethod.class);

	@Override
	public Result process(InmutableTally tally, Properties properties, TieBreaker tieBreaker) throws SeatAllocationException {

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
		QuotientsTable quotientsTable = QuotientsTable.from(remainingSeats, tally);
		quotientsTable.calculate();
		log.debug("Quotients table calculated: " + quotientsTable);

		Result result = new Result(Result.ResultType.MULTIPLE);

		do  {
			Map.Entry<Quotient, List<Candidate>> maxQuotientEntry = quotientsTable.getMaxQuotientEntry();
			List<Candidate> possibleWinners = maxQuotientEntry.getValue();

			if (possibleWinners.isEmpty()) {
				throw new SeatAllocationException("There was a problem generating the quotients table: " + quotientsTable);

			} else if (possibleWinners.size() <= remainingSeats) {
				for (Candidate winner: possibleWinners) {
					result.addSeat(winner);
					remainingSeats--;
				}
				quotientsTable.removeMaxQuotientEntry();

			} else {
				log.debug("Tie between: " + possibleWinners);
				if (tieBreaker == null) {
					return tieResult(possibleWinners);
				}

				log.debug("Using tie breaker: " + tieBreaker.getName());
				TieScenario breakScenario =
						tieBreaker.breakTie(possibleWinners.toArray(new Candidate[possibleWinners.size()]));

				if (breakScenario == null || breakScenario.isTied()) {
					return tieResult(possibleWinners);
				}

				Candidate winner = breakScenario.get(0);
				result.addSeat(winner);
				quotientsTable.removeCandidateFromMaxQuotient(winner);
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
		try {
			// If numberOfSeats is not defined it is set with a default value
			// to numberOfCandidates
			numberOfSeats = Integer.parseInt(properties.getProperty(NUMBER_OF_SEATS, Integer.toString(numberOfCandidates)));
		} catch (NumberFormatException exception) {
			throw new SeatAllocationException("numberOfSeats property is not a number: '" +
					properties.getProperty(NUMBER_OF_SEATS) + "'");
		}
		if (numberOfSeats < 0) {
			throw new SeatAllocationException("numberOfSeats is negative: " + numberOfSeats);
		}
		log.debug("numberOfSeats: " + numberOfSeats);

		return numberOfSeats;
	}
}
