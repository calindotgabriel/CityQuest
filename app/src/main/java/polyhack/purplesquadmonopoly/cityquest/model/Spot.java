package polyhack.purplesquadmonopoly.cityquest.model;

/**
 * Created by Ovi on 06-Dec-15.
 */
public class Spot {

    private String _id;
    private String journeID;
    private double lat;
    private double lng;
    private double radius;
    private String info;
    private String imgUrl;
    private String name;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getJourneID() {
        return journeID;
    }

    public void setJourneID(String journeID) {
        this.journeID = journeID;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
