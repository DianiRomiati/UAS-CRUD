package com.ipungdev.loginregistercrud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ipungdev.loginregistercrud.R;
import com.ipungdev.loginregistercrud.models.Project;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>{

    private List<Project> project;
    private int rowLayout;
    private Context context;
    public ProjectAdapter(List<Project> project, int rowLayout, Context context) {
        this.project = project;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    //View holder
    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        LinearLayout proyek_layout;
        TextView nama;
        TextView intro;
        TextView lokasi;
        TextView harga;

        public ProjectViewHolder(View v) {
            super(v);
            proyek_layout = (LinearLayout) v.findViewById(R.id.proyek_layout);
            nama = (TextView) v.findViewById(R.id.nama);
            intro = (TextView) v.findViewById(R.id.intro);
            lokasi = (TextView) v.findViewById(R.id.lokasi);
            harga = (TextView) v.findViewById(R.id.harga);
        }
    }

    @Override
    public ProjectAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, final int position) {

        holder.nama.setText(project.get(position).getNama());
        holder.intro.setText(project.get(position).getIntro());
        holder.lokasi.setText(project.get(position).getLokasi());
        holder.harga.setText(project.get(position).getHarga().toString());
    }

    @Override
    public int getItemCount() {
        return project.size();
    }
}
