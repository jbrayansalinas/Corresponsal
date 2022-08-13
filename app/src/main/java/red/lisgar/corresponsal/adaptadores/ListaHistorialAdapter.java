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
import red.lisgar.corresponsal.entidades.Corresponsal;
import red.lisgar.corresponsal.entidades.Transacciones;

public class ListaHistorialAdapter extends RecyclerView.Adapter<ListaHistorialAdapter.TransaccionesViewHolder> {

    ArrayList<Transacciones> listaTransacciones;
    ArrayList<Transacciones> listaOriginal;

    public ListaHistorialAdapter(ArrayList<Transacciones> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaTransacciones);
    }

    @NonNull
    @Override
    public ListaHistorialAdapter.TransaccionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultas, null, false);
        return new TransaccionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaHistorialAdapter.TransaccionesViewHolder holder, int position) {
        holder.campoUno.setText(listaTransacciones.get(position).getFecha_transaccion().substring(5));
        holder.campoDos.setText(listaTransacciones.get(position).getTipo_transaccion());
        holder.campoTres.setText(listaTransacciones.get(position).getMonto_transaccion());
        holder.campoCuatro.setText(String.valueOf(listaTransacciones.get(position).getId_cliente()));
        holder.campoOtro.setText(String.valueOf(listaTransacciones.get(position).getId_transaccion()));
    }

    @Override
    public int getItemCount() {
        return listaTransacciones.size();
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            listaTransacciones.clear();
            listaTransacciones.addAll(listaOriginal);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Transacciones> collection = listaTransacciones.stream().filter(i ->String.valueOf( i.getId_cliente()).toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                listaTransacciones.clear();
                listaTransacciones.addAll(collection);
            }else{
                for (Transacciones l: listaOriginal){
                    if (String.valueOf(l.getId_cliente()).toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaTransacciones.add(l);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class TransaccionesViewHolder extends RecyclerView.ViewHolder {

        TextView campoUno;
        TextView campoDos;
        TextView campoTres;
        TextView campoCuatro;
        TextView campoOtro;

        public TransaccionesViewHolder(@NonNull View itemView) {
            super(itemView);
            campoUno  = itemView.findViewById(R.id.campoUno);
            campoDos  = itemView.findViewById(R.id.campoDos);
            campoTres  = itemView.findViewById(R.id.campoTres);
            campoCuatro  = itemView.findViewById(R.id.campoCuatro);
            campoOtro  = itemView.findViewById(R.id.campoOtro);

        }
    }
}
