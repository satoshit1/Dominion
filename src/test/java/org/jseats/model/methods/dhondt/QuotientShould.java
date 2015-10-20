package org.jseats.model.methods.dhondt;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class QuotientShould {

	@Test
	public void create_a_valid_quotient() {
		BigDecimal.TEN.compareTo(Quotient.from(30, 3.00d).getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void fail_when_invalid_dividend() {
		Quotient.from(-1, 10.00d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void fail_when_invalid_divisor() {
		Quotient.from(10, 0);
	}

	@Test
	public void be_equal() {
		Assert.assertTrue(Quotient.from(30, 3.00d).equals(Quotient.from(30, 3.00d)));
		Assert.assertTrue(Quotient.from(10, 3.00d).equals(Quotient.from(10, 3.00d)));
		Assert.assertTrue(Quotient.from(100000, 7.7568d).equals(Quotient.from(100000, 7.7568d)));
	}

	@Test
	public void not_be_equal() {
		Assert.assertFalse(Quotient.from(30, 3.00d).equals(Quotient.from(10, 3.00d)));
	}

}
