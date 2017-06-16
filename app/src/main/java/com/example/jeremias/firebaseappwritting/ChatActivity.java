package com.example.jeremias.firebaseappwritting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.jeremias.firebaseappwritting.MainActivity.hideKeyboard;

public class ChatActivity extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private Button btnSend;
    private TextView tvMessage;
    private TextView tvChat;
    private TextView container;
    private String roomID;
    private String userName;
    private HashMap<String,String> dicColors;
    private ValueEventListener chatListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initializeVariables();

        chatListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    HashMap<String, String>  valor = (HashMap<String, String>)
                            dataSnapshot.child(roomID).getValue();
                    String userName = valor.get("user");
                    String message = valor.get("message");
                    String text = formatChat(userName, message);
                    tvChat.setText(Html.fromHtml(text));
                    scrollDownTextChat();

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        myRef.addValueEventListener(chatListener);

    }

    private String formatChat(String userName, String message) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = "("+df.format(Calendar.getInstance().getTime())+")";

        generateColors(userName);
        container.append("<font color=#cccccc><small>"+date+"</small></font>"+
                "<strong><font color='"+dicColors.get(userName)+"'>"+userName+
                "</font></strong>"+": "+message+"<br/> ");
        return String.valueOf(container.getText());
    }

    private void generateColors(String userName) {
        int r = (int) (0xff * Math.random());
        int g = (int) (0xff * Math.random());
        int b = (int) (0xff * Math.random());

        String hex = String.format("#%02x%02x%02x", r, g,b);

        if(!dicColors.containsKey(userName)){
            dicColors.put(userName,hex);
        }
    }

    private void scrollDownTextChat() {
        final Layout layout = tvChat.getLayout();
        if(layout != null){
            int scrollDelta = layout.getLineBottom(tvChat.getLineCount() - 1)
                    - tvChat.getScrollY() - tvChat.getHeight();
            if(scrollDelta > 0)
                tvChat.scrollBy(0, scrollDelta);
        }
    }

    private void initializeVariables() {
        Intent intent = getIntent();
        roomID = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_ROOMID);
        userName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_USERNAME);

        btnSend = (Button) findViewById(R.id.button);
        tvMessage = (TextView) findViewById(R.id.message);
        tvChat = (TextView) findViewById(R.id.tvChat);
        container = new TextView(this);
        dicColors = new HashMap<String,String>();
        tvChat.setMovementMethod(new ScrollingMovementMethod());
    }

    public void btnPressed(View v) {

        String message = String.valueOf(tvMessage.getText()).trim();
        HashMap<String, String> result = new HashMap<>();
        result.put("message", message);
        result.put("user", userName);
        myRef.child(String.valueOf(roomID)).setValue(result);
        tvMessage.setText("");
        hideKeyboard(ChatActivity.this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                myRef.removeEventListener(chatListener);
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
