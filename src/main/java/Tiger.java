import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * The class 'Tiger' models a tiger in a jungle environment.
 * The tiger hunts for its prey and eats Deers. The tiger 
 * also ages and can die of hunger or of old age. 
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 05/02/2020
 */

public class Tiger extends Predator {
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Characteristics of all tigers (class fields of tiger)
    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.8;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single hippo which is the
    // number of steps a tiger can go before it has to eat again.
    private static final int HIPPO_FOOD_VALUE = 10;
    // The moneky's food value
    private static final int MONKEY_FOOD_VALUE = 8;
    
    /**
     * Creates a new Tiger. A tiger can either be given a random age, or
     * an age of zero.
     * @param randomAge The tiger will have a random age and food level if
     * its true.
     * @param field The field that contains the tigers in.
     * @param location The location of the tiger within the field.
     */
    public Tiger(boolean randomAge, Field field, Location location) {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            increaseFoodLevel(rand.nextInt(
                   (HIPPO_FOOD_VALUE + MONKEY_FOOD_VALUE) / 2));
        }
        else {
            setAge(0);
            increaseFoodLevel((HIPPO_FOOD_VALUE + MONKEY_FOOD_VALUE) / 2);
        }
        // adds the class names of the animals the tiger eats to the set.
        addToEat(Hippo.class.getName());
        addToEat(Monkey.class.getName());
    }

    // Overridden methods
    
    /**
     * This is what the Tiger does most of the time: its on the prowl
     * for hippos. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newTigers A list to return newly born tigers.
     * @param isNightTime true if its night time, otherwise false.
     * @param currentWeather The current weather experienced in the 
     * simulation.
     */
    @Override
    public void act(List<Actor> newTigers,boolean isNightTime, Weather currentWeather) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (!isNightTime) {
                giveBirth(newTigers);            
                // Move towards a source of food if found.
                Location newLocation = locateFood();
                if(newLocation == null) { 
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(
                        getLocation());
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
                    //diseaseAct();
                }
            }
            /* otherwise when it is night time the antelope is sleeping and 
             * does not move or look for food. The antelope ages.
             */
        }
    }
    
    /**
     * Creates a new Tiger object using the parameters provided and returns the object.
     * @return A new Tiger object.
     */
    @Override
    public Animal getNewAnimalObject(boolean randomAge, Field field, Location location) {
        return new Tiger(randomAge, field, location);
    }

    /**
     * Returns the maximum age a tiger can live for.
     * @return Max age of tiger.
     */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Returns the breeding age of the tiger.
     * @return Breeding age of tiger.
     */
    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Returns the maximum litter size for tigers.
     * @return max litter size for tigers.
     */
    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * Returns the breeding proability for tigers.
     * @return Tiger breeding probability.
     */
    @Override
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }
}