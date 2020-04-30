import java.util.List;
/**
 * The class 'Banana' models a banana which ia food source for some of the
 * prey. 
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 12/02/2020
 */
public class Banana extends Plant {
    // The food value of a Banana
    private final static int FOOD_VALUE = 8;
    // The maxmimum age a plant can live for
    private final static int MAX_AGE = 30;
    // The age of the plant when the plant is ripe enough to eat
    private final static int RIPE_AGE = 8;
    // The probability that a plant will be created.
    private static final double CREATION_PROBABILITY = 0.002;

    /**
     * Creates a new banana. 
     * @param field The field where the banana is located in.
     * @param location The banana's location within the field.
     */
    public Banana(Field field, Location location){
        super(field, location); 
    }

    // Override methods
    
    /**
     * Returns the food value of the banana.
     * @return Banana's food value.
     */
    @Override
    public int getFoodValue() {
        return FOOD_VALUE;
    }

    /**
     * Returns the maximum age a plant grows for before dying.
     * @return max age of plant.
     */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Returns a new plant object created using the paramters. 
     * @return A new Banana object.
     * @param field The field where the banana object is located.
     * @param location The location in the field of the banana object.
     */
    @Override
    public Plant getNewPlantObject(Field field, Location location) {
        return new Banana(field, location);
    }

    /**
     * Simulates the plant growing and determines if the plant is still alive.
     * @param newBananas A list of new Bananas.
     * @param isNightTime true if its night time, otherwise false.
     * @param currentWeather The current weather experienced in the 
     * simulation.
     */
    @Override
    public void act(List<Actor> newBananas,boolean isNightTime, Weather currentWeather) {
        incrementAge();
        if (!isAlive()) {
            setDead();
        }
        else {
            if (currentWeather instanceof Sun) {
                generateNewPlant(currentWeather, newBananas);
            }  
            else if (currentWeather instanceof Rain) {
                killPlants(currentWeather);
            }
        }
    }

    /**
     * Returns the age of the plant when its ripe
     * enough to eat.
     * @return The age when plant is ripe. 
     */
    @Override
    public int getRipeAge() {
        return RIPE_AGE;
    }

    /**
     * Returns the probability of creation.
     * @return probabiliy creation.
     */
    @Override
    public double getCreationProbability() {
        return CREATION_PROBABILITY;
    }
}