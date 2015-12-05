package polyhack.purplesquadmonopoly.cityquest.service;

import polyhack.purplesquadmonopoly.cityquest.model.User;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;

/**
 * Created by Ovi on 05-Dec-15.
 */
public interface CityQuestService {

    @GET("/login")
    Call<User> loginUser(@Body User user);

}
