import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.util.HashMap;
import java.util.Set;
import java.util.ListIterator;
/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing animals and plants. The simulation has daytime 
 * from for 17 steps and night time for 7 steps.
 * 
 * @author David J. Barnes and Michael Kölling, Joshua Harris, Tadhg Amin
 * @version 2016.02.29 (2)
 */
public class Simulator {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // List of entities in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // holds the creation probabilites for the actors 
    private static HashMap<Actor, Double> creationProbabilites; 
    // The current weather
    private Weather currentWeather;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        // There is no weather condition that is affecting the environment.
        currentWeather = null; 
        actors = new ArrayList<>();
        field = new Field(depth, width);

        // holds the actor object and creation probability for the actor.
        creationProbabilites = new HashMap<>();
        creationProbabilites.put(new Hippo(false, field, new Location(1,1)), 0.20);
        creationProbabilites.put(new Tiger(false, field, new Location(1,1)), 0.03);
        creationProbabilites.put(new Lion(false, field, new Location(1,1)), 0.02);
        creationProbabilites.put(new Monkey(false, field, new Location(1,1)), 0.12);
        creationProbabilites.put(new Antelope(false, field, new Location(1,1)), 0.14);
        creationProbabilites.put(new Banana(field, new Location(1,1)), 0.03);
        creationProbabilites.put(new Grass(field, new Location(1,1)), 0.03);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);

        view.setColor(Hippo.class, Color.BLUE);
        view.setColor(Lion.class, Color.RED);
        view.setColor(Tiger.class, Color.ORANGE);
        view.setColor(Monkey.class, Color.PINK);
        view.setColor(Antelope.class, Color.MAGENTA);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Banana.class, Color.YELLOW);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60);   // runs simulation slower
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * actor.
     */
    public void simulateOneStep() {
        step++;

        // Provide space for newborn actors.
        List<Actor> newActors = new ArrayList<>();        
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {  
            Actor actor = it.next();     
            actor.act(newActors,isNightTime(), currentWeather);
            if(! actor.isAlive()) {                
                it.remove();
            }
        }       
        // Add the newly born actors to the actors list.
        actors.addAll(newActors);
        getCurrentWeather();
        view.showStatus(step, field, isNightTime());
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        actors.clear();
        populate();
        //view.setWeatherInformation(null);
        addSunAffect(10);
        // Show the starting state in the view.
        view.showStatus(step, field, isNightTime());
        
    }

    /**
     * Randomly populate the field with all of the actors that are to be created.
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                boolean created = false;
                Set keys = creationProbabilites.keySet();
                Iterator<Actor> it = keys.iterator();
                while (it.hasNext() && !created) {
                    Actor actor = it.next();
                    if (rand.nextDouble() <= creationProbabilites.get(actor)) {
                        Location location = new Location(row,col);
                        // animal actors breed so a new animal is created
                        if (actor.moves()) {
                            Animal animal = (Animal) actor;
                            actors.add(animal.getNewAnimalObject(true,field,location));   
                        }
                        else {
                            Plant plant = (Plant) actor;
                            actors.add(plant.getNewPlantObject(field,location));
                        }

                        created = true;
                    }
                }
                //else leave the location empty.
            }
        }

    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }

    /**
     * Returns the current weather object. 
     * @return The current weather object.
     */
    private void getCurrentWeather() {
        if (currentWeather != null && currentWeather.getDuration() >= 0) {
            currentWeather.decrementDuration();
            view.setWeatherInformation(currentWeather);
        }
        else {
            Random rand = new Random();
            Random rand2 = new Random();
            ArrayList<Weather> weatherProbabilities = new ArrayList<>();
            weatherProbabilities.add(new Sun(rand.nextInt(10)));
            weatherProbabilities.add(new Rain(rand.nextInt(10)));
            ListIterator<Weather> it = weatherProbabilities.listIterator();
            
            while ((it.hasNext()) ){ 
                Weather temp = it.next();
                double randtemp = rand2.nextDouble();
                if (randtemp <= temp.getWeatherProbability()) {
                   currentWeather = temp;
                   addAffect(currentWeather);
                }
            } 
        } 
    }

    /**
     * Simulates  the affect of a very rainy environment.
     * Sets the current weather to 'Sun'.
     * @param duration The duration of the weather.
     */
    public void addSunAffect(int duration) {
        currentWeather = new Sun(duration);
        view.setWeatherInformation(currentWeather);
    }
    
    /**
     * sets the weather information being displayed in the GUI.
     */
    public void addAffect(Weather weather) {
        view.setWeatherInformation(weather);
    }

    /**
     * Simulates  the affect of a very rainy environment.
     * Sets the current weather to 'Rain'.
     */
    public void addRainAffect(int duration) {
        currentWeather = new Rain(duration); 
        view.setWeatherInformation(currentWeather);
    }

    /**
     * Determines if it is night time in the simulation. 
     * @return true if the modulo 24 of the steps is between 17 and 23,
     * false if not.
     */
    private boolean isNightTime() {
        if (step % 24 >= 17) {
            return true;
        }
        else {
            return false;
        }
    }  
}