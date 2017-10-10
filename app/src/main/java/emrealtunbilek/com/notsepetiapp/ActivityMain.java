package emrealtunbilek.com.notsepetiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ActivityMain extends AppCompatActivity {

    Toolbar mToolbar;
    Button mButtonYeninot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mButtonYeninot= (Button) findViewById(R.id.btn_sepete_not_ekle);
        mButtonYeninot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notekleDialogGoster();
            }
        });


        backgrounResminiYerlestir();


    }

    private void notekleDialogGoster() {
        FragmentDialogYeniNot dialog=new FragmentDialogYeniNot();
        dialog.show(getSupportFragmentManager(), "DialogYeniNot");

    }

    private void backgrounResminiYerlestir() {
        ImageView background= (ImageView) findViewById(R.id.iv_background);
        Glide.with(this)
                .load(R.drawable.background)
                .apply(new RequestOptions().centerCrop())
                .into(background);

    }
}
