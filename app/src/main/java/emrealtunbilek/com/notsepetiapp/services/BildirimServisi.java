package emrealtunbilek.com.notsepetiapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import emrealtunbilek.com.notsepetiapp.data.Notlar;
import emrealtunbilek.com.notsepetiapp.data.NotlarProvider;

public class BildirimServisi extends IntentService {

    public static final String TAG=Thread.currentThread().getName();
    public static final Uri CONTENT_URI = NotlarProvider.CONTENT_URI;
    ArrayList<Notlar> tamamlanmayanNotlar=new ArrayList<>();

    public BildirimServisi() {
        super("BildirimServisi");
        Log.d(TAG, "BildirimServisi: constructor");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "BildirimServisi: onhandleıntent");
        tamamlanmayanNotlar=tamamlanmayanNotlariGetir();

        for(Notlar geciciNot : tamamlanmayanNotlar){


            if(bildirimGerekli(geciciNot.getNotEklenmetarihi(), geciciNot.getNotTarih())){

                Log.e(TAG, "Bildirim Gerekli");

            }

        }

    }

    private boolean bildirimGerekli(long notEklenmetarihi, long notTarih) {

    long now=System.currentTimeMillis();

        if(now > notTarih){
            return false;
        }else {
            long yuzde90 =(long) 0.9 * (notTarih - notEklenmetarihi);
            return (now > (notEklenmetarihi + yuzde90)) ? true : false;
        }
    }

    private ArrayList<Notlar> tamamlanmayanNotlariGetir(){

        ArrayList<Notlar> tamamlanmayanlar=new ArrayList<>();

        Cursor cursor=getContentResolver().query(CONTENT_URI, new String[]{"id", "notIcerik", "notEklenmeTarih", "notTarih", "tamamlandi"}, "tamamlandi=?", new String[]{"0"}, null);


        if(cursor != null){

            while(cursor.moveToNext()){
                Notlar geciciNot=new Notlar();
                geciciNot.setId(cursor.getInt(cursor.getColumnIndex("id")));
                geciciNot.setNotIcerik(cursor.getString(cursor.getColumnIndex("notIcerik")));
                geciciNot.setNotTarih(cursor.getLong(cursor.getColumnIndex("notTarih")));
                geciciNot.setTamamlandi(cursor.getInt(cursor.getColumnIndex("tamamlandi")));
                tamamlanmayanlar.add(geciciNot);

            }
        }


        return  tamamlanmayanlar;

    }

}