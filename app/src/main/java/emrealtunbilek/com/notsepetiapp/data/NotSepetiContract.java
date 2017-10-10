package emrealtunbilek.com.notsepetiapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Emre Altunbilek on 11.10.2017.
 */

public class NotSepetiContract {

    public static final String CONTENT_AUTHORITY="emrealtunbilek.com.notsepetiapp.notsepetiprovider";
    public static final String PATH_NOTLAR="notlar";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://" +CONTENT_AUTHORITY);

    public static final class NotlarEntry implements BaseColumns{

        public static final String TABLE_NAME="notlar";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NOT_ICERIK = "notIcerik";
        public static final String COLUMN_NOT_TARIH = "notTarih";
        public static final String COLUMN_YAPILDI = "yapildi";


    }
}
