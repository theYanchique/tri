package com.example.tri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetData.AsyncResponse {

    private static final String TAG ="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            URL url = new URL("https://calendarific.com/api/v2/holidays?api_key=82608ca140971c294080618294e48585f50e9e56&country=RU&year=2023&languages=ru");
            new GetData(this).execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processFinish(String output) {
        Log.d(TAG, "processFinish: "+output);
        try {
            JSONObject outputJSON = new JSONObject(output);
            JSONObject responseJSON= outputJSON.getJSONObject("response");
            JSONArray array=responseJSON.getJSONArray("holidays");
            int length=array.length();
            ArrayList <String> namesHolidays= new ArrayList<String>();
            for(int i=0; i<length;i++){
                JSONObject obj=array.getJSONObject(i);
                String name=obj.getString("name");
                namesHolidays.add(name);
                Log.d(TAG, "processFinish: "+name);
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namesHolidays);
            ListView list=findViewById(R.id.list);
            list.setAdapter(adapter);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
