package red.lisgar.corresponsal.corresponsal;

import static android.graphics.Color.parseColor;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.db.DbCliente;
import red.lisgar.corresponsal.db.DbCorresponsal;
import red.lisgar.corresponsal.entidades.Cliente;
import red.lisgar.corresponsal.entidades.Corresponsal;
import red.lisgar.corresponsal.entidades.Transacciones;

public class Transaccion extends AppCompatActivity {

    //Layout Tres
    TextView tituloTres;
    TextInputEditText primerCampoTres;
    TextInputLayout primerCampoTres2;
    TextInputEditText segundoCampoTres;
    TextInputLayout segundoCampoTres2;
    TextInputEditText tercerCampoTresIcon;
    TextInputLayout tercerCampoTresIcon2;
    TextInputLayout tercerCampoTres2;
    TextInputEditText tercerCampoTres;
    Button btncancelarTres;
    Button btnconfirmarTres;
    ImageView atras_tres;

    //Cosas de la clase
    String correoCorresponsal;
    DbCorresponsal dbCorresponsal;
    Corresponsal corresponsal;
    Transacciones transacciones;
    DbCliente dbCliente;
    Cliente cliente;
    TextView nombreCorresponsalTres;
    TextView saldoCorresponsalTres;
    TextView cuentaCorresponsalTres;
    String emisor;
    String receptor;
    String monto;

    //Layout Mensaje
    TextView tituloMensaje;
    ImageView imgMensaje;
    Button btnsalirMensaje;

    //Layout Uno
    ImageView atras_Uno;
    TextView tituloUno;
    EditText primerCampoUno;
    Button btnconfirmarUno;
    Button btncancelarUno;
    View lineaUno;

