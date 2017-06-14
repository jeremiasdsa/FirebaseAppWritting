package com.example.jeremias.firebaseappwritting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private Button btn;
    private TextView tvMessage;
    private TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(myRef.child("CHAT-APP"));

        btn = (Button) findViewById(R.id.button);
        tvMessage = (TextView) findViewById(R.id.message);
        tvUserName = (TextView) findViewById(R.id.userName);


    }

    public void btnPressed(View v) {

        String message = String.valueOf(tvMessage.getText()).trim();
        String userName =  String.valueOf(tvUserName.getText()).trim();

        HashMap<String, String> result = new HashMap<>();



        result.put("message", message);
        result.put("userName", userName);

        myRef.child("CHAT-APP").child("ID-ANDROID").setValue(result);

    }
}
