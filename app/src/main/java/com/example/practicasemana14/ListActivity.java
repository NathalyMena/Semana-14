package com.example.practicasemana14;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText descripcionTarea, nombreTarea;
    private Button agregarBtn;
    private FirebaseDatabase db;
    private ListView listaTareas;
    private String userId, nombre;
    private ArrayList<Task> dataTareas;
    private ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        agregarBtn = findViewById(R.id.agregarBtn);
        descripcionTarea = findViewById(R.id.descripcionTarea);
        nombreTarea = findViewById(R.id.nombreTarea);
        listaTareas = findViewById(R.id.listaTareas);

        dataTareas = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dataTareas);
        listaTareas.setAdapter(adapter);


        userId = getSharedPreferences("idUsuario", MODE_PRIVATE).getString("id", "no_ID");
        nombre = getSharedPreferences("idUsuario", MODE_PRIVATE).getString("nombre", "no_Nombre");

        agregarBtn.setOnClickListener(this);
        db = FirebaseDatabase.getInstance();

        loadDatabase();

        listaTareas.setOnItemClickListener(

                (list, row, position, id) -> {
                    Task task = dataTareas.get(position);
                    Toast.makeText(this, task.getTitle(), Toast.LENGTH_SHORT).show();
                    task.setCompleted(true);
                    task.isCompleted();
                    Log.e("user",task.getCompletado());
                }

        );

    }

    private void loadDatabase() {
        DatabaseReference ref = db.getReference().child("tasks").child(userId);
        Log.e("user",userId);
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {
                        dataTareas.clear();
                        Log.e("user","descarga"+data.getChildrenCount());
                        for (DataSnapshot child : data.getChildren()) {
                            Task taskN = child.getValue(Task.class);
                            dataTareas.add(taskN);
                            Log.e("user",taskN.getDescription());
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                }
        );
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.agregarBtn:

                String id = UUID.randomUUID().toString();
                DatabaseReference reference = db.getReference().child("tasks").child(userId).child(id);
                Task task = new Task(id, nombreTarea.getText().toString(), descripcionTarea.getText().toString());
                reference.setValue(task);

                break;
        }

    }
}