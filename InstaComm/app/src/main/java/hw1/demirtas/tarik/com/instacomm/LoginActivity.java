package hw1.demirtas.tarik.com.instacomm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    //Login Activity UI Components
    TextView userName;
    TextView password;
    Button btnLogin;

    //Gesture Detector Declaration
    GestureDetectorCompat compat;

    //Progress Dialog for Long Operations
    ProgressDialog mProgressDialog;

    //Volley Object
    MyVolleyUsage myVolleyUsage;

    //ArrayList to fill with JSON
    ArrayList<HashMap<String,String>> mArrayList = new ArrayList<>();

    //JSON Objects
    private String jsonStr;
    private JSONArray users;
    private JSONObject userOBJ;

    //JSON Tags
    public static final String TAG_USERS = "users";
    public static final String TAG_ID = "id"; //Optional: If the user's ID will be included in the project
    public static final String TAG_USERNAME = "uname";
    public static final String TAG_PASSWORD = "upassword";
    public static final String TAG_TYPE = "type";
    public static final String TAG_USES = "uses";


    @Override
    protected void onCreate(Bundle savedInstanceState) { //Assign necessary values
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        compat = new GestureDetectorCompat(LoginActivity.this,new MGestureListener());

        btnLogin = findViewById(R.id.btnLogin);
        userName = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPassword);

        myVolleyUsage = new MyVolleyUsage(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { // Assign the Gesture Listener on Login Activity
        compat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class getUsers extends AsyncTask<Void, Void, Void> { //Getting server JSON String using Async Task
        HashMap<String, String> user;
        String text;

        // Main job should be done here
        @Override
        protected Void doInBackground(Void... params) {
            String url_select = ""; //TODO insert Server Address
            URL url;
            StringBuffer response = new StringBuffer();
            try {
                url = new URL(url_select);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("invalid url");
            }

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(false);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

                // handle the response
                int status = conn.getResponseCode();
                if (status != 200) {
                    throw new IOException("Post failed with error code " + status);
                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }

                //Here is your json in string format
                String responseJSON = response.toString();
                jsonStr = responseJSON;
            }

            if (jsonStr != null) {
                try {
                    userOBJ = new JSONObject(jsonStr);
                    // Getting JSON Array
                    users = userOBJ.getJSONArray(TAG_USERS);

                    // looping through all books
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);

                        String name = c.getString(TAG_USERNAME);
                        String author = c.getString(TAG_PASSWORD);
                        String year = c.getString(TAG_TYPE);
                        String imgName = c.getString(TAG_USES);

                        HashMap<String, String> book = new HashMap<String, String>();

                        book.put(TAG_USERNAME, name);
                        book.put(TAG_PASSWORD, author);
                        book.put(TAG_TYPE, year);
                        book.put(TAG_USES, imgName);

                        User u = new User(book.get(TAG_USERNAME),book.get(TAG_PASSWORD),Integer.parseInt(book.get(TAG_TYPE)),Integer.parseInt(book.get(TAG_USES)));
                        if(u != null){
                            Commons.users.add(u);
                            Log.d("JSON","User ADDED.....................");
                        }
                    }
                } catch (JSONException ee) {
                    ee.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Downloading your data...");
            mProgressDialog.show();
            mProgressDialog.setCancelable(false);
        }

        // What do you want to do after doInBackground() finishes
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            boolean success = false;

            // Dismiss the progress dialog
            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();


            int i = 0;
            while(i<Commons.users.size()) {
                if (userName.getText().toString().matches(Commons.users.get(i).getUsername()) &&
                        password.getText().toString().matches(Commons.users.get(i).getPassword())) {
                    success = true;
                    Commons.curUser = Commons.users.get(i);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                i++;
            }

            if(!success){
                Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void login(View view) {
        new getUsers().execute();
    } //Executing Async Task on Login Button Press

    class MGestureListener extends GestureDetector.SimpleOnGestureListener{ //Gesture Listener(Simple Listener)
        @Override
        public boolean onDoubleTap(MotionEvent e) { //DoubleTap for Testing without Login
            Commons.curUser = new User("admin","admin",1,12);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) { //Long Press to Display Volley Result for JSON in the Log
            myVolleyUsage.requestJSONObject(""); //TODO insert your server adress here
            super.onLongPress(e);
        }
    }

    @Override
    public void onDestroy(){ //Finish the Progress Dialog when the Activity is Destroyed
        super.onDestroy();
        if ( mProgressDialog!=null && mProgressDialog.isShowing() ){
            mProgressDialog.cancel();
        }
    }
}
