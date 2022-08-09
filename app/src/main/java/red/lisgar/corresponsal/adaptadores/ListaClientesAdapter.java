package red.lisgar.corresponsal.adaptadores;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import red.lisgar.corresponsal.R;
import red.lisgar.corresponsal.entidades.Cliente;

public class ListaClientesAdapter extends RecyclerView.Adapter<ListaClientesAdapter.ClienteViewHolder> {

    ArrayList<Cliente> listaCliente;
    ArrayList<Cliente> listaOriginal;

    public ListaClientesAdapter(ArrayList<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaCliente);
    }

    @NonNull
    @Override
    public ListaClientesAdapter.ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultas, null, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaClientesAdapter.ClienteViewHolder holder, int position) {
        holder.campoUno.setText(listaCliente.get(position).getNombre_cliente());
        holder.campoDos.setText(listaCliente.get(position).getCuenta_cliente());
        holder.campoTres.setText(listaCliente.get(position).getCedula_cliente());
        holder.campoOtro.setText(listaCliente.get(position).getSaldo_cliente());
    }

    @Override
    public int getItemCount() {
        return listaCliente.size();
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            listaCliente.clear();
            listaCliente.addAll(listaOriginal);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Cliente> collection = listaCliente.stream().filter(i -> i.getCedula_cliente().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                listaCliente.clear();
                listaCliente.addAll(collection);
            }else{
                for (Cliente l: listaOriginal){
                    if (l.getCedula_cliente().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaCliente.add(l);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder {

        TextView campoUno;
        TextView campoDos;
        TextView campoTres;
        TextView campoCuatro;
        TextView campoOtro;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            campoUno  = itemView.findViewById(R.id.campoUno);
            campoDos  = itemView.findViewById(R.id.campoDos);
            campoTres  = itemView.findViewById(R.id.campoTres);
            campoCuatro  = itemView.findViewById(R.id.campoCuatro);
            campoOtro  = itemView.findViewById(R.id.campoOtro);

        }
    }
}
