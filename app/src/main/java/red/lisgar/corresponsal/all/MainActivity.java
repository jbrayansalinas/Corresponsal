package red.lisgar.corresponsal.all;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.banco.Banco;
import red.lisgar.corresponsal.corresponsal.Corresponsal;
import red.lisgar.corresponsal.corresponsal.Pago_tarjeta;
import red.lisgar.corresponsal.db.DbCorresponsal;

public class MainActivity extends AppCompatActivity{

    Button btnIniciarSesion;
    TextInputEditText campoEmail;
    TextInputEditText campoContrasena;
    DbCorresponsal dbCorresponsal;
    String correo;
    String contrasena;
    String CoAdmin;
    String PassAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutMain();
        creaAdmin();

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarObligatoriedad()) { //OBLIGATORIEDAD
                    if (validarCorreoCorresponsal()) { //CORRESPONSAL
                        ingresarCorresponsal();
                    }else if (validarCorreoBanco()) { // BANCO
                        ingresarBanco();
                    }else {
                        Toast.makeText(MainActivity.this, "El usuario no existe", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void ingresarCorresponsal(){
        Intent intent = new Intent(this, Corresponsal.class);
        startActivity(intent);
    }

    private void ingresarBanco(){
        Intent intent2 = new Intent(this, Banco.class);
        startActivity(intent2);
    }

    private void layoutMain(){
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        campoEmail = findViewById(R.id.campoEmail);
        campoContrasena = findViewById(R.id.campoContrasena);
    }

    private void recibeDatos(){
        correo = campoEmail.getText().toString().trim();
        contrasena = campoContrasena.getText().toString().trim();
    }

    private void creaAdmin(){
        CoAdmin = "admin@wposs.com".trim();
        PassAdmin = "Admin123*".trim();
    }

    private boolean validarObligatoriedad(){
        recibeDatos();

        if (!(TextUtils.isEmpty(correo) && TextUtils.isEmpty(contrasena))){
            return true;
        }else{
            return false;
        }
    }

    private boolean validarCorreoCorresponsal(){
        recibeDatos();
        dbCorresponsal = new DbCorresponsal(this);
        boolean checkCorreoCorresponsal =  dbCorresponsal.validarCorreoCorresponsal(correo, contrasena);
        return checkCorreoCorresponsal;
    }

    private boolean validarCorreoBanco(){
        creaAdmin();
        recibeDatos();
        if (CoAdmin.equals(correo) && PassAdmin.equals(contrasena)) {
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}