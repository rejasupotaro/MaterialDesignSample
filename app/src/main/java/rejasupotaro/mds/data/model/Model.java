package rejasupotaro.mds.data.model;

import rejasupotaro.mds.data.SerializerProvider;

public class Model {
    public static <T> T fromJson(String json, Class<T> clazz) {
        return SerializerProvider.GSON.fromJson(json, clazz);
    }

    public String toJson() {
        return SerializerProvider.GSON.toJson(this);
    }
}
