package com.example.ameacasambientaisfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditThreat extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference root = database.getReference();
    DatabaseReference threats = root.child(MainActivity.THREATS_KEY);
    EditText txtAddress, txtDate, txtDescription;
    Threat current;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_threat);

        txtAddress = findViewById(R.id.editAddress);
        txtDate = findViewById(R.id.editDate);
        txtDescription = findViewById(R.id.editDescription);

        key = getIntent().getStringExtra("KEY");
        current = (Threat) getIntent().getSerializableExtra("TRT");
        txtAddress.setText(current.getAddress());
        txtDate.setText(current.getDate());
        txtDescription.setText(current.getDescription());
    }

    public void updateThreat(View v){

        current.setAddress(txtAddress.getText().toString());
        current.setDate(txtDate.getText().toString());
        current.setDescription(txtDescription.getText().toString());

        threats.child(key).setValue(current);

        finish();

    }

}
