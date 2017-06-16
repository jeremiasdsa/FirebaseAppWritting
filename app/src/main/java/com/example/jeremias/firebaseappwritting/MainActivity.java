package com.example.jeremias.firebaseappwritting;


import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button btnLoggin;
    private TextView tvRoomID;
    private TextView tvUserName;
    private TextView tvpassword;
    public static final String EXTRA_MESSAGE_ROOMID = "com.example.jeremias.firebaseappwritting.ROOMID";
    public static final String EXTRA_MESSAGE_USERNAME = "com.example.jeremias.firebaseappwritting.USERNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLoggin = (Button) findViewById(R.id.btnLoggin);
        tvpassword = (TextView) findViewById(R.id.tvPassword);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvRoomID = (TextView) findViewById(R.id.tvRoomId);


    }

    public void btnLoggin(View v) {

        Intent intent = new Intent(this, ChatActivity.class);
        String roomID = String.valueOf(tvRoomID.getText()).trim();
        String userName = String.valueOf(tvUserName.getText()).trim();
        String password = String.valueOf(tvpassword.getText()).trim();

        if(!(userName.equals("") && roomID.equals("") && password.equals(""))){
            intent.putExtra(EXTRA_MESSAGE_ROOMID, roomID);
            intent.putExtra(EXTRA_MESSAGE_USERNAME, userName);
            startActivity(intent);
        }
        else{
            Snackbar.make(findViewById(android.R.id.content), "Empty fields", Snackbar.LENGTH_LONG).show();
            hideKeyboard(MainActivity.this);
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
