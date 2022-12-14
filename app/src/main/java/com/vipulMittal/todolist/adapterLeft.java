package com.vipulMittal.todolist;

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class adapterLeft extends RecyclerView.Adapter<adapterLeft.viewHolder> {

    ArrayList<String> tasksLeft,tasksDone;
    public static final String TAG="Vipul";
    ClickListener clickListener;
    CheckListener checkListener;

    public adapterLeft(ArrayList<String> tasksLeft, ArrayList<String> tasksDone, ClickListener clickListener, CheckListener checkListener) {
        this.tasksLeft = tasksLeft;
        this.tasksDone = tasksDone;
        this.clickListener=clickListener;
        this.checkListener=checkListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_per_item_left, parent,false);
        viewHolder viewholder=new viewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.task.setText(tasksLeft.get(position));
        Log.d(TAG, "Adapter left :: onBind "+position+" "+tasksLeft.get(position));
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(false);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
	        if(checkListener!=null)
		        checkListener.onCheckChanged(holder);

        });
        Log.d("Vipul", "onBind  Check Removed");


//        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//
//            Log.d("Vipul", ""+isChecked);
//            String t=tasksLeft.get(position);
//            tasksDone.add(0,t);
//            m.adpD.notifyItemInserted(0);
//            Log.d("Apos4",""+0);
//            tasksLeft.remove(position);
////            m.changed();
//            notifyItemRemoved(position);
//            Log.d("Rpos2",""+position);
//
//            m.changed();
//
//            if(m.toast!=null)
//                m.toast.cancel();
//            m.toast=Toast.makeText(m,"Hurray!!!",Toast.LENGTH_LONG);
//            m.toast.show();
//        });

//        holder.edit.setOnClickListener(v -> {
//            EditText edit1=new EditText(m);
//            edit1.setHint(tasksLeft.get(position));
//            AlertDialog.Builder alert=new AlertDialog.Builder(m);
//
////            edit1.addTextChangedListener(new TextWatcher() {
////                @Override
////                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////                }
////
////                @Override
////                public void onTextChanged(CharSequence s, int start, int before, int count) {
////                    if(s.toString().trim().length()==0)
////                        alert.
////                }
////
////                @Override
////                public void afterTextChanged(Editable s) {
////
////                }
////            });
//
//            alert.setTitle("Rename")
//                    .setNegativeButton("Cancel", (dialog, which) -> {})
//                    .setView(edit1)
//                    .setPositiveButton("Done", (dialog, which) -> {
//                        String a=edit1.getText().toString();
//                        if(a.trim().length()!=0) {
//                            tasksLeft.set(position, a);
//                            notifyItemChanged(position);
//                            if(m.toast!=null)
//                                m.toast.cancel();
//                            m.toast=Toast.makeText(m,"Renamed!!!",Toast.LENGTH_SHORT);
//                            m.toast.show();
//                        }
//                        else{
//                            if(m.toast!=null)
//                                m.toast.cancel();
//                            m.toast=Toast.makeText(m,"Same as before!!!",Toast.LENGTH_SHORT);
//                            m.toast.show();
//                        }
//                    })
//
//                    .show();
//            alert.create();
//        });
    }

    @Override
    public int getItemCount() {
        return tasksLeft.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        CheckBox checkBox;
        TextView task;
        ImageButton edit;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
            task=itemView.findViewById(R.id.taskNameId);
            edit=itemView.findViewById(R.id.imageButtonEditing);

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int position=getAdapterPosition();
                if(checkListener!=null && position!=RecyclerView.NO_POSITION)
                    checkListener.onCheckChanged(this);

            });
            edit.setOnClickListener(v -> {
                int position=getAdapterPosition();
                if(clickListener!=null && position!=RecyclerView.NO_POSITION)
                    clickListener.onEditClicked(this);
            });
        }

    }

    public interface ClickListener{
        void onEditClicked(RecyclerView.ViewHolder viewHolder);
    }

    public interface CheckListener{
        void onCheckChanged(RecyclerView.ViewHolder viewHolder);
    }
}
