package com.example.lab07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler databaseHandler;
    private Button btnSave, btnCancel;
    private EditText editTextInput;
    private List<Person> personList;
    private AdapterPerson adapterPerson;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);
        init();

    }

    private void init(){
        databaseHandler = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerView);
        btnSave = findViewById(R.id.btn_sql_save);
        btnCancel = findViewById(R.id.btn_sql_cancel);
        editTextInput = findViewById(R.id.editTextTextPersonName);

        personList = new ArrayList<>();
        personList = getDataFromData();

        adapterPerson = new AdapterPerson(personList,this);
        recyclerView.setAdapter(adapterPerson);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private List<com.example.lab07.Person> getDataFromData(){
        return databaseHandler.getAllPersons();
    }
    private void addActionForSaveBtn(){
        btnSave.setOnClickListener(view -> {
            int personID = personList.isEmpty() ? 1 : personList.get(personList.size() - 1).getId() +1;
            String personFullName = editTextInput.getText().toString().isEmpty() ? "Unknown": editTextInput.getText().toString();

            Person person = new Person(personID,personFullName);

            boolean inserted = databaseHandler.updatePerson(person);
            if(inserted){
                Toast.makeText(MainActivity.this,"Them 100%",Toast.LENGTH_SHORT).show();
                personList.add(person);
                adapterPerson.notifyDataSetChanged();
            }else{
                Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addActionForCancelButton(){
        btnCancel.setOnClickListener(view -> {
            editTextInput.setText("");
            editTextInput.clearFocus();
        });
    }


}