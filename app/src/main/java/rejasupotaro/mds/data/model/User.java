package rejasupotaro.mds.data.model;

import com.google.auto.value.AutoValue;

import rejasupotaro.mds.data.AutoGson;

@AutoValue @AutoGson
public abstract class User extends Model {
    public abstract String name();
    public abstract String imageUrl();

    public static User dummy() {
        return new AutoValue_User("rejasupotaro", "");
    }
}
