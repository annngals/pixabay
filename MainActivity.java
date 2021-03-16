package com.example.pixabay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {

    String API_URL = "https://pixabay.com/";
    String key = "14665819-68f588184b6da862b4927160a";
    String image_type = "photo";
    MainActivity mainActivity = this;

    EditText request;
    ListView listView;
    ImageView imageView;
    TextView numberOfHits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request = findViewById(R.id.request);
        listView = findViewById(R.id.listview);
        imageView = findViewById(R.id.image);
        numberOfHits = findViewById(R.id.hits);
    }

    interface PixabayAPI {
        @GET("/api")
        Call<Response> search(@Query("q") String q, @Query("key") String key, @Query("image_type") String image_type);
        @GET()
        Call<ResponseBody> getImage (@Url String pictureURL);
    }

    public void onType(View v) {
        switch (v.getId()) {
            case R.id.photo: {
                image_type = "photo";
                break;
            }
            case R.id.vector: {
                image_type = "vector";
                break;
            }
            case R.id.illustration: {
                image_type = "illustration";
                break;
            }
            case R.id.all: {
                image_type = "all";
                break;
            }
        }
    }

    public void startSearch(String text) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PixabayAPI api = retrofit.create(PixabayAPI.class);

        Retrofit noRetrofit = new Retrofit.Builder().baseUrl(API_URL).build();
        final PixabayAPI noApi = noRetrofit.create(PixabayAPI.class);

        Call<Response> call = api.search(text, key, image_type);
        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response r = response.body();

                numberOfHits.setText("Number of hits: " + r.hits.length);
                MyAdapter adapter = new MyAdapter(mainActivity, r.hits, noApi);
                listView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT);
                toast.show();
                Log.d("mytag", "Error: " + t.getLocalizedMessage());
            }
        };
        call.enqueue(callback);
    }

    public void onSearchClick(View v) {
        startSearch(request.getText().toString());
    }
}