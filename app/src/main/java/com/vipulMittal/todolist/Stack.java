package com.vipulMittal.todolist;

import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

public class Stack {

    public static class data {
        String task;
        int pos;
        Snackbar snackbar;

        public data(String task, int pos, Snackbar snackbar) {
            this.task = task;
            this.pos = pos;
            this.snackbar = snackbar;
        }
    }

    public static class node {
        node next;
        data d;

        public node(data d) {
            this.d = d;
        }
    }

    node head;

    public void add(data d) {
        node a = new node(d);
        a.next=head;
        head=a;
    }

    public data remove() {
        data d = head.d;
        head = head.next;
        return d;
    }

    public void empty() {
        head =  null;
    }

    public void print()
    {
        Log.d("Stack printing::", "\n");
        Log.d("Stack printing::", "\n");
        node curr=head;
        while(curr!=null)
        {
            System.out.println(curr.d.task);
            Log.d("Stack printing::", curr.d.task+"\n");
            curr=curr.next;
        }
        Log.d("Stack printing::", "\n");
        Log.d("Stack printing::", "\n");
    }
}