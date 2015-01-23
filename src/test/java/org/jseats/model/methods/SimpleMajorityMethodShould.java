package org.jseats.model.methods;

import org.jseats.model.*;
import org.jseats.model.tie.TieBreaker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.notNull;
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
        final Candidate candidateA = new Candidate("A", 10);
        final Candidate candidateB = new Candidate("B", 10);

        Tally tally = new Tally();
        Arrays.asList(candidateA, candidateB).stream().forEach(tally::addCandidate);

        final TieBreaker tieBreaker = mock(TieBreaker.class);
        sut.process(tally, null, tieBreaker);

        verify(tieBreaker).breakTie(Arrays.asList(candidateA, candidateB));
    }
    
}