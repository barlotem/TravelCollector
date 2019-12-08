package barlot.travelcollector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import barlot.travelcollector.models.TravelData;

/**
 * Created by USER on 06-Dec-19.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABAE_NAME = "travel.db";
    public static final String TABLE_NAME = "travelTable";
    public static final String album_id = "album_id";
    public static final String date = "date";
    public static final String groupName = "groupName";
    public static final String guideName = "guideName";
    public static final String description = "description";
    public static final String distanceInKm = "distanceInKm";
    public static final String tags = "tags";
    public static final String alternative = "alternative";
    public static final String country = "country";
    public static final String comments = "comments";
    public static final String link = "link";

    SimpleDateFormat dateFormmater = new SimpleDateFormat("dd-MM-yyyy");
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABAE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+TABLE_NAME +
                " (album_id integer primary key, date text, groupName text, " +
                "guideName text, description text, distanceInKm real, " +
                "tags text, alternative text, country text, comments text, link text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+TABLE_NAME);
    }

    public boolean insertData(List<TravelData> travelDataList) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result;

        for (int i=0; i<travelDataList.size(); i++)
        {
            TravelData travelData = travelDataList.get(i);
            contentValues.put(album_id,travelData.getAlbumId());
            contentValues.put(date,dateFormmater.format(travelData.getDate()));
            contentValues.put(groupName,travelData.getgroupName());
            contentValues.put(guideName,travelData.getGuideName());
            contentValues.put(description,travelData.getDescription());
            contentValues.put(distanceInKm,travelData.getDistanceInKm());
            contentValues.put(tags,travelData.getTags());
            contentValues.put(alternative,travelData.getAlternative());
            contentValues.put(country,travelData.getCountry());
            contentValues.put(comments,travelData.getComments());
            contentValues.put(link,travelData.getLink());

            result = db.insert(TABLE_NAME,null,contentValues);

            if (result == -1)
                return false;
        }

        return true;
    }

    public ArrayList<TravelData> getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<TravelData> travelDataList = new ArrayList<TravelData>();


        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);

        if (res.getCount() ==0)
        {
            Log.e(TAG, "got 0 rows from db");
        }

        try {
            while (res.moveToNext()) {

                travelDataList.add(new TravelData(
                        res.getInt(0), dateFormmater.parse(res.getString(1)),
                        res.getString(2),res.getString(3),res.getString(4),
                        res.getDouble(5),res.getString(6),res.getString(7),
                        res.getString(8),res.getString(9),res.getString(10)));
            }
        }
        catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return travelDataList;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
}
