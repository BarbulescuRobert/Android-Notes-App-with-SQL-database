package com.example.notesapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainFragment extends Fragment implements Adapter.OnNoteListener {

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Note> notes = new ArrayList<Note>();
    String id;

    public MainFragment(String id){
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);

        ReadNotesFromDatabase();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item1){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NoteFragment(this.id),null).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ReadNotesFromDatabase(){

        HttpsTrustManager.allowAllSSL();
        String url = "https://gigimandarina.000webhostapp.com/takenotes.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    if(!notes.isEmpty())
                        notes.clear();
                    for (int i = 0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        String note = object.getString("note");
                        String titl = object.getString("title");
                        notes.add(new Note(titl,note));

                    }
                    adapter = new Adapter(getContext(), notes, id, MainFragment.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                } catch (JSONException e) {

                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();

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
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    @Override
    public void onNoteClick(int position) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NoteFragment(this.id, position),null).commit();
    }
}