package com.example.practicasemana14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button ingresarBtn;
    private EditText nameInput;
    private FirebaseDatabase db;


    private ListView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ingresarBtn.setOnClickListener(this);

        db = FirebaseDatabase.getInstance();
        ingresarBtn = findViewById(R.id.ingresarBtn);
        nameInput = findViewById(R.id.nameInput);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ingresarBtn:
                db.getReference().child("usuarios").orderByChild("username").equalTo(nameInput.getText().toString()).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.e("sd",snapshot.getChildrenCount()+";");
                                if(snapshot.getChildrenCount() == 0){
                                    String id = UUID.randomUUID().toString();
                                    DatabaseReference reference = db.getReference().child("usuarios").child(id);
                                    Usuario usuario = new Usuario(nameInput.getText().toString(),id);
                                    reference.setValue(usuario);

                                    SharedPreferences preferences = getSharedPreferences("idUsuario", MODE_PRIVATE);
                                    preferences.edit().putString("id",id).apply();
                                    preferences.edit().putString("nombre", nameInput.getText().toString()).apply();

                                    Intent i = new Intent(MainActivity.this, ListActivity.class);
                                    startActivity(i);
                                } else{
                                    for(DataSnapshot child: snapshot.getChildren()){
                                        Usuario usuario = child.getValue(Usuario.class);
                                        SharedPreferences preferences = getSharedPreferences("idUsuario", MODE_PRIVATE);
                                        preferences.edit().putString("nombre",usuario.getUsername()).apply();
                                        preferences.edit().putString("id",usuario.getId()).apply();

                                    }
                                    Intent i = new Intent(MainActivity.this, ListActivity.class);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                );


                break;
        }
    }
}