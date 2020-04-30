import java.util.Random;
import javax.swing.*;
/**
 * The class 'Sun' models a very sunny environment condition. 
 * If the weather is sunny, more plants grow.
 *
 * @author Joshua Harris, tadhg Amin
 * @version 20/02/2020
 */
public class Sun extends Weather
{
    // The probability of the weather being sunny
    private static final double SUN_PROBABILITY = 0.5;
    // A random generator to generate a random period.
    private Random rnd = new Random(); 
    
    /**
     * Creates a new sun weather object. 
     * This simulates a sunny environment.
     * @param duration The duration of the weather event.
     */
    public Sun(int duration) {
        super(duration);
    }
    
    /**
     * Returns an image icon object of an image of the sun. 
     * @retun an image icon of the 'sun' image.
     */
    public ImageIcon getImageIcon() {
        return new ImageIcon("Sun.png");
    }
    
    // Override methods
    
    /**
     * Returns the probability of the weather being sunny.
     * @return The probability of sun.
     */
    @Override
    public double getWeatherProbability() {
        return SUN_PROBABILITY;
    }
}