package app.apphub.devon.walkingquest.database.objects;

/**This class keeps track of data and logic for quests.
 * @author      Cole DeMan cole@coledeman.com
 * @version     1.0                 (current version number of program)
 * @since       1.0          (the version of the package this class was first added to)
 *
 *
 */

public class Quest extends DatabaseObject<Quest> {

    private String name;
    private long activeSteps;
    private long stepGoal;
    private int userID;
    private String description;
    private boolean completed;
    private int difficulty;
    private Reward questReward;

    //TODO:level requirement for quest
    private short levelRequirement;

    /**
     * Constructor for the Quest object. This represents
     * a quest that the user can do.
     * @param name This Quest's name.
     * @param stepGoal Total number of steps needed to
     *                 complete the quest.
     * @param difficulty The difficulty rating of the quest.
     *                   1 being easy and 3 being hard.
     */
    public Quest(String name, long stepGoal, int difficulty) {
        this.name = name;
        activeSteps = 0;
        this.stepGoal = stepGoal;
        completed = false;
        this.difficulty = difficulty;

        /** For now I'm setting it to a blank reward. In the future, we should auto-generate this*/
        questReward = new Reward();
    }

    /**
     * Constructor for the Quest object. This represents
     * a quest that the user can do.
     * @param id The id number of the quest.
     * @param name This Quest's name.
     * @param activeSteps The number of steps the user
     *                    has completed
     * @param stepGoal Total number of steps needed to
     *                 complete the quest.
     * @param userID The id number of the user doing the quest.
     * @param completed A boolean to keep track of if the quest
     *                  has been completed.
     * @param difficulty The difficulty rating of the quest.
     *                   1 being easy and 3 being hard.
     */
    public Quest(int id, String name, String description, long activeSteps, long stepGoal, int userID, boolean completed, int difficulty,
                 short levelRequirement) {
        super(id);
        this.name = name;
        this.description = description;
        this.activeSteps = activeSteps;
        this.stepGoal = stepGoal;
        this.completed = completed;
        this.difficulty = difficulty;
        this.userID = userID;
        this.levelRequirement = levelRequirement;
    }

    /**
     * A simple method to check all parameters in 2
     * quest objects match.
     * @param quest
     * @return true if the objects are equal false otherwise.
     */
    public boolean equals(Quest quest){
            boolean result = true;
            if (this.id != quest.getId()) result = false;
            if (!this.name.equals(name)) result = false;
            if (this.activeSteps != quest.getActiveSteps()) result = false;
            if (this.stepGoal != quest.getStepGoal()) result = false;
            if (this.completed != quest.isCompleted()) result = false;
            if (this.difficulty != quest.getDifficulty()) result = false;
            if (this.userID != quest.getUserID()) result = false;
            if(this.levelRequirement != quest.getLevelRequirement()) result = false;

        return result;
    }

    /**
     * Checks if the quest is completed.
     * @return true if completed, false if not.
     */
    public boolean checkIfComplete(){
        if(activeSteps>stepGoal){
            completed = true;
            return true;
        }
        return false;
    }

    /**
     * Addes steps toward completing {@link Quest} goal.
     * @param steps Number of steps to be added.
     * @return True if {@link Quest} is completed false otherwise.
     */
    public boolean addActiveSteps(long steps){
        activeSteps+=steps;
        return checkIfComplete();
    }

    /**
     * Retrieves quest id number.
     * @return the id number of the {@link Quest}.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the step goal.
     * @return an long equal the the step goal.
     */
    public long getStepGoal() {
        return stepGoal;
    }

    /**
     * Sets a new step goal.
     * @param stepGoal the number of steps needed
     *                 to complete the {@link Quest}.
     */
    public void setStepGoal(long stepGoal) {
        this.stepGoal = stepGoal;
    }

    /**
     * Sets a new {@link Quest} id.
     * @param id the number identifing the
     *           {@link Quest} in the database.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the {@link Quest} name.
     * @return The name of the quest.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new {@link Quest} name.
     * @param name the name of the {@link Quest}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the number of steps towards
     * the {@link Quest}'s goal
     * @return The steps counted toward the goal so far.
     */
    public long getActiveSteps() {
        return activeSteps;
    }

    /**
     * Sets a new number of active steps.
     * <p>
     *     <Strong>If you are adding steps use the {@link #addActiveSteps(long)} method.</Strong>
     * </p>
     * @param  activeSteps a new value for active steps.
     */
    public void setActiveSteps(long activeSteps) {
        this.activeSteps = activeSteps;
    }

    /**
     * Gets the id number of the user doing the {@link Quest}.
     * @return The id number of the {@link User}.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the id of the {@link User} doing the {@link Quest}.
     * @param userID the id of the {@link User}doing the {@link Quest}
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets a boolean to tell if the {@link Quest} is completed.
     * @return true if completed, false if not.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets whether the {@link Quest}is completed or not.
     * @param completed true for completed,
     *                  false for not completed.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Gets the difficulty of the {@link Quest}.
     * @return The steps counted toward the goal so far.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty of the {@link Quest}.
     * 1 for easy. 3 for hard.
     * @param difficulty integer 1 to 3.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getLevelRequirement() { return levelRequirement; }

    public void setLevelRequirement(short levelRequirement) { this.levelRequirement = levelRequirement; }

    /**
     *  This function will delimit information based on quest information that is important for
     *  at-a-glance decisions, i.e. the bare minimum for what a user should know about the quest to
     *  make their decision
     *
     *  @return String*/
    public String getQuestHeader() {
        String msg = "";
        msg += name + "\t";
        msg += questReward;
        return msg;
    }
}
