package com.example.mynotes.ui.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.db.entity.NotaEntity;

import java.util.List;

public class MyNotaRecyclerViewAdapter extends RecyclerView.Adapter<MyNotaRecyclerViewAdapter.ViewHolder> {

    private List<NotaEntity> mValues;
    private Context context;
    private DialogFragmentNuevaNotaViewModel notaViewModel;

    public MyNotaRecyclerViewAdapter(List<NotaEntity> items, Context context) {
        mValues = items;
        this.context = context;
        notaViewModel = ViewModelProviders.of((AppCompatActivity)context).get(DialogFragmentNuevaNotaViewModel.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.titulo.setText(mValues.get(position).getTitulo());
        holder.contenido.setText(mValues.get(position).getContenido());

        if (holder.mItem.isFavorito()){
            holder.favorito.setImageResource(R.drawable.ic_star);
        }

        holder.favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mItem.isFavorito()){
                    holder.mItem.setFavorito(false);
                    holder.favorito.setImageResource(R.drawable.ic_star_border);
                    Toast.makeText(context, "Nota removida de favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    holder.mItem.setFavorito(true);
                    holder.favorito.setImageResource(R.drawable.ic_star);
                    Toast.makeText(context, "Nota agregada a favoritos", Toast.LENGTH_SHORT).show();
                }

                notaViewModel.updateNota(holder.mItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setNewNotas(List<NotaEntity> notas){
        this.mValues = notas;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titulo;
        public final TextView contenido;
        public final ImageView favorito;
        public NotaEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titulo = view.findViewById(R.id.txtTituloNota);
            contenido = view.findViewById(R.id.txtContenido);
            favorito = view.findViewById(R.id.imgFavorito);
    }

        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText() + "'";
        }
    }
}
