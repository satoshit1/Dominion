package org.jseats.model.methods.dhondt;

import static org.junit.Assert.*;

import com.scytl.consolidation.utils.RCCSVParser;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jseats.model.Candidate;
import org.jseats.model.Tally;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class QuotientsTableShould {

	protected static final String CANDIDATE_NAME_A = "A";

	protected static final String CANDIDATE_NAME_B = "B";

	protected static final String CANDIDATE_NAME_C = "C";

	protected static final String CANDIDATE_NAME_D = "D";

	protected static final String CANDIDATE_NAME_E = "E";

	protected static final String CANDIDATE_NAME_F = "F";

	protected static final String CANDIDATE_NAME_G = "G";

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
		Tally tally = getTallySheetWith(
				new Candidate(CANDIDATE_NAME_A, 30),
				new Candidate(CANDIDATE_NAME_B, 30),
				new Candidate(CANDIDATE_NAME_C, 30),
				new Candidate(CANDIDATE_NAME_D, 30));

		QuotientsTable firstTable = QuotientsTable.from(5, tally);
		QuotientsTable secondtable = QuotientsTable.from(5, tally);

		assertEquals(firstTable, secondtable);

		firstTable.calculate();
		secondtable.calculate();

		assertEquals(firstTable, secondtable);
	}

	@Test
	public void not_be_equal() {
		Tally tally = getTallySheetWith(
				new Candidate(CANDIDATE_NAME_A, 30),
				new Candidate(CANDIDATE_NAME_B, 30));

		QuotientsTable firstTable = QuotientsTable.from(4, tally);
		QuotientsTable secondTable = QuotientsTable.from(5, tally);

		assertNotEquals(firstTable, secondTable);

		firstTable.calculate();
		secondTable.calculate();

		assertNotEquals(firstTable, secondTable);
	}

	@Test
	public void get_the_max_quotient() {
		Candidate candidateA = new Candidate(CANDIDATE_NAME_A, 340_000);

		Tally tally = getTallySheetWith(
				candidateA,
				new Candidate(CANDIDATE_NAME_B, 280_000),
				new Candidate(CANDIDATE_NAME_C, 160_000),
				new Candidate(CANDIDATE_NAME_D, 60_000),
				new Candidate(CANDIDATE_NAME_E, 15_000));

		QuotientsTable table = QuotientsTable.from(7, tally);
		table.calculate();

		Quotient expectedQuotient =  new Quotient(new BigDecimal("340000.00"));

		Map.Entry<Quotient, Set<Candidate>> result = table.getMaxQuotientEntry();

		assertEquals(expectedQuotient, result.getKey());
		assertEquals(candidateA, result.getValue().iterator().next());
	}

	@Test
	public void be_equal_to_golden_master() throws IOException {

		Tally tally = getTallySheetWith(
				new Candidate("A0", 800),
				new Candidate("A1", 1000),
				new Candidate("A2", 500),
				new Candidate("A3", 300),
				new Candidate("A4", 30),
				new Candidate("A5", 20),
				new Candidate("A6", 5),
				new Candidate("A7", 700),
				new Candidate("A8", 400),
				new Candidate("A9", 600));

		QuotientsTable goldenMasterTable = QuotientsTable.from(10, tally);

		RCCSVParser rccsvParser = new RCCSVParser(',');
		CSVParser csvParser = rccsvParser.getCSVParser(
				new File("src/test/resources/quotients-map-golden-master.csv"));

		final Iterator<CSVRecord> iterator = csvParser.iterator();

		while (iterator.hasNext()) {
			final CSVRecord row = iterator.next();
			goldenMasterTable.addNewQuotient(
					new Quotient(new BigDecimal(row.get(2))),
					new Candidate(row.get(0), Integer.parseInt(row.get(3))));
		}

		QuotientsTable calculatedTable = QuotientsTable.from(10, tally);
		calculatedTable.calculate();

		assertEquals(goldenMasterTable, calculatedTable);
	}

	protected Tally getTallySheetWith(Candidate... candidates) {
		Tally tally = new Tally();
		Arrays.asList(candidates).stream().forEach(tally::addCandidate);
		return tally;
	}


}

