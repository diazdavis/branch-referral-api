package diaz.branch.brachreferral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;

public class InvitedActivity extends Activity {

    Button button;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Random referral code function call
        final String referralcode = RandomString.getAlphaNumericString(8);
        Button referral_button = findViewById(R.id.referral_button_code);
        referral_button.setText(referralcode);

        button = findViewById(R.id.referral_button);
        text = findViewById(R.id.referer_name);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = text.getText().toString();

                if (name.length()>0) {

//Branch Implementation
                    BranchUniversalObject buo = new BranchUniversalObject()
                            .setCanonicalIdentifier(name)
                            .setTitle("Invited")
                            .setContentDescription("Referral from " + name)
                            .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                            .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                            .setContentMetadata(new ContentMetadata().addCustomMetadata("status", "invited"));

                    LinkProperties lp = new LinkProperties()
                            .setChannel("any")
                            .setFeature("sharing")
                            .setCampaign("Referral System")
                            .addControlParameter("$desktop_url", "http://example.com/home");

                    buo.generateShortUrl(InvitedActivity.this, lp, new Branch.BranchLinkCreateListener() {
                        @Override
                        public void onLinkCreate(String url, BranchError error) {
                            String name = text.getText().toString();
                            if (error == null) {
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, "You have been referred by " + name + "\nJoin now " + url + "\nReferral Code: " + referralcode);
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Referral Link");
                                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                                Log.i("Referral Message","You have been referred by " + name + "\nJoin now " + url + "\nReferral Code: " + referralcode);
                            }
                        }
                    });

//Creation of Toast
                    Toast.makeText(InvitedActivity.this, "ready to share", Toast.LENGTH_SHORT).show();
                }
                else{
                        TextView no_name;
                        no_name = findViewById(R.id.textView5);
                        no_name.setText("Please enter your name");
                }
            }
        });
    }

//Function to generate referral code
    public static class RandomString {

        static String getAlphaNumericString(int n)
        {
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
            StringBuilder sb = new StringBuilder(n);

            for (int i = 0; i < n; i++) {
                int index = (int)(AlphaNumericString.length() * Math.random());
                sb.append(AlphaNumericString.charAt(index));
            }
            return sb.toString();
        }
    }

//Function to remove error when start to type name
    public void clearname(View view) {
        TextView no_name;
        no_name = findViewById(R.id.textView5);
        no_name.setText("");
    }

}