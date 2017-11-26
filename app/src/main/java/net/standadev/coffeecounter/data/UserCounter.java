package net.standadev.coffeecounter.data;

import java.util.ArrayList;

/**
 * Created by Standa on 05.11.2017.
 */

public class UserCounter {
    private User user;
    private ArrayList<IngredientCounter> ingredientCounters;
    private float debt;

    public UserCounter(User user, ArrayList<IngredientCounter> ingredientCounters, float debt) {
        this.user = user;
        this.ingredientCounters = ingredientCounters;
        this.debt = debt;
    }

    public UserCounter(User user, ArrayList<IngredientCounter> ingredientCounters) {
        this(user, ingredientCounters, 0.0f);
    }

    public User getUser() {
        return user;
    }

    public ArrayList<IngredientCounter> getIngredientCounters() {
        return ingredientCounters;
    }

    public float getDebt() {
        return debt;
    }
    public void addDebt(float diff) {
        debt += diff;
    }
    public void clearDebt(){
        debt = 0.0f;
    }
}