    //Layout mensajeAlert
    TextView textoAlert;
    Button btncancelarAlert;
    Button btnconfirmarAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tres_txt);
        Bundle extras = getIntent().getExtras();
        correoCorresponsal = extras.getString("correo");
        layoutTres();
        btnconfirmarTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarObligatoriedad()){
                    if (clientesExisten()){
                        if (validarSaldoTransferencia()){
                            setContentView(R.layout.uno_txt);
                            layoutPin();
                            btnconfirmarUno.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (validarPin()){
                                        findViewById(R.id.alertaUno).setVisibility(View.VISIBLE);
                                        layoutMensajeAlert();
                                        toolbaarMensajeAlert();
                                        btnconfirmarAlert.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                realizaTransferencia();
                                                guardarTransaccion();
                                                mensajeOk();
                                            }
                                        });
                                        btncancelarAlert.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mensajeSalir();
                                            }
                                        });
                                    }else{Toast.makeText(Transaccion.this, "PIN incorrecto", Toast.LENGTH_LONG).show();}
                                }
                            });
                            btncancelarUno.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mensajeSalir();
                                }
                            });
                        }else{Toast.makeText(Transaccion.this, "Saldo insuficiente", Toast.LENGTH_LONG).show();}
                    }else{Toast.makeText(Transaccion.this, "Cliente incorrecto", Toast.LENGTH_LONG).show();}
                }else{Toast.makeText(Transaccion.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
            }
        });
        btncancelarTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeSalir();
            }
        });
    }
    private void recibeDatos(){
        receptor =  primerCampoTres.getText().toString().trim();
        emisor =  segundoCampoTres.getText().toString().trim();
        monto =  tercerCampoTresIcon.getText().toString().trim();
    }
    private void layoutTres(){
        tituloTres = findViewById(R.id.tituloTres);
        primerCampoTres = findViewById(R.id.primerCampoTres);
        primerCampoTres2 = findViewById(R.id.primerCampoTres2);
        segundoCampoTres = findViewById(R.id.segundoCampoTres);
        segundoCampoTres2 = findViewById(R.id.segundoCampoTres2);
        tercerCampoTresIcon = findViewById(R.id.tercerCampoTresIcon);
        tercerCampoTresIcon2 = findViewById(R.id.tercerCampoTresIcon2);
        tercerCampoTres2 = findViewById(R.id.tercerCampoTres2);
        tercerCampoTres = findViewById(R.id.tercerCampoTres);
        btncancelarTres = findViewById(R.id.btncancelarTres);
        btnconfirmarTres = findViewById(R.id.btnconfirmarTres);
        nombreCorresponsalTres = findViewById(R.id.nombreCorresponsalTres);
        saldoCorresponsalTres = findViewById(R.id.saldoCorresponsalTres);
        cuentaCorresponsalTres = findViewById(R.id.cuentaCorresponsalTres);
        atras_tres = findViewById(R.id.atras_tres);
        toolbarTres();
    }
    private void toolbarTres(){
        tituloTres.setText("Transferencia");
        primerCampoTres2.setHint("Número de cédula a transferir");
        segundoCampoTres2.setHint("Número de cédula que transfiere");
        tercerCampoTresIcon2.setHint("Monto a transferir");
        tercerCampoTres2.setVisibility(View.INVISIBLE);
        primerCampoTres.setInputType(InputType.TYPE_CLASS_NUMBER);
        segundoCampoTres.setInputType(InputType.TYPE_CLASS_NUMBER);
        tercerCampoTresIcon.setInputType(InputType.TYPE_CLASS_NUMBER);
        tercerCampoTresIcon2.setStartIconTintList(ColorStateList.valueOf(parseColor("#3399FF")));
        atras_tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
        dbCorresponsal = new DbCorresponsal(this);
        corresponsal = new Corresponsal();
        corresponsal = dbCorresponsal.mostrarDatosCorresponsalHome(correoCorresponsal);
        nombreCorresponsalTres.setText(corresponsal.getNombre_corresponsal());
        saldoCorresponsalTres.setText(corresponsal.getSaldo_corresponsal());
        cuentaCorresponsalTres.setText(corresponsal.getCuenta_corresponsal());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            saldoCorresponsalTres.setCompoundDrawableTintList(ColorStateList.valueOf(parseColor("#3399FF")));
            cuentaCorresponsalTres.setCompoundDrawableTintList(ColorStateList.valueOf(parseColor("#3399FF")));
        }
    }
    private void layoutPin(){
        atras_Uno = findViewById(R.id.atras_Uno);
        lineaUno = findViewById(R.id.lineaUno);
        tituloUno = findViewById(R.id.tituloUno);
        primerCampoUno = findViewById(R.id.primerCampoUno);
        btncancelarUno = findViewById(R.id.btncancelarUno);
        btnconfirmarUno = findViewById(R.id.btnconfirmarUno);


        primerCampoUno.setHint("PIN");
        tituloUno.setText("Confirmar PIN");
    }
    private void layoutMensajeAlert(){
        textoAlert = findViewById(R.id.textoAlert);
        btncancelarAlert = findViewById(R.id.btncancelarAlert);
        btnconfirmarAlert = findViewById(R.id.btnconfirmarAlert);
    }
    private void toolbaarMensajeAlert(){
        primerCampoUno.setEnabled(false);
        btncancelarUno.setEnabled(false);
        btnconfirmarUno.setEnabled(false);
        atras_Uno.setEnabled(false);
        textoAlert.setText("La transacción tiene un costo de 1.000 pesos que se descontarán directamente de su cuenta WPOSS. ¿Desea continuar?");
    }
    private boolean validarObligatoriedad(){
        recibeDatos();
        if (!TextUtils.isEmpty(receptor) && !TextUtils.isEmpty(emisor) && !TextUtils.isEmpty(monto)){
            return true;
        }else{
            return false;
        }
    }
    private boolean validarPin(){
        recibeDatos();
        cliente = dbCliente.mostrarDatosCliente(emisor);
        String pinCleinte = cliente.getPin_cliente();
        String pinLayout = primerCampoUno.getText().toString().trim();
        if (pinCleinte.equals(pinLayout)){
            return true;
        }else return false;
    }
    private boolean clientesExisten(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        if (dbCliente.validarCedulaCliente(receptor)){
            if (dbCliente.validarCedulaCliente(emisor)){
                return !(receptor.equals(emisor));
            }else return false;
        }else return false;
    }
    private boolean validarSaldoTransferencia(){
        recibeDatos();
        cliente = new Cliente();
        cliente = dbCliente.mostrarDatosCliente(emisor);
        long saldoCliente = Long.parseLong(cliente.getSaldo_cliente());
        long monto2 = Long.parseLong(monto);
        if (saldoCliente >= monto2){
            return true;
        }else return false;
    }
    private boolean realizaTransferencia(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        if (dbCliente.sumarTransferenciaCorresponsal(correoCorresponsal, 1000)){
            int monto2 = Integer.parseInt(monto) +1000;
            if (dbCliente.restarTransferenciaCliente(emisor, String.valueOf(monto2))){
                int monto3 = Integer.parseInt(monto) -1000;
                return dbCliente.sumarTransferenciaCliente(receptor, String.valueOf(monto3));
            }else return false;
        }else return false;
    }
    private void guardarTransaccion(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        transacciones = new Transacciones();
        int monto2 = Integer.parseInt(monto) - 1000;
        transacciones.setId_cliente(Integer.parseInt(receptor));
        transacciones.setId_emisor(Integer.parseInt(emisor));
        transacciones.setMonto_transaccion(String.valueOf(monto2));
        transacciones.setId_corresponsal(correoCorresponsal);
        transacciones.setFecha_transaccion(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        transacciones.setTipo_transaccion("TRANSFERENCIA");

        dbCliente.insertarTransaccion(transacciones);

    }
    private void salir(){
        Intent intent = new Intent(Transaccion.this, CorresponsalHome.class);
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
        tituloMensaje.setText("Se canceló la transferencia");
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
        tituloMensaje.setText("Se realizó la transferencia");
        imgMensaje.setImageResource(R.drawable.bien);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}