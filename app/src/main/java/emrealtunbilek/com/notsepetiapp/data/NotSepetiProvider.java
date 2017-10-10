package emrealtunbilek.com.notsepetiapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Emre Altunbilek on 11.10.2017.
 */

public class NotSepetiProvider extends ContentProvider {

    private static final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
    private static final int URICODE_NOTLAR=1;

    static {
        matcher.addURI(NotSepetiContract.CONTENT_AUTHORITY, NotSepetiContract.PATH_NOTLAR, URICODE_NOTLAR);
    }

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    @Override
    public boolean onCreate() {
        helper=new DatabaseHelper(getContext());
        db=helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (matcher.match(uri)){
            case URICODE_NOTLAR:
            cursor=db.query(NotSepetiContract.NotlarEntry.TABLE_NAME, projection,selection,selectionArgs,null,null,sortOrder);
            break;

            default:
                throw new IllegalArgumentException("BILINMEYEN URI" +uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        switch (matcher.match(uri)){
            case URICODE_NOTLAR:
                return kayitEkle(uri, values, NotSepetiContract.NotlarEntry.TABLE_NAME);

            default:
                throw new IllegalArgumentException("BILINMEYEN URI" +uri);
        }


        return null;
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (matcher.match(uri)){
            case URICODE_NOTLAR:

                break;
            default:
                throw new IllegalArgumentException("BILINMEYEN URI" +uri);
        }


        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (matcher.match(uri)){
            case URICODE_NOTLAR:

                break;
            default:
                throw new IllegalArgumentException("BILINMEYEN URI" +uri);
        }


        return 0;
    }

    private Uri kayitEkle(Uri uri, ContentValues values, String tableName) {

        long id=db.insert(tableName, null, values);
        if(id == -1){
            Log.e("EMRE", "INSERT HATA: "+ uri );
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }
}
