package id.abdurrahman.firebasecreate;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.abdurrahman.firebasecreate.adapter.RequestAdapterRecycleView;
import id.abdurrahman.firebasecreate.model.Requests;

public class ListActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<Requests> arrayList;
    private RequestAdapterRecycleView reqAdapterRecyclerView;

    private RecyclerView list_item;
    private ProgressDialog dialog;
    private FloatingActionButton button_add;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance().getReference();

        list_item = findViewById(R.id.recycle_list);
        button_add = findViewById(R.id.button_add);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        list_item.setLayoutManager(layoutManager);
        list_item.setItemAnimator(new DefaultItemAnimator());

        dialog = ProgressDialog.show(ListActivity.this,
                null,
                "Please wait",
                true,
                false);

        database.child("Abdurrahman").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayList = new ArrayList<>();
                for (DataSnapshot listDataSnapshot : dataSnapshot.getChildren()) {

                    Requests requests = listDataSnapshot.getValue(Requests.class);
                    requests.setKey(listDataSnapshot.getKey());

                    arrayList.add(requests);
                }

                reqAdapterRecyclerView = new RequestAdapterRecycleView(arrayList, ListActivity.this);
                list_item.setAdapter(reqAdapterRecyclerView);
                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                dialog.dismiss();
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListActivity.this, MainActivity.class)
                        .putExtra("id", "")
                        .putExtra("title","")
                        .putExtra("email", "")
                        .putExtra("status", ""));
            }
        });
    }
}
