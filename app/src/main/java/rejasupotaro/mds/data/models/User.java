package rejasupotaro.mds.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class User extends Model {
    @JsonProperty("name")
    public abstract String name();

    @JsonProperty("image_url")
    public abstract String imageUrl();

    @JsonCreator
    public static User create(@JsonProperty("name") String name,
                              @JsonProperty("image_url") String imageUrl) {
        return new AutoValue_User(name, imageUrl);
    }

    public static User dummy() {
        return new AutoValue_User("rejasupotaro", "");
    }
}
