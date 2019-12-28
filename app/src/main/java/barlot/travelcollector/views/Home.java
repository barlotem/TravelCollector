package barlot.travelcollector.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import barlot.travelcollector.DatabaseHelper;
import barlot.travelcollector.MainActivity;
import barlot.travelcollector.R;
import barlot.travelcollector.controllers.LoadData;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openLoadData(View v) {
        Intent homeIntent = new Intent(Home.this, LoadData.class);
        startActivity(homeIntent);
    }

    public void openListView(View v) {
        DatabaseHelper db = new DatabaseHelper(this);
        Intent intent = new Intent(Home.this,ListViewer.class);
        intent.putParcelableArrayListExtra("data", db.getAllData());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
