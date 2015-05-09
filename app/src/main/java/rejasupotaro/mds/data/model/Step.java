package rejasupotaro.mds.data.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class Step extends Model {
    public static final Step dummy0 = new AutoValue_Step(
            "Prepare the toppings by shredding up the lettuce, cutting avocado into small chunks and chopping the tomato.",
            "http://upload.wikimedia.org/wikipedia/commons/c/cb/Banana_flamb%C3%A9_-_by_Jenene.jpg");
    public static final Step dummy1 = new AutoValue_Step(
            "While the tortillas are warming, heat 1 Tbsp oil in a skillet and cook the onions and garlic until onions are soft and translucent. Add chicken and green bell peppers and continue cooking until chicken has changed color.",
            "http://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Recette_pate_brisee_etape_5.jpg/640px-Recette_pate_brisee_etape_5.jpg");
    public static final Step dummy2 = new AutoValue_Step(
            "Let everyone add filling and toppings to their soft shells, fold in half and dig in!",
            "");

    @JsonProperty("description")
    public abstract String description();

    @JsonProperty("image_url")
    public abstract String imageUrl();

    @JsonCreator
    public static Step create(@JsonProperty("description") String description,
                              @JsonProperty("image_url") String imageUrl) {
        return new AutoValue_Step(description, imageUrl);
    }

    public static Step dummy() {
        int index = new SecureRandom().nextInt(3);
        switch (index) {
            case 1:
                return dummy1;
            case 2:
                return dummy2;
            default:
                return dummy0;
        }
    }

    public static List<Step> dummies() {
        return new ArrayList<Step>() {{
            add(Step.dummy());
            add(Step.dummy());
            add(Step.dummy());
            add(Step.dummy());
            add(Step.dummy());
        }};
    }

    ;
}
