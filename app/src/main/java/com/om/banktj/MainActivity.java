package com.om.banktj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    EditText editName, editPassword;
    EditText UserIdInput, MoneyInput;
    TextView balanceView;
    RequestQueue queue;
    String URL = "https://user.tjhsst.edu/2024ogole/data";

    String useremail;
    String userid;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        mAuth = FirebaseAuth.getInstance();

    }

    public void getTransHist(String user) {
        queue = Volley.newRequestQueue(this);
        String transhistURL = URL + "/transhistinfo?userid=" + user;
        ScrollView scrollView = (ScrollView) findViewById(R.id.transhist);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);

        //TextView textView = new TextView(this);
        //textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        StringRequest request = new StringRequest(Request.Method.GET, transhistURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                textView.setText("Your Test Worked");
//                textView.setTextSize(18);
//                linearLayout.addView(textView);
                try {

                    //format response to JSON object called "data"
                    JSONObject object = new JSONObject("{\"data\":" + response + "}");
                    //convert object to an array of objects
                    JSONArray jsonArray = object.getJSONArray("data");
                    Log.i("RAW", "" + jsonArray);
                    // Loop through the array and create a TextView for each item
                    //for (int i = 0; i < jsonArray.length(); i++) {
                    for (int i = jsonArray.length() - 1; i > -1; i--) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        // Create a new TextView
                        TextView textView = new TextView(MainActivity.this);
                        textView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));

                        // Set the text for the TextView
                        String text = "ID: " + jsonObject.getInt("id") +
                                "\nUser From: " + jsonObject.getString("userfrom") +
                                "\nUser To: " + jsonObject.getString("userto") +
                                "\nAmount: " + jsonObject.getInt("amount") +
                                "\nDate Time: " + jsonObject.getString("datetime");
                        textView.setText(text);

                        // Add the TextView to the LinearLayout
                        linearLayout.addView(textView);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("volley error" + error.toString());
            }
        });
        queue.add(request);
    }

    public void getTransHistTo(String user) {
        queue = Volley.newRequestQueue(this);
        String transhistURL = URL + "/transhistinfoto?userid=" + user;
        ScrollView scrollView = (ScrollView) findViewById(R.id.transhistto);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);

        //TextView textView = new TextView(this);
        //textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        StringRequest request = new StringRequest(Request.Method.GET, transhistURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                textView.setText("Your Test Worked");
