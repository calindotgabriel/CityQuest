package polyhack.purplesquadmonopoly.cityquest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ovi on 06-Dec-15.
 */
public class Spot implements Parcelable {

    private String _id;
    private String journeyID;
    private double lat;
    private double lng;
    private double radius;
    private String info;
    private String imgUrl;
    private String name;

    public Spot() {

    }

    public String getId() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getJourneyID() {
        return journeyID;
    }

    public void setJourneyID(String journeyID) {
        this.journeyID = journeyID;
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

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }


    public Spot(Parcel in) {
        _id = in.readString();
        journeyID = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        radius = in.readDouble();
        info = in.readString();
        imgUrl = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(journeyID);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeDouble(radius);
        dest.writeString(info);
        dest.writeString(imgUrl);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Spot> CREATOR = new Parcelable.Creator<Spot>() {
        @Override
        public Spot createFromParcel(Parcel in) {
            return new Spot(in);
        }

        @Override
        public Spot[] newArray(int size) {
            return new Spot[size];
        }
    };
}