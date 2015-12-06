package polyhack.purplesquadmonopoly.cityquest;

import android.test.ActivityInstrumentationTestCase2;

import java.io.IOException;
import java.util.List;

import polyhack.purplesquadmonopoly.cityquest.model.Journey;
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

        final List<Journey> journies = getJourneys();

        assertNotNull(journies);
    }

    private List<Journey> getJourneys() throws IOException {
        final CityQuestService service = ServiceGenerator.createService(CityQuestService.class);
        final Call<List<Journey>> notesCall = service.getAllJournies();
        return notesCall.execute().body();
    }


}
