package net.standadev.coffeecounter.data;

/**
 * Created by Standa on 05.11.2017.
 */

public class User extends BaseId {


    public User(long id, String name) {
        super(id, name);
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private boolean isActive = true;

}
