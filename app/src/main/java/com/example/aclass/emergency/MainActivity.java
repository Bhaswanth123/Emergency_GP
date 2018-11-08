package com.example.aclass.emergency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText name, ph, fph;
    Button submit,skip;
    String Name, Ph, Fph;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       db=FirebaseDatabase.getInstance().getReference("Emergency");


        name = (EditText)findViewById(R.id.name);
        ph = (EditText) findViewById(R.id.ph);
        fph = (EditText) findViewById(R.id.fph);
        skip=(Button)findViewById(R.id.skip);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Ph = ph.getText().toString();
                Fph = fph.getText().toString();

                if(Name.isEmpty()&&Ph.isEmpty()&&Fph.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "All Fields are empty", Toast.LENGTH_SHORT).show();
                }

                else if((Name.isEmpty()&&Ph.isEmpty())||(Ph.isEmpty()&&Fph.isEmpty())||(Name.isEmpty()&&Fph.isEmpty()))
                {
                    Toast.makeText(MainActivity.this, "Two fields are empty", Toast.LENGTH_SHORT).show();
                }

                else if (Name.isEmpty() || Ph.isEmpty() || Fph.isEmpty()) {
                    if (Name.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter your name!", Toast.LENGTH_SHORT).show();
                    }
                    if (Ph.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter your number", Toast.LENGTH_SHORT).show();
                    }
                    if (Fph.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter your Family member's number", Toast.LENGTH_SHORT).show();
                    }
                } else {


                    String id=db.push().getKey();
                    EmerData emerData=new EmerData(id,Name,Ph,Fph);

                     db.child(id).setValue(emerData);
                    Toast.makeText(MainActivity.this, "Your data has been recorded", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    Bundle extras = new Bundle();
                    extras.putString("Family", Fph);
                    extras.putString("Name", Name);
                    intent.putExtras(extras);
                    startActivity(intent);

                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                Bundle extras = new Bundle();
                extras.putString("Family", Fph);
                extras.putString("Name", Name);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }
}
