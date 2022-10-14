package com.example.sharepicture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final Gson gson = new Gson();
    ImageView iv_passwordSwitch = null;
    private boolean passwordSwitch = false;
    EditText et_password = null;
    EditText et_account ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        et_password = findViewById(R.id.et_password);
        iv_passwordSwitch= findViewById(R.id.iv_passwordSwitch);
        iv_passwordSwitch.setOnClickListener(this);
        Button bt_register = findViewById(R.id.bt_registr);
        bt_register.setOnClickListener(this);
        et_account=findViewById(R.id.et_account);
        TextView tv_register = findViewById(R.id.tv_register);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_passwordSwitch:
                passwordSwitch = !passwordSwitch;
                if (passwordSwitch) {
                    iv_passwordSwitch.setImageResource(R.drawable.ic_baseline_visibility_24);
                    et_password.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    iv_passwordSwitch.setImageResource(
                            R.drawable.ic_baseline_visibility_off_24);
                    et_password.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    et_password.setTypeface(Typeface.DEFAULT);
                }
                break;
            case R.id.bt_registr:
                String account = String.valueOf(et_account.getText());
                String password = String.valueOf(et_password.getText());
                post(account,password);
                break;

        }
    }

    private void post(String account,String password){
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/photo/user/register";

            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", "24d8ed2ab0444b048cbd5fcdde289109")
                    .add("appSecret", "300002f6abcaf485d4cb19de0695a0b049dc0")
                    .add("Content-Type", "application/json")
                    .build();

            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("password", password);
            bodyMap.put("username", account);
            // 将Map转换为字符串类型加入请求体中
            String body = gson.toJson(bodyMap);

            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .post(RequestBody.create(MEDIA_TYPE_JSON, body))
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                //发起请求，传入callback进行回调
                client.newCall(request).enqueue(callback);
            }catch (NetworkOnMainThreadException ex){
                ex.printStackTrace();
            }
        }).start();
    }

    final  Callback callback = new Callback() {

        public void onFailure(@NonNull Call call, IOException e) {
            //TODO 请求失败处理
            e.printStackTrace();
            Log.d("info1", "asdas");
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO 请求成功处理
            Type jsonType = new TypeToken<MainActivity.ResponseBody<Object>>(){}.getType();
            // 获取响应体的json串
            String body = response.body().string();
            Log.d("info1", body);
            MainActivity.ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);
            Log.d("info1", dataResponseBody.toString());
        }
    };


}