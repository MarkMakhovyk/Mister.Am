package com.markmahovyk.misteram.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
    private ArrayList<ActiveTasks> tasks;
    private Context context;
    private ArrayList<Integer> sendEndPointList = new ArrayList<>();


    public ActiveTaskAdapter(ArrayList<ActiveTasks> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<ActiveTasks> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<ActiveTasks> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ActiveTaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        if (tasks.size() == 0) {
            ActiveTaskHolder holder = new ActiveTaskHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_empty_list, viewGroup, false));
            holder.itemView.setTag("empty");
            return holder;
        }

        return new ActiveTaskHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tasks, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ActiveTaskHolder activeTaskHolder, final int position) {
        if (tasks.size() == 0) {
            return;
        }

        OrdersAdapter ordersAdapter = new OrdersAdapter(tasks.get(position));

        activeTaskHolder.ordersTaskRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        activeTaskHolder.ordersTaskRecyclerView.setAdapter(ordersAdapter);

        final Action action = tasks.get(position).getAction();
        activeTaskHolder.orderStatusTextView.setText(action.getTitle());

        activeTaskHolder.orderStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendEndPointList.indexOf(position) == -1){
                    activeTaskHolder.orderStatusButton.setBackgroundResource(R.color.colorLoginSingInButton);

                    activeTaskHolder.arrowLoginImageView.setVisibility(View.GONE);
                    activeTaskHolder.loginProgressBar.setVisibility(View.VISIBLE);
                    activeTaskHolder.loginProgressBar.setIndeterminate(true);

                    sendEndPointList.add(position);

                    sendEnPoint(activeTaskHolder, position);
                }
            }
        });

    }

    private void sendEnPoint(final ActiveTaskHolder activeTaskHolder, final int pos) {

        final Action action = tasks.get(pos).getAction();

        App.getApi().endPoint(SharePreference.getTokenApp(context),
                SharePreference.getAppAuthToken(context),action.getOrderId().toString(),action.getType())
                .enqueue(new Callback<ResponseEndPoint>() {
                    @Override
                    public void onResponse(Call<ResponseEndPoint> call, Response<ResponseEndPoint> response) {
                        defaultStage();

                        if (action.getType().equals("finish"))
                            OrdersFragment.getInstance().updateData();
                        else
                            OrdersFragment.getInstance().updateItem(pos);
                    }

                    @Override
                    public void onFailure(Call<ResponseEndPoint> call, Throwable t) {
                        defaultStage();

                        Snackbar.make(activeTaskHolder.orderStatusButton, R.string.noConnection, Snackbar.LENGTH_LONG).show();
                    }

                    private void defaultStage() {
                        sendEndPointList.remove(sendEndPointList.indexOf(pos));

                        activeTaskHolder.orderStatusButton.setBackgroundResource(R.color.colorDefaultSingInButton);
                        activeTaskHolder.loginProgressBar.setIndeterminate(false);
                        activeTaskHolder.loginProgressBar.setVisibility(View.GONE);
                        activeTaskHolder.arrowLoginImageView.setVisibility(View.VISIBLE);
                    }
                });

    }

    @Override
    public int getItemCount() {
        if (tasks.size() == 0) {
            return 1;
        }
        return tasks.size();
    }

    class ActiveTaskHolder extends RecyclerView.ViewHolder {
        private RecyclerView ordersTaskRecyclerView;
        private View orderStatusButton;
        private View arrowLoginImageView;
        private TextView orderStatusTextView;
        private ProgressBar loginProgressBar;

        public ActiveTaskHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView.getTag() != null && itemView.getTag().toString().equals("empty"))
                return;

            orderStatusButton = itemView.findViewById(R.id.orderStatusButton);
            arrowLoginImageView = itemView.findViewById(R.id.arrowLoginImageView);
            ordersTaskRecyclerView = (RecyclerView) itemView.findViewById(R.id.ordersTaskRecyclerView);
            orderStatusTextView = (TextView) itemView.findViewById(R.id.orderStatusTextView);
            loginProgressBar = (ProgressBar) itemView.findViewById(R.id.loginProgress);
        }
    }
}
