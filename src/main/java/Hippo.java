import java.util.List;
import java.util.Random;
/**
 * The class 'Hippo' models a Hippopotamus. 
 * Hippo's spend most of thier time wandering the jungle, eating 
 * plants and swimming in rivers. Hippo's can die of old age and 
 * they can breed multiple times during their lifetime.
 * 
 * @author Joshua Harris, Tadhg Amin
 * @version 05/02/2020
 */
public class Hippo extends Prey {
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Characteristics for all Hippos  (class fields)
    // The age when the hippo can start to breed.
    private static final int BREEDING_AGE = 5;
    // The maximum age a hippo can live for.
    private static final int MAX_AGE  = 100;
    // The likelihood of a hippo breeding.
    private static final double BREEDING_PROBABILITY = 1;
    // The maximum number of offspring a hippo can give birth to.
    private static final int MAX_LITTER_SIZE = 6;
    // The food value of a Hippo.
    private static final int FOOD_VALUE = 40;

    /**
     * Creates a new Hippo. A hippo can be given a random age or 
     * have an age of zero. 
     * @param randomAge If true, the hippo will have a random age.
     * @param field The field containing the hippos.
     * @param location The location of the hippo in the field.
     */
    public Hippo(boolean randomAge, Field field, Location location) {
        super( field, location);
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
     * This is what the Hippo does most of the time. The Hippo moves 
     * around and the hippo can beed, die of hunger or of old age.
     * @param newHippos A list to return newly born hippos.
     * @param isNightTime true if its night time, otherwise false.
     * @param currentWeather The current weather experienced in the 
     * simulation.
     */
    @Override
    public void act(List<Actor> newHippos, boolean isNightTime, Weather currentWeather) {
        incrementAge();
        if (isAlive()) {
            if (!isNightTime) {
                giveBirth(newHippos);
                // Try to move into a free location.
                Location newLocation = locatePlants();
                if (newLocation == null) {
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                if (newLocation != null) {
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
            /* otherwise when it is night time the Hippo is sleeping and 
             * does not move or look for food. The Hippo ages.
             */
        }
    }

    /**
     * Creates a new Hippo object using the parameters provided and returns the object.
     * @return A new hippo object.
     */
    public Animal getNewAnimalObject(boolean randomAge, Field field, Location location) {
        return new Hippo(randomAge, field, location);
    }

    /**
     * Returns the maximum age a hippo can live for.
     * @return Max age of hippo.
     */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Returns the breeding age of the hippo.
     * @return Breeding age of hippo.
     */
    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Returns the maximum litter size for hippos.
     * @return max litter size for hippos.
     */
    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * Returns the breeding proability for hippos.
     * @return Hippo breeding probability.
     */
    @Override
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * Returns the food vaue of the hippo which simulates
     * the extra steps an animal can make after eating a 
     * hippo before being hungry.
     */
    @Override
    public int getFoodValue() {
        return FOOD_VALUE;
    }
}