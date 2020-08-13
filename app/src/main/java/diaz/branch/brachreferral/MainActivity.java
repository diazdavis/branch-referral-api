package diaz.branch.brachreferral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
    }

    @Override
    public void onStart() {
        super.onStart();
        Branch branch = Branch.getInstance(getApplicationContext());

// Branch init
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    try {
                        if(referringParams.getString("$og_title").contains("Invited")){
                            setContentView(R.layout.invite);
                            TextView text = findViewById(R.id.textView2);
                            text.setText(referringParams.getString("$og_description"));
                        }
                    } catch (JSONException e) {
                        System.out.println(e);
                        startActivity(new Intent(MainActivity.this, InvitedActivity.class));
                    }
                } else {
                    setContentView(R.layout.wrong);
                }
            }
        }, this.getIntent().getData(), this);
    }

    public void closePage(View view) {
        startActivity(new Intent(MainActivity.this, InvitedActivity.class));
    }

}
