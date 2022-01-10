package com.vipulMittal.todolist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText task;
    Button add,reset;
    ArrayList<String> tasksLeft=new ArrayList<>(),tasksDone=new ArrayList<>();
    RecyclerView recyclerViewLeft,recyclerViewDone;
    adapterLeft adpL;
    adapterDone adpD;
    TextView t,t2;
    Toast toast;
    SharedPreferences saveLeft,saveDone;
    Snackbar snackbar;
    ConstraintLayout forSnackBar;
    Stack st=new Stack();
    ActionBar bar;
    public static final String TAG="Vipul";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Lifecycle", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewLeft=findViewById(R.id.RecyclerViewIdLeft);
        recyclerViewDone=findViewById(R.id.RecyclerViewIdDone);
        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDone.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLeft.setNestedScrollingEnabled(false);
        recyclerViewDone.setNestedScrollingEnabled(false);
        adpL=new adapterLeft(tasksLeft, tasksDone, this,  new adapterLeft.ClickListener() {
            @Override
            public void onEditClicked(int position) {
                EditText edit1=new EditText(MainActivity.this);
                edit1.setHint(tasksLeft.get(position));
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);

//            edit1.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if(s.toString().trim().length()==0)
//                        alert.
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });

                alert.setTitle("Rename")
                        .setNegativeButton("Cancel", (dialog, which) -> {})
                        .setView(edit1)
                        .setPositiveButton("Done", (dialog, which) -> {
                            String a=edit1.getText().toString();
                            if(a.trim().length()!=0) {
                                tasksLeft.set(position, a);
                                recyclerViewLeft.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        adpL.notifyItemChanged(position);
                                    }
                                });

                                if(toast!=null)
                                    toast.cancel();
                                toast=Toast.makeText(getApplicationContext(),"Renamed!!!",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else{
                                if(toast!=null)
                                    toast.cancel();
                                toast=Toast.makeText(MainActivity.this,"Same as before!!!",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        })

                        .show();
                alert.create();
            }
        },
        new adapterLeft.CheckListener() {
            @Override
            public void onCheckChanged(int position, boolean isChecked) {
                Log.d("Vipul", "adapter left "+isChecked);
                String t=tasksLeft.get(position);
                Log.d("Vipul", "adapter left "+position);
                tasksDone.add(0,t);
//                adpD.notifyItemInserted(0);
//                recyclerViewDone.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        adpD.notifyItemInserted(0);
//                    }
//                });

                runOnUiThread(() -> adpD.notifyItemInserted(0));
                Log.d("Apos4",""+0);
                tasksLeft.remove(position);
//            m.changed();
//                adpL.notifyItemRemoved(position);
//                recyclerViewLeft.post(() -> adpL.notifyItemRemoved(position));
                runOnUiThread(() -> adpL.notifyItemRemoved(position));
                Log.d("Rpos2",""+position);

                changed();

                Log.d(TAG,"Toast started");
                if(toast!=null)
                    toast.cancel();
                toast=Toast.makeText(getApplicationContext(),"Hurray!!!",Toast.LENGTH_LONG);
                toast.show();
            }
        });

        adpD=new adapterDone(tasksLeft, tasksDone, new adapterDone.ClickListener() {
            @Override
            public void onDeleteCLick(int position) {
                String s = tasksDone.get(position);

                tasksDone.remove(position);
                recyclerViewDone.post(new Runnable() {
                    @Override
                    public void run() {
                        adpD.notifyItemRemoved(position);
                    }
                });
                Log.d("Apos1", "" + position);
                changed();
                Log.d("check", "" + snackbar);
                snackbar = Snackbar.make(forSnackBar, "Deleted " + s + "!", Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", v1 -> {

                    tasksDone.add(st.head.d.pos, st.head.d.task);
                    recyclerViewDone.post(new Runnable() {
                        @Override
                        public void run() {
                            adpD.notifyItemInserted(st.head.d.pos);
                        }
                    });
                    Log.d("Apos2", "" + st.head.d.pos);
                    changed();
                    st.remove();
                    recursion(st);
                });
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (event == 0 || event == 2)
                            st.empty();
                    }
                });

                st.add(new Stack.data(s, position, snackbar));
                snackbar.show();
            }
        },
                new adapterDone.CheckListener() {
                    @Override
                    public void onCheckedChange(int position, boolean isChecked) {
                        Log.d(TAG, "adapter done "+isChecked);
                        String t=tasksDone.get(position);
                        Log.d(TAG, "adapter done "+position);
                        tasksLeft.add(0,t);
                        recyclerViewLeft.post(new Runnable() {
                            @Override
                            public void run() {
                                adpL.notifyItemInserted(0);
                            }
                        });
                        Log.d("Apos3",""+0);
                        tasksDone.remove(position);
//            m.changed();
                        recyclerViewDone.post(new Runnable() {
                            @Override
                            public void run() {
                                adpD.notifyItemRemoved(position);
                            }
                        });
                        Log.d("Rpos1",""+position);

                        changed();

                        if(toast!=null)
                            toast.cancel();
                        toast=Toast.makeText(MainActivity.this,"Added!!!",Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
        recyclerViewDone.setAdapter(adpD);
        recyclerViewLeft.setAdapter(adpL);

        bar= getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#17DABD")));

        add=findViewById(R.id.addbutton);
        task=findViewById(R.id.enterTask);
        t=findViewById(R.id.textView);
        t2=findViewById(R.id.textView2);
        reset=findViewById(R.id.buttonReset);
        forSnackBar=findViewById(R.id.mainId);

        add.setOnClickListener(v->{
            if(task.getText().toString().length()!=0)
            {
                tasksLeft.add(0,task.getText().toString());
                Log.d(TAG, "onCreate: "+task.getText().toString());
                System.out.println(task.getText().toString());
                task.setText("");

                if(toast!=null)
                    toast.cancel();
                toast=Toast.makeText(getApplicationContext(),"Added!!!",Toast.LENGTH_LONG);
                toast.show();

                recyclerViewLeft.post(new Runnable() {
                    @Override
                    public void run() {
                        adpL.notifyItemInserted(0);
                    }
                });
                changed();
            }
        });


        reset.setOnClickListener(v->{
            ArrayList<String> c1=new ArrayList<>(),c2=new ArrayList<>();
            c1.addAll(tasksLeft);
            c2.addAll(tasksDone);
            int a=tasksLeft.size();
            int b=tasksDone.size();
            tasksLeft.clear();
            tasksDone.clear();
            recyclerViewLeft.post(new Runnable() {
                @Override
                public void run() {
                    adpL.notifyItemRangeRemoved(0,a);
                }
            });
            recyclerViewDone.post(new Runnable() {
                @Override
                public void run() {
                    adpD.notifyItemRangeRemoved(0,b);
                }
            });
            changed();

            Snackbar.make(forSnackBar,"Deleted All Data!",Snackbar.LENGTH_INDEFINITE).setAction("Undo!",v1->{
                tasksLeft.addAll(c1);
                tasksDone.addAll(c2);
                recyclerViewLeft.post(new Runnable() {
                    @Override
                    public void run() {
                        adpL.notifyItemRangeInserted(0,a);
                    }
                });
                recyclerViewDone.post(new Runnable() {
                    @Override
                    public void run() {
                        adpD.notifyItemRangeInserted(0,b);
                    }
                });
                changed();
            }).show();
        });





        Log.d("Logged", "onCreate1v ");
        getdata();
    }



    public void changed()
    {
//        adpL=new adapterLeft(tasksLeft,tasksDone,this);
//        Log.d("vipul", "adapter");
//        adpD=new adapterDone(tasksLeft,tasksDone,this);
//        Log.d("vipul", "adapter2");
//        recyclerViewDone.setAdapter(adpD);
//        recyclerViewLeft.setAdapter(adpL);
//        Log.d("vipul", "adapter2");

//        adpD.notifyDataSetChanged();
//        adpL.notifyDataSetChanged();

        if(tasksDone.size()==0)
        {
            t2.setVisibility(View.GONE);
            t2.setTextSize(0);
            recyclerViewDone.setVisibility(View.GONE);
        }
        else
        {
            t2.setVisibility(View.VISIBLE);
            t2.setTextSize(28);
            recyclerViewDone.setVisibility(View.VISIBLE);
        }

        t.setText("Pending ("+tasksLeft.size()+")");
        t2.setText("Done ("+tasksDone.size()+")");
    }

    @Override
    protected void onPause() {
        Log.d("Lifecycle", "onPause: ");
        saveData();
        super.onPause();
    }

    public void saveData() {
        saveLeft=getSharedPreferences("Save Left", Context.MODE_PRIVATE);
        saveDone=getSharedPreferences("Save Done", Context.MODE_PRIVATE);

        SharedPreferences.Editor s1=saveLeft.edit();
        for(int i=-1;++i<tasksLeft.size();)
            s1.putString("key l"+i,tasksLeft.get(i));
        s1.putInt("left no",tasksLeft.size());

        SharedPreferences.Editor s2=saveDone.edit();
        for(int i=-1;++i<tasksDone.size();)
            s2.putString("key d"+i,tasksDone.get(i));
        s2.putInt("done no",tasksDone.size());

        s1.apply();s2.apply();
    }

    public void getdata() {
        saveLeft=getSharedPreferences("Save Left", Context.MODE_PRIVATE);
        saveDone=getSharedPreferences("Save Done", Context.MODE_PRIVATE);

        int x=saveLeft.getInt("left no",0);
        for(int i=-1;++i<x;)
            tasksLeft.add(saveLeft.getString("key l"+i,""));
        x=saveDone.getInt("done no",0);
        for(int i=-1;++i<x;)
            tasksDone.add(saveDone.getString("key d"+i,""));

        Log.d("vipul", "getdata: "+tasksDone.size());
        Log.d("vipul", "getdata: "+tasksLeft.size());
        changed();
    }

    @Override
    protected void onDestroy() {
        Log.d("Lifecycle", "onDestroy: ");
        saveData();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d("Lifecycle", "onRestart: ");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("Lifecycle", "onResume: ");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d("Lifecycle", "onStart: ");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("Lifecycle", "onStop: ");
        super.onStop();
    }

    private void recursion(Stack s) {
        if(s.head==null)
            return;
        snackbar=s.head.d.snackbar;
        snackbar.show();
    }
}