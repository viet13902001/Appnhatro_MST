package activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.mstappdemo.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.Giohang;
import model.Sanpham;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarchitiet;
    ImageView imgchitiet;
    TextView txtten,txtgia,txtmota;
    Button buttondatmua,comment;
    Spinner spinner;

    int id=0;
    String Tenchitiet="";
    int Giachitiet=0;
    String Hinhanhchitiet="";
    String Motachitiet="";
    int idsanpham=0;


    ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        Getinfomation();
        CatchEventSpiner();
        EventButton();
        ActionViewFlipper();
        comment = findViewById(R.id.comment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), activity.Comment.class);
                startActivity(intent);
            }
        });
    }

    private void EventButton() {
        buttondatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size()>0){
                    int sl=Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exit =false;
                    for(int i=0;i<MainActivity.manggiohang.size();i++){
                        if(MainActivity.manggiohang.get(i).getIdsp()==id){
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp()+sl);
                            if(MainActivity.manggiohang.get(i).getSoluongsp()>=10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(Giachitiet*MainActivity.manggiohang.get(i).getSoluongsp());
                            exit=true;
                        }
                    }
                    if(exit==false){
                        int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi=Giachitiet*soluong;
                        MainActivity.manggiohang.add(new Giohang(id,Tenchitiet,giamoi,Hinhanhchitiet,soluong));

                    }

                }else{
                    int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi=Giachitiet*soluong;
                    MainActivity.manggiohang.add(new Giohang(id,Tenchitiet,giamoi,Hinhanhchitiet,soluong));
                }
                Intent intent=new Intent(getApplicationContext(), activity.Giohang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpiner() {
        Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter =new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong );
        spinner.setAdapter(arrayAdapter);
    }

    private void Getinfomation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id =sanpham.getID();
        Tenchitiet=sanpham.getTensanpham();
        Giachitiet=sanpham.getGiasanpham();
        Hinhanhchitiet=sanpham.getHinhanhsanpham();
        Motachitiet=sanpham.getMotasanpham();
        idsanpham=sanpham.getIDSanpham();
        txtten.setText(Tenchitiet);
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        txtgia.setText("Giá: "+ decimalFormat.format(Giachitiet)+ "VNĐ");
        txtmota.setText(Motachitiet);
        Picasso.get().load(Hinhanhchitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgchitiet);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarchitiet);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add ("https://media-cldnry.s-nbcnews.com/image/upload/newscms/2018_30/1355945/home-exterior-today-180726-tease.jpg");
        mangquangcao.add ("https://theme.hstatic.net/1000034256/1000761792/14/slideshow_3.jpg?v=57");
        mangquangcao.add ("https://chungcuhn24h.net/wp-content/uploads/2020/01/chung-cu-epic-home.jpg");
        mangquangcao.add ("https://ducanhland.com/wp-content/uploads/2021/05/Chung-cu-The-Park-Home-07.jpg");
        for (int i =0; i<mangquangcao.size();i++){
            ImageView imageView =new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);


    }

    private void Anhxa() {
        toolbarchitiet=findViewById(R.id.toolbarchitietsanpham);
        imgchitiet=findViewById(R.id.imagechitietsanpham);
        txtten=findViewById(R.id.textviewtenchitietsanpham);
        txtgia=findViewById(R.id.textviewgiasanpham);
        txtmota=findViewById(R.id.textviewmotachitietsanpham);
        buttondatmua=findViewById(R.id.buttondatmua);
        spinner =findViewById(R.id.spiner);
        viewFlipper =this.findViewById(R.id.viewflipper);
    }
}