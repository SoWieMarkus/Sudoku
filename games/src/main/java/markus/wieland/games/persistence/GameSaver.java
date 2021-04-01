package markus.wieland.games.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class GameSaver<S extends GameState> {

    protected final String key;
    protected final String value;

    private final Class<S> sClass;
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public GameSaver(Class<S> sClass, String key, String value, Context context) {
        this.key = key;
        this.sClass = sClass;
        this.value = value;
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
    }

    public GameSaver(Class<S> sClass, Context context) {
        this(sClass, "markus.wieland.games.KEY", "markus.wieland.games.VALUE", context);
    }

    public S getGameState() {
        String gameState = sharedPreferences.getString(key, null);
        if (gameState == null) return null;
        return gson.fromJson(gameState, sClass);
    }

    public void delete() {
        storeString("");
    }

    public void save(S s) {
        storeString(gson.toJson(s));
    }

    private void storeString(String string) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, string);
        editor.apply();
    }

}
