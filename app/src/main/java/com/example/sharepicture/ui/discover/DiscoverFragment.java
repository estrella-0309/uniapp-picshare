package com.example.sharepicture.ui.discover;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sharepicture.MyApplication;
import com.example.sharepicture.R;
import com.example.sharepicture.adapter.discoverAdapter;
import com.example.sharepicture.entity.discoverPic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String userId;
    ListView lv_discover ;
    private final Gson gson = new Gson();
    private static final Handler handler = new Handler(Looper.getMainLooper());
    Context context;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        MyApplication application = MyApplication.getInstance();
        userId=application.info.get("id");
        Log.d("discover", "onCreate: "+userId);
        get();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=this.getActivity();
        View view = inflater.inflate(R.layout.fragment_discover,container,false);
        lv_discover=view.findViewById(R.id.lv_discover);
        return view;
    }

    private void get(){
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/photo/share?current=1&size=10&userId="+userId;

            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", "24d8ed2ab0444b048cbd5fcdde289109")
                    .add("appSecret", "300002f6abcaf485d4cb19de0695a0b049dc0")
                    .build();

            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .get()
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

    /**
     * 回调
     */
    private final Callback callback = new Callback() {
        @Override
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

            // 解析json串到自己封装的状态
            ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);

            //get json
            Gson gson =new Gson();
            String recBody = gson.toJson(dataResponseBody.getData());
            Type dataType = new TypeToken<BaseResponse<Object>>(){}.getType();
            BaseResponse<Object> dataData = gson.fromJson(recBody,dataType);
            String recData = gson.toJson(dataData.getRecords());
            Log.d("info","recData is:"+recData);

            discoverPic[] discoverPic = gson.fromJson(recData,discoverPic[].class);
            List<discoverPic> picList = Arrays.asList(discoverPic);


            handler.post(new Runnable() {
                @Override
                public void run() {
                    //代码逻辑
                    discoverAdapter  adapter= new discoverAdapter(context,R.layout.discover_list, picList);
                    lv_discover.setAdapter(adapter);


                    lv_discover.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            getActivity().getIntent().putExtra(pageId,pageid);
//                            myListener.sendId(recPics[i].getId());
//                            Log.d("info","send picId is:"+recPics[i].getId());
//                            getActivity().getIntent().putExtra("picId",recPics[i].getId());
//                            getActivity().getIntent().putExtra("picurl",recPics[i].getImageUrlList().toString());
//
//                            /*
//                            FragmentManager fm = getActivity().getSupportFragmentManager();
//                            fm.beginTransaction().add(R.id.fragmentContainerView,new PicDetailFragment()).commit();
//                             */
//
//                            NavController navController = Navigation.findNavController(getActivity(),R.id.fragmentContainerView);
//                            navController.navigate(R.id.pic_detail);


                        }
                    });


                }
            });

        }
    };



    /**
     * http响应体的封装协议
     * @param <T> 泛型
     */
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


    public static class BaseResponse <T> {
        private long size;
        private long total;
        private long current;
        private T records;

        public BaseResponse(){}

        public T getRecords(){
            return records;
        }

        @NonNull
        @Override
        public String toString() {
            return "get record:"+records;
        }
    }

}
