package emrealtunbilek.com.notsepetiapp;

import android.app.usage.UsageEvents;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
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
import emrealtunbilek.com.notsepetiapp.adapter.Filtreler;
import emrealtunbilek.com.notsepetiapp.adapter.SimpleTouchCallback;
import emrealtunbilek.com.notsepetiapp.data.Notlar;
import emrealtunbilek.com.notsepetiapp.data.NotlarProvider;

public class ActivityMain extends AppCompatActivity{

    static final Uri CONTENT_URI = NotlarProvider.CONTENT_URI;
    static final String SIRALAMA_ONEMSIZ="SIRALAMA ONEMSIZ";
    static final String TAMAMLANMA_ONEMSIZ="TAMAMLANMA ONEMSIZ";

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


        int secilenFiltre=sharedOku();

        switch (secilenFiltre){

            case 0:
                dataGuncelle(SIRALAMA_ONEMSIZ, TAMAMLANMA_ONEMSIZ);
                break;

            case 1:
                sharedYaz(Filtreler.COK_VAKIT_VAR);
                dataGuncelle("notTarih DESC", TAMAMLANMA_ONEMSIZ);
                break;

            case 2:
                sharedYaz(Filtreler.AZ_VAKIT_KALDI);
                dataGuncelle("notTarih ASC", TAMAMLANMA_ONEMSIZ);
                break;

            case 3:
                sharedYaz(Filtreler.TAMAMLANANLAR);
                dataGuncelle(SIRALAMA_ONEMSIZ, "1");
                break;


            case 4:
                sharedYaz(Filtreler.TAMAMLANMAYANLAR);
                dataGuncelle(SIRALAMA_ONEMSIZ, "0");
                break;
        }

        backgrounResminiYerlestir();


    }

    public void dataGuncelle(String siralama, String tamamlanma) {

        tumNotlar.clear();
        tumNotlar=tumNotlariGetir(siralama, tamamlanma);
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

    private void sharedYaz(int secilenFiltre){

        SharedPreferences preferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("filtre", secilenFiltre);
        editor.apply();

    }

    private int sharedOku(){

        SharedPreferences preferences=getPreferences(MODE_PRIVATE);
        int secilenFiltre=preferences.getInt("filtre", 0);
        return secilenFiltre;

    }

    private ArrayList<Notlar> tumNotlariGetir(String siralama, String tamamlanma){

        String siralamaSorgusu=siralama;
        String selection="tamamlandi=?";
        String[] tamamlanmaSorgusu={tamamlanma};

        if(siralama.equals(SIRALAMA_ONEMSIZ)){
            siralamaSorgusu=null;
        }
        if(tamamlanma.equals(TAMAMLANMA_ONEMSIZ)){
            selection=null;
            tamamlanmaSorgusu=null;
        }



        Cursor cursor=getContentResolver().query(CONTENT_URI, new String[]{"id", "notIcerik", "notTarih", "tamamlandi"}, selection, tamamlanmaSorgusu, siralamaSorgusu);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        boolean sonuc=true;

        switch (id){

            case R.id.menu_yeninot:
                notekleDialogGoster();
                break;

            case R.id.menu_cokvakit:
                sharedYaz(Filtreler.COK_VAKIT_VAR);
                dataGuncelle("notTarih DESC", TAMAMLANMA_ONEMSIZ);
                break;

            case R.id.menu_azvakit:
                sharedYaz(Filtreler.AZ_VAKIT_KALDI);
                dataGuncelle("notTarih ASC", TAMAMLANMA_ONEMSIZ);
                break;

            case R.id.menu_tamamlananlar:
                sharedYaz(Filtreler.TAMAMLANANLAR);
                dataGuncelle(SIRALAMA_ONEMSIZ, "1");
                break;


            case R.id.menu_tamamlanmayanlar:
                sharedYaz(Filtreler.TAMAMLANMAYANLAR);
                dataGuncelle(SIRALAMA_ONEMSIZ, "0");
                break;

            default:
                sonuc=false;
                sharedYaz(Filtreler.NOFILTER);
                break;

        }

        return sonuc;
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
           dataGuncelle(SIRALAMA_ONEMSIZ, TAMAMLANMA_ONEMSIZ);
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
