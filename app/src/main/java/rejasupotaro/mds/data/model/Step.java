package rejasupotaro.mds.data.model;

import com.google.auto.value.AutoValue;

import rejasupotaro.mds.data.AutoGson;

@AutoValue
@AutoGson
public abstract class Step extends Model {
    public abstract String description();
    public abstract String imageUrl();

    public static Step dummy() {
        return new AutoValue_Step(
                "Cut chicken into bite size strips. Mince the garlic and chop the onions and bell pepper.",
                "https://img-global.cpcdn.com/001_steps/2432172_38e6e39a181fd121/480x360cq70/photo.jpg");
    }
}
