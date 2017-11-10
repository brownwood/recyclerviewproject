package com.globalsoftng.recyclerviewproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://randomuser.me/api/1.1/?results=15";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

       /* for (int i = 0; i <= 10; i++){
            ListItem listItem = new ListItem("heading "+ (i+1),"Dummy Text");
            listItems.add(listItem);
        }

        adapter = new AdapterClass(listItems, this);
        recyclerView.setAdapter(adapter);*/

       loadRecyclerViewData();
    }

    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("name");
                                String name = jsonObject2.getString("title")+" "+jsonObject2.getString("first")
                                        +" "+jsonObject2.getString("last");
                                JSONObject jsonObject3 = jsonObject1.getJSONObject("picture");
                                ListItem item = new ListItem(
                                        name,
                                        jsonObject1.getString("email"),
                                        jsonObject3.getString("large")
                                );
                                listItems.add(item);
                            }

                            adapter = new AdapterClass(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

/*====================Notes==================
Two things are needed, RecyclerView and CardView (for the data items)
* 1. Add dependencies in the build.gradle file (recyclerview and cardview)
* 2. Setup the RecyclerView in the main_activity_xml file or the file you'd like to use it.
* 3. A separate file will be created to store every item in the RecyclerView. (list_item)
* 4. To bind the data with an RecyclerView, we need an Adapter.(This was created as a new Java class named 'AdapterClass')
*
* */
