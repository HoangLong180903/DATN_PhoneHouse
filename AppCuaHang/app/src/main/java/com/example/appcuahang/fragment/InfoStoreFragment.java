package com.example.appcuahang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.databinding.FragmentHomeBinding;
import com.example.appcuahang.databinding.FragmentInfoStoreBinding;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Store;
import com.example.appcuahang.untils.MySharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoStoreFragment extends Fragment {


    FragmentInfoStoreBinding binding;

    MySharedPreferences mySharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoStoreBinding.inflate(getLayoutInflater(), container, false);
        ((Activity) getContext()).setTitle("Thông Tin Cá Nhân");
        initVariable();
        action();
        return binding.getRoot();
    }

    private void initVariable() {
        mySharedPreferences = new MySharedPreferences(getContext());
        binding.infoTvUsername.setText("" + mySharedPreferences.getUserName());
        binding.infoTvPhone.setText("" + mySharedPreferences.getPhone());
        binding.infoTvEmail.setText("" + mySharedPreferences.getEmail());
        binding.infoTvAddress.setText("" + mySharedPreferences.getAddress());
    }

    private void action() {
        binding.cvUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangeInfo();
            }
        });

        binding.cvUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangePassword();
            }
        });
    }

    private void dialogChangeInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_info, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        EditText edUsername = view.findViewById(R.id.dl_store_edUsername);
        EditText edAddress = view.findViewById(R.id.dl_store_edAddress);
        EditText edEmail = view.findViewById(R.id.dl_store_edEmail);
        EditText edPhone = view.findViewById(R.id.dl_store_edPhone);

        Button btnSave = view.findViewById(R.id.dl_store_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_store_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_store_imageView);

        tvTitle.setText("Cập Nhật Thông Tin");
        edUsername.setText("" + mySharedPreferences.getUserName());
        edAddress.setText("" + mySharedPreferences.getAddress());
        edEmail.setText("" + mySharedPreferences.getEmail());
        edPhone.setText("" + mySharedPreferences.getPhone());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString().trim();
                String address = edAddress.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();
                ApiService apiService = ApiRetrofit.getApiService();
                Call<Store> call = apiService.putCuaHang(mySharedPreferences.getUserId(), new Store(address,username,phone,email));
                call.enqueue(new Callback<Store>() {
                    @Override
                    public void onResponse(Call<Store> call, Response<Store> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Store> call, Throwable t) {
                        Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void dialogChangePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_password, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        EditText edOldPass = view.findViewById(R.id.dl_store_edOldPass);
        EditText edNewPass = view.findViewById(R.id.dl_store_edNewPass);
        EditText edConfirmPass = view.findViewById(R.id.dl_store_edConfirmPass);

        Button btnSave = view.findViewById(R.id.dl_store_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_store_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_store_imageView);

        tvTitle.setText("Đổi Mật Khẩu");
        edOldPass.setText("" + mySharedPreferences.getPassword());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = edNewPass.getText().toString().trim();
                String confirmPass = edConfirmPass.getText().toString().trim();
                String oldPass = edOldPass.getText().toString().trim();
                if (!newPass.equals(confirmPass)) {
                    Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!oldPass.equals(mySharedPreferences.getPassword())) {
                    Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ApiService apiService = ApiRetrofit.getApiService();
                    Call<Store> call = apiService.putCuaHang(mySharedPreferences.getUserId(), new Store(newPass));
                    call.enqueue(new Callback<Store>() {
                        @Override
                        public void onResponse(Call<Store> call, Response<Store> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Store> call, Throwable t) {
                            Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}