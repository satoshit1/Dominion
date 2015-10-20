package org.jseats.model.methods.dhondt;

import org.jseats.model.Candidate;
import org.jseats.model.Tally;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class QuotientsTableShould {

	private static final String CANDIDATE_NAME_BOOZE = "Booze";

	private static final String CANDIDATE_NAME_ROYALTY = "Royalty";

	private static final String CANDIDATE_NAME_ROCK = "Rock";

	private static final String CANDIDATE_NAME_POLITICS = "Politics";

	@Test(expected = IllegalArgumentException.class)
	public void fail_when_invalid_number_of_seats() {
		Tally mockedTally = Mockito.mock(Tally.class);
		Mockito.when(mockedTally.getNumberOfCandidates()).thenReturn(1);

		QuotientsTable.from(0, mockedTally);
	}

	@Test(expected = IllegalArgumentException.class)
	public void fail_when_invalid_divisor() {
		Tally mockedTally = Mockito.mock(Tally.class);
		Mockito.when(mockedTally.getNumberOfCandidates()).thenReturn(0);

		QuotientsTable.from(1, mockedTally);
	}

	@Test
	public void be_equal() {
		Candidate booze = new Candidate(CANDIDATE_NAME_BOOZE, 30);
		Candidate royalty = new Candidate(CANDIDATE_NAME_ROYALTY, 30);
		Candidate rock = new Candidate(CANDIDATE_NAME_ROCK, 30);
		Candidate politics = new Candidate(CANDIDATE_NAME_POLITICS, 30);

		Tally tally = new Tally();
		tally.addCandidate(booze);
		tally.addCandidate(royalty);
		tally.addCandidate(rock);
		tally.addCandidate(politics);

		QuotientsTable firstTable = QuotientsTable.from(5, tally);
		QuotientsTable secondtable = QuotientsTable.from(5, tally);

		Assert.assertEquals(firstTable, secondtable);

		firstTable.calculate();
		secondtable.calculate();

		Assert.assertEquals(firstTable, secondtable);
	}

	@Test
	public void not_be_equal() {
		Candidate booze = new Candidate(CANDIDATE_NAME_BOOZE, 40);
		Candidate royalty = new Candidate(CANDIDATE_NAME_ROYALTY, 30);

		Tally tally = new Tally();
		tally.addCandidate(booze);
		tally.addCandidate(royalty);

		QuotientsTable firstTable = QuotientsTable.from(4, tally);
		QuotientsTable secondtable = QuotientsTable.from(5, tally);

		Assert.assertNotEquals(firstTable, secondtable);

		firstTable.calculate();
		secondtable.calculate();

		Assert.assertNotEquals(firstTable, secondtable);
	}

}
