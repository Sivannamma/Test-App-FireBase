package com.example.test_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Birthdate;
    private RadioButton Male;
    private RadioButton Female;
    private Button saveRegister;

    // firebase
    private FirebaseDatabase mDatabase; // creating database object
    private  DatabaseReference dbRootRef; // creating reference to our database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buttons and edit-text
        Name = (EditText) findViewById(R.id.editTextTextName);
        Birthdate = (EditText) findViewById(R.id.editTextTextBirthdate);
        Female = (RadioButton) findViewById(R.id.radioButtonFemale);
        Male = (RadioButton) findViewById(R.id.radioButtonMale);
        saveRegister=(Button) findViewById(R.id.buttonSaveRegister);


        mDatabase=FirebaseDatabase.getInstance();
        dbRootRef=mDatabase.getReference();
        readingFromDb();
    }

    // defining completionListener -> it will tell us if the saving has been succeeded or not.
    //it is linked to our database
    DatabaseReference.CompletionListener completionListener= new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            if(error!=null) // if != null it means we did recevie a msg, so it means we got an error
                Toast.makeText(MainActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();
            else // else it means we succeeded in saving the new client
                Toast.makeText(MainActivity.this, "Saved successfully",Toast.LENGTH_LONG).show();
        }
    };

    public void saveRegisterButtonClick(View view){
    Client client= new Client(Name.getText().toString(),Birthdate.getText().toString(),Female.isChecked()?"Female" : "Male","0");
    client.id=dbRootRef.push().getKey(); // we create a unique key in that way
    // inserting into clients node a child - new node as the new client.id that we recieved.
        // note : if clients isnt made it will create it and then insert the new node
    dbRootRef.child("clients").child(client.id).setValue(client,completionListener); // the completionListener can tell us if the save succeeded or not.
    }

    public void readingFromDb(){
        DatabaseReference dbRef;
        dbRef= mDatabase.getReference("/clients");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){ // going through all the childresns of clients
                   // Client client = child.getValue(Client.class);
                    Toast.makeText(getApplicationContext(),child.getKey(), Toast.LENGTH_LONG ).show();
                  //  Toast.makeText(getApplicationContext(), client.name, Toast.LENGTH_LONG).show();

                     Toast.makeText(getApplicationContext(),((Client)(child.getValue(Client.class))).toString(), Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}