package test.java;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.java.Alcohol;
import main.java.Cart;
import main.java.Dairy;
import main.java.FrozenFood;
import main.java.Produce;
import main.java.UnderAgeException;

public class CartTest {
	private static final int DEFAULT_AGE = 45;
	Cart cart;

	@Before
	public void setUp() throws Exception {
		cart = new Cart(DEFAULT_AGE);
	}

	/**
	 * Test that amount saved for an empty cart is 0
	 * 
	 * @throws UnderAgeException
	 */
	@Test
	public void testEnptyCart() throws UnderAgeException {
		assertEquals(0, cart.Amount_saved(), .01);
	}

	/**
	 * Test that amount saved for a cart with 2 Produce products is 0
	 * 
	 * @throws UnderAgeException
	 */
	@Test
	public void testCartWith2Produce() throws UnderAgeException {
		// add 2 Produce products
		cart.addItem(new Produce());
		cart.addItem(new Produce());
		assertEquals(0, cart.Amount_saved(), .01);
	}

	/**
	 * Test that amount saved for a cart with 3 Produce products is 1
	 * 
	 * @throws UnderAgeException
	 */
	@Test
	public void testCartWith3Produce() throws UnderAgeException {
		// add 3 Produce products
		cart.addItem(new Produce());
		cart.addItem(new Produce());
		cart.addItem(new Produce());
		assertEquals(1, cart.Amount_saved(), .01);
	}

	/**
	 * Test that calling Amount_saved() for a cart for an under-age person with
	 * Alcohol should result in an exception
	 * 
	 * @throws UnderAgeException
	 */
	@Test(expected = UnderAgeException.class)
	public void testUnderAgeCartWithAlcohol() throws UnderAgeException {
		// underage cart
		Cart cart = new Cart(Cart.MIN_ALCOHOL_AGE - 1);
		// add Alcohol
		cart.addItem(new Alcohol());
		cart.Amount_saved(); // we don't care about the return, just that the exception is thrown
	}
	
	/**
	 * Test that amount saved for a cart with 1 FrozenFood product is 0
	 * 
	 * @throws UnderAgeException
	 */
	@Test
	public void testCartWithFrozenFood() throws UnderAgeException {
		// add FrozenFood product
		cart.addItem(new FrozenFood());
		assertEquals(0, cart.Amount_saved(), .01);
	}
	
	/**
	 * Test that amount saved for a cart with 1 Dairy product is 0
	 * 
	 * @throws UnderAgeException
	 */
	@Test
	public void testCartWithDairy() throws UnderAgeException {
		// add FrozenFood product
		cart.addItem(new Dairy());
		assertEquals(0, cart.Amount_saved(), .01);
	}
	
	/**
	 * Test that amount saved for a cart with 1 Alcohol-FrozenFood combo is 3
	 * 
	 * @throws UnderAgeException
	 */
	@Test
	public void testCartWithAlcoholFrozenFoodCombo() throws UnderAgeException {
		// add Alcohol-FrozenFood combo
		cart.addItem(new Alcohol());
		cart.addItem(new FrozenFood());
		assertEquals(3, cart.Amount_saved(), .01);
	}
}
