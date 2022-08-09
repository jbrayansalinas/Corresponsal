package red.lisgar.corresponsal.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.adaptadores.ListaClientesAdapter;
import red.lisgar.corresponsal.db.DbCliente;

public class ListadoClientes extends AppCompatActivity {

    //layout Listado
    TextView titulolistado;
    View linealistado;
    EditText buscadorlistado;
    ImageView atras_listado;
    RecyclerView recyclerlistado;
    ListaClientesAdapter adapter;
    DbCliente dbCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);
        layoutListado();
        mostrarDatos();
    }

    private void layoutListado(){
        titulolistado = findViewById(R.id.titulolistado);
        linealistado = findViewById(R.id.linealistado);
        buscadorlistado = findViewById(R.id.buscadorlistado);
        atras_listado = findViewById(R.id.atras_listado);
        recyclerlistado = findViewById(R.id.recyclerlistado);
    }
    private void mostrarDatos(){
        dbCliente = new DbCliente(this);
        if (dbCliente.listaCliente()!=null){
            recyclerlistado.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ListaClientesAdapter(dbCliente.listaCliente());
            recyclerlistado.setAdapter(adapter);

            buscadorlistado.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    adapter.filtrado(s.toString());
                    recyclerlistado.setAdapter(adapter);
                }
            });
        }
    }
}