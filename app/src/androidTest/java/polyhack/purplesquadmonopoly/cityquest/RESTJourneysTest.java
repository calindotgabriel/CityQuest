package polyhack.purplesquadmonopoly.cityquest;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import polyhack.purplesquadmonopoly.cityquest.model.Journey;
import polyhack.purplesquadmonopoly.cityquest.service.CityQuestService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by motan on 20.10.2015.
 */
public class RESTJourneysTest extends ActivityInstrumentationTestCase2<MainActivity> {


    public static final String BASE_URL = "http://192.168.0.100/";
    private String TAG = "Tests";

    public RESTJourneysTest() {
        super(MainActivity.class);
    }

    public void testGetAll() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final CityQuestService service = retrofit.create(CityQuestService.class);

        final Call<List<Journey>> notesCall = service.getAllJourneys();

        final List<Journey> journeys = notesCall.execute().body();

        assertNotNull(journeys);

    }



}
