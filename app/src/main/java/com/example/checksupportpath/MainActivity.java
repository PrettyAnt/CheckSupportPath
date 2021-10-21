package com.example.checksupportpath;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView     tv_show;
    private EditText     et_name;
    private Button       btn_create;
    private Button       btn_delete;
    private StringBuffer sb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sb = new StringBuffer();
        initView();
        requestPermission();
    }

    private void initView() {
        tv_show = (TextView) findViewById(R.id.tv_show);
        et_name = (EditText) findViewById(R.id.et_name);
        btn_create = (Button) findViewById(R.id.btn_create);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_create.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create:
                String name = et_name.getText().toString();
                String sandboxPath = FileUtil.getSandboxPath(this, null) + "/" + name;
                File file = new File(sandboxPath);
                if (!file.exists()) {
                    file.mkdirs();
                    sb.append("新建文件的路径:" + sandboxPath + "\n");
                    tv_show.setText(sb.toString());
                }
                break;
            case R.id.btn_delete:
                sb = new StringBuffer();
                String rootPath = FileUtil.getSandboxPath(this, null);
                File rootFile = new File(rootPath);
                File[] files = rootFile.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
                tv_show.setText("刚刚创建的文件已删除!");
                break;
            default:
                break;
        }
    }
}