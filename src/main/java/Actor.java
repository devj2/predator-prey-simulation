import java.util.List;
/**
 * The interface 'Actor' models an actor in the simulation. 
 * The actor is either alive or dead and each actor can do
 * a certain action.
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 05/02/2020
 */
public interface Actor {
    /**
     * This is what the actor does most of the time. 
     * @param newActors A list for recievely newly created actors.
     * @param isNightTime true if its night time, otherwise false.
     * @param currentWeather The current weather in the simulation.
     */
    void act(List<Actor> newActors, boolean isNightTime, Weather currentWeather);
    
    /**
     * Determines if the animal is alive.
     * @return true, if the actor is alive, false if dead.
     */
    boolean isAlive();
    
    /**
     * Determines if the actor moves.
     * @return true if the actor moves, false if not.
     */
    boolean moves();
}