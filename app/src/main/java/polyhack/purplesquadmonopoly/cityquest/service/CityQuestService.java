package polyhack.purplesquadmonopoly.cityquest.service;

import polyhack.purplesquadmonopoly.cityquest.model.User;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Ovi on 05-Dec-15.
 */
public interface CityQuestService {

    @POST("/login")
    Call<User> loginUser(@Body User user);

}
