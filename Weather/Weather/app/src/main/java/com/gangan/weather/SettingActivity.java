package com.gangan.weather;

import android.app.UiModeManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gangan.weather.bean.UserBean;
import com.gangan.weather.databinding.ActivitySettingBinding;
import com.gangan.weather.utils.AppManager;
import com.gangan.weather.utils.SPUtils;
import com.kongzue.dialogx.dialogs.InputDialog;
import com.kongzue.dialogx.dialogs.PopTip;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener;

import org.litepal.LitePal;

public class SettingActivity extends BaseActivity {

    private ActivitySettingBinding binding;
    private UserBean mUserBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String user = (String) SPUtils.get(this, SPUtils.LOGIN_NAME, "");
        mUserBean = LitePal.where("username = ?", user).findFirst(UserBean.class);
        binding.tvPhone.setText(mUserBean.getPhone());
        binding.tvUsername.setText(mUserBean.getUsername());
        boolean isDark = (boolean) SPUtils.get(this, SPUtils.Theme_Dark, false);
        binding.sTheme.setChecked(isDark);

        binding.tvPasswordModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordDialog();
            }
        });

        binding.sTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.put(SettingActivity.this,SPUtils.Theme_Dark,isChecked);
                changeDarkTheme();
            }
        });
        binding.tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.put(SettingActivity.this, SPUtils.LOGIN_NAME, "");
                AppManager.getInstance().finishAllActivity();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
    //设置切换模式
    private void changeDarkTheme() {
        boolean isDark = (boolean) SPUtils.get(SettingActivity.this, SPUtils.Theme_Dark, false);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            UiModeManager systemService = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
            if (isDark) {
                systemService.setApplicationNightMode(UiModeManager.MODE_NIGHT_YES);
            } else {
                systemService.setApplicationNightMode(UiModeManager.MODE_NIGHT_NO);
            }
        } else {
            if (isDark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        Intent intent = getIntent();
        finish();
        startActivity(new Intent(SettingActivity.this,MainActivity.class));
        startActivity(intent);
    }
    private void showPasswordDialog() {
        new InputDialog("修改密码", "请输入新密码", "确定", "取消", "")
                .setInputText("")
                .setOkButton(new OnInputDialogButtonClickListener<InputDialog>() {
                    @Override
                    public boolean onClick(InputDialog baseDialog, View v, String inputStr) {
                        if (TextUtils.isEmpty(inputStr)) {
                            TipDialog.show("请输入密码", WaitDialog.TYPE.ERROR);
                            return false;
                        }
                        ContentValues values = new ContentValues();
                        values.put("password", inputStr);
                        LitePal.update(UserBean.class, values, mUserBean.getId());
                        PopTip.show("修改成功").iconSuccess();
                        return false;
                    }
                })
                .show();
    }
}