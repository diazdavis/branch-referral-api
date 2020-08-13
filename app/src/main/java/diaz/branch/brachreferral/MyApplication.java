package diaz.branch.brachreferral;

import android.app.Application;

import io.branch.referral.Branch;

public final class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Branch object
        Branch.getAutoInstance(this);
    }
}