package com.ipungdev.loginregistercrud;

import static com.ipungdev.loginregistercrud.LoginActivity.tokenkey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.ipungdev.loginregistercrud.adapter.ProjectAdapter;
import com.ipungdev.loginregistercrud.apihelper.UserService;
import com.ipungdev.loginregistercrud.apihelper.UtilsApi;
import com.ipungdev.loginregistercrud.models.Project;
import com.ipungdev.loginregistercrud.models.ProjectResponse;


import java.security.Policy;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Policy ma;
    TextView tvResultToken;
    private Button createCourseBtn;
    String resultNama, resultToken;

    UserService projectServices;
    Context mContext;

   // SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView = null;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedpreferences = getSharedPreferences(mypreference, 0);

        TextView tvResultNama = (TextView) findViewById(R.id.tvResultNama);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        createCourseBtn = findViewById(R.id.btncreate);

        mContext = this;
        projectServices = UtilsApi.getUserService();

        // untuk mendapatkan data dari activity sebelumnya, yaitu activity login.
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            resultNama = extras.getString("result_name");
        tvResultNama.setText(resultNama);


        if (sharedpreferences.contains(tokenkey)) {
            String value = sharedpreferences.getString(tokenkey, "");

            projectServices.getProyek("Bearer "+value).enqueue(new Callback<ProjectResponse>() {
                @Override
                public void onResponse(Call<ProjectResponse> call, Response<ProjectResponse> response) {
                    List<Project> movies = response.body().getProyeks();
                    recyclerView.setAdapter(new ProjectAdapter(movies, R.layout.list_item_project, getApplicationContext()));
                }

                @Override
                public void onFailure(Call<ProjectResponse> call, Throwable t) {
                    Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                    Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();

                }
            });
        }
        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity via a intent.
                Intent i = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(i);
            }
        });


    }


}