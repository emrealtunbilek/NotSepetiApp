package emrealtunbilek.com.notsepetiapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import emrealtunbilek.com.notsepetiapp.data.NotSepetiContract.*;

/**
 * Created by Emre Altunbilek on 11.10.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="notsepeti.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NOTLAR_CREATE=
            "CREATE TABLE " + NotlarEntry.TABLE_NAME + " ("+
                    NotlarEntry._ID + " INTEGER PRIMARY KEY, "+
                    NotlarEntry.COLUMN_NOT_ICERIK + " TEXT, " +
                    NotlarEntry.COLUMN_NOT_TARIH + " TEXT, "+
                    NotlarEntry.COLUMN_YAPILDI + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_NOTLAR_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NotlarEntry.TABLE_NAME);
    }
}
