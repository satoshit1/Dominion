package org.jseats.model.methods.by.votes.rank;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jseats.model.Candidate;
import org.jseats.model.InmutableTally;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toCollection;

/**
 * Stores votes from candidates.
 * - Votes are sorted in ascending order
 * - Each amount of votes is map to a set of candidates sorted by name.
 */
public class ByVotesRankTable {

	private static final int MIN_NUMBER_OF_CANDIDATES = 1;

	private final List<Candidate> candidates;

	private final TreeMap<Integer, Set<Candidate>> votesMap = new TreeMap<>();

	private ByVotesRankTable(final InmutableTally tally) {
		this.candidates = new ArrayList<>();

		for (int i = 0; i < tally.getNumberOfCandidates(); i++){
			candidates.add(tally.getCandidateAt(i));
		}
	}

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public TreeMap<Integer, Set<Candidate>> getVotesMap() {
		return this.votesMap;
	}

	public static ByVotesRankTable from(final InmutableTally tally) {
		if (tally.getNumberOfCandidates() < MIN_NUMBER_OF_CANDIDATES) {
			throw new IllegalArgumentException("numberOfCandidates can not be less than " + MIN_NUMBER_OF_CANDIDATES);
		}

		return new ByVotesRankTable(tally);
	}

	public void calculate() {
		TreeMap<Integer, Set<Candidate>> votes = candidates.stream()
				.collect(groupingBy
						(Candidate::getVotes, TreeMap::new,
								mapping(identity(), toCollection(TreeSet::new))));
		votesMap.putAll(votes);
	}

	public Map.Entry<Integer, Set<Candidate>> getMaxVotesEntry() {
		return this.votesMap.lastEntry();
	}

	public Map.Entry<Integer, Set<Candidate>> removeMaxQuotientEntry() {
		return this.votesMap.pollLastEntry();
	}

	public boolean removeCandidateFromMaxQuotient(Candidate candidate) {
		Map.Entry<Integer, Set<Candidate>> maxQuotientEntry = getMaxVotesEntry();
		return maxQuotientEntry.getValue().remove(candidate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || !(obj instanceof ByVotesRankTable)) {
			return false;
		}

		ByVotesRankTable other = (ByVotesRankTable) obj;

		EqualsBuilder eBuilder = new EqualsBuilder();
		eBuilder = eBuilder.append(this.candidates, other.candidates);
		eBuilder = eBuilder.append(this.votesMap, other.votesMap);
		return eBuilder.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcBuilder = new HashCodeBuilder();
		hcBuilder.append(this.candidates);
		hcBuilder.append(this.votesMap);
		return hcBuilder.toHashCode();
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		builder.append("candidates", this.candidates);
		builder.append("votesMap", this.votesMap);
		return builder.build();
	}

}
