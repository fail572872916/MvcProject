package com.test.oschina.mvcproject.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.test.oschina.mvcproject.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CopyPhotoActivity extends Activity {

    private ArrayAdapter<String> adapter;
    private ListView mShowPathLv;
    String newpath;
    Button mButton;
    private boolean isTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_photo);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 内存卡存在
            newpath = "/mnt/sdcard/lmln/";
        }else{
            newpath = "/data/dat/lmln/";
        }
        initView();
    }

    private void initView() {
        try {
            String fileNames[] =CopyPhotoActivity.this.getAssets().list("lmln");//获取assets目录下的所有文件及目录名

            for (String fileName : fileNames) {
                Log.d("CopyPhotoActivity", fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mShowPathLv = (ListView) findViewById(R.id.lv_show_path);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1);
        adapter.addAll(getImagePathFromSD());
        mShowPathLv.setAdapter(adapter);
        mButton = (Button) findViewById(R.id.bt_copy);
        // 判断当前时候有内存卡的存在

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); startActivityForResult(i, 2);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            //获取图片的path
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                System.out.println(picturePath);
            Log.d("CopyPhotoActivity", picturePath);
            try {
                isTrue = copyFile(picturePath,newpath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), isTrue ?"文件复制成功":"失败", Toast.LENGTH_SHORT).show();
            }


    }
    // 文件复制
    public static boolean copyFile(String source, String copy) throws Exception {
        source = source.replace("\\", "/");
        copy = copy.replace("\\", "/");
        File source_file = new File(source);
        copy=copy+source_file.getName();

        File copy_file = new File(copy);
        // BufferedStream缓冲字节流
        if (!source_file.exists()) {
            throw new IOException("文件复制失败：源文件（" + source_file + "） 不存在");
        }
        if (copy_file.isDirectory()) {
            throw new IOException("文件复制失败：复制路径（" + copy_file + "） 错误");
        }
        File parent = copy_file.getParentFile();
        // 创建复制路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        // 创建复制文件
        if (!copy_file.exists()) {
            copy_file.createNewFile();
        }
        FileInputStream fis = new FileInputStream(source_file);
        FileOutputStream fos = new FileOutputStream(copy_file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        byte[] KB = new byte[1024];
        int index;
        while ((index = bis.read(KB)) != -1) {
            bos.write(KB, 0, index);
        }
        bos.close();
        bis.close();
        fos.close();
        fis.close();
        if (!copy_file.exists()) {
            return false;
        } else if (source_file.length() != copy_file.length()) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 从sd卡获取图片资源
     * @return
     */
    private List<String> getImagePathFromSD() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
//        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
//                + "image";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(newpath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
            }
        }
        // 返回得到的图片列表
        return imagePathList;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     * @param fName  文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }
}
