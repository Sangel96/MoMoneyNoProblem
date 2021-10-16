package com.example.momoneynoproblem.Transaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.databinding.ActivityDeleteTransactionBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class deleteTransaction extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    ActivityDeleteTransactionBinding binding;
    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = binding.DateEdit2.getText().toString();
                if(!date.isEmpty()){

                    delteData(date);

                } else {
                    Toast.makeText(deleteTransaction.this, "Enter the transaction date for deleting",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void delteData(String date) {
        Task<Void> databaseReference = firebaseDatabase.getInstance().getReference("Transactions")
                .child(date).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(deleteTransaction.this, "Successfully Deleted",Toast.LENGTH_SHORT).show();
                    binding.DateEdit2.setText("");

                } else {

                    Toast.makeText(deleteTransaction.this, "Failed",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}