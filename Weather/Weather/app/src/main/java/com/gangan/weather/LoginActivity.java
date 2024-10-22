package com.gangan.weather;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gangan.weather.bean.UserBean;
import com.gangan.weather.databinding.ActivityLoginBinding;
import com.gangan.weather.utils.SPUtils;
import com.gangan.weather.utils.StatusBarUtil;
import com.kongzue.dialogx.dialogs.PopTip;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private boolean isShowPwd=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StatusBarUtil.setTransparent(this,true);

        binding.ivPsdStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowPwd = !isShowPwd;
                if (isShowPwd) {
                    binding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    binding.ivPsdStatus.setImageResource(R.drawable.ic_login_psd_show);
                    binding.etPassword.setSelection(binding.etPassword.getText().length());
                } else {
                    binding.ivPsdStatus.setImageResource(R.drawable.ic_login_psd_hide);
                    binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    binding.etPassword.setSelection(binding.etPassword.getText().length());
                }
            }
        });


        binding.btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();
                if (TextUtils.isEmpty(account)) {
                    binding.etUsername.setError("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    binding.etPassword.setError("请输入密码");
                    return;
                }
                login(account, password);

            }
        });
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

    }

    private void login(String account,String pwd) {
        List<UserBean> list = LitePal.where("username = ? and password = ?",account,pwd).find(UserBean.class);
        if (list.size() == 0) {
            PopTip.show("用户名或密码错误").iconError();
            return;
        }
        SPUtils.put(this, SPUtils.LOGIN_NAME,account);
        SPUtils.put(this, SPUtils.USER_ID, list.get(0).getId());
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

}