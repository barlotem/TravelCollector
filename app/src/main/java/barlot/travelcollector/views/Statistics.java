package barlot.travelcollector.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import barlot.travelcollector.R;
import barlot.travelcollector.models.TravelData;

public class Statistics extends AppCompatActivity {

    private static final String TAG = "Statistics";

    ArrayList<TravelData> listOfTravels;

    TextView tv_countOfTravel;
    TextView tv_totalDistance;
    TextView tv_avgDistance;
    TextView tv_topCountries;
    TextView tv_topGuides;
    TextView tv_topGroups;

    int countOfTravel = 0;
    double totalDistance = 0;
    double avgDistance = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tv_countOfTravel = (TextView) findViewById(R.id.tv_countOfTravel);
        tv_totalDistance = (TextView) findViewById(R.id.tv_totalDistance);
        tv_avgDistance = (TextView) findViewById(R.id.tv_avgDistance);
        tv_topCountries = (TextView) findViewById(R.id.tv_topCountries);
        tv_topGuides = (TextView) findViewById(R.id.tv_topGuides);
        tv_topGroups = (TextView) findViewById(R.id.tv_topGroups);

        listOfTravels = getIntent().getParcelableArrayListExtra("data");

        if (listOfTravels.size() == 0) {
            Toast.makeText(this, "לא קיימים טיולים", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Statistics.this, Home.class);
            startActivity(intent);
            finish();
        }

        calculateSimpleData();
        calculateTopInListData();
    }

    private void calculateSimpleData() {

        // Counter
        countOfTravel = listOfTravels.size();

        // Total distance of all travels
        for (int i = 0; i < listOfTravels.size(); i++) {
            totalDistance += listOfTravels.get(i).getDistanceInKm();
        }

        // Avg distance per travel
        avgDistance = totalDistance / countOfTravel;

        tv_countOfTravel.setText("סה\"כ " + Integer.toString(countOfTravel) + " טיולים");
        tv_totalDistance.setText(String.format("%.2f", totalDistance) + " ק\"מ מצטברים");
        tv_avgDistance.setText(String.format("%.2f", avgDistance) + " ק\"מ בממוצע לטיול");
    }

    private void calculateTopInListData() {

        try {
            // Top countries
            Map<String, Integer> map = new HashMap<String, Integer>();

            for (TravelData td : listOfTravels) {
                if (map.containsKey(td.getCountry())) {
                    map.put(td.getCountry(), map.get(td.getCountry()) + 1);
                } else {
                    map.put(td.getCountry(), 1);
                }
            }

            List<Map.Entry<String, Integer>> sortedList = sortByValue(map).subList(0, 3);

            // TODO: organize this
            tv_topCountries.setText("המדינות הנפוצות ביותר: " + sortedList.get(0).getKey() + "(" + sortedList.get(0).getValue() +
                    ")\n" + sortedList.get(1).getKey() + "(" + sortedList.get(1).getValue() +
                    ")\n" + sortedList.get(2).getKey() + "(" + sortedList.get(2).getValue() + ")");

            // Top guides
            map = new HashMap<String, Integer>();

            for (TravelData td : listOfTravels) {
                if (map.containsKey(td.getGuideName())) {
                    map.put(td.getGuideName(), map.get(td.getGuideName()) + 1);
                } else {
                    map.put(td.getGuideName(), 1);
                }
            }

            sortedList = sortByValue(map).subList(0, 3);

            tv_topGuides.setText("המדריכים הנפוצים ביותר: " + sortedList.get(0).getKey() + "(" + sortedList.get(0).getValue() +
                    ")\n" + sortedList.get(1).getKey() + "(" + sortedList.get(1).getValue() +
                    ")\n" + sortedList.get(2).getKey() + "(" + sortedList.get(2).getValue() + ")");

            // Top groups
            map = new HashMap<String, Integer>();

            for (TravelData td : listOfTravels) {
                if (map.containsKey(td.getgroupName())) {
                    map.put(td.getgroupName(), map.get(td.getgroupName()) + 1);
                } else {
                    map.put(td.getgroupName(), 1);
                }
            }

            sortedList = sortByValue(map).subList(0, 3);

            tv_topGroups.setText("הקבוצות הנפוצות ביותר: " + sortedList.get(0).getKey() + "(" + sortedList.get(0).getValue() +
                    ")\n" + sortedList.get(1).getKey() + "(" + sortedList.get(1).getValue() +
                    ")\n" + sortedList.get(2).getKey() + "(" + sortedList.get(2).getValue() + ")");
        } catch (Exception e) {
            Log.e(TAG, "Could not calculateTopInListData: " + e.getMessage());
        }
    }

    public static List<Map.Entry<String, Integer>> sortByValue(Map<String, Integer> map) {

        Set<Map.Entry<String, Integer>> set = map.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        return list;
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
