package com.example.lab07;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterPerson extends RecyclerView.Adapter<AdapterPerson.UserViewHolder> {
    private List<Person> listPerson;
    private Context context;


    public AdapterPerson(List<Person> listPerson, Context context) {
        this.listPerson = listPerson;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user,parent,false);
        return new UserViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPerson.UserViewHolder holder, int position) {
        Person person = listPerson.get(position);
        holder.txtPerson.setText(person.getId()+". "+person.getFullName());

        holder.btnEdit.setOnClickListener(view -> {
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            person.setFullName(person.getFullName()+"Update 100%!");

            boolean isUpdated = databaseHandler.updatePerson(person);
            if(isUpdated){
                Toast.makeText(context,"Updated thanh cong",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
            databaseHandler.close();
        });
        holder.btnDelete.setOnClickListener(view -> {
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            person.setFullName(person.getFullName()+"Delete Person");

            new AlertDialog.Builder(context).setTitle("Delete User")
                    .setMessage("Do you really want to remove this person?")
                    .setPositiveButton(android.R.string.yes,
                            ((dialogInterface, i) -> {
                                boolean isDeleted = databaseHandler.deletePersonbyID(person.getId());
                                if(isDeleted){
                                    Toast.makeText(context,"Delete thanh cong",Toast.LENGTH_SHORT).show();
                                    listPerson.remove(person);
                                    notifyDataSetChanged();
                                }
                            })).setNegativeButton(android.R.string.no,null).show();

            boolean isUpdated = databaseHandler.updatePerson(person);
            if(isUpdated){
                Toast.makeText(context,"Updated thanh cong",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
            databaseHandler.close();
        });
    }

    @Override
    public int getItemCount() {
        return listPerson.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
    private TextView txtPerson;
    private ImageButton btnEdit, btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPerson = itemView.findViewById(R.id.name_id);
            btnEdit = itemView.findViewById(R.id.update_id_icon);
            btnDelete = itemView.findViewById(R.id.delete_id_icon);
        }
    }
}
