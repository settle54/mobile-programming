package com.example.medidex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "MediProject";
    MediAdapter adapter;
    String mediData;
    RecyclerView recyclerView;
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = editText.getText().toString();
                if (search.equals(""))
                    Toast.makeText(getApplicationContext(), "검색창이 비어있습니다.", Toast.LENGTH_SHORT).show();
                else
                    makeRequest(search);
                    }
                });

        if (requestQueue == null) {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MediAdapter();
        recyclerView.setAdapter(adapter);
            }

    public void makeRequest(String search) {
        StringBuilder sb = new StringBuilder("https://apis.data.go.kr/1471000/MdcinGrnIdntfcInfoService01/getMdcinGrnIdntfcInfoList01?serviceKey=");
        String key = "InGajI2ZXjW5C4dLDopKeN9WgTJ4pXYZ%2BRMnGG%2FH5sqCwgYK5timH5fnznt7qO0SroiMQSCbA4qZ1krpY2A6bQ%3D%3D";
        int numOfRows = 100;
        sb.append(key);
        sb.append("&item_name=" + search);
        sb.append("&numOfRows=" + numOfRows);
        sb.append("&type=json");
        String url = sb.toString();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                adapter.clearItems();
                processResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                println("에러: " + error);
            }
        });
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void processResponse(String response) {
        Gson gson = new Gson();
        MediInfo mediInfo = gson.fromJson(response, MediInfo.class);
        ArrayList<Medicine> MediLs = mediInfo.body.items;
        for (Medicine medicine : MediLs) {
            adapter.addItem(medicine);
        }
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() == 0)
            Toast.makeText(getApplicationContext(), "검색된 결과가 없습니다.", Toast.LENGTH_SHORT).show();
    }

    public void println(String data) {
        Log.d(TAG, data);
    }
}