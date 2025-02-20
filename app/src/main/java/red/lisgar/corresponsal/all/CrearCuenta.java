package red.lisgar.corresponsal.all;

import static android.graphics.Color.parseColor;

import static red.lisgar.corresponsal.R.color.bolita_roja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.banco.Banco;
import red.lisgar.corresponsal.banco.ConsultarCliente;
import red.lisgar.corresponsal.banco.RegistrarCorresponsal;
import red.lisgar.corresponsal.corresponsal.CorresponsalHome;
import red.lisgar.corresponsal.db.DbCliente;
import red.lisgar.corresponsal.db.DbCorresponsal;
import red.lisgar.corresponsal.entidades.Cliente;
import red.lisgar.corresponsal.entidades.Corresponsal;

public class CrearCuenta extends AppCompatActivity {

    //Layout Cuatro
    TextView tituloTres;
    TextInputEditText primerCampoTres;
    TextInputLayout primerCampoTres2;
    TextInputEditText segundoCampoTres;
    TextInputLayout segundoCampoTres2;
    TextInputEditText tercerCampoTresIcon;
    TextInputLayout tercerCampoTresIcon2;
    TextInputEditText tercerCampoTres;
    TextInputLayout tercerCampoTres2;
    Button btncancelarTres;
    Button btnconfirmarTres;
    ImageView atras_tres;
    View lineaTres;

    //Layout Uno
    ImageView atras_Uno;
    TextView tituloUno;
    EditText primerCampoUno;
    Button btnconfirmarUno;
    Button btncancelarUno;
    View lineaUno;

    //Cosas de la clase
    DbCliente dbCliente;
    DbCorresponsal dbCorresponsal;
    Cliente cliente;
    String nombreCliente;
    String cedula;
    String saldoCliente;
    String pin;
    String pin2;
    String cvv;
    String fechaExp;
    String cuentaCliente;
    SharePreference sharePreference;
    String correoCorresponsal;

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

