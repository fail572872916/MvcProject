package com.test.oschina.mvcproject.activity;

/**
 * @name MvcProject
 * @class name：com.test.oschina.mvcproject.activity
 * @class describe
 * @anthor Administrator
 * @time 2017/9/30 17:50
 * @change
 * @chang time
 * @class describe
 */


import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.test.oschina.mvcproject.R;

import java.util.Hashtable;

public class QrCodeActivity extends Activity {

    private EditText et_only_company;
    private EditText et_only_position;
    private EditText et_only_phone;
    private EditText et_only_email;
    private EditText et_only_web1;
    private EditText et_only_add;
    private EditText et_only_note;
    private EditText et_only_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcod);
        et_only_name =(EditText) findViewById(R.id.et_only_name);
        et_only_company =(EditText) findViewById(R.id.et_only_company);
        et_only_position =(EditText) findViewById(R.id.et_only_position);
        et_only_phone=(EditText) findViewById(R.id.et_only_phone);
        et_only_email =(EditText) findViewById(R.id.et_only_email);
        et_only_web1 =(EditText) findViewById(R.id.et_only_web1);
        et_only_add=(EditText) findViewById(R.id.et_only_add);
        et_only_note=(EditText) findViewById(R.id.et_only_note);

        findViewById(R.id.but).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = et_only_name.getText().toString().trim() ;
                String company=et_only_company.getText().toString().trim() ;
                String position = et_only_position.getText().toString().trim()  ;
                String phone =et_only_phone .getText().toString().trim() ;
                String email = et_only_email.getText().toString().trim() ;
                String web1 = et_only_web1.getText().toString().trim() ;
                String add =et_only_add.getText().toString().trim() ;
                String note = et_only_note.getText().toString().trim() ;
                String contents= "BEGIN:VCARD\nVERSION:3.0\n"+"N:"+name+"\nORG:"+company+"\nTITLE:"+position+"\nNOTE:"+note+ "\nTEL:"+phone+"\nADR:"+add+"\nURL:"+web1+"\nEMAIL:"+email+"\nPHOTO;VALUE=uri:http://img2.imgtn.bdimg.com/it/u=2974104803,1439396293&fm=200&gp=0.jpg"+"\nEND:VCARD";
                try {
                    Bitmap bm=      qr_code(contents, BarcodeFormat.QR_CODE);

                    ImageView img=		(ImageView)findViewById(R.id.img_only) ;

                    img.setImageBitmap(bm);
                } catch (WriterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

    }


    public Bitmap qr_code(String string, BarcodeFormat format)
            throws WriterException {
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix matrix = writer.encode(string, format, 400, 400, hst);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

}

