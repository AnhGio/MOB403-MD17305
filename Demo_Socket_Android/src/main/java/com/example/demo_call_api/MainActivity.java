package com.example.demo_call_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.annotations.SerializedName;

import java.net.URISyntaxException;
import java.util.List;

import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    private NYManagerService nyManagerService;

    private EditText megTextInput;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SocketManager.getInstance().connect();

        megTextInput = findViewById(R.id.megTextInput);
        sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = megTextInput.getText().toString();
                SocketManager.getInstance().emit("TestEvent", message);
            }
        });



        SocketManager.getInstance().on("ThongBaoTuServer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("ThinhNT" + args[0]);
            }
        });

        NYManagerService nyManagerApi = RetrofitClient.getNyManagerApi();
        Call<List<NyModel>> call = nyManagerApi.getListNy();
        call.enqueue(new Callback<List<NyModel>>() {
            @Override
            public void onResponse(Call<List<NyModel>> call, Response<List<NyModel>> response) {
                if (response.code() == 200) {
                    List<NyModel> arr = response.body();
                    System.out.println(arr);
                }
            }

            @Override
            public void onFailure(Call<List<NyModel>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}

class RetrofitClient {
    private static final String BASE_URL = "http://10.82.1.154:3000/";

    public static NYManagerService getNyManagerApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(NYManagerService.class);
    }
}

interface NYManagerService {
    @GET("nyManager/list-ny")
    Call<List<NyModel>> getListNy();

    @POST("nyManager/add-ny")
    Call<String>addNewNy(@Body NyRequestModel requestModel);
}

class NyModel {
    public String _id;
    public String name;
    public String phone;
    public String description;
    public NyType type;
}

class NyType {
    @SerializedName("_id")
    public String id;
    public String name;
    public String description;
}

class NyRequestModel {
    public String name;
    public String phone;
    public String description;
    public String type;
}