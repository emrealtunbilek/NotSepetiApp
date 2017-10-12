package emrealtunbilek.com.notsepetiapp;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import emrealtunbilek.com.notsepetiapp.adapter.AdapterNotlarListesi;
import emrealtunbilek.com.notsepetiapp.data.Notlar;
import emrealtunbilek.com.notsepetiapp.data.NotlarProvider;

public class ActivityMain extends AppCompatActivity {

    static final Uri CONTENT_URI = NotlarProvider.CONTENT_URI;

    Toolbar mToolbar;
    Button mButtonYeninot;
    RecyclerView mRecyclerViewNotlar;
    AdapterNotlarListesi mAdapterNotlarListesi;
    ArrayList<Notlar> tumNotlar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mButtonYeninot= (Button) findViewById(R.id.btn_sepete_not_ekle);
        mButtonYeninot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notekleDialogGoster();
            }
        });

        tumNotlar=new ArrayList<>();

        mRecyclerViewNotlar= (RecyclerView) findViewById(R.id.rv_not_listesi);
        mAdapterNotlarListesi=new AdapterNotlarListesi(this);
        LinearLayoutManager mLayoutmanger=new LinearLayoutManager(this);
        mRecyclerViewNotlar.setLayoutManager(mLayoutmanger);
        mRecyclerViewNotlar.setAdapter(mAdapterNotlarListesi);



        backgrounResminiYerlestir();


    }

    private void notekleDialogGoster() {
        FragmentDialogYeniNot dialog=new FragmentDialogYeniNot();
        dialog.show(getSupportFragmentManager(), "DialogYeniNot");

    }

    private ArrayList<Notlar> tumNotlariGetir(){

        Cursor cursor=getContentResolver().query(CONTENT_URI, null, null, null, null);
        Notlar geciciNot=null;

        if(cursor != null){

            while(cursor.moveToNext()){

                geciciNot.setId(cursor.getInt(cursor.getColumnIndex("id")));
                geciciNot.setNotIcerik(cursor.getString(cursor.getColumnIndex("notIcerik")));
                tumNotlar.add(geciciNot);

            }
        }

    return  tumNotlar;

    }

    private void backgrounResminiYerlestir() {
        ImageView background= (ImageView) findViewById(R.id.iv_background);
        Glide.with(this)
                .load(R.drawable.background)
                .apply(new RequestOptions().centerCrop())
                .into(background);

    }
}
