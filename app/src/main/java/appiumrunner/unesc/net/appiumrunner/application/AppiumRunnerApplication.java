package appiumrunner.unesc.net.appiumrunner.application;

import android.app.Application;

import appiumrunner.unesc.net.appiumrunner.engine.Setup;

/**
 * Created by fabri on 19/03/2018.
 */

public class AppiumRunnerApplication extends Application {

    public final static Setup TESTSETUP = new Setup();

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
