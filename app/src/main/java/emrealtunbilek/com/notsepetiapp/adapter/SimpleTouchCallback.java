package emrealtunbilek.com.notsepetiapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by Emre Altunbilek on 15.10.2017.
 */

public class SimpleTouchCallback extends ItemTouchHelper.Callback {

    SwipeListener mSwipeListener;

    public SimpleTouchCallback(SwipeListener mSwipeListener) {
        this.mSwipeListener = mSwipeListener;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if(viewHolder.getItemViewType()==0){
            return makeMovementFlags(0, ItemTouchHelper.END);
        }else{
            return makeMovementFlags(0,0);
        }

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {


            mSwipeListener.onSwipe(viewHolder.getAdapterPosition());


    }
}