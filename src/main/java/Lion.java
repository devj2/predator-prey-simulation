import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * The class 'Lion' models a lion in a jungle environment.
 * The lion prowls around looking for hippos. Whilst the lion
 * prowls around the jungle, the lion can become hungry and ages.
 * The lion can die from hunger or old age.
 * 
 * @author Joshua Haris, Tadhg Amin
 * @version 05/02/2020
 */
public class Lion extends Predator {
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Characteristics of all lions (class fields of lion)
    // The age at which a lion can start to breed.
    private static final int BREEDING_AGE = 12;
    // The age to which a lion can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a lion breeding.
    private static final double BREEDING_PROBABILITY = 0.6;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single hippo which is the
    // number of steps a lion can go before it has to eat again.
    private static final int HIPPO_FOOD_VALUE = 10;
    // The antelopes food value
    private static final int ANTELOPE_FOOD_VALUE = 10;

    /**
     * Creates a new lion. A lion can either be given a random age, or 
     * an age of zero. 
     * @param randomAge The lion will have a random age and food level if
     * its true.
     * @param field The field that contains the lions in.
     * @param location The location of the lion within the field.
     */
    public Lion(boolean randomAge, Field field, Location location) {
        super(field, location);
        if (randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            increaseFoodLevel(rand.nextInt(HIPPO_FOOD_VALUE));
        }
        else {
            setAge(0);
            increaseFoodLevel(HIPPO_FOOD_VALUE);
        }
        // adds the class names of the animals the lion eats
        addToEat(Hippo.class.getName());
        addToEat(Antelope.class.getName());
    }

    /**
     * This is what the Lion does most of the time: its on the prowl for
     * hippos. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newLions A list to return newly born lions.
     * @param isNightTime true if its night time otherwise false.
     * @param currentWeather The current weather experienced in the 
     * simulation.
     */
    @Override
    public void act(List<Actor> newLions, boolean isNightTime, Weather currentWeather) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (!isNightTime) {
                giveBirth(newLions);            
                // Move towards a source of food if found.
                Location newLocation = locateFood();
                if(newLocation == null) { 
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                // See if it was possible to move.
                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
                if (checkDisease()) {
                    diseaseAct();
                }
            } 
            // otherwise lion is sleeping and does not move or look for food
        }
    }

    /**
     * Creates a new Lion object using the parameters and returns the new object.
     * @return A new Lion object.
     */
    @Override
    public Animal getNewAnimalObject(boolean randomAge, Field field, Location location) {
        return new Lion(randomAge, field, location);
    }

    /**
     * Returns the maximum age a lion can live for.
     * @return Max age of lion.
     */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Returns the breeding age of the lion.
     * @return Breeding age of lion.
     */
    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Returns the maximum litter size for lions.
     * @return max litter size for lions.
     */
    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * Returns the breeding proability for lions.
     * @return Lion breeding probability.
     */
    @Override
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }
}