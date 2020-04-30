import javax.swing.*;
/**
 * The class 'Weather' models a weather environment that
 * affects the simulation.
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 17/02/2020
 */
public abstract class Weather {
    // The duration of the weather.
    private int duration;
    // The default duration
    private static final int DEFAULT_DURATION = 5;
    
    /**
     * Creates a weather object. This simulates a weather condition. 
     * @param duration The duration of the weather storm.
     */
    public Weather(int duration) {
        if (duration >= 0) {
            this.duration = duration;
        }
        else {
            this.duration = DEFAULT_DURATION;
        }
    }

    /**
     * Returns the duration of the storm.
     * @return The duration of the storm.
     */
    public int getDuration() {
        return duration;
    }
    
    /**
     * Decrements the duration left of the storm.
     */
    public void decrementDuration() {
        duration--;
    }
    
    // Abstract methods
    
    /**
     * Returns the probability that the weather event happens.
     * @return weather event probability.
     */
    abstract protected double getWeatherProbability();
    
    /**
     * Returns an image icon of the maching the current weather.
     * @return an image icon of the current weather.
     */
    abstract protected ImageIcon getImageIcon();
}