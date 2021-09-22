package com.example.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.notesapp.HttpsTrustManager;
import com.example.notesapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new StartFragment(), null).commit();
    }

//    private void WriteInDatabase()
//    {
//        HttpsTrustManager.allowAllSSL();
//        String un = usernamee.getText().toString();
//        String tt = tweet.getText().toString();
//        String url = "https://gigimandarina.000webhostapp.com/signup.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(MainActivity.this, "Succes", Toast.LENGTH_LONG).show();
//                System.out.println(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
//                System.out.println(error.toString());
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String,String> params = new HashMap<>();
//                params.put("username", un);
//                params.put("tweet", tt);
//                return params;
//            }
//        };
//        requestQueue = Volley.newRequestQueue(MainActivity.this);
//        requestQueue.add(stringRequest);
//    }
//
//    public void ReadFromDatabase(){
//
//        HttpsTrustManager.allowAllSSL();
//        String whatidd = whatid.getText().toString();
//        String url = "https://gigimandarina.000webhostapp.com/getdata.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray array = new JSONArray(response);
//
//                    for (int i = 0; i<array.length(); i++){
//
//                        JSONObject object = array.getJSONObject(i);
//
//                        String idd = object.getString("id");
//                        String ttweet = object.getString("tweet");
//                        id.setText(idd);
//                        tweett.setText(ttweet);
//                    }
//
//                } catch (JSONException e) {
//
//                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String,String> params = new HashMap<>();
//                params.put("id", whatidd);
//                return params;
//            }
//        };
//        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
//    }
}
