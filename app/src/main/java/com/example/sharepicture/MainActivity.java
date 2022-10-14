package com.example.sharepicture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.text.InputType;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final Gson gson = new Gson();
    ImageView iv_passwordSwitch = null;
    private boolean passwordSwitch = false;
    EditText et_password = null;
    EditText et_account ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_password = findViewById(R.id.et_password);
        iv_passwordSwitch= findViewById(R.id.iv_passwordSwitch);
        iv_passwordSwitch.setOnClickListener(this);
        Button bt_login = findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        et_account=findViewById(R.id.et_account);
        TextView tv_register = findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()){
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
            case R.id.bt_login:
                String account = String.valueOf(et_account.getText());
                String password = String.valueOf(et_password.getText());
                String url = "http://47.107.52.7:88/member/photo/user/login?password="+password+"&username="+account;
                Log.d("jtw", "onClick: "+account+password+url);
                // 请求头
                Headers headers = new Headers.Builder()
                        .add("appId", "24d8ed2ab0444b048cbd5fcdde289109")
                        .add("appSecret", "300002f6abcaf485d4cb19de0695a0b049dc0")
                        .build();

                MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

                //请求组合创建
                Request request = new Request.Builder()
                        .url(url)
                        // 将请求头加至请求中
                        .headers(headers)
                        .post(RequestBody.create(MEDIA_TYPE_JSON, ""))
                        .build();
                try {
                    OkHttpClient client = new OkHttpClient();
                    //发起请求，传入callback进行回调
                    client.newCall(request).enqueue(callback);
                }catch (NetworkOnMainThreadException ex){
                    ex.printStackTrace();
                }
                Intent intent1 = new Intent(MainActivity.this,BottomNavigation.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.tv_register:
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }




    final Callback callback = new Callback() {

        public void onFailure(@NonNull Call call, IOException e) {
            //TODO 请求失败处理
            e.printStackTrace();
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO 请求成功处理
            Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
            // 获取响应体的json串
            String body = response.body().string();
            Log.d("info", body);
            ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);
            Log.d("info", dataResponseBody.toString());
            String databody = gson.toJson(dataResponseBody.getData());
            Type dataType = new TypeToken<ReturnData<Object>>(){}.getType();
            if(dataResponseBody.getCode() == 200){
                ReturnData<Object> data = gson.fromJson(databody,dataType);
                Long id= data.getId();
                String name = data.getUsername();
                MyApplication application = MyApplication.getInstance();
                Log.d("jtw", String.valueOf(id));
                application.info.put("id", String.valueOf(id));
                application.info.put("userName",name);
            }else{
                Toast.makeText(MainActivity.this,"账号或者密码错误",Toast.LENGTH_LONG).show();
            }
        }
    };

    public static class ResponseBody <T> {
        /**
         * 业务响应码
         */
        private int code;
        /**
         * 响应提示信息
         */
        private String msg;
        /**
         * 响应数据
         */
        private T data;

        public ResponseBody(){}

        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
        public T getData() {
            return data;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseBody{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
    public static class ReturnData <T> {
        private String appKey;
        private Long id;
        private String introduce;
        private Long lastUpdateTime;
        private String username;
        private String password;
        private int sex;
        private String avatar;
        private Long createTime;


        public long getId() {
            return id;
        }
        public String getUsername() {return username;}

    }
}