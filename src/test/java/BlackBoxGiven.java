package test.java;

import main.java.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BlackBoxGiven {

    private Class<Cart> classUnderTest;

    @SuppressWarnings("unchecked")
    public BlackBoxGiven(Object classUnderTest) {
        this.classUnderTest = (Class<Cart>) classUnderTest;
    }

    // Define all classes to be tested
    @Parameterized.Parameters
    public static Collection<Object[]> cartClassUnderTest() {
        Object[][] classes = {
            {Cart0.class},
            {Cart1.class},
            {Cart2.class},
            {Cart3.class},
            {Cart4.class},
            {Cart5.class}
        };
        return Arrays.asList(classes);
    }

    private Cart createCart(int age) throws Exception {
        Constructor<Cart> constructor = classUnderTest.getConstructor(Integer.TYPE);
        return constructor.newInstance(age);
    }

    // A sample Cart

    Cart cart1;
    double cart1Expected;
    
    Cart cartAge20;
    
    Cart cartWithTwoProduce;
    double cartWithTwoProduceExpectedCost;
    
    Cart cartWithThreeProduce;
    double cartWithThreeProduceExpectedCost;
    
    Cart cartWithSevenProduce;
    double cartWithSevenProduceExpectedCost;
    
    Cart cartWithAlcoholAndFrozen;
    double cartWithAlcoholAndFrozenExpectedCost;


    @org.junit.Before
    public void setUp() throws Exception {

        // all carts should be set up like this

        // cart created with an age 40 shopper
        cart1 = createCart(40);
        for (int i = 0; i < 2; i++) {
            cart1.addItem(new Alcohol());
        }
        for(int i = 0; i < 3; i++) {
            cart1.addItem(new Dairy());
        }
        for(int i = 0; i < 4; i++) {
            cart1.addItem(new Meat());
        }

        cart1Expected = 70.2;
        
        cartAge20 = createCart(20);
        cartAge20.addItem(new Alcohol());
        
        cartWithTwoProduce = createCart(40);
        cartWithTwoProduce.addItem(new Produce());
        cartWithTwoProduce.addItem(new Produce());
        cartWithTwoProduceExpectedCost = 4.32;
        
        cartWithThreeProduce = createCart(40);
        for(int i = 0; i < 3; i++) {
        	cartWithThreeProduce.addItem(new Produce());
        }
        cartWithThreeProduceExpectedCost = 5.40;
        
        cartWithSevenProduce = createCart(40);
        for(int i = 0; i < 7; i++) {
        	cartWithSevenProduce.addItem(new Produce());
        }
        cartWithSevenProduceExpectedCost = 12.96;
        
        cartWithAlcoholAndFrozen = createCart(40);
        cartWithAlcoholAndFrozen.addItem(new Alcohol());
        cartWithAlcoholAndFrozen.addItem(new FrozenFood());
        cartWithAlcoholAndFrozenExpectedCost = 10.80;
    }

    // sample test
    @Test
    public void calcCostCart1() throws UnderAgeException {
        double amount = cart1.calcCost();
        assertEquals(cart1Expected, amount, .01);
    }
    
    @Test(expected = UnderAgeException.class)
    public void testUnderAgeExceptionThrown() throws UnderAgeException{
    	cartAge20.calcCost();
    }
    
    @Test
    public void calcCostCartWithTwoProduce() throws UnderAgeException {
        double amount = cartWithTwoProduce.calcCost();
        assertEquals(cartWithTwoProduceExpectedCost, amount, .01);
    }
    
    @Test
    public void calcCostCartWithThreeProduce() throws UnderAgeException {
        double amount = cartWithThreeProduce.calcCost();
        assertEquals(cartWithThreeProduceExpectedCost, amount, .01);
    }
    
    @Test
    public void calcCostCartWithSevenProduce() throws UnderAgeException {
        double amount = cartWithSevenProduce.calcCost();
        assertEquals(cartWithSevenProduceExpectedCost, amount, .01);
    }
    
    @Test
    public void calcCostCartWithAlcoholAndFrozen() throws UnderAgeException {
        double amount = cartWithAlcoholAndFrozen.calcCost();
        assertEquals(cartWithAlcoholAndFrozenExpectedCost, amount, .01);
    }
    
}