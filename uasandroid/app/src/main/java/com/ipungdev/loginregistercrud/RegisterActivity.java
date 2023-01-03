package com.ipungdev.loginregistercrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    EditText etNama;
    EditText etEmail;
    EditText etPassword;
    EditText etRePassword;
    Button btnRegister;
    ProgressDialog loading;

    Context mContext;
    //BaseApiService mApiService;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;
        userService = UtilsApi.getUserService();

        initComponents();
    }

    private void initComponents() {
        etNama = (EditText) findViewById(R.id.etNama);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(etPassword.getText() == etRePassword.getText()){
                    loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                    requestRegister();


               // }else {
                   // showDialog();
               // }

            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Konfirmasi Password Salah");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Silahkan masukan password konfirmasi dengan benar!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        Intent i = new Intent(RegisterActivity.this, RegisterActivity.class);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void requestRegister() {
        userService.registerRequest(etNama.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(), etRePassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("success").equals("true")){
                                    Toast.makeText(mContext, "BERHASIL REGISTRASI", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                } else {
                                    String error_message = jsonRESULTS.getString("message");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
        }
}