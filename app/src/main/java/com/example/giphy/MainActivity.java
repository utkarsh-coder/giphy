package com.example.giphy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ArrayList<GifObject> list;
    private EditText searchEditText;
    private Retrofit retrofit;
    private GiphyInterface giphyInterface;
    private RecyclerViewAdapter recyclerViewAdapterResidingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        searchEditText = findViewById(R.id.search_edit_text);

        list = new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.giphy.com/v1/gifs/")
                .build();

        giphyInterface = retrofit.create(GiphyInterface.class);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.clear();

                addGifsToArrayList(s.toString());
            }
        });
    }

    private void addGifsToArrayList(String s){
        giphyInterface.getGif(s, getString(R.string.api_key)).enqueue(new Callback<ParentObject>() {
            @Override
            public void onResponse(Call<ParentObject> call, Response<ParentObject> response) {
                if (response.isSuccessful()) {
                    Log.d("testing", "data fetched successfully");
                    for(GifObject gifObject: response.body().getList()){
                        list.add(new GifObject(gifObject.getId(), gifObject.getImages()));
                    }
                    if(list.size()>0){
                        LinearLayoutManager linearLayoutManagerResidingList = new LinearLayoutManager(MainActivity.this);
                        RecyclerViewAdapter recyclerViewAdapterResidingList = new RecyclerViewAdapter(MainActivity.this, list, s);
                        recyclerView.setLayoutManager(linearLayoutManagerResidingList);
                        recyclerView.setAdapter(recyclerViewAdapterResidingList);
                    }
                } else {

                    giphyInterface.getTrendingGif(getString(R.string.api_key)).enqueue(new Callback<ParentObject>() {
                        @Override
                        public void onResponse(Call<ParentObject> call, Response<ParentObject> response) {
                            if (response.isSuccessful()) {
                                Log.d("testing", "data fetched successfully");
                                for(GifObject gifObject: response.body().getList()){
                                    list.add(new GifObject(gifObject.getId(), gifObject.getImages()));
                                }
                                if(list.size()>0){
                                    LinearLayoutManager linearLayoutManagerResidingList = new LinearLayoutManager(MainActivity.this);
                                    recyclerViewAdapterResidingList = new RecyclerViewAdapter(MainActivity.this, list, s);
                                    recyclerView.setLayoutManager(linearLayoutManagerResidingList);
                                    recyclerView.setAdapter(recyclerViewAdapterResidingList);
                                }
                            } else {
                            }
                        }

                        @Override
                        public void onFailure(Call<ParentObject> call, Throwable t) {
                            Log.d("testing","failed: "+t.toString());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ParentObject> call, Throwable t) {
                Log.d("testing","failed: "+t.toString());
            }
        });
    }
}