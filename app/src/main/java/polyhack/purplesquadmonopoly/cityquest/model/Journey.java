package polyhack.purplesquadmonopoly.cityquest.model;

import java.io.Serializable;

/**
 * Created by motan on 05.12.2015.
 */
public class Journey implements Serializable{
    private Long id;
    private String name;
    private String desc;
    private Long duration; // in km.
    private Long distance;
    private String imgURL;

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", duration=" + duration +
                ", distance=" + distance +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }
}
