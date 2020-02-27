package main.java;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    protected int userAge;
    public List<Product> cart;
    public int cartStorage;
    public static final int MIN_PRODUCE_QTY_FOR_DISCOUNT = 3;
    public static final double DISCOUNTED_PRODUCE_COST = 5;
    public static final double PRODUCE_COST = 2;
    public static final double ALCOHOL_COST = 8;
    public static final double DAIRY_COST = 3;
    public static final double MEAT_COST = 10;
    public static final double FROZEN_COST = 5;
    public static final double ALCOHOL_FROZEN_COMBO_COST = 10;
    public static final String DEFAULT_STATE = "AZ";
    public static final int MIN_ALCOHOL_AGE = 21;

    /**
     * Calculates the final cost after all savings and tax has been applied. Also checks
     * that the user is of age to purchase alcohol if it is in their cart at checkout. Sales tax is always AZ tax.
     *
     * Calculation is based off of the following prices and deals:
     * Dairy -> $3
     * Meat -> $10
     * Produce -> $2 or 3 for $5
     * Alcohol -> $8
     * Frozen Food -> $5
     * Alcohol + Frozen Food -> $10
     *
     * If there is an alcohol product in the cart and the user is under 21, then an
     * UnderAgeException should be thrown.
     *
     * @return double totalCost
     * @throws UnderAgeException
     */
    public double calcCost() throws UnderAgeException {
    	int produceCounter = 0;
    	int alcoholCounter = 0;
    	int frozenCounter = 0;
    	double cost = 0;
    	for(int i = 0; i < cart.size(); i++) {
    		if(cart.get(i) instanceof Produce) {
    			produceCounter++;
    		}
    		else if(cart.get(i) instanceof Alcohol) {
    			if(userAge < MIN_ALCOHOL_AGE) {
    				throw new UnderAgeException("Buyer is under age");
    			}
    			alcoholCounter++;
    		}
    		else if(cart.get(i) instanceof FrozenFood) {
    			frozenCounter++;
    		}
    		else if(cart.get(i) instanceof Dairy) {
    			cost += DAIRY_COST;
    		}
    		else if(cart.get(i) instanceof Meat) {
    			cost += MEAT_COST;
    		}
    			
    	}
    	while(produceCounter > 0) {
    		if(produceCounter >= MIN_PRODUCE_QTY_FOR_DISCOUNT) {
    			cost += DISCOUNTED_PRODUCE_COST;
    			produceCounter -= MIN_PRODUCE_QTY_FOR_DISCOUNT;
    		}
    		else {
    			cost += PRODUCE_COST * produceCounter;
    			produceCounter = 0;
    		}
    	}
    	
    	while(alcoholCounter > 0 && frozenCounter > 0) {
    		cost += ALCOHOL_FROZEN_COMBO_COST;
    		alcoholCounter--;
    		frozenCounter--;
    	}
    	cost += ALCOHOL_COST * alcoholCounter;
    	cost += FROZEN_COST * frozenCounter;
    	
    	cost += getTax(cost, DEFAULT_STATE);
    	
    	return cost; //implement me, will be important for assignment 4 (nothing to do here for assignment 3)
    }

    // calculates how much was saved in the current shopping cart based on the deals, returns the saved amount
    // throws exception if alcohol is bought from underage person
    // TODO: Create node graph for this method in assign 4: create white box tests and fix the method, reach at least 98% coverage
    public int Amount_saved() throws UnderAgeException {
        int subTotal = 0;
        int costAfterSavings = 0;

        double produce_counter = 0;
        int alcoholCounter = 0;
        int frozenFoodCounter = 0;
        int dairyCounter = 0;

        for(int i = 0; i < cart.size(); i++) {
            subTotal += cart.get(i).getCost();
            costAfterSavings =costAfterSavings+cart.get(i).getCost();
            /*SER316-start instanceof works better in determining type */
            if (cart.get(i) instanceof Produce) { /*SER316-end*/
                produce_counter++;

                if (produce_counter >= 3) {
                    costAfterSavings -= 1;
                    produce_counter = 0;
                }
            } /*SER316-start instanceof works better in determining type */
            else if (cart.get(i) instanceof Alcohol) { /*SER316-end*/
                alcoholCounter++;
                if (userAge < 21) {
                    throw new UnderAgeException("The User is not of age to purchase alcohol!");
                }
            } /*SER316-start instanceof works better in determining type */
            else if (cart.get(i) instanceof FrozenFood) { /*SER316-end*/
                frozenFoodCounter++;
            } /*SER316-start instanceof works better in determining type */
            else if (cart.get(i) instanceof Dairy /*SER316-end*/)	// this needs changing to add the Dairy class to the algorithm, otherwise FrozenFood is attempted to be tested twice
                dairyCounter++;

            if (alcoholCounter >= 1 && frozenFoodCounter >= 1) {
                 costAfterSavings = costAfterSavings /*SER316-start*/ - /*SER316-end*/ 3;	// this should subtract from costAfterSavings to reflect the discount
                 alcoholCounter--;
                 frozenFoodCounter--;
            }
        }

        return subTotal - costAfterSavings;
    }

    /**
     *  Gets the tax based on state and the total. This only calculates the tax. Return value is meant to be added to total.
     * @param totalBT Total cost before taxes.
     * @param twoLetterUSStateAbbreviation State to calculate tax for.
     * @return tax based on state and the total
     */
    public double getTax(double totalBT, String twoLetterUSStateAbbreviation) {
        double newTotal = 0;
        switch (twoLetterUSStateAbbreviation) {
            case "AZ":
                newTotal = totalBT * .08;
                break;
            case "CA":
                newTotal = totalBT * .09;
                break;
            case "NY":
                newTotal = totalBT * .1;
            case "CO":
                newTotal = totalBT * .07;
                break;
            default:
                return totalBT;
        }
        return newTotal;
    }

    public void addItem(Product np) {
      cart.add(np);
    }

    public boolean RemoveItem(Product productToRemove)
    {
    		boolean test = false;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i) == productToRemove) {
                 cart.remove(i);
                 test = true;
                 return test;
            }
        }
        return false;
    }

    public Cart(int age) {
        userAge = age;
        cart = new ArrayList<Product>();
    }
}
