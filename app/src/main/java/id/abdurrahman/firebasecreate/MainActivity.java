package id.abdurrahman.firebasecreate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import id.abdurrahman.firebasecreate.model.Requests;

public class MainActivity extends AppCompatActivity  {

    private DatabaseReference database;
    private EditText editUsername, editEmail, editStatus;
    private Button buttonSave, buttonCancel;

    private ProgressDialog dialog;

    private String stringId, stringUsername, stringEmail, stringStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringId = getIntent().getStringExtra("id");
        stringUsername = getIntent().getStringExtra("username");
        stringEmail = getIntent().getStringExtra("email");
        stringStatus = getIntent().getStringExtra("status");

        database = FirebaseDatabase.getInstance().getReference();

        editUsername = findViewById(R.id.edit_username);
        editEmail = findViewById(R.id.edit_email);
        editStatus = findViewById(R.id.edit_status);
        buttonSave = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);

        editUsername.setText(stringUsername);
        editEmail.setText(stringEmail);
        editStatus.setText(stringStatus);


        if (stringId.equals("")) {
            buttonSave.setText("Save");
            buttonCancel.setText("Cancel");
        } else {
            buttonSave.setText("Edit");
            buttonCancel.setText("Delete");
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Susername = editUsername.getText().toString();
                String Semail = editEmail.getText().toString();
                String Sstatus = editStatus.getText().toString();

                if (buttonSave.getText().equals("Save")) {

                    if (Susername.equals("")) {
                        editUsername.setError("Mohon masukkan username");
                        editUsername.requestFocus();
                    } else if (Semail.equals("")) {
                        editEmail.setError("Mohon masukkan email");
                        editEmail.requestFocus();
                    } else if (Sstatus.equals("")) {
                        editStatus.setError("Mohon masukkan status Anda!");
                        editStatus.requestFocus();
                    } else {

                        dialog = ProgressDialog.show(MainActivity.this,
                                null,
                                "Wait a moment",
                                true,
                                false);

                        submitUser(new Requests(
                                Susername.toLowerCase(),
                                Semail.toLowerCase(),
                                Sstatus.toLowerCase()));

                        finish();
                    }

                } else {
                    if (Susername.equals("")) {
                        editUsername.setError("Mohon masukkan username");
                        editUsername.requestFocus();
                    } else if (Semail.equals("")) {
                        editEmail.setError("Mohon masukkan email");
                        editEmail.requestFocus();
                    } else if (Sstatus.equals("")) {
                        editStatus.setError("Mohon masukkan status");
                        editStatus.requestFocus();
                    } else {

                        dialog = ProgressDialog.show(MainActivity.this,
                                null,
                                "Wait a moment",
                                true,
                                false);

                        submitEdit(new Requests(
                                Susername.toLowerCase(),
                                Semail.toLowerCase(),
                                Sstatus.toLowerCase()), stringId);

                        finish();
                    }
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonCancel.equals("Cancel")) {
                    finish();
                } else {

                    database.child("Abdurrahman")
                            .child(stringId)
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();

                                    finish();
                                }
                            });
                }
            }

        });
    }

    private void submitUser(Requests requests) {
        database.child("Abdurrahman")
                .push()
                .setValue(requests)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        dialog.dismiss();

                        editUsername.setText("");
                        editEmail.setText("");
                        editStatus.setText("");

                        Toast.makeText(MainActivity.this, "data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                });
        }

    private void submitEdit(Requests requests, String id) {
        database.child("Abdurrahman")

                .child(id)
                .setValue(requests)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dialog.dismiss();

                        editUsername.setText("");
                        editEmail.setText("");
                        editStatus.setText("");

                        Toast.makeText(MainActivity.this, "Data telah diedit", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

