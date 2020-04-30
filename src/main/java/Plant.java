import java.util.Random;
import java.util.List;
import java.util.Iterator;
/**
 * The 'Plant' class models a plant. A plant is a food 
 * source for some animals, normally the animals at the
 * bottom of the food chain.
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 15/02/2020
 */
public abstract class Plant implements Actor {
    // A shared random number generator to control brreding.
    private static final Random rand = Randomizer.getRandom();
    // Determines if the plant is alive
    private boolean isAlive;
    // The age of the plant
    private int age;
    // The plant's field
    private Field field;
    // The plant's position in the field
    private Location location;

    /**
     * Creates a new plant.
     * @param field The field the plant is in.
     * @param location The location of the plant in the field.
     */
    public Plant(Field field, Location location) {
        this.field = field;
        setLocation(location);
        isAlive = true;
        age = 0;
    }

    /**
     * Place the plant at the new location in the given field.
     * @param newLocation The plant's new location.
     */
    protected void setLocation(Location newLocation) {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the plant's field.
     * @return The plant's field.
     */
    protected Field getField() {
        return field;
    }

    /**
     * Return the plant's location.
     * @return The plant's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Returns the current age of the plant.
     * @return The plant's current age.
     */
    protected int getAge() {
        return age;
    }

    /**
     * Indicate that the plant is no longer alive.
     * It is removed from the field.
     */
    protected void setDead() {
        isAlive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Increments the age of the plant.
     */
    protected void incrementAge() {
        age++;
    }

    /**
     * Determines if the plant can be eaten.
     * The plant has to have grown for a certain time before an animal can eat it
     * @return true if plant is ripe enough, false if not.
     */
    protected boolean isRipe() {
        return (age >= getRipeAge());
    }

    /**
     * Generates new plants and places them in the field.
     * @param currentWeather The current weather to provide the 
     * probability of the weather.
     */
    protected void generateNewPlant(Weather currentWeather, List<Actor> newPlants) {
        if (isAlive()) {
            Location newLocation = field.freeAdjacentLocation(location);
            if (newLocation != null) {
                if (rand.nextDouble() <= currentWeather.getWeatherProbability() && isRipe()) {
                    newPlants.add(getNewPlantObject(field, newLocation));
                }
            }
        }
    }

    /**
     * Kills some of the plants in the field.
     * The plants get drown by the rain.
     * @param currentWeather The current weather to provide the 
     * probability of the weather. 
     */
    protected void killPlants(Weather currentWeather) {
        if (rand.nextDouble() <= currentWeather.getWeatherProbability()) {
            setDead();
        }
    }

    // Override methods

    /**
     * Determines if the plant can move.
     * This is always false.
     * @return false
     */
    @Override
    public boolean moves() {
        return false;
    }

    /**
     * Determines if the plant is still alive.
     * @return true, if younger than max age and if it hasn't been eaten.
     */
    @Override
    public boolean isAlive() {
        if (age <= getMaxAge() && isAlive) {
            return true;
        }
        else {
            return false;
        }
    }

    // Abstract methods

    /**
     * Returns a new plant object created using the paramters. 
     * @return A new plant object.
     * @param field The field where the plant object is located.
     * @param location The location in the field of the plant object.
     */
    abstract protected Plant getNewPlantObject(Field field, Location location);

    /**
     * Returns the maximum age of the plant.
     * @return max age of the plant.
     */
    abstract protected int getMaxAge();

    /**
     * Retuns the food value of the plant. This is how much the animals 
     * food level is increased by.
     * @ return FoodLevel of the plant.
     */
    abstract protected int getFoodValue();

    /**
     * Returns the age of the plant when its ripe
     * enough to eat.
     * @return The age when plant is ripe.
     */
    abstract protected int getRipeAge();

    /**
     * Returns the probability the plant is created. 
     * @return creation probability.
     */
    abstract protected double getCreationProbability();
}