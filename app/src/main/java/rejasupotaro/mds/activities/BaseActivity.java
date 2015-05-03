package rejasupotaro.mds.activities;

import android.app.Activity;
import android.os.Bundle;

import rejasupotaro.mds.AndroidApplication;
import rejasupotaro.mds.di.components.ApplicationComponent;
import rejasupotaro.mds.di.modules.ActivityModule;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
