package com.example.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Groupchat extends AppCompatActivity {
    Button Send;
    EditText TextField;
    ListView Texts;
    ArrayList<String> messages = new ArrayList<String>();
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    ChatAdapter messageAdapter;
    String name;


    private class ChatAdapter extends ArrayAdapter<String> {


        public ChatAdapter(Context ctx) {
            super(ctx, 0);

        }

        public int getCount() {
            return messages.size();
        }

        public String getItem(int position) {
            return messages.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = Groupchat.this.getLayoutInflater();
            String[] messages = getItem(position).split(" ", 3);

            String user = messages[0] + " "+ messages[1];
            String messageString = messages[2];

            View result = null;
            if (user.compareTo(name) != 0 ) {
                result = inflater.inflate(R.layout.message_from_user, null);
                TextView msg = result.findViewById(R.id.message);
                msg.setText(messageString);

            }
            else {
                result = inflater.inflate(R.layout.message_from_person, null);
                TextView msg = result.findViewById(R.id.message);
                msg.setText(messageString);
            }

            TextView name = (TextView) result.findViewById(R.id.nameMessage);
            name.setText(user); // get the string at position
            return result;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);
        Send = findViewById(R.id.SendButton);
        TextField = findViewById(R.id.EnterMsg);
        Texts = findViewById(R.id.ListArray);
        messageAdapter = new ChatAdapter(this);
        Texts.setAdapter(messageAdapter);
        Intent intent = getIntent();

        String groupId = intent.getStringExtra("groupid");
        name = intent.getStringExtra("name");

        GetInformation(groupId);


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = TextField.getText().toString();
                messages.add(name + " " + message);
                messageAdapter.notifyDataSetChanged();
                WriteMessagetoDatabase(messages.size(), groupId, name );


            }
        });



    }

    public void WriteMessagetoDatabase(int position, String groupid, String name)
    {


        db.collection("groups")
                .document(groupid)
                .update("messages", FieldValue.arrayUnion( messages.get(position-1)));
        Toast.makeText(this, "Sent!", Toast.LENGTH_LONG);
        TextField.setText("");

    }

    public void GetInformation(String groupid)
    {
        db.collection("groups").document(groupid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                messages.clear();
                                messages =  (ArrayList<String> ) document.get("messages");

                                if (messages!=null) {
                                    messageAdapter.notifyDataSetChanged();
                                }

                            } else {
                                Log.d("Helo", "No such document");
                            }
                        } else {
                            Log.d("Hello", "get failed with ", task.getException());
                        }
                    }
                });
    }
}


/*

public class ChatWindow extends AppCompatActivity {




    private static final String ACTIVITY_NAME = "CHATWINDOW ACTIVITY";
    Button Send;
    TextView TextField;
    ListView Texts;
    ChatDatabaseHelper chat = null;
    ArrayList<String> messages = new ArrayList<String>();
    Cursor cursor;






    @SuppressLint("Range") // The code gave a warning of cursor being -1 which didnt apply because im checking for the null value first and moving it to the first colimn
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chat = new ChatDatabaseHelper(this);
        SQLiteDatabase chats = chat.getWritableDatabase();
        setContentView(R.layout.activity_chat_window);
        Send = findViewById(R.id.SendButton);
        TextField = (EditText) findViewById(R.id.TextView);
        Texts = findViewById(R.id.ListArray);
        ChatAdapter messageAdapter = new ChatAdapter(this);
        Texts.setAdapter(messageAdapter);

        if (findViewById(R.id.FrameId) !=null )
        {
            Toast.makeText(this, "In Tablet view", Toast.LENGTH_SHORT).show();
        }

        Texts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ChatWindow.this, "item " + i + " Clicked", Toast.LENGTH_SHORT).show();


            }
        });

        cursor = chats.rawQuery(
                "SELECT "  + ChatDatabaseHelper.KEY_ID + "," + chat.KEY_MESSAGE + "  FROM " + ChatDatabaseHelper.TABLE_NAME, null);



        if (cursor!=null ) {
            Log.i(ACTIVITY_NAME, "Cursors column count =" + cursor.getColumnCount());
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.i(ACTIVITY_NAME, "SQL Message: " + cursor.getString(cursor.getColumnIndex(chat.KEY_MESSAGE)));
                messages.add(cursor.getString(cursor.getColumnIndex(chat.KEY_MESSAGE)));
                cursor.moveToNext();

            }
        }
        // Also, print an information message about the Cursor:

        for (int i = 0; i < cursor.getColumnCount(); i++) {

            {
                Log.i(ACTIVITY_NAME, "Column name : " + cursor.getColumnName(i));
            }

            Send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContentValues storeMessage = new ContentValues();
                    storeMessage.put(chat.KEY_MESSAGE, TextField.getText().toString());
                    chats.insert(ChatDatabaseHelper.TABLE_NAME, "NullPlaceHolder",  storeMessage);
                    messages.add(TextField.getText().toString());
                    messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/
                    TextField.setText("");


                }
            });


        }

    }

    public void onDestroy() {
        Log.i(ACTIVITY_NAME, "Destroying Data and closing database");
        super.onDestroy();
        chat.close();

    }
*/
