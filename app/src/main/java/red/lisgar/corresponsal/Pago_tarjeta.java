package red.lisgar.corresponsal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class Pago_tarjeta extends AppCompatActivity {

    Button btnconfirmarTarjeta;
    Button btncancelarTarjeta;
    TextView numeroTarjeta;
    Spinner spinnerTarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pago_tarjeta);

        notificacioninvisible();

        //Spinner
        final List<String> states = Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12");
        spinnerTarjeta = findViewById(R.id.spinnerTarjeta);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.frontend_item_spinner, states);
        adapter.setDropDownViewResource(R.layout.background_item_spinner);
        spinnerTarjeta.setAdapter(adapter);

        btnconfirmarTarjeta = findViewById(R.id.btnconfirmarTarjeta);

        btnconfirmarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificacionVisible();
            }
        });


        btncancelarTarjeta = findViewById(R.id.btncancelarTarjeta);
        btncancelarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresarTarjeta();
            }
        });




    }
    private void notificacioninvisible(){
        findViewById(R.id.alerta).setVisibility(View.INVISIBLE);
    }

    private void notificacionVisible(){

        numeroTarjeta = findViewById(R.id.numeroTarjeta);
        btncancelarTarjeta.setEnabled(false);
        numeroTarjeta.setInputType(InputType.TYPE_NULL);
        findViewById(R.id.alerta).setVisibility(View.VISIBLE);
    }

    private void ingresarTarjeta(){
        Intent intent = new Intent(this, Pago_tarjeta.class);
        startActivity(intent);
    }
}