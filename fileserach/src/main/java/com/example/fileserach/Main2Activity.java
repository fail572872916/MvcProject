package com.example.fileserach;

import android.content.Context;
import android.os.Build;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    TextView tv_read;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv_read = findViewById(R.id.tv_read);
        tv_read.setMovementMethod(ScrollingMovementMethod.getInstance());
        String a = getIntent().getStringExtra("i");
        Log.d("Main2Activity", a);
        if (TextUtils.isEmpty(a)) {

            Toast.makeText(this, "错误", Toast.LENGTH_SHORT).show();
        } else {

            try {
                String vvv=    readInternal(this,a);
                tv_read.setText(vvv);
                Log.d("Main2Activity", vvv);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("Main2Activity", a.toString());
//            String s="";
//            File f=new File(a);
//            InputStream in=null;
//            try{
//                in=new FileInputStream(f);
//                int tempByte;
//                while((tempByte=in.read())!=-1){
//                    System.out.println(tempByte);
//                    s+=tempByte;
//                }
//                in.close();
//            }catch(Exception e){
//                e.printStackTrace();
//                Log.d("Main2Activity", e.getMessage());
//            }
//            tv_read.setText(s);
//            try (FileReader reader = new FileReader(a);
//                 BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
//            ) {
//                String line;
//                //网友推荐更加简洁的写法
//                while ((line = br.readLine()) != null) {
//                    // 一次读入一行数据
//                    tv_read.append(line);
//                }
//            } catch (IOException e) {
//                Log.d("Main2Activity", "ssssssssss");
//                e.printStackTrace();
//            }

        }
    }



    /**
     * 读内存卡中文件方法
     * @param context
     * @param filename 文件名
     * @return
     * @throws IOException
     */
    public static String readInternal(Context context,String filename) throws IOException{
        StringBuilder sb = new StringBuilder("");

        //获取文件在内存卡中files目录下的路径
//        File file = context.getFilesDir();
//        filename = file.getAbsolutePath() + File.separator + filename;

        //打开文件输入流
        FileInputStream inputStream = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        int len = inputStream.read(buffer);
        //读取文件内容
        while(len > 0){
            sb.append(new String(buffer,0,len));

            //继续将数据放到buffer中
            len = inputStream.read(buffer);
        }
        //关闭输入流
        inputStream.close();
        return sb.toString();
    }

}
