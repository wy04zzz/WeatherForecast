package com.gangan.weather;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gangan.weather.adapter.MyFileListRecyclerAdapter;
import com.gangan.weather.bean.FileBean;
import com.gangan.weather.databinding.ActivityMusicListBinding;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends BaseActivity {

    private ActivityMusicListBinding binding;
    private List<FileBean> mList = new ArrayList<>();
    private MyFileListRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyFileListRecyclerAdapter(mList,this);
        binding.recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyFileListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MusicListActivity.this, MusicPlayActivity.class);
                intent.putExtra("list", (Serializable) mList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        mList.addAll(getMp3File());
        adapter.notifyDataSetChanged();
        if (mList.size() == 0) {
            binding.recyclerview.setVisibility(View.GONE);
            TipDialog.show("没有搜索到音乐文件", WaitDialog.TYPE.ERROR);
        }

        binding.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MusicListActivity.this, SearchMusicActivity.class));
            }
        });

    }
    private List<FileBean> getMp3File() {
        List<FileBean> list = new ArrayList<>();
        String[] DOC_PROJECTION = {
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION
        };
        try {
            // 第一步 扫描内部存储的 Excel  MediaStore.Files.getContentUri("external")就是获取外部存储的 Uri
            // query 方法第一个参数是要扫描的 Uri，第二个参数是要查询的列，第三个参数是查询的
            // projection是我们要查询的列；
            // selection使我们自己的查询条件；
            // 最后一个参数order是排序方式，默认可以传null
            final Cursor cursor = getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    DOC_PROJECTION,
                    null,
                    null,
                    null
            );
            if (cursor != null) {
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                    Log.e("name", name);
                    if (!TextUtils.isEmpty(name)&&name.endsWith(".mp3")) {
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                        long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                        Log.e("duration", duration+"");
                        FileBean bean = new FileBean();
                        bean.setName(name.substring(0, name.length() - 4));
                        bean.setPath(path);
                        bean.setSize(duration);
                        list.add(bean);
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}