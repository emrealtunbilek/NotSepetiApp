package emrealtunbilek.com.notsepetiapp;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import emrealtunbilek.com.notsepetiapp.data.NotlarProvider;

/**
 * Created by Emre Altunbilek on 10.10.2017.
 */

public class FragmentDialogYeniNot extends DialogFragment {

    static final Uri CONTENT_URI = NotlarProvider.CONTENT_URI;

    private ImageButton mBtnKapat;
    private EditText mNotIcerik;
    private DatePicker mNotTarih;
    private Button mBtnNotEkle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_yeni_not, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnKapat = (ImageButton) view.findViewById(R.id.btn_dialog_kapat);
        mNotIcerik = (EditText) view.findViewById(R.id.et_not);
        mNotTarih = (DatePicker) view.findViewById(R.id.dp_tarih);
        mBtnNotEkle = (Button) view.findViewById(R.id.btn_not_ekle);

        mBtnKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBtnNotEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, mNotTarih.getDayOfMonth());
                calendar.set(Calendar.MONTH, mNotTarih.getMonth());
                calendar.set(Calendar.YEAR, mNotTarih.getYear());

                calendar.set(Calendar.HOUR,0);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);



                ContentValues values = new ContentValues();
                values.put("notIcerik", mNotIcerik.getText().toString());
                values.put("notTarih", calendar.getTimeInMillis());
                Uri uri = getActivity().getContentResolver().insert(CONTENT_URI, values);

                Toast.makeText(getContext(), "Tarih:"+calendar.getTimeInMillis(), Toast.LENGTH_LONG).show();

                EventBus.getDefault().post(new DataEvent.DataGuncelleMethoduTetikle(1));
            }
        });
    }
}
