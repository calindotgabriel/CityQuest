package polyhack.purplesquadmonopoly.cityquest.utils;

import java.util.ArrayList;
import java.util.List;

import polyhack.purplesquadmonopoly.cityquest.model.Journey;

/**
 * Created by motan on 05.12.2015.
 */
public class MockFactory {

    public static List<Journey> getJourneys() {
        List<Journey> journeys = new ArrayList<>();

        Journey journey1 = new Journey();
        journey1.setName("Cetatuie");
        journey1.setDesc("Take a lovely walk thorught Cluj-Napoca's finest hills");
        journey1.setImgURL("http://mw2.google.com/mw-panoramio/photos/medium/4112582.jpg");
        journey1.setDistance((long) 2350);
        journey1.setDuration((long) 28);

        Journey journey2 = new Journey();
        journey2.setName("Centru vechi");
        journey2.setDesc("Enjoy a lovely day in a beautiful old place");
        journey2.setImgURL("http://www.cluj.travel/wp-content/uploads/2013/01/03_Case-vechi-in-Ko%C5%A1ce.jpg");
        journey2.setDistance((long) 1400);
        journey2.setDuration((long) 20);

        Journey journey3 = new Journey();
        journey3.setName("Padurea Baciu");
        journey3.setDesc("Explore the beautifully haunted Baciu forest.");
        journey3.setImgURL("http://static2.libertatea.ro/typo3temp/pics/04-foto-2-1588_9b532b063a.jpg");
        journey3.setDistance((long) 4550);
        journey3.setDuration((long) 55);

        journeys.add(journey1);
        journeys.add(journey2);
        journeys.add(journey3);

        return journeys;
    }
}
