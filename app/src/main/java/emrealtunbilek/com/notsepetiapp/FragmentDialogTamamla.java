package emrealtunbilek.com.notsepetiapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Emre Altunbilek on 17.10.2017.
 */

public class FragmentDialogTamamla extends DialogFragment {

    private ImageButton mBtnKapat;
    private Button mBtnTamamlandi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_tamamlandi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnKapat= (ImageButton) view.findViewById(R.id.btn_dialog_kapat2);
        mBtnKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBtnTamamlandi= (Button) view.findViewById(R.id.btn_tamamlandi);

    }
}