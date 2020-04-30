import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
/**
 * The class 'Prey' models an animal that is close to the bottom of the food chain. 
 * The animal is eaten by predator animals. 
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 13/02/2020
 */
public abstract class Prey extends Animal {
    // The set of plants the animal eats
    Set<String> eatsPlants;

    /**
     *  Creates a new prey object. 
     *  @param field The field the prey object is in.
     *  @param location The location in the field where the prey is located.
     */
    public Prey(Field field, Location location) {
        super(field, location);
        eatsPlants = new HashSet<>();
    }

    /**
     * This simulates the predator looking for its prey. 
     * @return The location of one of the prey that was eaten.
     */
    protected Location locatePlants() {
        List<Location> adjacentLocations =
            getField().adjacentLocations(getLocation());
        Location location = null; // The location of an alive hippo
        Iterator<Location> iterator = adjacentLocations.iterator();
        while(iterator.hasNext()) {
            Location foundAt = iterator.next();
            Object obj = getField().getObjectAt(foundAt);
            if (obj != null) {
                Iterator<String> it = eatsPlants.iterator();
                while (it.hasNext()) {
                    Actor actor = (Actor) obj;
                    String className = it.next();
                    if (actor.getClass().getName().equals(className)){
                        Plant plant = (Plant) actor;
                        if ((plant.isAlive()) && (plant.isRipe())) {
                            if ((plant.isRipe())){
                                plant.setDead();
                                increaseFoodLevel(plant.getFoodValue());
                                location = foundAt;
                                break;
                            }
                        }
                        // otherwise the plant is either dead or not ripe enough
                    }
                }
            }
        }  
        return location;
    }

    /**
     * Adds the class name of the plant that the animal eats.
     * @param plant The class name of the plant.
     */
    public void addToEatsPlants(String plant) {
        if (plant != null) {
            eatsPlants.add(plant);
        }
    }

    /**
     * Returns all of the plant the prey eats.
     * @return A set of plant class names.
     */
    public Set<String> getEatsPlants() {
        return eatsPlants;
    }

    // Abstract methods

    /**
     * Returns the food value of the prey.
     * @return Food value of the prey.
     */
    abstract public int getFoodValue();
}