package com.longs7.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private final String URL = "https://60ad9ad980a61f00173313b0.mockapi.io/api/books";

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<JSONObject>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        getData(URL);
    }

    private void getData(String url) {
        new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    for(int i = 0; i < response.length(); i++) {
                        adapter.clear();
                        try {
                            adapter.add(response.get(i));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    Toast.makeText(this, "Error when get data", Toast.LENGTH_SHORT).show();
                }
        );
    }
}