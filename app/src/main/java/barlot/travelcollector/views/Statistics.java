package barlot.travelcollector.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import barlot.travelcollector.R;
import barlot.travelcollector.models.TravelData;

public class Statistics extends AppCompatActivity {

    ArrayList<TravelData> listOfTravels;

    TextView tv_countOfTravel;
    TextView tv_totalDistance;
    TextView tv_avgDistance;

    int countOfTravel = 0;
    double totalDistance = 0;
    double avgDistance = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

//        tv_countOfTravel = (TextView) findViewById(R.id.tv_countOfTravel);
//        tv_totalDistance =(TextView) findViewById(R.id.tv_totalDistance);
//        tv_avgDistance = (TextView) findViewById(R.id.tv_avgDistance);

        listOfTravels = getIntent().getParcelableArrayListExtra("data");

        if (listOfTravels.size()==0) {
            Toast.makeText(this, "לא קיימים טיולים", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Statistics.this, Home.class);
            startActivity(intent);
            finish();
        }

        calculateData();

    }

    private void calculateData() {
        countOfTravel = listOfTravels.size();

        for (int i=0; i<listOfTravels.size();i++)
        {
            totalDistance += listOfTravels.get(i).getDistanceInKm();
        }

        avgDistance = totalDistance / countOfTravel;


    }

    public static <T> T mostCommon(ArrayList<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }



    // number of travels
    // top guides
    // top groups
    // top countries
    // total distance
    // avarage distance
    //
}
