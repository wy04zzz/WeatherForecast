package com.gangan.weather;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.gangan.weather.bean.FileBean;
import com.gangan.weather.databinding.ActivityMusicPlayBinding;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayActivity extends AppCompatActivity {

    private ObjectAnimator animator;
    private ActivityMusicPlayBinding binding;
    private int currentPlaying = 0; // 用作 ArrayList 下标，当前播放的歌曲
    private List<FileBean> playList = new ArrayList<>();
    private boolean isPausing = false, isPlaying = false; // 音乐暂停状态，音乐第一次播放之后变为true
    private MediaPlayer player;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        playList = (List<FileBean>) getIntent().getSerializableExtra("list");
        currentPlaying = getIntent().getIntExtra("position", 0);
        String type = getIntent().getStringExtra("type");
        if ("search".equals(type)) {
            String url = getIntent().getStringExtra("url");
            String name = getIntent().getStringExtra("name");
            FileBean bean = new FileBean();
            bean.setPath(url);
            bean.setName(name);
            playList = new ArrayList<>();
            playList.add(bean);
        }

        animator = ObjectAnimator.ofFloat(binding.ivDisk, "rotation", 0, 360.0F); // 初始化状态
        animator.setDuration(10000); // 转动时长，10秒
        animator.setInterpolator(new LinearInterpolator()); // 时间函数，有很多类型
        animator.setRepeatCount(-1); // 一直旋转
        animator.start();


        handler.postDelayed(runnable, 500);
        startPausingPlay();

        OnSeekBarChangeControl onSbChange = new OnSeekBarChangeControl();
        binding.sbProgress.setOnSeekBarChangeListener(onSbChange);
        OnClickControl onClick = new OnClickControl();
        binding.btnPlayPause.setOnClickListener(onClick);
        binding.btnPrev.setOnClickListener(onClick);
        binding.btnNext.setOnClickListener(onClick);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int now = player.getCurrentPosition()- 5 * 1000;
                if (now < 0) {
                    now = 0;
                }
                player.seekTo(now);
            }
        });
        binding.btnKuaijin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int now = player.getCurrentPosition()+ 5 * 1000;
                player.seekTo(now);

            }
        });

    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (isPlaying) {
                updateTimer();
            }
            handler.postDelayed(this, 500);
        }
    };
    private void updateTimer() {
        if (player != null) {
            int currentMs = player.getCurrentPosition();
            int sec = currentMs / 1000;
            int min = sec / 60;
            sec -= min * 60;
            String time = String.format("%02d:%02d", min, sec);
            binding.sbProgress.setProgress(currentMs);
            binding.tvProgressCurrent.setText(time);
        }
    }
    private void prepareMedia() {
        if (isPlaying&&player!=null) {
            player.stop();
            player.reset();
        }

        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.setDataSource(playList.get(currentPlaying).getPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
        player.prepareAsync();//异步准备
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            //异步准备监听
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                player.start();//播放
                int musicDuration = player.getDuration();
                Log.e("sadsd", musicDuration + "");
                binding.sbProgress.setMax(musicDuration);
                int sec = musicDuration / 1000;
                int min = sec / 60;
                sec -= min * 60;
                String musicTime = String.format("%02d:%02d", min, sec);
                binding.tvProgressTotal.setText(musicTime);

                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        // 在这里执行播放完成后的操作
                        nextPlay();
                    }
                });
            }
        });



        binding.tvSongName.setText(playList.get(currentPlaying).getName());


    }
    private class OnClickControl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_prev) {
                prevPlay();
            }else if (v.getId() == R.id.btn_play_pause) {
                startPausingPlay();
            }else if (v.getId() == R.id.btn_next) {
                nextPlay();
            }
        }
    }

    private void nextPlay() {
        // 切歌
        Log.i("INFO", "onClick: 切歌按钮被点击！");
        binding.btnPlayPause.setImageResource(R.drawable.control_pause_dark); // 切换成暂停键
        currentPlaying = ++currentPlaying % playList.size();
        prepareMedia();
        animator.start();
        isPausing = false;
        isPlaying = true;
    }

    private void prevPlay() {
        // 重播、上切
        Log.i("INFO", "onClick: 重播按钮被点击！");
        binding.btnPlayPause.setImageResource(R.drawable.control_pause_dark); // 切换成暂停键
        animator.start();
        if (!player.isPlaying()) {
            --currentPlaying;
            if (currentPlaying < 0) {
                currentPlaying = playList.size()-1;
            }
        }
        prepareMedia();
        isPausing = false;
        isPlaying = true;
    }
    //播放暂停
    private void startPausingPlay() {
        // 开始暂停
        Log.i("INFO", "onClick: 开始暂停按钮被点击！");
        if (!isPausing && !isPlaying) { // 暂停状态，且从未被播放
            // 开始播放
            binding.btnPlayPause.setImageResource(R.drawable.control_pause_dark); // 切换成暂停键
            animator.start();
            prepareMedia();
            isPlaying = true;
            isPausing=true;
        } else if (isPausing && isPlaying) { // 暂停状态，且被播放过一次
            // 继续播放
            binding.btnPlayPause.setImageResource(R.drawable.control_pause_dark); // 切换成暂停键
            animator.resume();
            player.start();
        } else { // 播放状态
            // 暂停播放
            binding.btnPlayPause.setImageResource(R.drawable.control_play_dark); // 切换成播放键
            animator.pause();
            player.pause();
        }
        isPausing = !isPausing; // 切换状态
    }
    private class OnSeekBarChangeControl implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                player.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            player.pause();
            animator.pause();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            player.start();
            if (seekBar.getProgress() < 10) {
                animator.start();
            } else {
                animator.resume();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        handler.removeCallbacks(runnable);
    }
}