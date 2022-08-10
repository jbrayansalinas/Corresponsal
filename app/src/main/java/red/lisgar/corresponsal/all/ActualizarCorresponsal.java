package red.lisgar.corresponsal.all;

import static android.graphics.Color.parseColor;
import static red.lisgar.corresponsal.R.color.bolita_roja;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.banco.Banco;
import red.lisgar.corresponsal.corresponsal.CorresponsalHome;
import red.lisgar.corresponsal.db.DbCorresponsal;
import red.lisgar.corresponsal.entidades.Corresponsal;

public class ActualizarCorresponsal extends AppCompatActivity {

    //Layout Cuatro
    TextView tituloCuatro;
    TextInputEditText primerCampoCuatro;
    TextInputLayout primerCampoCuatro2;
    TextInputEditText segundoCampoCuatro;
    TextInputLayout segundoCampoCuatro2;
    TextInputEditText tercerCampoCuatro;
    TextInputLayout tercerCampoCuatro2;
    TextInputEditText cuartoCampoCuarto;
    TextInputLayout cuartoCampoCuarto2;
    Button btnconfirmarCuatro;
    Button btncancelarCuatro;
    ImageView atras_Cuatro;
    View lineaCuatro;

    //Layout datos_corresponsal
    TextView tituloDtCorresponsal;
    View lineaDtCorresponsal;
    TextView primerCampoDtCorresponsal;
    TextView segundoCampoDtCorresponsal;
    TextView tercerCampoDtCorresponsal;
    TextView cuartoCampoDtCorresponsal;
    Button btnconfirmarDtCorresponsal;
    Button btncancelarDtCorresponsal;
    Button btnHabilitarDtCorresponsal;
    RelativeLayout relativeDatosCorresponsal;

    //Layout Mensaje
    TextView tituloMensaje;
    ImageView imgMensaje;
    Button btnsalirMensaje;

