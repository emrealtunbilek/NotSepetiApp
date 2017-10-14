package emrealtunbilek.com.notsepetiapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import emrealtunbilek.com.notsepetiapp.R;
import emrealtunbilek.com.notsepetiapp.data.Notlar;

/**
 * Created by Emre Altunbilek on 12.10.2017.
 */

public class AdapterNotlarListesi extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    public static final int FOOTER = 1;
    LayoutInflater mInflater;
    ArrayList<Notlar> tumNotlar;
    private AddListener mAddListener;

    public void setAddListener(AddListener listener){
        mAddListener=listener;
    }

    public AdapterNotlarListesi(Context context, ArrayList<Notlar> notlar){
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
        return tumNotlar.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(tumNotlar == null || tumNotlar.size()==0){

            return ITEM;

        }else if(position < tumNotlar.size()){
            return ITEM;
        }else return FOOTER;
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

    public class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Button mBtnFooterEkle;

        public FooterHolder(View itemView) {
            super(itemView);

            mBtnFooterEkle= (Button) itemView.findViewById(R.id.btn_footer);
            mBtnFooterEkle.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mAddListener.ekleDialogGoster();
        }
    }
}
