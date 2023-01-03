package com.ipungdev.loginregistercrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ipungdev.loginregistercrud.apihelper.BaseApiService;
import com.ipungdev.loginregistercrud.apihelper.UserService;
import com.ipungdev.loginregistercrud.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    Button btnRegister;
    ProgressDialog loading;

    Context mContext;
    //BaseApiService mApiService;

    UserService userService;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tokenkey = "tokenkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(mypreference, 0);


        mContext = this;
        userService = UtilsApi.getUserService(); // meng-init yang ada di package apihelper
        initComponents();
    }

    private void initComponents() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });
    }

    private void requestLogin() {
        userService.loginRequest(etEmail.getText().toString(), etPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                Log.d("JSON", jsonRESULTS.toString());
                                if (jsonRESULTS.getString("success").equals("true")){
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya.
                                    Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                    String name = jsonRESULTS.getJSONObject("user").getString("name");
                                    String token = jsonRESULTS.getString("access_token");

                                    //simpan disharedpreference
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(tokenkey, token);
                                    editor.commit();


                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    intent.putExtra("result_name", name);
                                    intent.putExtra("result_token", token);
                                    startActivity(intent);
                                } else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("message");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();

                    }
                });

    }

}