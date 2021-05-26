package com.longs7.bookstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final String URL = "https://60ad9ad980a61f00173313b0.mockapi.io/api/books";

    private ArrayAdapter adapter;
    private TextView txtId;
    private TextView txtName;
    private TextView txtYear;
    private TextView txtAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtName);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtYear = findViewById(R.id.txtYear);

        findViewById(R.id.btnFind).setOnClickListener(v -> {
            getData(URL, txtId.getText().toString());
        });

        findViewById(R.id.btnEmpty).setOnClickListener(v -> {
            txtId.setText("");
            txtName.setText("");
            txtAuthor.setText("");
            txtYear.setText("");
        });

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            
        });

        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<JSONObject>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            JSONObject item = (JSONObject) adapter.getItem(i);

            try {
                txtId.setText(item.get("id").toString());
                txtName.setText(item.getString("bookName"));
                txtAuthor.setText(item.getString("author"));
                txtYear.setText(String.valueOf(item.getInt("publishedYear")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        getData(URL);
    }

    private void getData(String url) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    adapter.clear();
                    for (int i = 0; i < response.length(); i++) {
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

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void getData(String url, String id) {
        if(id.isEmpty()){
            getData(url);
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url + "/" + id,
                null,
                response -> {
                    adapter.clear();
                    adapter.add(response);
                },
                error -> {
                    Toast.makeText(this, "Error when get data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void putData(String url) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null,
                response -> {
                    Toast.makeText(this, "PUT thành công!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(this, "Error when put data", Toast.LENGTH_SHORT).show();
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", txtId.getText().toString());
                params.put("bookName", txtName.getText().toString());
                params.put("author", txtAuthor.getText().toString());
                params.put("publishedYear", txtYear.getText().toString());

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void putData(String url, String id) {
        if(id.isEmpty()){
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url + "/" + id,
                null,
                response -> {
                    Toast.makeText(this, "PUT thành công!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(this, "Error when put data", Toast.LENGTH_SHORT).show();
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}