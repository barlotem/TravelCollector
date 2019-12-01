package barlot.travelcollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class TravelViewer extends AppCompatActivity {

    TextView tv_description;
    TextView tv_date;
    TextView tv_country;
    TextView tv_distanceInKm;
    TextView tv_guideName;
    TextView tv_teamName;
    TextView tv_comments;
    TextView tv_alternative;
    TextView tv_tags;
    TextView tv_albumId;

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
        tv_teamName =(TextView) findViewById(R.id.tv_teamName);
        tv_comments = (TextView) findViewById(R.id.tv_comments);
        tv_alternative =(TextView) findViewById(R.id.tv_alternative);
        tv_tags = (TextView) findViewById(R.id.tv_tags);
        tv_albumId =(TextView) findViewById(R.id.tv_albumId);

        TravelData travelData = getIntent().getParcelableExtra("data");

        tv_description.setText(travelData.getDescription());
        tv_date.setText(dateFormmater.format(travelData.getDate()));
        tv_country.setText(travelData.getCountry());
        tv_distanceInKm.setText(Double.toString(travelData.getDistanceInKm()));
        tv_guideName.setText(travelData.getGuideName());
        tv_teamName.setText(travelData.getTeamName());
        tv_comments.setText(travelData.getComments());
        tv_alternative.setText(travelData.getAlternative());
        tv_tags.setText(travelData.getTags());
        tv_albumId.setText("אלבום מספר "+Integer.toString(travelData.getAlbumId()));

        showOnlyIfNotEmpty();
    }

    private void showOnlyIfNotEmpty() {
        if(tv_description.length() == 0 || tv_description.equals(""))
        {
            tv_description.setVisibility(tv_description.GONE);
        } else {
            tv_description.setVisibility(tv_description.VISIBLE);
        }

        if(tv_date.length() == 0 || tv_date.equals(""))
        {
            tv_date.setVisibility(tv_date.GONE);
        } else {
            tv_date.setVisibility(tv_date.VISIBLE);
        }

        if(tv_country.length() == 0 || tv_country.equals(""))
        {
            tv_country.setVisibility(tv_country.GONE);
        } else {
            tv_country.setVisibility(tv_country.VISIBLE);
        }

        if(tv_distanceInKm.length() == 0 || tv_distanceInKm.equals(""))
        {
            tv_distanceInKm.setVisibility(tv_distanceInKm.GONE);
        } else {
            tv_distanceInKm.setVisibility(tv_distanceInKm.VISIBLE);
        }

        if(tv_guideName.length() == 0 || tv_guideName.equals(""))
        {
            tv_guideName.setVisibility(tv_guideName.GONE);
        } else {
            tv_guideName.setVisibility(tv_guideName.VISIBLE);
        }

        if(tv_teamName.length() == 0 || tv_teamName.equals(""))
        {
            tv_teamName.setVisibility(tv_teamName.GONE);
        } else {
            tv_teamName.setVisibility(tv_teamName.VISIBLE);
        }

        if(tv_comments.length() == 0 || tv_comments.equals(""))
        {
            tv_comments.setVisibility(tv_comments.GONE);
        } else {
            tv_comments.setVisibility(tv_comments.VISIBLE);
        }

        if(tv_alternative.length() == 0 || tv_alternative.equals(""))
        {
            tv_alternative.setVisibility(tv_alternative.GONE);
        } else {
            tv_alternative.setVisibility(tv_alternative.VISIBLE);
        }

        if(tv_tags.length() == 0 || tv_tags.equals(""))
        {
            tv_tags.setVisibility(tv_tags.GONE);
        } else {
            tv_tags.setVisibility(tv_tags.VISIBLE);
        }

        if(tv_albumId.length() == 0 || tv_albumId.equals(""))
        {
            tv_albumId.setVisibility(tv_albumId.GONE);
        } else {
            tv_albumId.setVisibility(tv_albumId.VISIBLE);
        }
    }
}
