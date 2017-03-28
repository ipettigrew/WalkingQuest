package app.apphub.devon.walkingquest.database.objects;

/**
 * This class keeps track of data and logic for quests.
 *
 *  @author  Cole DeMan
 *  @author  Devon Rimmington
 *  @author  Ian Pettigrew
 *  @author  Jonathan McDevitt
 *  @version 1.8
 *  @see     app.apphub.devon.walkingquest.database.DatabaseHandler
 *  @see     app.apphub.devon.walkingquest.database.objects.DatabaseObject
 *  @see     Character
 *  @since   2017-03-03
 */
public class Quest extends DatabaseObject<Quest> {
    private String name;            // The name of the quest.
    private String description;     // Flavour text describing the quest.
    private long activeSteps;       // The amount of progress the User has made towards stepGoal.
    private long stepGoal;          // The amount of steps required to complete the quest.
    private boolean completed;      // Whether or not the quest has previously been finished.
    private short difficulty;       // Quest difficulty value, where EASY = 1, MEDIUM = 2, HARD = 3.
    private short levelRequirement; // Level a {@link Character} has to be for the quest to be available.

    /**
     * Default constructor.
     */
    public Quest() {
    }

    /**
     * Constructor for the Quest object that species all instance variables except for
     * activateSteps and completed. For instantiating new quests.
     *
     * @param id               The ID number of the quest.
     * @param name             This Quest's name.
     * @param description      Flavour text that describes a quest.
     * @param stepGoal         Total number of steps needed to complete the quest.
     * @param difficulty       The difficulty rating of the quest. 1 being easy and 3 being hard.
     * @param levelRequirement Level a {@link Character} has to be for the quest to be available.
     */
    public Quest(int id, String name, String description, long stepGoal, short difficulty,
                 short levelRequirement) {
        super(id);
        this.name = name;
        this.description = description;
        this.activeSteps = 0;
        this.stepGoal = stepGoal;
        this.completed = false;
        this.difficulty = difficulty;
        this.levelRequirement = levelRequirement;
    }

    /**
     * Constructor for the Quest object that specifies all instance variables. This represents a
     * quest that the user can do.
     *
     * @param id               The ID number of the quest.
     * @param name             This Quest's name.
     * @param description      Flavour text that describes a quest.
     * @param activeSteps      The number of steps the user has completed.
     * @param stepGoal         Total number of steps needed to complete the quest.
     * @param completed        A boolean to keep track of if the quest has been completed.
     * @param difficulty       The difficulty rating of the quest. 1 being easy and 3 being hard.
     * @param levelRequirement Level a {@link Character} has to be for the quest to be available.
     */
    public Quest(int id, String name, String description, long activeSteps, long stepGoal,
                 boolean completed, short difficulty, short levelRequirement) {
        super(id);
        this.name = name;
        this.description = description;
        this.activeSteps = activeSteps;
        this.stepGoal = stepGoal;
        this.completed = completed;
        this.difficulty = difficulty;
        this.levelRequirement = levelRequirement;
    }

    /**
     * Retrieves the {@link Quest} name.
     *
     * @return The name of the quest.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new {@link Quest} name.
     *
     * @param name the name of the {@link Quest}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor method for description.
     *
     * @return flavour text describing the quest.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Mutator method for description.
     *
     * @param description flavour text describing the quest.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the number of steps towards
     * the {@link Quest}'s goal
     *
     * @return The steps counted toward the goal so far.
     */
    public long getActiveSteps() {
        return activeSteps;
    }

    /**
     * Sets a new number of active steps.
     * <p>
     * <Strong>If you are adding steps use the {@link #addActiveSteps(long)} method.</Strong>
     * </p>
     *
     * @param activeSteps a new value for active steps.
     */
    public void setActiveSteps(long activeSteps) {
        this.activeSteps = activeSteps;
    }

    /**
     * Retrieves the step goal.
     *
     * @return an long equal the the step goal.
     */
    public long getStepGoal() {
        return stepGoal;
    }

    /**
     * Sets a new step goal.
     *
     * @param stepGoal the number of steps needed
     *                 to complete the {@link Quest}.
     */
    public void setStepGoal(long stepGoal) {
        this.stepGoal = stepGoal;
    }

    /**
     * Gets a boolean to tell if the {@link Quest} is completed.
     *
     * @return true if completed, false if not.
     */
    public boolean getCompleted() {
        return completed;
    }

    /**
     * Gets a boolean to tell if the {@link Quest} is completed.
     *
     * @return true if completed, false if not.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets whether the {@link Quest}is completed or not.
     *
     * @param completed true for completed,
     *                  false for not completed.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Gets the difficulty of the {@link Quest}.
     *
     * @return The steps counted toward the goal so far.
     */
    public short getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty of the {@link Quest}.
     * 1 for easy. 3 for hard.
     *
     * @param difficulty integer 1 to 3.
     */
    public void setDifficulty(short difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Accessor method for levelRequirement.
     *
     * @return the level required for the quest to become available to the {@link Character}
     */
    public short getLevelRequirement() {
        return levelRequirement;
    }

    /**
     * Mutator method for levelRequirement.
     *
     * @param levelRequirement level required for the quest to be available to the {@link Character}
     */
    public void setLevelRequirement(short levelRequirement) {
        this.levelRequirement = levelRequirement;
    }

    /**
     * A simple method to check all parameters in 2
     * quest objects match.
     *
     * @param quest Quest object to be compared to this Quest object.
     * @return true if the objects are equal false otherwise.
     */
    public boolean equals(Quest quest) {
        return (this.id == quest.getId() &&
                this.name.equals(quest.getName()) &&
                this.description.equals(quest.getDescription()) &&
                this.stepGoal == quest.getStepGoal() &&
                this.completed == quest.isCompleted() &&
                this.difficulty == quest.getDifficulty() &&
                this.levelRequirement == quest.getLevelRequirement());
    }

    /**
     * Checks if the quest is completed.
     *
     * @return true if completed, false if not.
     */
    public boolean checkIfComplete() {
        if (activeSteps >= stepGoal) {
            completed = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds steps toward completing {@link Quest} goal.
     *
     * @param steps Number of steps to be added.
     */
    public void addActiveSteps(long steps) {
        activeSteps += steps;
    }

    /**
     * This function will delimit information based on quest information that is important for
     * at-a-glance decisions, i.e. the bare minimum for what a user should know about the quest to
     * make their decision
     *
     * @return String
     */
    public String getQuestHeader() {
        String msg = "";
        msg += name + "\t";
        return msg;
    }
}