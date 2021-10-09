package com.example.ameacasambientaisfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public static final String THREATS_KEY = "threats";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference root = database.getReference();
    DatabaseReference threats = root.child(THREATS_KEY);
    FirebaseListAdapter<Threat> listAdapter;
    ListView listThreat;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        listThreat = findViewById(R.id.listThreat);
        listAdapter = new FirebaseListAdapter<Threat>(this, Threat.class, R.layout.threat_list_item, threats) {
            @Override
            protected void populateView(View v, Threat model, int position) {

                TextView txtListAddress = v.findViewById(R.id.textListAddress);
                TextView txtListDate = v.findViewById(R.id.textListDate);
                ImageView imageItem = v.findViewById(R.id.imageList);
                txtListAddress.setText(model.getAddress());
                txtListDate.setText(model.getDate());

                if(model.getImage() != null){
                    byte imageData[] = Base64.decode(model.getImage(), Base64.DEFAULT);
                    Bitmap img = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    imageItem.setImageBitmap(img);
                }

            }
        };

        listThreat.setAdapter(listAdapter);
        listThreat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(mAuth.getCurrentUser() == null){
                    Intent it = new Intent(getBaseContext(), UserAuth.class);
                    startActivity(it);
                } else {
                    DatabaseReference item = listAdapter.getRef(position);
                    item.removeValue();
                }

                return false;

            }
        });

        listThreat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference item = listAdapter.getRef(position);
                changeToUpdate(item.getKey(), listAdapter.getItem(position));
            }
        });
    }

    public void changeToAdd(View v){
        Intent it = new Intent(getBaseContext(), AddThreat.class);
        startActivity(it);
    }

    public void changeToUpdate(String key, Threat t){
        Intent it = new Intent(getBaseContext(), EditThreat.class);
        it.putExtra("KEY", key);
        it.putExtra("TRT", t);
        startActivity(it);
    }

}
