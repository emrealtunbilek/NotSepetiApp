package emrealtunbilek.com.notsepetiapp.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import emrealtunbilek.com.notsepetiapp.DataEvent;
import emrealtunbilek.com.notsepetiapp.R;
import emrealtunbilek.com.notsepetiapp.data.Notlar;
import emrealtunbilek.com.notsepetiapp.data.NotlarProvider;

/**
 * Created by Emre Altunbilek on 12.10.2017.
 */

public class AdapterNotlarListesi extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    public static final int FOOTER = 1;
    static final Uri CONTENT_URI = NotlarProvider.CONTENT_URI;

    LayoutInflater mInflater;
    ArrayList<Notlar> tumNotlar;
    private ContentResolver resolver;



    public AdapterNotlarListesi(Context context, ArrayList<Notlar> notlar){
        resolver=context.getContentResolver();
        mInflater=LayoutInflater.from(context);
        tumNotlar=notlar;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == ITEM){
            View view=mInflater.inflate(R.layout.tek_satir_not, parent, false);
            NotHolder holder=new NotHolder(view);
            return  holder;
        }else if (viewType == FOOTER){

            View view=mInflater.inflate(R.layout.footer, parent, false);
            FooterHolder footerHolder=new FooterHolder(view);

            return footerHolder;

        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NotHolder){
            NotHolder notHolder= (NotHolder) holder;
            notHolder.mTextNotIcerik.setText(tumNotlar.get(position).getNotIcerik());
            notHolder.mTextNotTarih.setText(""+tumNotlar.get(position).getId());
        }


    }

    @Override
    public int getItemCount() {
        if(tumNotlar == null || tumNotlar.isEmpty()){
            return 0;
        }else {
            return tumNotlar.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(tumNotlar == null || tumNotlar.size()==0){

            return ITEM;

        }else if(position < tumNotlar.size()){
            return ITEM;
        }else return FOOTER;
    }


    public void update(ArrayList<Notlar> tumNotlar) {
        this.tumNotlar=tumNotlar;
        notifyDataSetChanged();
    }

   @Subscribe
    public void onSwipe(DataEvent.KaydirilanNotunPozisyonu event) {
       int position=event.getPosition();
        if(position < tumNotlar.size()){
            Notlar silinecekNot=tumNotlar.get(position);
            String silinecekNotID=String.valueOf(silinecekNot.getId());
            Log.e("SILINECEK NOT ID:", ""+silinecekNotID);
            int etkilenenSatirSayisi=resolver.delete(CONTENT_URI,"id=?", new String[]{silinecekNotID});
            if(etkilenenSatirSayisi != 0){
                tumNotlar.remove(silinecekNot);
                notifyDataSetChanged();
                Log.e("SILINEN NOT ID : ", silinecekNotID);
            }

        }

    }


    public static class NotHolder extends RecyclerView.ViewHolder{

        TextView mTextNotIcerik;
        TextView mTextNotTarih;

        public NotHolder(View itemView) {
            super(itemView);

            mTextNotIcerik= (TextView) itemView.findViewById(R.id.tv_not_icerik);
            mTextNotTarih= (TextView) itemView.findViewById(R.id.tv_not_tarih);

        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {

        Button mBtnFooterEkle;

        public FooterHolder(View itemView) {
            super(itemView);

            mBtnFooterEkle= (Button) itemView.findViewById(R.id.btn_footer);
            mBtnFooterEkle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new DataEvent.NotEkleDialogGoster(1));
                }
            });

        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }
}
