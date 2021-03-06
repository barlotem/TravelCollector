package barlot.travelcollector.views;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import barlot.travelcollector.R;
import barlot.travelcollector.models.TravelData;

public class TravelViewer extends AppCompatActivity {

    TextView tv_description;
    TextView tv_date;
    TextView tv_country;
    TextView tv_distanceInKm;
    TextView tv_guideName;
    TextView tv_groupName;
    TextView tv_comments;
    TextView tv_alternative;
    TextView tv_tags;
    TextView tv_albumId;
    String link;

    SimpleDateFormat dateFormmater = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_viewer);

        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_date =(TextView) findViewById(R.id.tv_date);
        tv_country = (TextView) findViewById(R.id.tv_country);
        tv_distanceInKm =(TextView) findViewById(R.id.tv_distanceInKm);
        tv_guideName = (TextView) findViewById(R.id.tv_guideName);
        tv_groupName =(TextView) findViewById(R.id.tv_groupName);
        tv_comments = (TextView) findViewById(R.id.tv_comments);
        tv_alternative =(TextView) findViewById(R.id.tv_alternative);
        tv_tags = (TextView) findViewById(R.id.tv_tags);
        tv_albumId =(TextView) findViewById(R.id.tv_albumId);


        TravelData travelData = getIntent().getParcelableExtra("data");

        tv_description.setText(travelData.getDescription());
        tv_date.setText(dateFormmater.format(travelData.getDate()));
        tv_country.setText(travelData.getCountry());
        tv_distanceInKm.setText(Double.toString(travelData.getDistanceInKm())+" ק\"מ");
        tv_guideName.setText(travelData.getGuideName());
        tv_groupName.setText(travelData.getgroupName());
        tv_comments.setText(travelData.getComments());
        tv_alternative.setText(travelData.getAlternative());
        tv_tags.setText(travelData.getTags());
        tv_albumId.setText("אלבום מספר "+Integer.toString(travelData.getAlbumId()));

        link = travelData.getLink();

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.layout_travel_viewer_text);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof TextView) {
                setVisibility((TextView)v);
            }
        }

        layout = (RelativeLayout)findViewById(R.id.layout_travel_viewer_button);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof ImageButton) {
                setImageVisibility((ImageButton) v);
            }
        }
    }

    void setVisibility(TextView object)
    {
        if (object.getText().equals("")||object.getText().equals(",")||object.length() ==0)
        {
            object.setVisibility(object.GONE);
        } else {
            object.setVisibility(object.VISIBLE);
        }
    }

    void setImageVisibility(ImageButton object) {
        if (link.isEmpty() || link == null || link.equals("") || link.equals(","))
            object.setVisibility(object.GONE);
        else
            object.setVisibility(object.VISIBLE);
    }

    public void goToUrl(View view) {
        try {
            Uri uriUrl = Uri.parse(link);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        }
        catch (Exception e) {
            Toast.makeText(this, "לא ניתן לטעון את הקישור: "+link, Toast.LENGTH_SHORT).show();
        }

    };
}
