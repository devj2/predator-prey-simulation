import java.util.Random;
import java.util.List;
import java.util.Iterator;
/**
 * The class 'Antelope' models the animal Antelope. Antelopes are
 * able to roam around, give birth, feed and die from old age.
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 05/02/2020
 */
public class Antelope extends Prey {
    // Characteristics for all antelopes  (class fields)
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The age when the antelope can start to breed.
    private static final int BREEDING_AGE = 8;
    // The maximum age a antelope can live for.
    private static final int MAX_AGE  = 100;
    // The likelihood of a antelope breeding.
    private static final double BREEDING_PROBABILITY = 0.2;
    // The maximum number of offspring a antelope can give birth to.
    private static final int MAX_LITTER_SIZE = 6;
    // The food value of the antelope
    private static final int FOOD_VALUE = 20;
    
    /**
     * Creates a new Antelope using and places in at a location in the current
     * field. An antelope can have a random age or have an age of zero.
     * @param randomAge true if antelope is to have a random age, else false.
     * @param field The field the antelope will be in.
     * @param location The location of the animal inside the field.
     */
    public Antelope(boolean randomAge, Field field, Location location) {
        super(field, location);
          if (randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            increaseFoodLevel(50);
        }
        else {
            setAge(0);
            increaseFoodLevel(50);
        }
        addToEatsPlants(Grass.class.getName());
    }

    /**
     * This is what the Antelope does most of the time. The Antelope 
     * moves around and the antelope can beed, die of hunger or of old age.
     * @param newAntelopes A list to return newly born antelopes. 
     * @param isNightTime tue if its night time otherwise false.
     * @param currentWeather The current weather experienced in the 
     * simulation.
     */
    @Override
    public void act(List<Actor> newAntelopes,boolean isNightTime,Weather currentWeather) {
        incrementAge();
        if (isAlive()) {
            if (!isNightTime) {
                giveBirth(newAntelopes);
                // Try to move into a free location.
                Location newLocation = locatePlants();
                if (newLocation == null) {
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                if (checkDisease()) {
                    diseaseAct();
                }
                if (newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
            }
            /* otherwise when it is night time the antelope is sleeping and 
             * does not move or look for food. The antelope ages.
             */
        }
    }

    /**
     * Creates a new antelope object using the parameters and returns the 
     * antelope object.
     * @return An antelope object.
     */
    @Override
    public Animal getNewAnimalObject(boolean randomAge, Field field, Location location) {
        return new Antelope(randomAge, field, location);
    }

    /**
     * Returns the maximum age a antelope can live for.
     * @return Max age of antelope.
     */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Returns the breeding age of the antelope.
     * @return Breeding age of antelope.
     */
    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Returns the maximum litter size for antelope.
     * @return max litter size for antelopes.
     */
    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * Returns the breeding proability for antelopes.
     * @return Antelope breeding probability.
     */
    @Override
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * Returns the food value for the antelope.
     * @return Antelope's food value.
     */
    @Override
    public int getFoodValue(){
        return FOOD_VALUE;
    }
}