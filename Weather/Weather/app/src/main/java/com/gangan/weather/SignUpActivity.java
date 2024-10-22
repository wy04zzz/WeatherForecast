package com.gangan.weather;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.gangan.weather.bean.UserBean;
import com.gangan.weather.databinding.ActivitySignUpBinding;
import com.kongzue.dialogx.dialogs.PopTip;

import org.litepal.LitePal;

import java.util.List;

public class SignUpActivity extends BaseActivity {

    private ActivitySignUpBinding binding;
    private String username;
    private String password;

    private String confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.etUsername.getText().toString();
                String phone = binding.etPhone.getText().toString();
                String password = binding.etPassword.getText().toString();
                String confirm = binding.etPasswordConfirm.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    binding.etUsername.setError("请输入用户名");
                    return ;
                }
                if (TextUtils.isEmpty(phone)) {
                    binding.etPhone.setError("请输入手机号");
                    return ;
                }
                if (TextUtils.isEmpty(password)) {
                    binding.etPassword.setError("请输入密码");
                    return ;
                }
                if (TextUtils.isEmpty(confirm)) {
                    binding.etPasswordConfirm.setError("请输入确认密码");
                    return ;
                }
                if (!password.equals(confirm)) {
                    binding.etPasswordConfirm.setError("密码不一致");
                    return ;
                }

                List<UserBean> list = LitePal.where("username = ?",username).find(UserBean.class);
                if (list.size() > 0) {
                    binding.etUsername.setError("用户已注册");
                    return ;
                }
                UserBean userBean = new UserBean();
                userBean.setUsername(username);
                userBean.setPassword(password);
                userBean.setPhone(phone);
                userBean.save();
                PopTip.show("注册成功，请登录").iconSuccess();
                finish();

            }
        });
    }


}