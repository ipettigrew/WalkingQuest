package app.apphub.devon.walkingquest.database.objects;

/**
 * Created by cole_ on 3/28/2017.
 */

public class IDandNameRow {

    int id;
    String name;

    public IDandNameRow(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
