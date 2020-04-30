import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
/**
 * The 'Predator' class represents the shared characteristics of all predators.
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 10/02/2020
 */
public abstract class Predator extends Animal {
    // Holds the class names of the prey the predator eats
    protected static Set<String> prey;

    /**
     * Creates a new predator object.
     * @param field The field that the predator is currently in.
     * @param location The location of the predator in the field.
     */
    public Predator(Field field, Location location) {   
        super(field,location);
        prey = new HashSet<>();
    }

    /**
     * Adds a prey to the set of prey the predator eats.
     * @param The prey to add to the set.
     */
    protected void addToEat(String prey) {
        if (prey != null) {  
            this.prey.add(prey);
        }
    }

    /**
     * Returns all of the prey the predator eats.
     * @return A set of prey.
     */
    public Set<String> getPrey() {
        return prey;
    }

    /**
     * This simulates the predator looking for its prey. 
     * @return The location of one of the prey that was eaten.
     */
    protected Location locateFood() {
        List<Location> adjacentLocations =
            getField().adjacentLocations(getLocation());
        Location location = null; // The location of an alive hippo
        Iterator<Location> iterator = adjacentLocations.iterator();
        while(iterator.hasNext()) {
            Location foundAt = iterator.next();
            Object obj = getField().getObjectAt(foundAt);
            if (obj != null) {
                Iterator<String> it = prey.iterator();
                while (it.hasNext()) {
                    Actor actor = (Actor) obj;
                    String className = it.next();
                    if (actor.getClass().getName().equals(className)){
                        Prey prey = (Prey) actor;
                        if (prey.isAlive()) {
                            prey.setDead();
                            increaseFoodLevel(prey.getFoodValue());
                            location = foundAt;
                            break;
                        }
                    }
                }
            }
        }  
        return location;   
    }
}