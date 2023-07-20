package com.example.demo_json_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    List<NyModel> nysList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NYManagerService nyManagerService = RetrofitClient.getService();
//        Call<List<NyModel>> call = nyManagerService.getListNy();
//
//        call.enqueue(new Callback<List<NyModel>>() {
//            @Override
//            public void onResponse(Call<List<NyModel>> call, Response<List<NyModel>> response) {
//                if (response.code() == 200) {
//                    MainActivity.this.nysList = response.body();
//                    Log.d("ThinhNT", "onResponse: nysList");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<NyModel>> call, Throwable t) {
//                System.out.println(t);
//            }
//        });


        NyRequestModel model = new NyRequestModel();
        model.name = "Nguyễn Thị D";
        model.phone = "0123456780";
        model.description = "\"Xinh gái ngời ngời\"";
        model.type = "649c5f4678469416d92c724b";

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Đây là token");
        header.put("ClientKey", "djfa;ksdfj;ạdkfakjsd");
        header.put("Other-Header", "djfkasdfasdfa");

        Call<ResMessage> call = nyManagerService.addNewNy(header, model);
        call.enqueue(new Callback<ResMessage>() {
            @Override
            public void onResponse(Call<ResMessage> call, Response<ResMessage> response) {
                Log.d("ThinhNT", response.body().message);
            }

            @Override
            public void onFailure(Call<ResMessage> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void uploadFile(File file, String descriptionText) {
        NYManagerService nyManagerService = RetrofitClient.getService();
        RequestBody filePart = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), filePart);

        Call<String> call = nyManagerService.uploadImage(fileToUpload);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Handle success response
                    String data = response.body();
                    // ...
                } else {
                    // Handle error response
                    // ...
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle failure
                // ...
            }
        });
    }
}

class NyModel {
    public String _id;
    public String name;
    public String phone;
    public String description;
    public Type type;
}

class Type {
    public String _id;
    public String name;
    public String description;
}

class NyRequestModel {
    public String name;
    public String phone;
    public String description;
    public String type;
}

class ResMessage{
    public int cede;
    public String message;
}