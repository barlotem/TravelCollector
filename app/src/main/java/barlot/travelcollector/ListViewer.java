package barlot.travelcollector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

//        ArrayAdapter<TravelData> adapter = new ArrayAdapter<TravelData>(this, android.R.layout.simple_list_item_1, uploadData);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                openTravelViewerActivity(uploadData.get(i));
//            }
//        });

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<uploadData.size();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("description", uploadData.get(i).getDescription());
            hm.put("date", dateFormmater.format(uploadData.get(i).getDate()));
            data.add(hm);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
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
        Intent intent = new Intent(ListViewer.this,TravelViewer.class);
        intent.putExtra("data", data);
//        intent.putExtra("albumId",data.getAlbumId());
//        intent.putExtra("description",data.getDescription());
//        intent.putExtra("date",data.getDate());
        startActivity(intent);
    }

}
