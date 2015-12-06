package polyhack.purplesquadmonopoly.cityquest;

import android.test.ActivityInstrumentationTestCase2;

import java.io.IOException;
import java.util.List;

import polyhack.purplesquadmonopoly.cityquest.model.Journey;
import polyhack.purplesquadmonopoly.cityquest.model.Spot;
import polyhack.purplesquadmonopoly.cityquest.service.CityQuestService;
import polyhack.purplesquadmonopoly.cityquest.service.ServiceGenerator;
import retrofit.Call;

/**
 * Created by motan on 20.10.2015.
 */
public class RESTSpotsTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public RESTSpotsTest() {
        super(MainActivity.class);
    }

    public void testGetAll() throws IOException {

        final List<Spot> spots = getSpotsForJourney();

        assertNotNull(spots);
    }

    private List<Spot> getSpotsForJourney() throws IOException {
        final CityQuestService service = ServiceGenerator.createService(CityQuestService.class);
        final Call<List<Spot>> spotsCall = service.getSpotsForJourney("41224d776a326fb40f000004");

        final List<Spot> spots = spotsCall.execute().body();

        return spots;
    }


}
