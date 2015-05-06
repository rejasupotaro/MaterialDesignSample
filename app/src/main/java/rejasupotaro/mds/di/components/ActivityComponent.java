package rejasupotaro.mds.di.components;

import android.app.Activity;
import android.app.Application;

import dagger.Component;
import rejasupotaro.mds.di.ActivityScope;
import rejasupotaro.mds.di.modules.ActivityModule;

@ActivityScope
@Component(dependencies = Application.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
