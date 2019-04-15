package com.markmahovyk.misteram.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.data.SharePreference;
import com.markmahovyk.misteram.data.newtwork.App;
import com.markmahovyk.misteram.model.Action;
import com.markmahovyk.misteram.model.ActiveTasks;
import com.markmahovyk.misteram.model.ResponseEndPoint;
import com.markmahovyk.misteram.ui.main.OrdersFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveTaskAdapter extends RecyclerView.Adapter<ActiveTaskAdapter.ActiveTaskHolder> {
    public ArrayList<ActiveTasks> tasks;
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
    public void onBindViewHolder(@NonNull final ActiveTaskHolder activeTaskHolder, int i) {

        OrdersAdapter ordersAdapter = new OrdersAdapter(tasks.get(i));

        activeTaskHolder.ordersTaskRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        activeTaskHolder.ordersTaskRecyclerView.setAdapter(ordersAdapter);
        activeTaskHolder.itemView.setTag(String.valueOf(i));

        final Action action = tasks.get(i).getAction();
        activeTaskHolder.orderStatusTextView.setText(action.getTitle());

        activeTaskHolder.orderStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEnPoint(activeTaskHolder.itemView);
            }
        });

    }

    private void sendEnPoint(final View view) {
        Action action = tasks.get((Integer.parseInt(view.getTag().toString()))).getAction();

        App.getApi().endPoint(SharePreference.getTokenApp(context),
                SharePreference.getAppAuthToken(context),action.getOrderId().toString(),action.getType())
                .enqueue(new Callback<ResponseEndPoint>() {
                    @Override
                    public void onResponse(Call<ResponseEndPoint> call, Response<ResponseEndPoint> response) {

                        OrdersFragment.getInstance().updateItem((Integer.parseInt(view.getTag().toString())));
                    }

                    @Override
                    public void onFailure(Call<ResponseEndPoint> call, Throwable t) {
                        Snackbar.make(view, R.string.noConnection, Snackbar.LENGTH_LONG).show();
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
