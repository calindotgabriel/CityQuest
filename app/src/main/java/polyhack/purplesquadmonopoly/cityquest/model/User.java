package polyhack.purplesquadmonopoly.cityquest.model;

/**
 * Created by Ovi on 05-Dec-15.
 */
public class User {

    private String email;
    private String name;
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
