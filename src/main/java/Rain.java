import javax.swing.*;
/**
 * The class 'Rain' models a torrential rain environment.
 * The rain causes plants to die.
 *
 * @author Joshua Harris, Tadhg Amin
 * @version 20/02/2020
 */
public class Rain extends Weather
{
    // The probability that it will rain.
    private static final double WEATHER_PROBABILITY = 0.2;
    
    /**
     * Creats a Rain object to simulate rain 
     * in the environment.
     * @param The duration of the weather.
     */
    public Rain(int duration) {  
        super(duration);
    }
    
        /**
     * Returns an image icon object of an image of the rain. 
     * @retun an image icon of the 'Rain' image.
     */
    public ImageIcon getImageIcon() {
        return new ImageIcon("Rain.png");
    }
    
    // Override methods
    
    /**
     * Returns the probability of creation.
     * @return probabiliy creation.
     */
    @Override
    public double getWeatherProbability() {
        return WEATHER_PROBABILITY;
    }
}