package com.gangan.weather;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.gangan.weather.adapter.MyFileListRecyclerAdapter;
import com.gangan.weather.adapter.MyMusicSearchRecyclerAdapter;
import com.gangan.weather.bean.FileBean;
import com.gangan.weather.databinding.ActivitySearchMusicBinding;
import com.gangan.weather.http.api.MusicApi;
import com.gangan.weather.http.api.WeatherApi;
import com.gangan.weather.http.model.HttpData;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallbackProxy;
import com.kongzue.dialogx.dialogs.PopTip;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SearchMusicActivity extends BaseActivity {

    private ActivitySearchMusicBinding binding;
    private List<MusicApi.Bean> mList = new ArrayList<>();
    private MyMusicSearchRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyMusicSearchRecyclerAdapter(mList,this);
        binding.recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyMusicSearchRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MusicDownApi(mList.get(position).getName(),mList.get(position).getId());
            }
        });

        binding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMusic();
            }
        });
        binding.etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    SearchMusic();
                    return true;
                }
                return false;
            }
        });
        binding.etInput.requestFocus();
    }

    private void SearchMusic() {
        String name = binding.etInput.getText().toString();
        if (TextUtils.isEmpty(name)) {
            TipDialog.show("请输入歌名", WaitDialog.TYPE.WARNING);
            return;
        }
        MusicSearchApi(name);
    }
    private void MusicSearchApi(String name) {
        MusicApi api = new MusicApi();
        api.setMsg(name);
        EasyHttp.get(SearchMusicActivity.this)
                .api(api)
                .request(new HttpCallbackProxy<HttpData<List<MusicApi.Bean>>>(null) {
                    @Override
                    public void onHttpSuccess(HttpData<List<MusicApi.Bean>> result) {
                        mList.clear();
                        mList.addAll(result.getData());
                        adapter.notifyDataSetChanged();
                        if (mList.size() == 0) {
                            binding.recyclerview.setVisibility(View.GONE);
                            TipDialog.show("没有搜索到音乐文件", WaitDialog.TYPE.ERROR);
                        }
                    }
                    @Override
                    public void onHttpStart(Call call) {
                        WaitDialog.show("加载中");
                    }


                    @Override
                    public void onHttpFail(Throwable throwable) {
                        PopTip.show(throwable.getMessage()).iconError();

                    }

                    @Override
                    public void onHttpEnd(Call call) {
                        WaitDialog.dismiss();
                    }
                });
    }
    private void MusicDownApi(String name,int id) {
        MusicApi api = new MusicApi();
        api.setId(id);
        api.setType("songid");
        EasyHttp.get(SearchMusicActivity.this)
                .api(api)
                .request(new HttpCallbackProxy<HttpData<String>>(null) {
                    @Override
                    public void onHttpSuccess(HttpData<String> result) {
                        Intent intent = new Intent(SearchMusicActivity.this, MusicPlayActivity.class);
                        intent.putExtra("type", "search");
                        intent.putExtra("url", result.getSong_url());
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                    @Override
                    public void onHttpStart(Call call) {
                        WaitDialog.show("加载中");
                    }


                    @Override
                    public void onHttpFail(Throwable throwable) {
                        PopTip.show(throwable.getMessage()).iconError();

                    }

                    @Override
                    public void onHttpEnd(Call call) {
                        WaitDialog.dismiss();
                    }
                });
    }
}