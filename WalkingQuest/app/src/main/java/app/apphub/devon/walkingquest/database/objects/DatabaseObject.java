package app.apphub.devon.walkingquest.database.objects;

import app.apphub.devon.walkingquest.database.DatabaseHandler;

/**
 * Abstract for Objects that need to be stored in the database.
 *
 * Created by Cole DeMan on 3/3/2017.
 */

public abstract class DatabaseObject<T extends DatabaseObject> {

    int id;

    public DatabaseObject(int id){
        this.id = id;
    }

    public DatabaseObject(){}

    /**
     * Retrieves object's id number.
     * @return the id number of the Object.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets a new Object database id.
     * @param id the number identifing the
     *           Object in the database.
     */
    public void setId(int id) {
        this.id = id;
    }

    public abstract boolean equals(T object);
}
