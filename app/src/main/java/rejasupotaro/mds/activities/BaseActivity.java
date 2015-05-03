package rejasupotaro.mds.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rejasupotaro.mds.AndroidApplication;
import rejasupotaro.mds.di.components.ApplicationComponent;
import rejasupotaro.mds.di.modules.ActivityModule;

public class BaseActivity extends AppCompatActivity {

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
