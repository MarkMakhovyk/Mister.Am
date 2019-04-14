package com.markmahovyk.misteram.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.model.Action;
import com.markmahovyk.misteram.model.ActiveTasks;

import java.util.ArrayList;

public class ActiveTaskAdapter extends RecyclerView.Adapter<ActiveTaskAdapter.ActiveTaskHolder> {
    private ArrayList<ActiveTasks> tasks;
    private Context context;


    public ActiveTaskAdapter(ArrayList<ActiveTasks> tasks) {
        this.tasks = tasks;
    }


    @NonNull
    @Override
    public ActiveTaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        return new ActiveTaskHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tasks, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveTaskHolder activeTaskHolder, int i) {

        OrdersAdapter ordersAdapter = new OrdersAdapter(tasks.get(i));

        activeTaskHolder.ordersTaskRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        activeTaskHolder.ordersTaskRecyclerView.setAdapter(ordersAdapter);

        Action action = tasks.get(i).getAction();
        activeTaskHolder.orderStatusTextView.setText(action.getTitle());

        activeTaskHolder.orderStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class ActiveTaskHolder extends RecyclerView.ViewHolder {
        private RecyclerView ordersTaskRecyclerView;
        private View orderStatusButton;
        private TextView orderStatusTextView;

        public ActiveTaskHolder(@NonNull View itemView) {
            super(itemView);
            orderStatusButton = itemView.findViewById(R.id.orderStatusButton);
            ordersTaskRecyclerView = (RecyclerView) itemView.findViewById(R.id.ordersTaskRecyclerView);
            orderStatusTextView = (TextView) itemView.findViewById(R.id.orderStatusTextView);
        }
    }
}
