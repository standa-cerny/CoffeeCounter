package net.standadev.coffeecounter.data;

import android.content.Context;

import net.standadev.coffeecounter.data.db.CounterDataProvider;

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

    private CounterDataProvider dataProvider;

    protected Counter(Context context) {
        ingredientCounters = new ArrayList<IngredientCounter>();
        userCounters = new ArrayList<UserCounter>();

        mapIdUserCounters = new HashMap<Long, UserCounter>();
        mapIdIngredientCounters = new HashMap<Long, IngredientCounter>();

        dataProvider = new CounterDataProvider(context);

    }

    static public Counter getInstance(Context context) {
        if (instance == null) {
            instance = new Counter(context);
            instance.reload();
        }

        return instance;
    }

    static public Counter getInstance() {
        return instance;
    }


    public void reload() {
        ingredientCounters.clear();
        userCounters.clear();

        loadTestData();
    }

    public void saveUser(User user) {
        if (user.getId() > 0){
            dataProvider.update(user);

        } else {
            dataProvider.insert(user);
            loadUser(user);
        }

    }


    public void loadUser(User user) {
        ArrayList<IngredientCounter> uic;
        uic = new ArrayList<IngredientCounter>();

        for (IngredientCounter ic : ingredientCounters) {
            uic.add(new IngredientCounter(ic.getIngredient()));
        }
        UserCounter uc =new UserCounter(user, uic);
        userCounters.add(uc);
        mapIdUserCounters.put(user.getId(), uc);
    }

    public void saveIngredient(Ingredient ingredient){
        // Save ingredient type
        IngredientType ingredientType = ingredient.getIngredientType();
        if (ingredientType.getId() > 0) {
            dataProvider.update(ingredientType);
        }else{
            dataProvider.insert(ingredient.getIngredientType());
        }

        // Save ingredient
        if (ingredient.getId() > 0) {
            dataProvider.update(ingredient);
        }else{
            dataProvider.insert(ingredient);
            reload();
        }

    }

    public void orderIngredient(User user, Ingredient ingredient, float diff) {
        // TODO Save order transaction to DB
        UserCounter uc = getUserCounterFromId(user.getId());
        IngredientCounter ic = getIngredientCounterFromId(ingredient.getId());

        ic.addQuantity(diff);
        addQuantity(uc.getIngredientCounters(), ingredient, diff);
    }

    public void closeStatement(Ingredient ingredient){
        // Clear total counter
        ingredientCounters.get(ingredient.getCounterIndex()).clear();

        // Clear user counters
        int index = ingredient.getCounterIndex();
        for (UserCounter uc : userCounters){
            uc.getIngredientCounters().get(index).clear();
        }
    }

    public ArrayList<UserCounter> getListOfUserCounter() {
        return userCounters;
    }
    public ArrayList<IngredientCounter> getListOfIngredientCounter() {
        return ingredientCounters;
    }

    public UserCounter getUserCounterFromId(long id) {
        return mapIdUserCounters.get(id);
    }

    public IngredientCounter getIngredientCounterFromId(long id) {
        return mapIdIngredientCounters.get(id);
    }


    private void loadTestData() {
        if (0 == 1) {
            Ingredient ic = new Ingredient(1, "Brasilia Santos", new IngredientType(1, "coffee"));
            loadIngredient(ic);

            Ingredient im = new Ingredient(2, "Lidl milk", new IngredientType(2, "milk"));
            loadIngredient(im);
        }

        for (Ingredient ingredient : dataProvider.getListOfIngredients()){
            loadIngredient(ingredient);
        }

        for (User user : dataProvider.getListOfUsers()){
            loadUser(user);
        }

        if (0 == 1) {
            User us = new User(1, "Standa");
            loadUser(us);

            User uv = new User(2, "Vlaďa");
            loadUser(uv);

            User ur = new User(3, "Robert");
            loadUser(ur);

            User um = new User(4, "Michal");
            loadUser(um);

            //orderIngredient(us, ic, 2.0f);
            //orderIngredient(us, im, 1.0f);
            //orderIngredient(uv, im, 1.0f);
        }
    }

    private void loadIngredient(Ingredient ingredient){
        // Save index to ingredient counters
        ingredient.setCounterIndex(ingredientCounters.size());

        // Add ingredient to counters
        IngredientCounter ic = new IngredientCounter(ingredient, 0);
        ingredientCounters.add(ic);
        mapIdIngredientCounters.put(ingredient.getId(), ic);
    }

    private void addQuantity(ArrayList<IngredientCounter> ingredientCounters, Ingredient ingredient, float diff){
        ingredientCounters.get(ingredient.getCounterIndex()).addQuantity(diff);
    }
}
