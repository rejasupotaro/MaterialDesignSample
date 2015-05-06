package rejasupotaro.mds.data.model;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rejasupotaro.mds.data.AutoGson;

@AutoValue
@AutoGson
public abstract class Recipe extends Model {
    public static final Recipe dummy0 = new AutoValue_Recipe(
            "Chicken Soft Tacos",
            "Super easy, healthy tacos without using packaged taco seasoning. Spice-wise you just need some chili powder and ground cumin!",
            "https://img-global.cpcdn.com/001_recipes/2432172_0645bbd289b77ede/640x453cq70/photo.jpg",
            Step.dummies(),
            "Updated at 05/06/2015",
            User.dummy());
    public static final Recipe dummy1 = new AutoValue_Recipe(
            "Bacon and Cheese Halusky",
            "A national dish of Slovakia! Halusky are like soft gnocchi, somewhat similar to German Spaetzle noodles. It's mixed with a slightly tangy, yet creamy Slovakian sheep cheese called Bryndza and topped with fried bacon. Bryndza can be hard to find outside of Slovakia, but you get pretty close by substituting another soft cheese like feta, cottage, goat cheese, etc. When I visited Slovakia, my friend introduced me to this dish. I had the chance to eat it with fresh brynza goat cheese in Spisské!",
            "https://img-global.cpcdn.com/001_recipes/6511449102876672/640x453cq70/photo.jpg",
            Step.dummies(),
            "Updated at 06/06/2015",
            User.dummy());

    public abstract String title();

    public abstract String description();

    public abstract String imageUrl();

    public abstract List<Step> steps();

    public abstract String updatedAt();

    public abstract User user();

    public static Recipe dummy() {
        int index = new Random().nextInt(1);
        switch (index) {
            case 1:
                return dummy1;
            default:
                return dummy0;
        }
    }

    public static List<Recipe> dummies() {
        return new ArrayList<Recipe>() {{
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
        }};
    }
}
