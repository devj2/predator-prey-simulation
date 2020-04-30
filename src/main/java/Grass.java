import java.util.List;
/**
 * The class 'Grass' models an area of grass that is a food souce for some 
 * animals. The grass grows util its too old and dies. The grass can be eaten
 * and after a certain age, the grass has grown enough to be eaten.
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 20/02/2020
 */
public class Grass extends Plant
{
    // The food value of Grass
    private final static int FOOD_VALUE = 20;
    // The maxmimum age a plant can live for
    private final static int MAX_AGE = 30;
    // The age of the plant when the plant is ripe enough to eat
    private final static int RIPE_AGE = 8;
    // The probability that a plant will be created.
    private static final double CREATION_PROBABILITY = 0.05;

    /**
     * Creates a new Grass plant. 
     * @param field The field the grass is located in.
     * @param location The location of the grass in the field.
     */
    public Grass(Field field, Location location){
        super(field, location); 
    }
   
    // Override methods
    
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
     * Returns the food value of the Grass
     * @return grass' food value.
     */
    @Override
    public int getFoodValue() {
        return FOOD_VALUE;
    }

    /**
     * Returns the maximum age a plant grows for before dying.
     * @return max age of plant.
     */
    public int getMaxAge() {
        return MAX_AGE;
    }
    
    /**
     * Returns a new plant object created using the paramters. 
     * @return A new Grass object.
     * @param field The field where the grass object is located.
     * @param location The location in the field of the grass object.
     */
    @Override
    public Plant getNewPlantObject(Field field, Location location) {
        return new Grass(field, location);
    }

    /**
     * Simulates the plant growing and determines if the plant is still alive.
     * @param newGrass A list of new grass objects.
     * @param isNightTime true if its night time.
     * @param currentWeather The current weather experienced in the 
     * simulation.
     */
    @Override
    public void act(List<Actor> newGrass, boolean isNghtTime, Weather currentWeather) {
        incrementAge();
        if (!isAlive()) {
            setDead();
        }
        else {
            if (currentWeather instanceof Sun) {
                generateNewPlant(currentWeather, newGrass);
            }  
            else if (currentWeather instanceof Rain) {
                killPlants(currentWeather);
            }
        }
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