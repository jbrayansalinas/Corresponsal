package red.lisgar.corresponsal.corresponsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.all.ActualizarCorresponsal;
import red.lisgar.corresponsal.all.CrearCuenta;

public class CorresponsalHome extends AppCompatActivity {

    ImageView menuCorresponsal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corresponsal);
        menuCorresponsal = findViewById(R.id.menuCorresponsal);
        menuCorresponsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCuenta();
            }
        });
    }

    private void crearCuenta(){
        Intent intent = new Intent(this, CrearCuenta.class);
        intent.putExtra("vista", "corresponsal");
        startActivity(intent);
    }
}