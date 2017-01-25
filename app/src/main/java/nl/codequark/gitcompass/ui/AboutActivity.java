package nl.codequark.gitcompass.ui;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import nl.codequark.gitcompass.R;

/**
 * Created by lao on 10/5/15.
 */
public class AboutActivity extends BaseActivity {

    String aboutText = "It's a GitHub Trending repositories Viewer with Material Design.<br>" +
            "https://github.com/trending";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ((TextView) findViewById(R.id.about)).setText(Html.fromHtml(aboutText));

        final AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
