package com.example.notesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NoteFragment extends Fragment {
    private EditText title,note;
    String id,note_nr;
    int position = -1;

    public NoteFragment(String id){this.id = id; }
    public NoteFragment(String id, int position){this.id = id; this.position=position;}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_note,container,false);

            title = view.findViewById(R.id.title);
            note = view.findViewById(R.id.note);
            if(position != -1)
                TakeNoteFromDatabase(id, position);

            return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item1){
            if(position == -1)
                SaveNote(title.getText().toString(), note.getText().toString());
            else
                UpdateNote(note_nr, title.getText().toString(), note.getText().toString());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void SaveNote(String t, String n)
    {
        if(t.equals("") && n.equals(""))
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(id),null).commit();
        }
        else
        {
            AddNoteToDatabase(t,n);
        }
    }
    public void UpdateNote(String nr, String t, String n)
    {
        if(t.equals("") && n.equals(""))
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(id),null).commit();
        }
        else
        {
            UpdateDatabase(nr,t,n);
        }
    }

    public void AddNoteToDatabase(String t, String n){

        HttpsTrustManager.allowAllSSL();
        String url = "https://gigimandarina.000webhostapp.com/addnote.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Failed"))
                {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(id),null).commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("id", id);
                params.put("title", t);
                params.put("note", n);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    public void TakeNoteFromDatabase(String i, int p){

        HttpsTrustManager.allowAllSSL();
        String url = "https://gigimandarina.000webhostapp.com/takenote.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject object = array.getJSONObject(p);

                        String not = object.getString("note");
                        String titl = object.getString("title");
                        note_nr = object.getString("nr");
                        note.setText(not);
                        title.setText(titl);
                    } catch (JSONException e) {

                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("error", e.toString());

                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("error", error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("id", i);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    public void UpdateDatabase(String nr, String t, String n)
    {
        HttpsTrustManager.allowAllSSL();
        String url = "https://gigimandarina.000webhostapp.com/updaterow.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Failed"))
                {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment(id),null).commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("nr", nr);
                params.put("title", t);
                params.put("note", n);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}