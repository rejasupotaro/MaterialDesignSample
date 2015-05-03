package rejasupotaro.mds.di.modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import rejasupotaro.mds.di.PerActivity;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
