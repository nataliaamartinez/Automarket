package com.example.automarket.Controlador;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automarket.Modelo.Anuncio;
import com.example.automarket.R;
import com.example.automarket.Utils;
import com.example.automarket.Vista.ModificarAnuncioActivity;

import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.AnuncioViewHolder> {

    private final Context context;
    private final List<Anuncio> lista;
    private final String usuarioId;

    public AnuncioAdapter(Context context, List<Anuncio> lista, String usuarioId) {
        this.context = context;
        this.lista = lista;
        this.usuarioId = usuarioId;
    }

    @NonNull
    @Override
    public AnuncioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_anuncio, parent, false);
        return new AnuncioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnuncioViewHolder holder, int position) {
        Anuncio anuncio = lista.get(position);
        holder.tvTitulo.setText(anuncio.getMarca() + " " + anuncio.getModelo() + " (" + anuncio.getAnio() + ")");
        holder.tvDescripcion.setText(anuncio.getDescripcion());
        holder.tvPrecio.setText("€" + anuncio.getPrecio());
        holder.tvKm.setText("KM: " + anuncio.getKilometraje());

        holder.btnEliminar.setOnClickListener(v -> eliminarAnuncio(anuncio.getId(), position));
        holder.btnModificar.setOnClickListener(v -> {
            Intent intent = new Intent(context, ModificarAnuncioActivity.class);
            intent.putExtra("anuncio_id", anuncio.getId());
            intent.putExtra("usuario", usuarioId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class AnuncioViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion, tvPrecio, tvKm;
        Button btnEliminar, btnModificar;

        public AnuncioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvKm = itemView.findViewById(R.id.tvKilometraje);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnModificar = itemView.findViewById(R.id.btnModificar);
        }
    }

    private void eliminarAnuncio(int idAnuncio, int position) {
        String url = Utils.IP + "eliminar_anuncio.php?id=" + idAnuncio;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    if (response.trim().equalsIgnoreCase("success")) {
                        lista.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Anuncio eliminado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(context, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show());

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}