    //Layout Mensaje
    TextView tituloMensaje;
    ImageView imgMensaje;
    Button btnsalirMensaje;

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
        if (extras.getString("vista").equals("banco")) {
            setContentView(R.layout.tres_txt);
            layoutCrearCuenta();
            toolbarRojo();
            barraRoja();

            btnconfirmarTres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validarObligatoriedad()){
                        if (!validarNombreCliente()){
                            if (!validarCedulaCliente()){
                                if (!validarCedulaNit()) {
                                    if (validarSaldo()) {
                                        setContentView(R.layout.uno_txt);
                                        layoutPin();
                                        toolbarRojoUno();
                                        tituloUno.setText(R.string.ingresePin);
                                        btnconfirmarUno.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (validarObligatoriedadPin()) {
                                                    setContentView(R.layout.uno_txt);
                                                    layoutPin();
                                                    toolbarRojoUno();
                                                    tituloUno.setText(R.string.ingreseNuevamentePin);
                                                    btnconfirmarUno.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (validarObligatoriedadPin2()) {
                                                                if (confirmarPin()) {
                                                                    findViewById(R.id.alertaUno).setVisibility(View.VISIBLE);
                                                                    layoutMensajeAlert();
                                                                    toolbaarMensajeAlert();
                                                                    btnconfirmarAlert.setBackground(getDrawable(R.drawable.btn_rojo_bordes));
                                                                    btncancelarAlert.setBackground(getDrawable(R.drawable.btn_transparente_bordes_rojo));
                                                                    btncancelarAlert.setTextColor(parseColor("#ff5f58"));
                                                                    btnconfirmarAlert.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            setContentView(R.layout.datos_cliente);
                                                                            layoutDtCLiente();
                                                                            toolbarRojoDtCLiente();
                                                                            btnconfirmarDtCliente.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    if (enviarDatosCliente()) {
                                                                                        restarComision();
                                                                                        irConsultarCliente();
                                                                                    } else {Toast.makeText(CrearCuenta.this, "Error al registrarse", Toast.LENGTH_LONG).show();}
                                                                                }
                                                                            });
                                                                            btncancelarDtCliente.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    mensajeSalir();
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
                                                                } else {Toast.makeText(CrearCuenta.this, "Pin incorrecto", Toast.LENGTH_LONG).show();}
                                                            } else {Toast.makeText(CrearCuenta.this, "Ingrese un pin", Toast.LENGTH_LONG).show();}
                                                        }
                                                    });
                                                    btncancelarUno.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            mensajeSalir();
                                                        }
                                                    });
                                                } else {Toast.makeText(CrearCuenta.this, "Ingrese un pin", Toast.LENGTH_LONG).show();}
                                            }
                                        });
                                        btncancelarUno.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mensajeSalir();
                                            }
                                        });
                                    } else {Toast.makeText(CrearCuenta.this, "El saldo debe ser mayor a 10.000", Toast.LENGTH_LONG).show();}
                                }else {Toast.makeText(CrearCuenta.this, "Cédula no disponible", Toast.LENGTH_LONG).show();}
                            }else {Toast.makeText(CrearCuenta.this, "Cédula no disponible", Toast.LENGTH_LONG).show();}
                        }else {Toast.makeText(CrearCuenta.this, "Nombre no disponible", Toast.LENGTH_LONG).show();}
                    }else{Toast.makeText(CrearCuenta.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
                }
            });
            btncancelarTres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mensajeSalir();
                }
            });

        }
        else if (extras.getString("vista").equals("corresponsal")){
            layoutCrearCuenta();
            toolbarAzul();
            btnconfirmarTres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validarObligatoriedad()){
                        if (!validarNombreCliente()){
                            if (!validarCedulaCliente()){
                                if (!validarCedulaNit()) {
                                    if (validarSaldo()) {
                                            setContentView(R.layout.uno_txt);
                                            toolbarUnoAzul();
                                            tituloUno.setText("Ingrese su Pin");
                                            btnconfirmarUno.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (validarObligatoriedadPin()) {
                                                        setContentView(R.layout.uno_txt);
                                                        toolbarUnoAzul();
                                                        tituloUno.setText("Ingrese nuevamente su Pin");
                                                        btnconfirmarUno.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if (validarObligatoriedadPin2()) {
                                                                    if (confirmarPin()) {
                                                                        findViewById(R.id.alertaUno).setVisibility(View.VISIBLE);
                                                                        layoutMensajeAlert();
                                                                        toolbaarMensajeAlert();
                                                                        btnconfirmarAlert.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {
                                                                                setContentView(R.layout.datos_cliente);
                                                                                layoutDtCLiente();
                                                                                toolbarAzulDtCliente();
                                                                                btnconfirmarDtCliente.setOnClickListener(new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View v) {
                                                                                        if (enviarDatosCliente()) {
                                                                                            restarComision();
                                                                                            sumarComision();
                                                                                            mensajeOk();
                                                                                            btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View v) {
                                                                                                    irConsultarClienteCorresponsal();
                                                                                                }
                                                                                            });
                                                                                        } else {Toast.makeText(CrearCuenta.this, "Error al registrarse", Toast.LENGTH_LONG).show();}
                                                                                    }
                                                                                });
                                                                                btncancelarDtCliente.setOnClickListener(new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View v) {
                                                                                        mensajeSalirAzul();
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                        btncancelarAlert.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {
                                                                                mensajeSalirAzul();
                                                                            }
                                                                        });

                                                                    } else {Toast.makeText(CrearCuenta.this, "Pin incorrecto", Toast.LENGTH_LONG).show();}
                                                                }else {Toast.makeText(CrearCuenta.this, "Ingrese un pin", Toast.LENGTH_LONG).show();}
                                                            }
                                                        });

                                                    }else {Toast.makeText(CrearCuenta.this, "Ingrese un pin", Toast.LENGTH_LONG).show();}
                                                }
                                                    });
                                                btncancelarUno.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                mensajeSalirAzul();
                                                            }
                                                        });
                                        btncancelarUno.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mensajeSalirAzul();
                                            }
                                        });
                                    } else {Toast.makeText(CrearCuenta.this, "El saldo debe ser mayor a 10.000", Toast.LENGTH_LONG).show();}
                                }else {Toast.makeText(CrearCuenta.this, "Cédula no disponible", Toast.LENGTH_LONG).show();}
                            }else {Toast.makeText(CrearCuenta.this, "Cédula no disponible", Toast.LENGTH_LONG).show();}
                        }else {Toast.makeText(CrearCuenta.this, "Nombre no disponible", Toast.LENGTH_LONG).show();}
                    }else{Toast.makeText(CrearCuenta.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
            }
            });
            btncancelarTres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mensajeSalirAzul();
                }
            });
        }
    }



    private void toolbarAzul(){
        tituloTres.setText(R.string.crearCliente);
        tercerCampoTresIcon2.setStartIconTintList(ColorStateList.valueOf(parseColor("#3399FF")));
        tercerCampoTresIcon2.setEndIconTintList(ColorStateList.valueOf(parseColor("#3399FF")));
        tercerCampoTres.setVisibility(View.INVISIBLE);
        tercerCampoTres2.setVisibility(View.INVISIBLE);
        segundoCampoTres.setInputType(InputType.TYPE_CLASS_NUMBER);
        tercerCampoTresIcon.setInputType(InputType.TYPE_CLASS_NUMBER);
        atras_tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir2();
            }
        });

    }
    private void toolbarUnoAzul(){
        layoutPin();
        btnconfirmarUno.setText("Aceptar");
        atras_Uno.setVisibility(View.INVISIBLE);
    }
    private void toolbarRojo(){

        findViewById(R.id.bolitas_azulesTres).setVisibility(View.INVISIBLE);
        lineaTres.setBackgroundColor(parseColor("#ff5f58"));
        tituloTres.setText(R.string.crearCliente);

        primerCampoTres.setBackground(getDrawable(R.drawable.txt_login_rojo));
        primerCampoTres2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        primerCampoTres2.setBoxStrokeColor(parseColor("#ff5f58"));

        segundoCampoTres.setBackground(getDrawable(R.drawable.txt_login_rojo));
        segundoCampoTres2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        segundoCampoTres2.setBoxStrokeColor(parseColor("#ff5f58"));

        tercerCampoTres.setVisibility(View.INVISIBLE);
        tercerCampoTres2.setVisibility(View.INVISIBLE);

        tercerCampoTresIcon.setBackground(getDrawable(R.drawable.txt_login_rojo));
        tercerCampoTresIcon2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        tercerCampoTresIcon2.setBoxStrokeColor(parseColor("#ff5f58"));
        tercerCampoTresIcon2.setStartIconTintList(ColorStateList.valueOf(parseColor("#ff5f58")));
        tercerCampoTresIcon2.setEndIconTintList(ColorStateList.valueOf(parseColor("#ff5f58")));

        btnconfirmarTres.setBackground(getDrawable(R.drawable.btn_rojo));
        btncancelarTres.setBackground(getDrawable(R.drawable.btn_transparente_roja));
        btncancelarTres.setTextColor(parseColor("#ff5f58"));


        atras_tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir();
            }
        });
        segundoCampoTres.setInputType(InputType.TYPE_CLASS_NUMBER);
        tercerCampoTresIcon.setInputType(InputType.TYPE_CLASS_NUMBER);
    }
    private void toolbarRojoUno(){

        findViewById(R.id.bolitas_azulesUno).setVisibility(View.INVISIBLE);

        lineaUno.setBackgroundColor(parseColor("#ff5f58"));
        primerCampoUno.setBackground(getDrawable(R.drawable.txt_login_rojo));
        atras_Uno.setVisibility(View.INVISIBLE);
        btnconfirmarUno.setBackground(getDrawable(R.drawable.btn_rojo));
        btncancelarUno.setBackground(getDrawable(R.drawable.btn_transparente_roja));
        btncancelarUno.setTextColor(parseColor("#ff5f58"));
        btnconfirmarUno.setText("Aceptar");
    }
    private void toolbarAzulDtCliente(){
        recibeDatos();
        primerCampoDtCliente.setText(nombreCliente);
        segundoCampoDtCliente.setText(cedula);
        int saldo = Integer.parseInt(saldoCliente)-10000;
        tercerCampoDtCliente.setText(String.valueOf(saldo));
        tituloDtCliente.setText("Confirmar datos Cliente");
        btnAceptarDtCliente.setVisibility(View.INVISIBLE);
    }
    private void toolbarRojoDtCLiente(){
        recibeDatos();
        findViewById(R.id.bolitas_azulesDtCliente).setVisibility(View.INVISIBLE);

        lineaDtCliente.setBackgroundColor(parseColor("#ff5f58"));
        relativeDatosCliente.setBackground(getDrawable(R.drawable.txt_login_rojo));
        btnconfirmarDtCliente.setBackground(getDrawable(R.drawable.btn_rojo));
        btncancelarDtCliente.setBackground(getDrawable(R.drawable.btn_transparente_roja));
        btncancelarDtCliente.setTextColor(parseColor("#ff5f58"));
        tituloDtCliente.setText("Confirmar datos Cliente");
        primerCampoDtCliente.setText(nombreCliente);
        segundoCampoDtCliente.setText(cedula);
        int saldo = Integer.parseInt(saldoCliente)-10000;
        tercerCampoDtCliente.setText(String.valueOf(saldo));
        btnAceptarDtCliente.setVisibility(View.INVISIBLE);
    }
    private void toolbaarMensajeAlert(){
        primerCampoUno.setEnabled(false);
        btncancelarUno.setEnabled(false);
        btnconfirmarUno.setEnabled(false);
        atras_Uno.setEnabled(false);

        textoAlert.setText("La creación tiene un costo de 10.000 pesos que se descontarán directamente de su cuenta WPOSS. ¿Desea continuar?");
    }
    private void layoutPin(){
        atras_Uno = findViewById(R.id.atras_Uno);
        lineaUno = findViewById(R.id.lineaUno);
        tituloUno = findViewById(R.id.tituloUno);
        primerCampoUno = findViewById(R.id.primerCampoUno);
        btncancelarUno = findViewById(R.id.btncancelarUno);
        btnconfirmarUno = findViewById(R.id.btnconfirmarUno);

        primerCampoUno.setHint("PIN");
    }
    private void layoutMensajeAlert(){
        textoAlert = findViewById(R.id.textoAlert);
        btncancelarAlert = findViewById(R.id.btncancelarAlert);
        btnconfirmarAlert = findViewById(R.id.btnconfirmarAlert);
    }
    private void layoutCrearCuenta(){
        atras_tres = findViewById(R.id.atras_tres);
        lineaTres = findViewById(R.id.lineaTres);
        tituloTres = findViewById(R.id.tituloTres);
        primerCampoTres = findViewById(R.id.primerCampoTres);
        primerCampoTres2 = findViewById(R.id.primerCampoTres2);
        segundoCampoTres = findViewById(R.id.segundoCampoTres);
        segundoCampoTres2 = findViewById(R.id.segundoCampoTres2);
        tercerCampoTresIcon = findViewById(R.id.tercerCampoTresIcon);
        tercerCampoTresIcon2 = findViewById(R.id.tercerCampoTresIcon2);
        tercerCampoTres = findViewById(R.id.tercerCampoTres);
        tercerCampoTres2 = findViewById(R.id.tercerCampoTres2);
        btncancelarTres = findViewById(R.id.btncancelarTres);
        btnconfirmarTres = findViewById(R.id.btnconfirmarTres);

        primerCampoTres2.setHint("Nombre del Cliente");
        segundoCampoTres2.setHint("Número de Cédula");
        tercerCampoTresIcon2.setHint("Saldo inicial");
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
    }
    private void recibeDatos(){
        dbCorresponsal = new DbCorresponsal(this);
        dbCliente = new DbCliente(this);
        nombreCliente = primerCampoTres.getText().toString().trim();
        cedula = segundoCampoTres.getText().toString().trim();
        saldoCliente = tercerCampoTresIcon.getText().toString().trim();
        fechaExp = fecha();
        cvv = cvvRandom();
        cuentaCliente = dbCorresponsal.NunTarjetaRandom(cedula);
    }
    private boolean validarObligatoriedad(){
        recibeDatos();
        if (!TextUtils.isEmpty(nombreCliente) && !TextUtils.isEmpty(cedula) && !TextUtils.isEmpty(saldoCliente)){
            return true;
        }else{
            return false;
        }
    }
    private boolean validarObligatoriedadPin(){
        pin = primerCampoUno.getText().toString().trim();
        if (!TextUtils.isEmpty(pin)){
            return true;
        }else{
            return false;
        }
    }
    private boolean validarObligatoriedadPin2(){
        pin2 = primerCampoUno.getText().toString().trim();
        if (!TextUtils.isEmpty(pin2)){
            return true;
        }else{
            return false;
        }
    }
    private boolean confirmarPin(){
        if (pin.equals(pin2)){
            return true;
        } else return false;
    }
    private boolean validarNombreCliente(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        return dbCliente.validarNombreCliente(nombreCliente);
    }
    private boolean validarCedulaCliente(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        return dbCliente.validarCedulaCliente(cedula);
    }
    private boolean validarCedulaNit(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        return dbCliente.validarCedulaNit(cedula);
    }
    private boolean validarSaldo(){
        recibeDatos();
        if (Integer.parseInt(saldoCliente) >= 10000){
            return true;
        } else return false;
    }
    private void mensaje(){
        setContentView(R.layout.mensaje);
        tituloMensaje = findViewById(R.id.tituloMensaje);
        imgMensaje = findViewById(R.id.imgMensaje);
        btnsalirMensaje = findViewById(R.id.btnsalirMensaje);
    }
    private void mensajeSalir(){
        mensaje();
        findViewById(R.id.bolitas_azulesMensaje).setVisibility(View.INVISIBLE);
        tituloMensaje.setText("Creación del cliente cancelada");
        imgMensaje.setImageResource(R.drawable.error);
        btnsalirMensaje.setBackgroundResource(R.drawable.btn_rojo);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void mensajeSalirAzul(){
        mensaje();
        tituloMensaje.setText("Creación del cliente cancelada");
        imgMensaje.setImageResource(R.drawable.error);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir2();
            }
        });
    }
    private void mensajeOk(){
        mensaje();
        tituloMensaje.setText("Se creó el Cliente");
        imgMensaje.setImageResource(R.drawable.bien);
    }
    private void salir(){
        Intent intent = new Intent(CrearCuenta.this, Banco.class);
        startActivity(intent);
    }
    private void salir2(){
        Intent intent = new Intent(CrearCuenta.this, CorresponsalHome.class);
        intent.putExtra("cuenta", correoCorresponsal);
        startActivity(intent);
    }
    public String fecha(){
        String año = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
        int newaño = Integer.parseInt(año) + 5;
        String fecha = newaño+"/"+ new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        return fecha;
    }
    public String cvvRandom(){
        String numero = "";
        for (int i = 0; i < 3; i++){
            numero += String.valueOf(Math.round(Math.random()* 9));
        }
        return numero;
    }
    private boolean enviarDatosCliente(){
        recibeDatos();
        cliente = new Cliente();
        cliente.setNombre_cliente(nombreCliente);
        cliente.setCedula_cliente(cedula);
        cliente.setSaldo_cliente(saldoCliente);
        cliente.setFecha_exp_cliente(fechaExp);
        cliente.setCvv_cliente(cvv);
        cliente.setCuenta_cliente(cuentaCliente);
        cliente.setPin_cliente(pin);
        long id = dbCliente.insertarCliente(cliente);
        if(id > 0) {
            return true;
        }else{
            return false;
        }
    }
    private void irConsultarCliente(){
        Intent intent = new Intent(CrearCuenta.this, ConsultarCliente.class);
        intent.putExtra("cedula", cedula);
        intent.putExtra("vista", "banco");
        startActivity(intent);
    }
    private void irConsultarClienteCorresponsal(){
        Intent intent = new Intent(CrearCuenta.this, ConsultarCliente.class);
        intent.putExtra("cedula", cedula);
        intent.putExtra("vista", "corresponsal");
        intent.putExtra("correo", correoCorresponsal);
        startActivity(intent);
    }
    private void restarComision(){
        recibeDatos();
        dbCliente = new DbCliente(this);
        dbCliente.restarTransferenciaCliente(cedula, String.valueOf(10000));
    }
    private void sumarComision(){
        dbCliente = new DbCliente(this);
        dbCliente.sumarTransferenciaCorresponsal(correoCorresponsal, 10000);
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
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