package org.jseats.model.methods;

import org.jseats.model.*;
import org.jseats.model.tie.TieBreaker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SimpleMajorityMethodShould {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private SimpleMajorityMethod sut;

    @Before
    public void setUp(){
        sut = new SimpleMajorityMethod();
    }

    @Test
    public void canary_test() throws Exception {
        assertTrue(true);
    }

    @Test
    public void fail_on_a_null_tally() throws SeatAllocationException {
        expectedException.expect(SeatAllocationException.class);
        expectedException.expectMessage("Cannot process a null tallysheet");
        
        sut.process(null, mock(Properties.class), mock(TieBreaker.class));
    }

    @Test
    public void not_fail_on_a_null_properties() throws SeatAllocationException {
        assertThat(sut.process(mock(InmutableTally.class), null, mock(TieBreaker.class)), is(not(nullValue())));
    }

    @Test
    public void not_fail_on_a_null_tiebreaker() throws SeatAllocationException {
        assertThat(sut.process(mock(InmutableTally.class), null, mock(TieBreaker.class)), is(not(nullValue())));
    }

    @Test
    public void ask_tiebreaker_on_a_tie() throws SeatAllocationException {
        final int amountOfVotesToTie = 10;
        List<Candidate> allCandidates = asList(new Candidate("A", amountOfVotesToTie), new Candidate("B", amountOfVotesToTie), new Candidate("C", 1));
        List<Candidate> tiedCandidates = allCandidates.stream().filter(x -> x.getVotes() == amountOfVotesToTie).collect(Collectors.<Candidate>toList());

        Tally tally = new Tally();
        allCandidates.stream().forEach(tally::addCandidate);

        final TieBreaker tieBreaker = mock(TieBreaker.class);
        sut.process(tally, null, tieBreaker);

        verify(tieBreaker).breakTie(tiedCandidates);
    }

    @Test
    public void return_the_tied_candidates_on_tie() throws SeatAllocationException {
        final int amountOfVotesToTie = 10;

        List<Candidate> allCandidates = asList(new Candidate("A", amountOfVotesToTie), new Candidate("B", amountOfVotesToTie), new Candidate("C", 1));
        List<Candidate> tiedCandidates = allCandidates.stream().filter(x -> x.getVotes() == amountOfVotesToTie).collect(Collectors.<Candidate>toList());

        Tally tally = new Tally();
        allCandidates.stream().forEach(tally::addCandidate);

        final TieBreaker tieBreaker = mock(TieBreaker.class);
        Result result = sut.process(tally, null, tieBreaker);

        assertThat(result.getType(), is(Result.ResultType.TIE));
        assertThat(result.getNumerOfSeats(), is(2));
        assertThat(result.getSeats().get(0).getCandidate(), is(tiedCandidates.get(0)));
        assertThat(result.getSeats().get(1).getCandidate(), is(tiedCandidates.get(1)));
    }

    @Test
    public void not_tie_when_tiebreaker_solves_the_tie() throws SeatAllocationException {

        Tally tally = new Tally();

        final TieBreaker tieBreaker = mock(TieBreaker.class);
        final Candidate candidateA = new Candidate("A", 10);
        doReturn(Arrays.asList(candidateA)).when(tieBreaker).breakTie((List<Candidate>) anyObject());
        Result result = sut.process(tally, null, tieBreaker);
        
        assertThat(result.getType(), is(Result.ResultType.SINGLE));
        assertThat(result.getNumerOfSeats(), is(1));
        assertThat(result.getSeats().get(0).getCandidate(), equalTo(candidateA));
    }

    @Test
    public void tie_given_zero_candidates() throws SeatAllocationException {
        Tally tally = new Tally();

        System.out.println(sut.process(tally, null, null));
    }

    @Test
    public void not_tie_given_one_candidate() throws SeatAllocationException {
        Tally tally = new Tally();
        final Candidate candidateA = new Candidate("A", 10);
        asList(candidateA).stream().forEach(tally::addCandidate);

        System.out.println(sut.process(tally, null, null));
    }


}