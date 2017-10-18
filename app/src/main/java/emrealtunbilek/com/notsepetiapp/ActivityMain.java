package emrealtunbilek.com.notsepetiapp;

import android.app.usage.UsageEvents;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import emrealtunbilek.com.notsepetiapp.adapter.AdapterNotlarListesi;
import emrealtunbilek.com.notsepetiapp.adapter.Divider;
import emrealtunbilek.com.notsepetiapp.adapter.SimpleTouchCallback;
import emrealtunbilek.com.notsepetiapp.data.Notlar;
import emrealtunbilek.com.notsepetiapp.data.NotlarProvider;

public class ActivityMain extends AppCompatActivity{

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
        LinearLayoutManager mLayoutmanger=new LinearLayoutManager(this);
        mRecyclerViewNotlar.setLayoutManager(mLayoutmanger);
        mAdapterNotlarListesi=new AdapterNotlarListesi(this, tumNotlar);
        mRecyclerViewNotlar.setAdapter(mAdapterNotlarListesi);

//swipe işlemi için yapılanlar
        SimpleTouchCallback callback=new SimpleTouchCallback();
        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerViewNotlar);


        dataGuncelle();


        backgrounResminiYerlestir();


    }

    public void dataGuncelle() {

        tumNotlar.clear();
        tumNotlar=tumNotlariGetir();
        mAdapterNotlarListesi.update(tumNotlar);

    }

    private void notekleDialogGoster() {
        FragmentDialogYeniNot dialog=new FragmentDialogYeniNot();
        dialog.show(getSupportFragmentManager(), "DialogYeniNot");

    }

    private void notTamamlaDialogGoster(int position) {

        EventBus.getDefault().postSticky(new DataEvent.TamamlanacakNotPosition(position));
        FragmentDialogTamamla dialog=new FragmentDialogTamamla();
        dialog.show(getSupportFragmentManager(), "DialogNotTamamla");

    }

    private ArrayList<Notlar> tumNotlariGetir(){

        Cursor cursor=getContentResolver().query(CONTENT_URI, new String[]{"id", "notIcerik", "notTarih", "tamamlandi"}, null, null, null);


        if(cursor != null){

            while(cursor.moveToNext()){
                Notlar geciciNot=new Notlar();
                geciciNot.setId(cursor.getInt(cursor.getColumnIndex("id")));
                geciciNot.setNotIcerik(cursor.getString(cursor.getColumnIndex("notIcerik")));
                geciciNot.setNotTarih(cursor.getLong(cursor.getColumnIndex("notTarih")));
                geciciNot.setTamamlandi(cursor.getInt(cursor.getColumnIndex("tamamlandi")));
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

    @Subscribe
    public void onDialogNotTamamla(DataEvent.DialogTamamlaNotPosition event){

        notTamamlaDialogGoster(event.getPosition());

    }

    @Subscribe
    public void onNotEkleDialogGoster(DataEvent.NotEkleDialogGoster event){
        if(event.getTetikle()==1){
            notekleDialogGoster();
        }
    }

    @Subscribe
    public void onDataGuncelleMethoduTetikle(DataEvent.DataGuncelleMethoduTetikle event){
        if(event.getTetikle()==1){
           dataGuncelle();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
