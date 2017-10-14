package emrealtunbilek.com.notsepetiapp;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import emrealtunbilek.com.notsepetiapp.adapter.AdapterNotlarListesi;
import emrealtunbilek.com.notsepetiapp.adapter.AddListener;
import emrealtunbilek.com.notsepetiapp.adapter.Divider;
import emrealtunbilek.com.notsepetiapp.data.Notlar;
import emrealtunbilek.com.notsepetiapp.data.NotlarProvider;

public class ActivityMain extends AppCompatActivity implements AddListener{

    static final Uri CONTENT_URI = NotlarProvider.CONTENT_URI;

    View bosListe;
    Toolbar mToolbar;
    Button mButtonYeninot;
    NotlarRecyclerView mRecyclerViewNotlar;
    AdapterNotlarListesi mAdapterNotlarListesi;
    ArrayList<Notlar> tumNotlar=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bosListe=findViewById(R.id.bos_liste);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mButtonYeninot= (Button) findViewById(R.id.btn_sepete_not_ekle);
        mButtonYeninot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notekleDialogGoster();
            }
        });




        mRecyclerViewNotlar= (NotlarRecyclerView) findViewById(R.id.rv_not_listesi);
        mRecyclerViewNotlar.addItemDecoration(new Divider(this,LinearLayoutManager.VERTICAL));
        mRecyclerViewNotlar.egerElemanYoksaSaklanacaklar(mToolbar);
        mRecyclerViewNotlar.egerElemanYoksaGosterilecekler(bosListe);
        dataGuncelle();


        backgrounResminiYerlestir();


    }

    public void dataGuncelle() {



        tumNotlar.clear();
        tumNotlar=tumNotlariGetir();
        LinearLayoutManager mLayoutmanger=new LinearLayoutManager(this);
        mRecyclerViewNotlar.setLayoutManager(mLayoutmanger);
        mAdapterNotlarListesi=new AdapterNotlarListesi(this, tumNotlar);
        mRecyclerViewNotlar.setAdapter(mAdapterNotlarListesi);

        mAdapterNotlarListesi.setAddListener(this);
    }

    private void notekleDialogGoster() {
        FragmentDialogYeniNot dialog=new FragmentDialogYeniNot();
        dialog.show(getSupportFragmentManager(), "DialogYeniNot");

    }

    private ArrayList<Notlar> tumNotlariGetir(){

        Cursor cursor=getContentResolver().query(CONTENT_URI, new String[]{"id", "notIcerik"}, null, null, null);


        if(cursor != null){

            while(cursor.moveToNext()){
                Notlar geciciNot=new Notlar();
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

    @Override
    public void ekleDialogGoster() {
        notekleDialogGoster();
    }
}
