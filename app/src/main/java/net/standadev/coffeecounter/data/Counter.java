package net.standadev.coffeecounter.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Standa on 05.11.2017.
 */

public class Counter {

    final static public String USER_ID = "USER_ID";
    final static public String INGREDIENT_ID = "INGREDIENT_ID";

    static private Counter instance;
    private ArrayList<IngredientCounter> ingredientCounters;
    private ArrayList<UserCounter> userCounters;
    private Map<Long, UserCounter> mapIdUserCounters;
    private Map<Long, IngredientCounter> mapIdIngredientCounters;

    protected Counter() {
        ingredientCounters = new ArrayList<IngredientCounter>();
        userCounters = new ArrayList<UserCounter>();

        mapIdUserCounters = new HashMap<Long, UserCounter>();
        mapIdIngredientCounters = new HashMap<Long, IngredientCounter>();

    }

    static public Counter getInstance() {
        if (instance == null) {
            instance = new Counter();
            instance.reload();
        }

        return instance;
    }

    public void reload() {
        ingredientCounters.clear();
        userCounters.clear();

        loadTestData();
    }

    public void addUser(User user) {
        // TODO add user to DB
        ArrayList<IngredientCounter> uic;
        uic = new ArrayList<IngredientCounter>();

        for (IngredientCounter ic : ingredientCounters) {
            uic.add(new IngredientCounter(ic.getIngredient()));
        }
        UserCounter uc =new UserCounter(user, uic);
        userCounters.add(uc);
        mapIdUserCounters.put(user.getId(), uc);
    }

    public void orderIngredient(User user, Ingredient ingredient, float quantity) {

    }

    public ArrayList<UserCounter> getListOfUserCounter() {
        return userCounters;
    }

    public UserCounter getUserCounterFromId(long id) {
        return mapIdUserCounters.get(id);
    }


    private void loadTestData() {
        Ingredient ic = new Ingredient(1, "Brasilia Santos", new IngredientType(1, "coffee"));
        IngredientCounter icc = new IngredientCounter(ic, 0);
        ingredientCounters.add(icc);
        mapIdIngredientCounters.put(ic.getId(), icc);

        Ingredient im = new Ingredient(2, "Lidl milk", new IngredientType(2, "milk"));
        IngredientCounter imc = new IngredientCounter(im, 0);
        ingredientCounters.add(new IngredientCounter(im, 0));
        mapIdIngredientCounters.put(im.getId(), imc);

        User us = new User(1, "Standa");
        addUser(us);

        User uv = new User(2, "Vlaďa");
        addUser(uv);

        User ur = new User(3, "Robert");
        addUser(ur);

        User um = new User(4, "Michal");
        addUser(um);

        orderIngredient(us, ic, 2.0f);
        orderIngredient(us, im, 1.0f);
        orderIngredient(uv, im, 1.0f);
    }
}
