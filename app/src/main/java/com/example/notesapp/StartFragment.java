package com.example.notesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class StartFragment extends Fragment {
    private Button registerBtn, loginBtn;
    private EditText registerText, loginText;
    RequestQueue requestQueue;

    public StartFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start,container,false);

        registerBtn = view.findViewById(R.id.registerBtn);
        loginBtn = view.findViewById(R.id.loginBtn);
        registerText = view.findViewById(R.id.register_user);
        loginText = view.findViewById(R.id.login_user);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = registerText.getText().toString();
                if(txt.equals(""))
                    Toast.makeText(getContext(), "Username required.", Toast.LENGTH_SHORT).show();
                else
                {
                    RegisterInDatabase(txt);
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = loginText.getText().toString();
                if(txt.equals(""))
                    Toast.makeText(getContext(), "Username required.", Toast.LENGTH_SHORT).show();
                else
                {
                    LoginDatabase(txt);
                }
            }
        });

        return view;
    }

    private void RegisterInDatabase(String username)
    {
        HttpsTrustManager.allowAllSSL();
        String url = "https://gigimandarina.000webhostapp.com/adduser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Taken"))
                {
                    Toast.makeText(getContext(), "Username already taken", Toast.LENGTH_LONG).show();
                }
                else if(response.equals("Failed"))
                {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(response),null).commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println(error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void LoginDatabase(String username)
    {
        HttpsTrustManager.allowAllSSL();
        String url = "https://gigimandarina.000webhostapp.com/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Failed"))
                {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(response),null).commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println(error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}