package rejasupotaro.mds;

import android.app.Application;

import rejasupotaro.mds.di.components.ApplicationComponent;
import rejasupotaro.mds.di.components.DaggerApplicationComponent;
import rejasupotaro.mds.di.modules.ApplicationModule;

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    public void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
