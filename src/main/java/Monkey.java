import java.util.Random;
import java.util.List;
/**
 * The class 'Monkey' models a monkey in a jungle environment.
 * The monkey can eats plants and roam around the jungle. The monkey
 * can give birth to offspring and can possibly die from old age.
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 05/02/2020
 */
public class Monkey extends Prey {
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Characteristics for all monkeys (class fields).
    // The age when monkeys can start to breed
    private static final int BREEDING_AGE = 8;
    // The age at where the monkey will die from old age
    private static final int MAX_AGE = 80;
    // The likelihood of a monkey breeding.
    private static final double BREEDING_PROBABILITY = 0.4;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The monkey's food value 
    private static final int FOOD_VALUE = 40;

    /**
     * Creates a new monkey. A monkey can either be given a random age, or
     * have an age of zero.
     * @param randomAge The monkey will have a random age if its true.
     * @param field The field that contains the monkeys in.
     * @param location The location of the monkey within the field.
     */
    public Monkey(boolean randomAge, Field field, Location location) {
        super(field, location);
          if (randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            increaseFoodLevel(50);
        }
        else {
            setAge(0);
            increaseFoodLevel(50);
        }
        addToEatsPlants(Banana.class.getName());
    }

    /**
     * This is what the monkey does most of the time. The monkey swings 
     * around the jungle and it might give birth to other monkeys and can
     * die of old age.
     * @param newMonkeys A list of newly created monkeys.
     * @param isNightTime true if its night time, otherwise false.
     * @param currentWeather The current weather experienced in the 
     * simulation.
     */
    @Override
    public void act(List<Actor> newMonkeys,boolean isNightTime, Weather currentWeather) {
        incrementAge();
        if (isAlive()) {
            if (!isNightTime) {
                giveBirth(newMonkeys);
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
            /* otherwise when it is night time the amonkey is sleeping and 
             * does not move or look for food. The monkey ages.
             */
        }
    }

    /**
     * Creates a new Monkey object using the parameters and returns the new object.
     * @return A new monkey object.
     */
    public Animal getNewAnimalObject(boolean randomAge, Field field, Location location) {
        return new Monkey(randomAge, field, location);
    }

    /**
     * Returns the maximum age a monkey can live for.
     * @return Max age of monkey.
     */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Returns the breeding age of the monkey.
     * @return Breeding age of monkey.
     */
    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Returns the maximum litter size for monkey.
     * @return max litter size for monkey.
     */
    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * Returns the breeding proability for monkey.
     * @return monkey breeding probability.
     */
    @Override
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * Returns the Monkey's food value.
     * @return Monkey's food value.
     */
    @Override
    public int getFoodValue(){
        return FOOD_VALUE;
    }
}