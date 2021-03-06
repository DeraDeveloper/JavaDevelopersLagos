package com.example.seamfix.javadeveloperslagos.controller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seamfix.javadeveloperslagos.ItemAdapter;
import com.example.seamfix.javadeveloperslagos.R;
import com.example.seamfix.javadeveloperslagos.api.Client;
import com.example.seamfix.javadeveloperslagos.api.Service;
import com.example.seamfix.javadeveloperslagos.model.Item;
import com.example.seamfix.javadeveloperslagos.model.ItemResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    TextView Disconnected;
    private Item item;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    private String page = "1";
    private String perPage = "20";
    private ItemAdapter itemAdapter;
    private List<Item> items;

    //set view to activity_main.xml once appplication sarts and call the initViews() method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<Item>();
        initViews();

        //initializing swipeContainer and calling the onRefresh() method
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_purple);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON();
                Toast.makeText(MainActivity.this, "Github Users Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //the progress dialog is displayed while fetching data from the loadJSON() method
    private void initViews() {
        pd = new ProgressDialog(this);
        pd.setMessage("fetching users .....");
        pd.setCancelable(false);
        pd.show();
        itemAdapter = new ItemAdapter(getApplicationContext(), items);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(itemAdapter);
        loadJSON();
    }

    private void loadJSON() {
        Disconnected = (TextView) findViewById(R.id.disconnected);
        try {
            Client client = new Client();
            Service apiService =
                    client.getClient().create(Service.class);
            Call<ItemResponse> call = apiService.getItems(page, perPage);
            call.enqueue(new Callback<ItemResponse>() {
                @Override
                public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                    List<Item> items = response.body().getItems();
                    recyclerView.smoothScrollToPosition(0);
                    itemAdapter.setItems(items);
                    swipeContainer.setRefreshing(false);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    pd.hide();

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}
