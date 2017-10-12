package emrealtunbilek.com.notsepetiapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import emrealtunbilek.com.notsepetiapp.R;

/**
 * Created by Emre Altunbilek on 12.10.2017.
 */

public class AdapterNotlarListesi extends RecyclerView.Adapter<AdapterNotlarListesi.NotHolder> {

    LayoutInflater mInflater;

    public AdapterNotlarListesi(Context context){
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public NotHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=mInflater.inflate(R.layout.tek_satir_not, parent, false);
        NotHolder holder=new NotHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NotHolder holder, int position) {
        holder.mTextNotIcerik.setText("emre");
        holder.mTextNotTarih.setText("altunbilek");
    }

    @Override
    public int getItemCount() {
        return 1;
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
