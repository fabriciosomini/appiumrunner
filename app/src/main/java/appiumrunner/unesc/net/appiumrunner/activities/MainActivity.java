package appiumrunner.unesc.net.appiumrunner.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.fragments.HomeFragment;

import static appiumrunner.unesc.net.appiumrunner.application.AppiumRunnerApplication.TESTSETUP;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTestSetup();

        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.container_layout,
                homeFragment,
                homeFragment.getTag())
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();
    }

    private void setTestSetup() {
        TESTSETUP.setAppActivity(this.getClass().getName());
        TESTSETUP.setDeviceName("adroid");
        TESTSETUP.setPlatformVersion("7.1.1");
        TESTSETUP.setUseDefaultTearDown(true);
    }
}
