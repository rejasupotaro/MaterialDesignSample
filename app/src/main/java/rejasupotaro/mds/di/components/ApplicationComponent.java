package rejasupotaro.mds.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import rejasupotaro.mds.di.modules.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();
}
