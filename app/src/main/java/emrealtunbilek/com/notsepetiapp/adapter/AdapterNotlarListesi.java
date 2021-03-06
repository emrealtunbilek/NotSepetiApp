package emrealtunbilek.com.notsepetiapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import emrealtunbilek.com.notsepetiapp.R;
import emrealtunbilek.com.notsepetiapp.data.Notlar;

/**
 * Created by Emre Altunbilek on 12.10.2017.
 */

public class AdapterNotlarListesi extends RecyclerView.Adapter<AdapterNotlarListesi.NotHolder> {

    LayoutInflater mInflater;
    ArrayList<Notlar> tumNotlar;

    public AdapterNotlarListesi(Context context, ArrayList<Notlar> notlar){
        mInflater=LayoutInflater.from(context);
        tumNotlar=notlar;

    }

    @Override
    public NotHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=mInflater.inflate(R.layout.tek_satir_not, parent, false);
        NotHolder holder=new NotHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NotHolder holder, int position) {
        holder.mTextNotIcerik.setText(tumNotlar.get(position).getNotIcerik());
        holder.mTextNotTarih.setText(""+tumNotlar.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return tumNotlar.size();
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
}