    //Cosas de la clase
    DbCorresponsal dbCorresponsal;
    Corresponsal corresponsal;
    String nombre;
    String nit;
    String correo;
    String contrasena;
    String correoCorresponsal;
    String contrasenaCorresponsal;
    String contrasenaNuevaCorresponsal;
    String contrasenaNueva2Corresponsal;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        correoCorresponsal = extras.getString("correo");
        if (extras.getString("vista").equals("banco")){
            setContentView(R.layout.cuatro_txt);
            layoutRegistrarCorresponsal();
            toolbar();
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(bolita_roja));
            }

            btnconfirmarCuatro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validarObligatoriedad()){
                            if (validarNit()) {
                                if (validarEmailFormato()) {
                                    setContentView(R.layout.datos_corresponsal);
                                    layoutDtCorresponsal();
                                    toolbarDtCorresponsal();
                                    btnconfirmarDtCorresponsal.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            setContentView(R.layout.mensaje);
                                            if (actualizarDatos()) {
                                                mensajeOk();
                                                findViewById(R.id.bolitas_azulesMensaje).setVisibility(View.INVISIBLE);
                                                btnsalirMensaje.setBackgroundResource(R.drawable.btn_rojo);
                                            } else {
                                                Toast.makeText(ActualizarCorresponsal.this, "Error al Actualizar", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                    btncancelarDtCorresponsal.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mensajeSalir();
                                            btnsalirMensaje.setBackgroundResource(R.drawable.btn_rojo);
                                        }
                                    });
                                } else {Toast.makeText(ActualizarCorresponsal.this, "Formato de correo incorrrecto", Toast.LENGTH_LONG).show();}
                            } else {Toast.makeText(ActualizarCorresponsal.this, "Nit no encontrado", Toast.LENGTH_LONG).show();}
                    } else{Toast.makeText(ActualizarCorresponsal.this, "Rellene todos los campos.", Toast.LENGTH_LONG).show();}
                }
            });
            escribirCorreo();
            btncancelarCuatro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mensajeSalir();
                    btnsalirMensaje.setBackgroundResource(R.drawable.btn_rojo);
                }
            });
        }
        else if (extras.getString("vista").equals("corresponsal")){
            setContentView(R.layout.tres_txt);
            layoutTres();

            btnconfirmarTres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validarObligatoriedadTres()) {
                        if (validarContrasena()) {
                            if (contrasenaIgual()){
                                setContentView(R.layout.datos_corresponsal);
                                layoutDtCorresponsal();
                                toolbarDtCorresponsalTres();
                                btnconfirmarDtCorresponsal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        actualizarContrasena();
                                        mensajeOk();
                                        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                salir2();
                                            }
                                        });
                                    }
                                });
                                btncancelarDtCorresponsal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mensajeSalir();
                                        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                salir2();
                                            }
                                        });
                                    }
                                });
                            }else {Toast.makeText(ActualizarCorresponsal.this, "Las contraseñas deben ser iguales", Toast.LENGTH_LONG).show();}
                        } else {Toast.makeText(ActualizarCorresponsal.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();}
                    }else {Toast.makeText(ActualizarCorresponsal.this, "Rellene todos los datos", Toast.LENGTH_LONG).show();}
                }
            });
            btncancelarTres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mensajeSalir();
                    btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            salir2();
                        }
                    });
                }
            });
        }
    }

    private boolean validarObligatoriedadTres(){
        recibeDatosTres();
        if (!TextUtils.isEmpty(contrasenaCorresponsal) && !TextUtils.isEmpty(contrasenaNuevaCorresponsal) && !TextUtils.isEmpty(contrasenaNueva2Corresponsal)){
            return true;
        }else{
            return false;
        }
    }
    private boolean contrasenaIgual(){
        recibeDatosTres();
        if (contrasenaNuevaCorresponsal.equals(contrasenaNueva2Corresponsal)){
            return true;
        } else return false;
    }
    private void recibeDatosTres(){
        contrasenaCorresponsal = primerCampoTres.getText().toString().trim();
        contrasenaNuevaCorresponsal = segundoCampoTres.getText().toString().trim();
        contrasenaNueva2Corresponsal = tercerCampoTres.getText().toString().trim();
    }
    private boolean validarContrasena(){
        recibeDatosTres();
        dbCorresponsal = new DbCorresponsal(this);
        return dbCorresponsal.validarCorreoCorresponsal(correoCorresponsal, contrasenaCorresponsal);
    }
    private void layoutTres(){
        tituloTres = findViewById(R.id.tituloTres);
        primerCampoTres = findViewById(R.id.primerCampoTres);
        primerCampoTres2 = findViewById(R.id.primerCampoTres2);
        segundoCampoTres = findViewById(R.id.segundoCampoTres);
        segundoCampoTres2 = findViewById(R.id.segundoCampoTres2);
        segundoCampoCuatro = findViewById(R.id.segundoCampoCuatro);
        tercerCampoTresIcon = findViewById(R.id.tercerCampoTresIcon);
        tercerCampoTresIcon2 = findViewById(R.id.tercerCampoTresIcon2);
        tercerCampoTres2 = findViewById(R.id.tercerCampoTres2);
        tercerCampoTres = findViewById(R.id.tercerCampoTres);
        btncancelarTres = findViewById(R.id.btncancelarTres);
        btnconfirmarTres = findViewById(R.id.btnconfirmarTres);
        atras_tres = findViewById(R.id.atras_tres);

        tituloTres.setText("Actualizar Corresponsal");
        primerCampoTres2.setHint("Contraseña Actual");
        segundoCampoTres2.setHint("Contraseña Nueva");
        tercerCampoTres2.setHint("Confirmar Contraseña Nueva");
        tercerCampoTresIcon2.setVisibility(View.INVISIBLE);
        atras_tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir2();
            }
        });
    }
    private void toolbarDtCorresponsalTres(){
        recibeDatosTres();
        findViewById(R.id.bolitas_rojasDtCorresponsal).setVisibility(View.INVISIBLE);
        lineaDtCorresponsal.setBackgroundColor(parseColor("#3399FF"));
        relativeDatosCorresponsal.setBackground(getDrawable(R.drawable.txt_login));
        btnconfirmarDtCorresponsal.setBackground(getDrawable(R.drawable.btn_azul));
        btncancelarDtCorresponsal.setBackground(getDrawable(R.drawable.btn_transparente));
        btncancelarDtCorresponsal.setTextColor(parseColor("#3399FF"));
        dbCorresponsal = new DbCorresponsal(this);
        corresponsal = new Corresponsal();
        corresponsal =                                                                    dbCorresponsal.mostrarDatosCorresponsalHome(correoCorresponsal);
        primerCampoDtCorresponsal.setText(corresponsal.getNombre_corresponsal());
        segundoCampoDtCorresponsal.setText(corresponsal.getNit_corresponsal());
        tercerCampoDtCorresponsal.setText(corresponsal.getSaldo_corresponsal());
        cuartoCampoDtCorresponsal.setText(correoCorresponsal);
    }
    private void salir2(){
        Intent intent = new Intent(ActualizarCorresponsal.this, CorresponsalHome.class);
        intent.putExtra("cuenta", correoCorresponsal);
        startActivity(intent);
    }
    private void actualizarContrasena(){
        recibeDatosTres();
        dbCorresponsal = new DbCorresponsal(this);
        dbCorresponsal.actualizarContrasena(contrasenaNuevaCorresponsal, correoCorresponsal);
    }
    private void toolbar(){

        findViewById(R.id.bolitas_azulesCuatro).setVisibility(View.INVISIBLE);
        lineaCuatro.setBackgroundColor(parseColor("#ff5f58"));
        tituloCuatro.setText("Actualizar Corresponsal");

        primerCampoCuatro.setBackground(getDrawable(R.drawable.txt_login_rojo));
        primerCampoCuatro2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        primerCampoCuatro2.setBoxStrokeColor(parseColor("#ff5f58"));

        segundoCampoCuatro.setBackground(getDrawable(R.drawable.txt_login_rojo));
        segundoCampoCuatro2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        segundoCampoCuatro2.setBoxStrokeColor(parseColor("#ff5f58"));

        tercerCampoCuatro.setBackground(getDrawable(R.drawable.txt_login_rojo));
        tercerCampoCuatro2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        tercerCampoCuatro2.setBoxStrokeColor(parseColor("#ff5f58"));

        cuartoCampoCuarto.setBackground(getDrawable(R.drawable.txt_login_rojo));
        cuartoCampoCuarto2.setHintTextColor(ColorStateList.valueOf(parseColor("#ff5f58")));
        cuartoCampoCuarto2.setBoxStrokeColor(parseColor("#ff5f58"));
        cuartoCampoCuarto2.setStartIconTintList(ColorStateList.valueOf(parseColor("#ff5f58")));
        cuartoCampoCuarto2.setEndIconTintList(ColorStateList.valueOf(parseColor("#ff5f58")));

        btnconfirmarCuatro.setBackground(getDrawable(R.drawable.btn_rojo));
        btncancelarCuatro.setBackground(getDrawable(R.drawable.btn_transparente_roja));
        btncancelarCuatro.setTextColor(parseColor("#ff5f58"));


        atras_Cuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActualizarCorresponsal.this, Banco.class);
                startActivity(intent);
            }
        });
        tercerCampoCuatro.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }
    private void layoutRegistrarCorresponsal(){
        atras_Cuatro = findViewById(R.id.atras_Cuatro);
        lineaCuatro = findViewById(R.id.lineaCuatro);
        tituloCuatro = findViewById(R.id.tituloCuatro);
        primerCampoCuatro = findViewById(R.id.primerCampoCuatro);
        primerCampoCuatro2 = findViewById(R.id.primerCampoCuatro2);
        segundoCampoCuatro = findViewById(R.id.segundoCampoCuatro);
        segundoCampoCuatro2 = findViewById(R.id.segundoCampoCuatro2);
        tercerCampoCuatro = findViewById(R.id.tercerCampoCuatro);
        tercerCampoCuatro2 = findViewById(R.id.tercerCampoCuatro2);
        cuartoCampoCuarto = findViewById(R.id.cuartoCampoCuarto);
        cuartoCampoCuarto2 = findViewById(R.id.cuartoCampoCuarto2);
        btnconfirmarCuatro = findViewById(R.id.btnconfirmarCuatro);
        btncancelarCuatro = findViewById(R.id.btncancelarCuatro);

        primerCampoCuatro2.setHint("Nombre Corresponsal");
        segundoCampoCuatro2.setHint("NIT de Corresponsal");
        tercerCampoCuatro2.setHint("Correo Corresponsal");
        cuartoCampoCuarto2.setHint("Contraseña Corresponsal");
    }
    private void recibeDatos(){
        dbCorresponsal = new DbCorresponsal(this);
        nombre = primerCampoCuatro.getText().toString().trim();
        nit = segundoCampoCuatro.getText().toString().trim();
        correo = tercerCampoCuatro.getText().toString().trim();
        contrasena = cuartoCampoCuarto.getText().toString().trim();
    }
    private boolean validarObligatoriedad(){
        recibeDatos();
        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(nit) && !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(contrasena)){
            return true;
        }else{
            return false;
        }
    }
    private boolean validarNit(){
        recibeDatos();
        dbCorresponsal = new DbCorresponsal(this);
        return dbCorresponsal.validarCreado(nit);
    }
    private boolean validarEmailFormato(){
        recibeDatos();
        dbCorresponsal = new DbCorresponsal(this);
        if (correo.length()>10){
            if (correo.substring(correo.length()-10).equals("@wposs.com")){
                return dbCorresponsal.validarEmailFormato(correo);
            }else return false;
        }else return false;
    }
    private void escribirCorreo(){
        primerCampoCuatro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                recibeDatos();
                tercerCampoCuatro.setText(nombre.toLowerCase() + "@wposs.com");
            }
        });
    }
    private boolean actualizarDatos(){
        recibeDatos();
        return dbCorresponsal.actualizarDatos(nit, nombre, correo, contrasena);
    }
    private void layoutDtCorresponsal(){
        tituloDtCorresponsal = findViewById(R.id.tituloDtCorresponsal);
        lineaDtCorresponsal = findViewById(R.id.lineaDtCorresponsal);
        primerCampoDtCorresponsal = findViewById(R.id.primerCampoDtCorresponsal);
        segundoCampoDtCorresponsal = findViewById(R.id.segundoCampoDtCorresponsal);
        tercerCampoDtCorresponsal = findViewById(R.id.tercerCampoDtCorresponsal);
        cuartoCampoDtCorresponsal = findViewById(R.id.cuartoCampoDtCorresponsal);
        btnconfirmarDtCorresponsal = findViewById(R.id.btnconfirmarDtCorresponsal);
        btncancelarDtCorresponsal = findViewById(R.id.btncancelarDtCorresponsal);
        btnHabilitarDtCorresponsal = findViewById(R.id.btnHabilitarDtCorresponsal);
        relativeDatosCorresponsal = findViewById(R.id.relativeDatosCorresponsal);

        tituloDtCorresponsal.setText("Confirmar datos Correponsal");
        btnHabilitarDtCorresponsal.setVisibility(View.INVISIBLE);
    }
    private void toolbarDtCorresponsal(){
        recibeDatos();
        primerCampoDtCorresponsal.setText(nombre);
        segundoCampoDtCorresponsal.setText(nit);
        tercerCampoDtCorresponsal.setText(dbCorresponsal.mostrarNit(nit));
        cuartoCampoDtCorresponsal.setText(correo);
    }
    private void mensajeOk(){
        mensaje();
        tituloMensaje.setText("Se actualizó el Corresponsal");
        imgMensaje.setImageResource(R.drawable.bien);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void mensaje(){
        setContentView(R.layout.mensaje);
        tituloMensaje = findViewById(R.id.tituloMensaje);
        imgMensaje = findViewById(R.id.imgMensaje);
        btnsalirMensaje = findViewById(R.id.btnsalirMensaje);
    }
    private void mensajeSalir(){
        mensaje();
        tituloMensaje.setText("Actualización del Corresponsal cancelado");
        imgMensaje.setImageResource(R.drawable.error);
        btnsalirMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
    }
    private void salir(){
        Intent intent = new Intent(ActualizarCorresponsal.this, Banco.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}