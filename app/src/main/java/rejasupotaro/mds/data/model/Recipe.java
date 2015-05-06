package rejasupotaro.mds.data.model;

import com.google.auto.value.AutoValue;

import rejasupotaro.mds.data.AutoGson;

@AutoValue @AutoGson
public abstract class Recipe extends Model {
    public abstract String title();
    public abstract String description();
    public abstract String imageUrl();
    public abstract String updatedAt();
    public abstract User user();

    public static Recipe dummy() {
        return new AutoValue_Recipe(
                "Chicken Soft Tacos",
                "Super easy, healthy tacos without using packaged taco seasoning. Spice-wise you just need some chili powder and ground cumin!",
                "",
                "Updated at 05/06/2015",
                User.dummy());
    }
}
