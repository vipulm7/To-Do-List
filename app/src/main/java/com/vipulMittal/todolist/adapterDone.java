package com.vipulMittal.todolist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class adapterDone extends RecyclerView.Adapter<adapterDone.viewHolder> {

    ArrayList<String> tasksLeft,tasksDone;
    MainActivity m;

    public adapterDone(ArrayList<String> tasksLeft, ArrayList<String> tasksDone,  MainActivity m) {
        this.tasksLeft = tasksLeft;
        this.tasksDone = tasksDone;
        this.m=m;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_per_item_done,parent,false);
        viewHolder viewholder=new viewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.task.setText(tasksDone.get(position));
        holder.checkBox.setChecked(true);
        Log.d("Vipul", "onBindViewHolder: ");

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.d("Vipul done", ""+isChecked);
            String t=tasksDone.get(position);
            tasksLeft.add(0,t);
            m.adpL.notifyItemInserted(0);
            Log.d("Apos3",""+0);
            tasksDone.remove(position);
            Log.d("vipul", "Unreachable ");
//            m.changed();
            notifyItemRemoved(position);
            Log.d("Rpos1",""+position);

            m.changed();

            if(m.toast!=null)
                m.toast.cancel();
            m.toast=Toast.makeText(m,"Added!!!",Toast.LENGTH_LONG);
            m.toast.show();
        });

        holder.del.setOnClickListener(v -> {
            String s=tasksDone.get(position);

            tasksDone.remove(position);
            notifyItemRemoved(position);
            Log.d("Apos1",""+position);
            m.changed();
            Log.d("check", ""+m.snackbar);
            m.snackbar=Snackbar.make(m.forSnackBar,"Deleted "+s+"!",Snackbar.LENGTH_LONG);
            m.snackbar.setAction("Undo", v1 -> {

                tasksDone.add(m.st.head.d.pos, m.st.head.d.task);
                notifyItemInserted(m.st.head.d.pos);
                Log.d("Apos2",""+m.st.head.d.pos);
                m.changed();
                m.st.remove();
                recursion(m.st);
            });
            m.snackbar.addCallback(new Snackbar.Callback(){
                @Override
                public void onDismissed(Snackbar snackbar, int event)
                {
                    if(event==0 || event==2)
                        m.st.empty();
                }
            });

            m.st.add(new Stack.data(s,position,m.snackbar));
            m.snackbar.show();
        });
    }

    private void recursion(Stack s) {
        if(s.head==null)
            return;
        m.snackbar=s.head.d.snackbar;
        m.snackbar.show();
    }

    @Override
    public int getItemCount() {
        return tasksDone.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        CheckBox checkBox;
        TextView task;
        ImageButton del;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
            task=itemView.findViewById(R.id.taskNameId);
            del=itemView.findViewById(R.id.deleteButton);
        }
    }
}
