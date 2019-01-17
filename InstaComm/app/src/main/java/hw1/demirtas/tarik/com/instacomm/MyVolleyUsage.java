package hw1.demirtas.tarik.com.instacomm;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyVolleyUsage {
    RequestQueue queue;
    Context context;

    public MyVolleyUsage(Context context){
        this.context =context;
        queue = Volley.newRequestQueue(context);

    }
    // Request String by using Volley
    public void requestForString(String urlString) {
        /*
         * StringRequest parameters are method:POST or GET request, destination
         * url and two listener: Response.Listener and Response.ErrorListener
         *
         * Response.Listener, is called when the response is received and is
         * ready. In this case the onResponse method is called. This method runs
         * in the main thread so we can update, for example, some UI wigtes.
         * Response.ErrorListener: If something goes wrong, the
         * ErrorListener.onErrorResponse is called. In this method
         */
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                urlString, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Result", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result",
                        "ERROR string request " + error.toString());
            }
        });

        // add request to queue
        queue.add(stringRequest);
    }

    // JSONObject request by using Volley
    public void requestJSONObject(String urlString) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, urlString, null,

                new Response.Listener<JSONObject>() {
                    JSONObject object;
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Result: ", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result",
                        "ERROR JSONObject request" + error.toString());
            }
        });

        // add request to queue
        queue.add(jsonObjectRequest);
    }

    // JSONArray request by using Volley
    public void requestJSOnArray(String urlString) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, urlString, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Result: ", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result",
                        "ERROR JSONArray request" + error.toString());
            }
        });
        // add request to queue
        queue.add(jsonArrayRequest);

    }

    public void sendGETRequest(String urlString) {
        // To get
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                urlString, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Request", response.toString());
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError arg0) {
                Toast.makeText(context, "ERROR",
                        Toast.LENGTH_SHORT).show();
            }
        }) {
            // Use this to add parameters to request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("num1", "10"); // num1 is the name of the parameter
                // and 10 is the value
                return params;
            }
        };
        ;
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    /* Post data with JSON notation */
    public void sendPOSTRequest(String urlString) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response,
                        Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uname", "Nese");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
