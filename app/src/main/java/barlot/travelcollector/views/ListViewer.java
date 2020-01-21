package barlot.travelcollector.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import barlot.travelcollector.R;
import barlot.travelcollector.controllers.LoadData;
import barlot.travelcollector.models.TravelData;

public class ListViewer extends AppCompatActivity {

    ArrayList<TravelData> uploadData;
    SimpleDateFormat dateFormmater = new SimpleDateFormat("dd-MM-yyyy");

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_viewer);

          listView = findViewById(R.id.list_view);
          uploadData = getIntent().getParcelableArrayListExtra("data");

          if (uploadData.size()==0) {
              Toast.makeText(this, "לא קיימים טיולים", Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(ListViewer.this, Home.class);
              startActivity(intent);
              finish();
          }

        Collections.sort(uploadData);


        // Each row in the list description and date
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<uploadData.size();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("description", uploadData.get(i).getDescription());
            hm.put("date", dateFormmater.format(uploadData.get(i).getDate()));
            list.add(hm);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2,
                new String[] {"description", "date"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openTravelViewerActivity(uploadData.get(i));
            }
        });

    }


    private void openTravelViewerActivity(TravelData data) {
        if (data.getDescription().equals("")||data.getDescription().equals(",")||data.getDescription().length()<2)
        {
            Toast.makeText(this, "טיול זה לא מכיל מספיק מידע", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(ListViewer.this,TravelViewer.class);
            intent.putExtra("data", data);
            startActivity(intent);
        }

    }

}
