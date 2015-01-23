package org.jseats.model.methods;

import org.hamcrest.MatcherAssert;
import org.jseats.model.InmutableTally;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.tie.TieBreaker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.lang.model.util.SimpleElementVisitor6;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;

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
    
}