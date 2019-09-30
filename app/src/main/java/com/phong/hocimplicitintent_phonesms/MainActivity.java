package com.phong.hocimplicitintent_phonesms;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtPhone, edtSms;
    Button btnDial, btnCall, btnSms1, btnSms2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XuLyDial();
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XuLyCall();
            }
        });
        btnSms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XuLySms1();
            }
        });
        btnSms2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XuLySms2();
            }
        });
    }

    private void XuLySms2() {
        //Quản lý tính năng tin nhắn gửi đi thành công hay thất bại
        SmsManager smsManager = SmsManager.getDefault();
        Intent msgSent = new Intent("ACTION_MSG_SENT");
        //Khai báo pendingIntent để kiểm tra kết quả
        PendingIntent pendingMsgSent = PendingIntent.getBroadcast(MainActivity.this,0,msgSent,0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int kq = getResultCode();
                if (kq == Activity.RESULT_OK)
                {
                    Toast.makeText(MainActivity.this,"Đã gửi tin nhắn cho bạn thân Hải Cẩu!!!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Thất bại gửi tin nhắn cho bạn thân Hải Cẩu!!!",Toast.LENGTH_SHORT).show();
                }
            }
        },new IntentFilter("ACTION_MSG_SENT"));//Lắng nghe tin nhắn gửi đi thành công hay thất bại
        //Gọi hàm gửi tin nhắn:
        smsManager.sendTextMessage(edtPhone.getText().toString(),null,edtSms.getText().toString(),pendingMsgSent,null);
    }

    private void XuLySms1() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(edtPhone.getText().toString(),null,edtSms.getText().toString(),null,null);
        //Cấp quyền
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void XuLyCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        //Tạo số điện thoại:
        Uri uri = Uri.parse("tel:" + edtPhone.getText().toString());
        intent.setData(uri);
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        startActivity(intent);//alt + Enter để phát sinh: android.permission.CALL_PHONE và Security
    }

    private void XuLyDial() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        //Tạo số điện thoại:
        Uri uri = Uri.parse("tel:" + edtPhone.getText().toString());
        intent.setData(uri);
        startActivity(intent);//Kích hoạt màn hình quay số
    }

    private void addControls() {
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtSms = (EditText) findViewById(R.id.edtSms);
        btnDial = (Button) findViewById(R.id.btnDial);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnSms1 = (Button) findViewById(R.id.btnSms1);
        btnSms2 = (Button) findViewById(R.id.btnSms2);
    }
}
