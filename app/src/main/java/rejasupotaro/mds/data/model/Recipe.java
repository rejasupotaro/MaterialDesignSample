package rejasupotaro.mds.data.model;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

import rejasupotaro.mds.data.AutoGson;

@AutoValue @AutoGson
public abstract class Recipe extends Model {
    public abstract String title();
    public abstract String description();
    public abstract String imageUrl();
    public abstract List<Step> steps();
    public abstract String updatedAt();
    public abstract User user();

    public static Recipe dummy() {
        List<Step> steps = new ArrayList<Step>() {{
            add(Step.dummy());
            add(Step.dummy());
            add(Step.dummy());
            add(Step.dummy());
            add(Step.dummy());
        }};
        return new AutoValue_Recipe(
                "Chicken Soft Tacos",
                "Super easy, healthy tacos without using packaged taco seasoning. Spice-wise you just need some chili powder and ground cumin!",
                "https://img-global.cpcdn.com/001_recipes/2432172_0645bbd289b77ede/640x453cq70/photo.jpg",
                steps,
                "Updated at 05/06/2015",
                User.dummy());
    }
}