//                textView.setTextSize(18);
//                linearLayout.addView(textView);
                try {

                    //format response to JSON object called "data"
                    JSONObject object = new JSONObject("{\"data\":" + response + "}");
                    //convert object to an array of objects
                    JSONArray jsonArray = object.getJSONArray("data");
                    Log.i("RAW", "" + jsonArray);
                    // Loop through the array and create a TextView for each item
                    //for (int i = 0; i < jsonArray.length(); i++) {
                    for (int i = jsonArray.length() - 1; i > -1; i--) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        // Create a new TextView
                        TextView textView = new TextView(MainActivity.this);
                        textView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));

                        // Set the text for the TextView
                        String text = "ID: " + jsonObject.getInt("id") +
                                "\nUser From: " + jsonObject.getString("userfrom") +
                                "\nUser To: " + jsonObject.getString("userto") +
                                "\nAmount: " + jsonObject.getInt("amount") +
                                "\nDate Time: " + jsonObject.getString("datetime");
                        textView.setText(text);

                        // Add the TextView to the LinearLayout
                        linearLayout.addView(textView);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("volley error" + error.toString());
            }
        });
        queue.add(request);
    }

    public void createAccount(View view) {

        editName = (EditText) findViewById(R.id.username_input);
        editPassword = (EditText) findViewById(R.id.password_input);
        // get text from EditText name view
        String email = editName.getText().toString();
// get text from EditText password view
        String password = editPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setContentView(R.layout.activity_main);

                            System.out.println("Testing: " + mAuth.getCurrentUser().getEmail());
                        } else {
                            setContentView(R.layout.activity_main);
                            // If sign in fails, display a message to the user.

                            System.out.println("Testing: failed to create account");
                        }

                        // ...
                    }
                });
    }

    public void signIn(View view) {
        textView = findViewById(R.id.UserInfo);
        editName = (EditText) findViewById(R.id.username_input);
        editPassword = (EditText) findViewById(R.id.password_input);
        // get text from EditText name view
        String email = editName.getText().toString();
// get text from EditText password view
        String password = editPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setContentView(R.layout.activity_main);
                            //getUserInfo(mAuth.getCurrentUser().getEmail());
                            useremail = mAuth.getCurrentUser().getEmail();
                            goMain();
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("Testing: " + mAuth.getCurrentUser().getEmail());
                        } else {
                            // If sign in fails, display a message to the user.
                            setContentView(R.layout.activity_main);

                            System.out.println("Testing: failed to create account");
                        }
                    }
                });

    }

    public void goSendMoney(View view) {
        setContentView(R.layout.send_money);
    } //to android page

    public void goTransHist(View view) {
        setContentView(R.layout.trans_hist);
        getTransHist(userid);
        getTransHistTo(userid);
    }

    public void goMain(View view) {
        setContentView(R.layout.activity_main);
        updateUserInfo(useremail);
    }

    public void goMain() {
        setContentView(R.layout.activity_main);
        Log.i("GETUSERINFO", useremail);
        updateUserInfo(useremail);
    }

    public void updateUserInfo(String email) {
        queue = Volley.newRequestQueue(this);
        Log.i("GETUSERINFO", "Working");
        String userinfoURL = URL + "/userinfo?email=" + email;
        //String userinfoURL = URL + "/userinfo?email=test@test.com";
        textView = findViewById(R.id.UserInfo);
        balanceView = findViewById(R.id.balance);

        StringRequest request = new StringRequest(Request.Method.GET, userinfoURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //format response to JSON object called "data"
                    JSONObject object = new JSONObject("{\"data\":" + response + "}");
                    //convert object to an array of objects
                    JSONArray array = object.getJSONArray("data");

                    userid = array.getJSONObject(0).getString("userid");
                    textView.setText("" + array.getJSONObject(0).getString("userid"));
                    balanceView.setText("$" + array.getJSONObject(0).getString("balance"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("volley error" + error.toString());
            }
        });
        queue.add(request);
    }
    
    public void sendMoney(View view) {
        UserIdInput = (EditText)findViewById(R.id.UserIdInput);
        MoneyInput = (EditText)findViewById(R.id.MoneyInput);

        final String recipient = UserIdInput.getText().toString();
        final String amount = MoneyInput.getText().toString();

        // Create an AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Confirm Transaction")
                .setMessage("Are you sure you want to send $" + amount + " to user " + recipient + "?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked YES button
                        queue = Volley.newRequestQueue(getApplicationContext());
                        Log.i("SENDMONEY", "Working");

                        //time stuff
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        String datetime = formatter.format(date);
                        Log.i("DATETIME",datetime);

                        String newURL = URL + "/transaction?useridTO=" + recipient + "&amtTO=" + amount + "&useridFROM=" + userid + "&amtFROM=" + amount + "&datetime=" + datetime;
                        StringRequest request = new StringRequest(Request.Method.GET, newURL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //format response to JSON object called "data"
                                    JSONObject object = new JSONObject("{\"data\":"+response+"}");
                                    //convert object to an array of objects
                                    JSONArray array = object.getJSONArray("data");
                                    Log.i("USER","" + array.getJSONObject(0).getString("userid"));
                                    Log.i("MONEY","$" + array.getJSONObject(0).getString("balance"));
                                    Log.i("USER","" + array.getJSONObject(1).getString("userid"));
                                    Log.i("MONEY","$" + array.getJSONObject(1).getString("balance"));
                                    //get first object and display its id
                                    //                      textView.setText("" + array.getJSONObject(0).getString("userid"));
                                    //                      balanceView.setText("$" + array.getJSONObject(0).getString("balance"));
                                    goMain();
                                    Toast.makeText(getApplicationContext(), "You sent $" + amount + " to " + recipient + "!", Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("volley error"+ error.toString());
                            }
                        });
                        queue.add(request);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    } //sends transaction to server

}