package it.itsar.todo_esame_callegari_gabriele;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {
    private final ArrayList<ListaTipo> lista;

    public ListaAdapter(ArrayList<ListaTipo> lista) {
        this.lista = lista;
    }


    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carta,parent,false);
        return new ListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        holder.bind(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ListaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titolo;
        private final CheckBox check;

        public ListaViewHolder(@NonNull View itemView) {
            super(itemView);
            titolo = itemView.findViewById(R.id.testoCarta);
            check = itemView.findViewById(R.id.checkboxCarta);
        }

        public void bind(ListaTipo type){
            titolo.setText(type.getTitolo());
            check.setChecked(type.isCheck());
        }

    }
}

