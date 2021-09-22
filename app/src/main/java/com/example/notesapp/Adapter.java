package com.example.notesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    ArrayList<Note> list;
    String id;
    OnNoteListener mOnNoteListener;

    public Adapter(Context context, ArrayList<Note> data, String id, OnNoteListener onNoteListener)
    {
        this.context = context;
        this.list = data;
        this.id = id;
        this.mOnNoteListener = onNoteListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notes_layout,parent,false);
        return new MyViewHolder(view, mOnNoteListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Note note = list.get(position);
        holder.title.setText(note.getTitle());
        holder.position = position;
        holder.id = id;
        holder.context = context;
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageButton btn;
        int position;
        String id;
        Context context;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            title = itemView.findViewById(R.id.notes_title);
            btn = itemView.findViewById(R.id.trash_btn);
            this.onNoteListener = onNoteListener;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RemoveFromDatabase(String.valueOf(position), id, context);
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, list.size());
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    public static void RemoveFromDatabase(String p, String id, Context context){

        HttpsTrustManager.allowAllSSL();
        String url = "https://gigimandarina.000webhostapp.com/removenote.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Failed"))
                {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("id", id);
                params.put("position", p);
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }

}
