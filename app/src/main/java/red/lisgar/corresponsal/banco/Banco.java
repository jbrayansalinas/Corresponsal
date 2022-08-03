package red.lisgar.corresponsal.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import red.lisgar.corresponsal.R;

public class Banco extends AppCompatActivity {

    TextView nombreCorresponsal;
    ImageView menuCorresponsal;
    RecyclerView recyclerCorresponsal;
    String nomCorresponsal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corresponsal);
        layoutBanco();
        toolbar();
    }

    private void layoutBanco(){
        nombreCorresponsal = findViewById(R.id.nombreCorresponsal);
        menuCorresponsal = findViewById(R.id.menuCorresponsal);
        recyclerCorresponsal = findViewById(R.id.recyclerCorresponsal);
    }

    private void toolbar(){

        findViewById(R.id.bolitas_azulesCorresponsal).setVisibility(View.INVISIBLE);
        nombreCorresponsal.setText("Mi Banco");
        nombreCorresponsal.setTextColor(getResources().getColor(R.color.bolita_roja));
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}