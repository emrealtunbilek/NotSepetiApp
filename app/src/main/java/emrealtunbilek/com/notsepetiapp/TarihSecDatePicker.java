package emrealtunbilek.com.notsepetiapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Emre Altunbilek on 21.10.2017.
 */

public class TarihSecDatePicker extends LinearLayout{

    TextView mTextGun;
    TextView mTextAy;
    TextView mTextYil;
    Calendar mCalendar;
    SimpleDateFormat mFormatter;

    public TarihSecDatePicker(Context context) {
        super(context);
        init(context);
    }

    public TarihSecDatePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TarihSecDatePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view=LayoutInflater.from(context).inflate(R.layout.tarih_view, this);
        mCalendar=Calendar.getInstance();
        mFormatter=new SimpleDateFormat("MMM");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextGun= (TextView) this.findViewById(R.id.tv_tarih_gun);
        mTextAy= (TextView) this.findViewById(R.id.tv_tarih_ay);
        mTextYil= (TextView) this.findViewById(R.id.tv_tarih_yil);

        int gun=mCalendar.get(Calendar.DATE);
        int ay=mCalendar.get(Calendar.MONTH);
        int yil=mCalendar.get(Calendar.YEAR);

        guncelle(gun, ay, yil,0,0,0);
    }

    private void guncelle(int gun, int ay, int yil, int saat, int dakika, int saniye){

        mTextGun.setText(""+gun);
        mTextAy.setText(mFormatter.format(mCalendar.getTime()));
        mTextYil.setText(""+yil);

    }

    public long getTime(){
        return  mCalendar.getTimeInMillis();
    }
}
