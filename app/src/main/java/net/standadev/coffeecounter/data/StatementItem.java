package net.standadev.coffeecounter.data;

/**
 * Created by Standa on 29.11.2017.
 */

public class StatementItem {
    private User user;
    private IngredientUser ingredientUser;

    public StatementItem(User user, IngredientUser ingredientUser) {
        this.user = user;
        this.ingredientUser = ingredientUser;
    }

    public User getUser() {
        return user;
    }

    public IngredientUser getIngredientUser() {
        return ingredientUser;
    }
}
