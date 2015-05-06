package rejasupotaro.mds.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerializerProvider {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapterFactory(new AutoValueAdapterFactory())
            .create();
}