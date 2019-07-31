package wediariasantana.gmail.pertemuan9;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //TODO 1 : mengaitkan object dengan komponen view
    Button camera= (Button) findViewById(R.id.open);
    iv=(ImageView)findViewById(R.id.img1);
    //TODO 2 : membuat event untuk tombol open untuk membuka kamera
        camera.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //TODO 3 : membuat intent untuk mengaksesk kamera dengan nilai kembaian berupa gambar bitmap request code 0
            Intent tp=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(tp,0);
        }
    });
}
    //TODO 4 : membuat method untuk pengembailian nilai
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){

        //TODO 5 : mengirimkan data parameter ke super klas
        super.onActivityResult(requestCode,resultCode,data);

        //TODO 6 : menyeleksi kondisi apakah gambar jadi iambil menggunakan kamera atau tidak jika iya maka result ok yang akan tereksekusi jika tidak maka result censle yang akan tereksekusi
        if (resultCode==RESULT_OK) {
            //TODO 7 : tahap penampil gambar
            Bitmap bi = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(bi);
            //TODO 8 : bagaian ini memanggil method untuk menyimpan gambar yang diambil kedalam memori external
            SaveImage(bi);
        }else if (resultCode==RESULT_CANCELED){
            //TODO 9 : menampilkan pesan jika kamera tidak jadi digunakan
            Toast.makeText(this, "kamera tidak jadi digunakan", Toast.LENGTH_LONG).show();
        }
    }
    private void SaveImage(Bitmap finalBitmap) {
        //TODO 10 : Bagian ini mengarahkan lokasi penyimpanan ke direktori external Picture, jika tidak ada folder tersebut maka akan dibuat saat itu juga
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        //TODO 11 : menampilkan lokasi penyimpanan dalam bentuk Toast
        Toast.makeText(this, myDir.toString(), Toast.LENGTH_LONG).show();

        //TODO 12 memberi nama file secara acak
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        File file = new File (myDir,  "Image-"+ n +".jpg");
        if (file.exists ())
            file.delete ();

        //TODO 13 : memasukan data gambar kedalam file yang telah dibuat
        try {
            FileOutputStream out = new FileOutputStream(file);
            //TODO 14 : mengkopres data kedalam file jpg ter kompres (seperti yang kita tahu format jpg merupakan fil gambar ter kompresi)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            //TODO 15 : menutup file
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
