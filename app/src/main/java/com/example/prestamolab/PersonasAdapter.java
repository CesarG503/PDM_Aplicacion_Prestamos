package com.example.prestamolab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestamolab.entitys.Personas;
import java.util.List;

public class PersonasAdapter extends RecyclerView.Adapter<PersonasAdapter.PersonaViewHolder> {

    private List<Personas> personasList;

    public PersonasAdapter(List<Personas> personasList) {
        this.personasList = personasList;
    }

    @NonNull
    @Override
    public PersonaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new PersonaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonaViewHolder holder, int position) {
        Personas persona = personasList.get(position);
        holder.text1.setText(persona.nombre);
        holder.text2.setText(persona.contacto);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), InsertPersonaActivity.class);
            intent.putExtra("id", persona.id);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return personasList.size();
    }

    static class PersonaViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        PersonaViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}