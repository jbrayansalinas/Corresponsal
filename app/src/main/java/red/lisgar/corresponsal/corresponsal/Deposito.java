package red.lisgar.corresponsal.corresponsal;

import static android.graphics.Color.parseColor;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.db.DbCliente;
import red.lisgar.corresponsal.db.DbCorresponsal;
import red.lisgar.corresponsal.entidades.Corresponsal;
import red.lisgar.corresponsal.entidades.Transacciones;

public class Deposito extends AppCompatActivity {

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
    TextView nombreCorresponsalTres;
    TextView saldoCorresponsalTres;
    TextView cuentaCorresponsalTres;
    String emisor;
    String receptor;
    String monto;

    //Layout confirmar deposito
    TextView nombreConfirmacionDeposito;
    TextView otronombreConfirmacionDeposito;
    TextView pagoDepositoConfirmacion;
    Button btnconfirmarDepositoConfirmacion;
    Button btncancelarDepositoConfirmacion;

    //Layout Mensaje
    TextView tituloMensaje;
    ImageView imgMensaje;
    Button btnsalirMensaje;

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
                if (validarObligatoriedad()) {
                    if (receptorExiste()) {
                            setContentView(R.layout.confirmacion_deposito);
                            layoutConfirmarDeposito();
                            btnconfirmarDepositoConfirmacion.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    realizaRetiro();
                                    guardarTransaccion();
                                    mensajeOk();
                                }
                            });
                            btncancelarDepositoConfirmacion.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mensajeSalir();
                                }
                            });
                    }else{Toast.makeText(Deposito.this, "No existe.", Toast.LENGTH_LONG).show();}
                }else{Toast.makeText(Deposito.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
            }
        });
        btncancelarTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeSalir2();
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
        tituloTres.setText("Depósito");
        primerCampoTres2.setHint("Número de cédula a depositar");
        segundoCampoTres2.setHint("Número de cédula que deposita");
        tercerCampoTresIcon2.setHint("Monto a depositar");
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
    private void layoutConfirmarDeposito(){
        recibeDatos();
        nombreConfirmacionDeposito = findViewById(R.id.nombreConfirmacionDeposito);
        otronombreConfirmacionDeposito = findViewById(R.id.otronombreConfirmacionDeposito);
        pagoDepositoConfirmacion = findViewById(R.id.pagoDepositoConfirmacion);
        btnconfirmarDepositoConfirmacion = findViewById(R.id.btnconfirmarDepositoConfirmacion);
        btncancelarDepositoConfirmacion = findViewById(R.id.btncancelarDepositoConfirmacion);

        nombreConfirmacionDeposito.setText(emisor);
        otronombreConfirmacionDeposito.setText(receptor);
        pagoDepositoConfirmacion.setText(monto);
    }
    private boolean validarObligatoriedad(){
        recibeDatos();
        if (!TextUtils.isEmpty(receptor) && !TextUtils.isEmpty(emisor) && !TextUtils.isEmpty(monto)){
            return true;
        }else{
            return false;
        }
    }
    private boolean receptorExiste(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        return dbCliente.validarCedulaCliente(receptor);
    }
    private boolean realizaRetiro(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        if (dbCliente.sumarTransferenciaCorresponsal(correoCorresponsal, 1000)){
            int monto2 = Integer.parseInt(monto) +1000;
            return dbCliente.restarTransferenciaCliente(receptor, String.valueOf(monto2));
        }else return false;
    }
    private void guardarTransaccion(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        transacciones = new Transacciones();
        int monto2 = Integer.parseInt(monto) - 1000;
        transacciones.setId_cliente(Integer.parseInt(receptor));
        transacciones.setMonto_transaccion(String.valueOf(monto2));
        transacciones.setId_corresponsal(correoCorresponsal);
        transacciones.setFecha_transaccion(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        transacciones.setTipo_transaccion("DEPOSITO");

        dbCliente.insertarTransaccion(transacciones);

    }
    private void salir(){
        Intent intent = new Intent(Deposito.this, CorresponsalHome.class);
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
        tituloMensaje.setText("Se canceló el depósito");
        imgMensaje.setImageResource(R.drawable.error);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void mensajeSalir2(){
        mensaje();
        tituloMensaje.setText("Se canceló el depósito");
        imgMensaje.setImageResource(R.drawable.cancelar);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void mensajeOk(){
        mensaje();
        tituloMensaje.setText("Se realizó el depósito");
        imgMensaje.setImageResource(R.drawable.bien);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
}