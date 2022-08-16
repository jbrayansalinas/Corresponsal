package red.lisgar.corresponsal.corresponsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.banco.BuscadorConsultarCliente;
import red.lisgar.corresponsal.db.DbCliente;
import red.lisgar.corresponsal.entidades.Cliente;

public class ConsultarSaldo extends AppCompatActivity {

    //Layout Uno
    ImageView atras_ConsultarSaldo;
    TextView tituloConsultarSaldo;
    EditText primerCampoConsultarSaldo;
    Button btnconfirmarConsultarSaldo;
    Button btncancelarConsultarSaldo;

    //Layout Mensaje
    TextView tituloMensaje;
    ImageView imgMensaje;
    Button btnsalirMensaje;

    //Layout datos_Cliente
    TextView tituloDtCliente;
    View lineaDtCliente;
    TextView primerCampoDtCliente;
    TextView segundoCampoDtCliente;
    TextView tercerCampoDtCliente;
    Button btncancelarDtCliente;
    Button btnconfirmarDtCliente;
    Button btnAceptarDtCliente;
    RelativeLayout relativeDatosCliente;

    //Layout mensajeAlert
    TextView textoAlert;
    Button btncancelarAlert;
    Button btnconfirmarAlert;

    //Cosas de la clase
    String correoCorresponsal;
    DbCliente dbCliente;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_saldo);
        Bundle extras = getIntent().getExtras();
        correoCorresponsal = extras.getString("correo");
        findViewById(R.id.alertaConsultaSal).setVisibility(View.INVISIBLE);
        layoutConsultarSaldo();
        btnconfirmarConsultarSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarObligatoriedad()) {
                    if (validarCedulaCliente()) {
                        findViewById(R.id.alertaConsultaSal).setVisibility(View.VISIBLE);
                        layoutMensajeAlert();
                        toolbaarMensajeAlert();
                        btnconfirmarAlert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mensajeOk();
                                btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        setContentView(R.layout.datos_cliente);
                                        layoutDtCLiente();
                                        toolbarAzulDtCliente();
                                        realizaRetiro();
                                        btnAceptarDtCliente.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                salir();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                        btncancelarAlert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mensajeSalir();
                            }
                        });
                    }else{Toast.makeText(ConsultarSaldo.this, "No existe", Toast.LENGTH_LONG).show();}
                }else{Toast.makeText(ConsultarSaldo.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
    }
        });
        btncancelarConsultarSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeSalir();
            }
        });
    }
    private void layoutConsultarSaldo(){
        atras_ConsultarSaldo = findViewById(R.id.atras_ConsultarSaldo);
        tituloConsultarSaldo = findViewById(R.id.tituloConsultarSaldo);
        primerCampoConsultarSaldo = findViewById(R.id.primerCampoConsultarSaldo);
        btncancelarConsultarSaldo = findViewById(R.id.btncancelarConsultarSaldo);
        btnconfirmarConsultarSaldo = findViewById(R.id.btnconfirmarConsultarSaldo);

        primerCampoConsultarSaldo.setHint("Consulta de saldo");
        tituloConsultarSaldo.setText("Número de cédula a consultar");

        atras_ConsultarSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void layoutDtCLiente(){
        tituloDtCliente = findViewById(R.id.tituloDtCliente);
        lineaDtCliente = findViewById(R.id.lineaDtCliente);
        primerCampoDtCliente = findViewById(R.id.primerCampoDtCliente);
        segundoCampoDtCliente = findViewById(R.id.segundoCampoDtCliente);
        tercerCampoDtCliente = findViewById(R.id.tercerCampoDtCliente);
        btncancelarDtCliente = findViewById(R.id.btncancelarDtCliente);
        btnconfirmarDtCliente = findViewById(R.id.btnconfirmarDtCliente);
        btnAceptarDtCliente = findViewById(R.id.btnAceptarDtCliente);
        relativeDatosCliente = findViewById(R.id.relativeDatosCliente);
        btncancelarDtCliente.setVisibility(View.INVISIBLE);
        btnconfirmarDtCliente.setVisibility(View.INVISIBLE);
        btnAceptarDtCliente.setText("Aceptar");
    }
    private void layoutMensajeAlert(){
        textoAlert = findViewById(R.id.textoAlert);
        btncancelarAlert = findViewById(R.id.btncancelarAlert);
        btnconfirmarAlert = findViewById(R.id.btnconfirmarAlert);
    }
    private void toolbaarMensajeAlert(){
        layoutConsultarSaldo();
        primerCampoConsultarSaldo.setEnabled(false);
        btncancelarConsultarSaldo.setEnabled(false);
        btnconfirmarConsultarSaldo.setEnabled(false);
        atras_ConsultarSaldo.setEnabled(false);

        textoAlert.setText("Consultar tiene un costo de 1.000 pesos que se descontarán directamente de su cuenta WPOSS. ¿Desea continuar?");
    }
    private void toolbarAzulDtCliente(){
        dbCliente = new DbCliente(this);
        cliente = new Cliente();
        cliente = dbCliente.mostrarDatosCliente(primerCampoConsultarSaldo.getText().toString().trim());
        primerCampoDtCliente.setText(cliente.getNombre_cliente());
        segundoCampoDtCliente.setText(primerCampoConsultarSaldo.getText().toString().trim());
        int saldo = (Integer.parseInt(cliente.getSaldo_cliente()) - 1000);
        tercerCampoDtCliente.setText(String.valueOf(saldo));
        tituloDtCliente.setText("Consulta de saldo");
    }
    private boolean validarObligatoriedad(){
        if (!TextUtils.isEmpty(primerCampoConsultarSaldo.getText().toString().trim())){
            return true;
        }else{
            return false;
        }
    }
    private boolean realizaRetiro(){
        dbCliente = new DbCliente(this);
        cliente = new Cliente();
        cliente = dbCliente.mostrarDatosCliente(primerCampoConsultarSaldo.getText().toString().trim());
        if (dbCliente.sumarTransferenciaCorresponsal(correoCorresponsal, 1000)){
            return dbCliente.restarTransferenciaCliente(primerCampoConsultarSaldo.getText().toString().trim(), String.valueOf(1000));
        }else return false;
    }
    private boolean validarCedulaCliente(){
        dbCliente = new DbCliente(this);
        return dbCliente.validarCedulaCliente(primerCampoConsultarSaldo.getText().toString().trim());
    }
    private void salir(){
        Intent intent = new Intent(ConsultarSaldo.this, CorresponsalHome.class);
        intent.putExtra("cuenta", correoCorresponsal);
        startActivity(intent);
    }
    private void mensaje(){
        setContentView(R.layout.mensaje);
        tituloMensaje = findViewById(R.id.tituloMensaje);
        imgMensaje = findViewById(R.id.imgMensaje);
        btnsalirMensaje = findViewById(R.id.btnsalirMensaje);
    }
    private void mensajeSalir(){
        mensaje();
        tituloMensaje.setText("Se canceló la consulta");
        imgMensaje.setImageResource(R.drawable.error);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void mensajeOk(){
        mensaje();
        tituloMensaje.setText("Se realizó la consulta");
        imgMensaje.setImageResource(R.drawable.bien);
        btnsalirMensaje.setText("Aceptar");
    }
}