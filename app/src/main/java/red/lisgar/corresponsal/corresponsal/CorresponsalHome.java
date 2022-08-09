package red.lisgar.corresponsal.corresponsal;

import static android.graphics.Color.parseColor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.adaptadores.ListaHomeAdapter;
import red.lisgar.corresponsal.all.ActualizarCorresponsal;
import red.lisgar.corresponsal.all.CrearCuenta;
import red.lisgar.corresponsal.all.MainActivity;
import red.lisgar.corresponsal.banco.ActualizarCliente;
import red.lisgar.corresponsal.banco.Banco;
import red.lisgar.corresponsal.banco.BuscadorConsultarCliente;
import red.lisgar.corresponsal.banco.ConsultarCorresponsal;
import red.lisgar.corresponsal.banco.ListadoClientes;
import red.lisgar.corresponsal.banco.ListadoCorresponsal;
import red.lisgar.corresponsal.banco.RegistrarCorresponsal;
import red.lisgar.corresponsal.db.DbCorresponsal;
import red.lisgar.corresponsal.entidades.Corresponsal;
import red.lisgar.corresponsal.entidades.Home;

public class CorresponsalHome extends AppCompatActivity {

    ImageView menuCorresponsal;
    TextView nombreCorresponsal;
    TextView saldoCorresponsal;
    TextView cuentaCorresponsal;
    RecyclerView recyclerCorresponsal;
    ListaHomeAdapter lista;
    PopupMenu popupMenu;
    DbCorresponsal dbCorresponsal;
    String cuenta;
    Corresponsal corresponsal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corresponsal);
        layoutCorresponsal();
        toolbar();
        opciones();

        menuCorresponsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu = new PopupMenu(CorresponsalHome.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.items_menu_corresponsal, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.actualizarDatos:
                                actualizarCorresponsal();
                                break;
                            case R.id.crarClientes:
                                crearCliente();
                                break;
                            case R.id.salir:
                                cerrarSesion();
                                break;
                            default:
                                return CorresponsalHome.super.onOptionsItemSelected(menuItem);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void layoutCorresponsal(){
        nombreCorresponsal = findViewById(R.id.nombreCorresponsal);
        saldoCorresponsal = findViewById(R.id.saldoCorresponsal);
        cuentaCorresponsal = findViewById(R.id.cuentaCorresponsal);
        menuCorresponsal = findViewById(R.id.menuCorresponsal);
        recyclerCorresponsal = findViewById(R.id.recyclerCorresponsal);
    }
    private void toolbar(){
        dbCorresponsal = new DbCorresponsal(this);
        Bundle extras = getIntent().getExtras();
        cuenta = extras.getString("cuenta");
        corresponsal = new Corresponsal();
        corresponsal = dbCorresponsal.mostrarDatosCorresponsalHome(cuenta);
        nombreCorresponsal.setText(corresponsal.getNombre_corresponsal());
        saldoCorresponsal.setText(corresponsal.getSaldo_corresponsal());
        cuentaCorresponsal.setText(corresponsal.getCuenta_corresponsal());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            saldoCorresponsal.setCompoundDrawableTintList(ColorStateList.valueOf(parseColor("#3399FF")));
            cuentaCorresponsal.setCompoundDrawableTintList(ColorStateList.valueOf(parseColor("#3399FF")));
        }
    }
    // Opciones Array
    public void opciones(){

        ArrayList<Home> listaOp = new ArrayList<>();

        listaOp.add(new Home(R.drawable.tarjeta, "Pago con tarjeta", Pago_tarjeta.class, ""));
        listaOp.add(new Home(R.drawable.retiros, "Retiros", Retiros.class, ""));
        listaOp.add(new Home(R.drawable.deposito, "Depositos", Deposito.class, ""));
        listaOp.add(new Home(R.drawable.transferencias, "Transferencias", Transaccion.class, ""));
        listaOp.add(new Home(R.drawable.historial_transaccional, "Historial Transaccional", HistorialTrasacciones.class, ""));
        listaOp.add(new Home(R.drawable.consulta_saldo, "Consulta de saldo", ConsultarSaldo.class, ""));

        lista = new ListaHomeAdapter(listaOp, CorresponsalHome.this, "azul");
        recyclerCorresponsal.setAdapter(lista);
        recyclerCorresponsal.setLayoutManager(new GridLayoutManager(CorresponsalHome.this, 2));

    }

    private void crearCliente(){
        Intent intent = new Intent(this, CrearCuenta.class);
        intent.putExtra("vista", "corresponsal");
        startActivity(intent);
    }
    private void actualizarCorresponsal(){
        Intent intent = new Intent(this, ActualizarCorresponsal.class);
        intent.putExtra("vista", "corresponsal");
        intent.putExtra("correo", cuenta);
        startActivity(intent);
    }
    private void cerrarSesion(){
        Intent intent3 = new Intent(this, MainActivity.class);
        startActivity(intent3);
    }
    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }
}