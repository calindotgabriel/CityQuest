package polyhack.purplesquadmonopoly.cityquest;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import polyhack.purplesquadmonopoly.cityquest.model.Adventure;
import polyhack.purplesquadmonopoly.cityquest.model.ComplexPreferences;
import polyhack.purplesquadmonopoly.cityquest.model.Spot;

/**
 * Created by motan on 06.12.2015.
 */
public class AdventurePersistence {

    public static final String KEY = "adventures";


    private final ComplexPreferences mPreferances;

    public AdventurePersistence(Context mContext) {
        mPreferances = ComplexPreferences.getComplexPreferences(mContext, KEY, Activity.MODE_PRIVATE);
    }

    public void save(Adventure adventure) {
        mPreferances.putObject(KEY, adventure);
        mPreferances.commit();
    }

    public boolean isAnotherAdventureStarted() {
        return mPreferances.containsObject(KEY);
    }

    public Adventure getAdventure() {
        return mPreferances.getObject(KEY, Adventure.class);
    }





}
