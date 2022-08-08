package red.lisgar.corresponsal.banco;

import static red.lisgar.corresponsal.R.color.bolita_roja;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.all.CrearCuenta;
import red.lisgar.corresponsal.db.DbCliente;
import red.lisgar.corresponsal.entidades.Cliente;

public class ConsultarCliente extends AppCompatActivity {

    //Layout Consultar Cliente
    TextView numeroTarjetaModelo;
    TextView marcaTarjetaModelo;
    TextView txtcvv;
    TextView fechaTarjetaModelo;
    TextView nombreClienteModelo;
    TextView pagoConsultarCliente;
    Button btnConsultarCliente;
    ImageView atras_ConsultarCliente;

    DbCliente dbCliente;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_cliente);
        layoutConsultarCliente();
        toolbarRojoConsultarCliente();
        nombreTarjeta();
        barraRoja();
        btnConsultarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void toolbarRojoConsultarCliente(){

        atras_ConsultarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

    }
    private void layoutConsultarCliente(){
        atras_ConsultarCliente = findViewById(R.id.atras_ConsultarCliente);
        numeroTarjetaModelo = findViewById(R.id.numeroTarjetaModelo);
        marcaTarjetaModelo = findViewById(R.id.marcaTarjetaModelo);
        txtcvv = findViewById(R.id.txtcvv);
        fechaTarjetaModelo = findViewById(R.id.fechaTarjetaModelo);
        nombreClienteModelo = findViewById(R.id.nombreClienteModelo);
        pagoConsultarCliente = findViewById(R.id.pagoConsultarCliente);
        btnConsultarCliente = findViewById(R.id.btnConsultarCliente);

        dbCliente = new DbCliente(this);
        cliente = new Cliente();
        Bundle extras = getIntent().getExtras();
        cliente = dbCliente.mostrarDatosCliente(extras.getString("cedula"));

        nombreClienteModelo.setText(cliente.getNombre_cliente());
        pagoConsultarCliente.setText(cliente.getSaldo_cliente());
        fechaTarjetaModelo.setText(cliente.getFecha_exp_cliente());
        txtcvv.setText(cliente.getCvv_cliente());
        numeroTarjetaModelo.setText(cliente.getCuenta_cliente());
    }
    private void nombreTarjeta(){
        String nombreTar = numeroTarjetaModelo.getText().toString().substring(0,1);
        switch (nombreTar){
            case "3":
                marcaTarjetaModelo.setTextSize(20);
                marcaTarjetaModelo.setText("AMERICAN EXPRESS");
                break;
            case "4":
                marcaTarjetaModelo.setText("VISA");
                break;
            case "5":
                marcaTarjetaModelo.setText("MASTERCARD");
                break;
            case "6":
                marcaTarjetaModelo.setText("UNIONPAY");
                break;
            default:
                Toast.makeText(ConsultarCliente.this, "número de tarjeta no valido", Toast.LENGTH_LONG).show();
        }
    }
    private void salir(){
        Intent intent = new Intent(ConsultarCliente.this, Banco.class);
        startActivity(intent);
    }
    private void barraRoja(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(bolita_roja));
        }
    }
}