import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal implements Actor {
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();
    // Determines if the animal moves.
    protected static final boolean doesMove = true;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age.
    private int age;
    // The animals gender
    private boolean isMale;
    // The animals's food level 
    private int foodLevel;
    // Determines if the animal has a disease.
    private boolean diseased;

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location) {
        alive = true;
        diseased = false;
        this.field = field;
        setLocation(location);
        int random = rand.nextInt(101);
        if (random % 2 == 0) {
            isMale = true;
        }
        else {
            isMale = false;
        }
    }

    /**
     * Make this animal more hungry. Decremets the food level field.
     * This could result in the animal's death.
     */
    protected void incrementHunger() {
        decrementFoodLevel();
        if(getFoodLevel() <= 0) {
            setDead();
        }
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    @Override
    public boolean isAlive() {
        return alive;
    }

    /**
     * Determines if the animal has the disease.
     * @retrun true if the animal has the disease, false if the animal doesn't
     * have the disease.
     */
    protected boolean checkDisease() {
        Random rand = new Random();
        if (rand.nextInt(100) ==  17) {
            setDisease();
            return diseased;
        }
        else {
            return diseased;
        }
    }

    /**
     * Sets the animal to having the disease and drops the max age by 10 steps.
     */
    protected void setDisease() {
        diseased = true;
        age = getMaxAge() - 10;
    }

    /**
     * Simulates the diseease acting in the animal. 
     * if an animal has the disease ,an animal in an adjacent location
     * will become infected. 
     */
    protected void diseaseAct() {
        // this will only execute if the animal is not dead.
        if (this.getField() != null) { 
            List<Location> adjacentLocations = 
                getField().adjacentLocations(getLocation());
            Iterator<Location> iterator = adjacentLocations.iterator();
            while (iterator.hasNext()){
                Location foundAt = iterator.next();
                Object actor = getField().getObjectAt(foundAt);
                if (actor != null) {
                    Actor actor1 = (Actor) actor;
                    // only animals can move & disease only infects animals.
                    if (actor1.moves()) {
                        Animal animal = (Animal) actor;
                        if (animal.isAlive()) {
                            animal.setDisease();
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead() {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Simulates the animal giving birth.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born animals.
     */
    protected void giveBirth(List<Actor> newAnimals) {
        // New animals are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(
                getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            newAnimals.add(getNewAnimalObject(false,field,loc));
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            List<Location> adjacentLocations = getField().adjacentLocations(
                    getLocation());
            Iterator<Location> it = adjacentLocations.iterator();
            while (it.hasNext()) {
                Location location = it.next();
                Actor actor = (Actor) getField().getObjectAt(location);
                if (actor != null) {
                    if (actor.moves()) {
                        Animal animal = (Animal) actor;
                        if (this.getClass().getName().equals(animal.getClass()
                        .getName()) 
                        && animal.getIsMale()) {
                            births = rand.nextInt(getMaxLitterSize()) + 1;
                        }
                    }
                }
            }
        }
        return births;
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * An animal can breed if it has reached the breeding age.
     * @return true, if the animal is female and is of breeding age, otherwise false.
     * 
     */
    protected boolean canBreed() {
        // only female animals can breed
        if (!getIsMale()) {
            return age >= getBreedingAge();
        }
        else {
            return false;
        }
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation) {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField() {
        return field;
    }

    /**
     * Increase the age.
     * This could result in the animals's death.
     */
    protected void incrementAge() {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Sets the age of the animal.
     * @param age The animal's age.
     */
    protected void setAge(int age) {
        if (age >= 0)
        this.age = age;
    }
    
    /**
     * Returns true if the animal is male, false if female.
     * @return true if the animal is male, false if female.
     */
    protected boolean getIsMale() {
        return isMale;
    }

    /**
     * Returns the animal's food level.
     * @return animal's food level.
     */
    public int getFoodLevel() {
        return foodLevel;
    }

    /**
     * Decrements the animal's food level.
     */
    public void decrementFoodLevel() {
        foodLevel--;
    }

    /**
     * Increases the food level of the animal by a given amount.
     * @param amount The amount to increase the fod value by.
     */
    protected void increaseFoodLevel(int amount) {
        if (amount > 0 ) {
            foodLevel += amount;
        }
    }

    /**
     * Determines if the animal can move.
     * All animals can move.
     * @return true
     */
    @Override
    public boolean moves() {
        return true;
    }

    // Abstract methods

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     * @param isNightTime true if its night time, otherwise false.
     * @param currentWeather The current weather in the simulation.
     */
    abstract public void act(List<Actor> newAnimals, boolean isNightTime,
    Weather currentWeather);

    /**
     * Creates a new actor object using the parameters provided and returns the actor.
     * @return a new actor object.
     */
    abstract protected Animal getNewAnimalObject(boolean randomAge, Field field, 
    Location location);

    /**
     * Returns the max litter size for an animal.
     * @return max litter size of animal
     */
    abstract protected int getMaxLitterSize();

    /**
     * Returns the breeding probability of the animal.
     * @return Breeding probability of animal.
     */
    abstract protected double getBreedingProbability();

    /**
     * Retuns the maximum age an animal can live for.
     * @return Max age an animal can live for.
     */
    abstract protected int getMaxAge();

    /**
     * Returns the breeding age for an animal.
     * @return The breeding age for the aniaml.
     */
    abstract protected int getBreedingAge();
}