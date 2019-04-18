package com.markmahovyk.misteram.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.model.task.ActiveTasks;
import com.markmahovyk.misteram.model.task.Order;
import com.markmahovyk.misteram.ui.main.OrdersFragment;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersHolder> {
    private ActiveTasks task;
    private Context context;

    public OrdersAdapter(ActiveTasks tasks) {
        this.task = tasks;
    }

    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        return new OrdersHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_order, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersHolder ordersHolder, int i) {
        ordersHolder.bindView(task.getOrders().get(i));

        if (task.getOrders().get(i).getActive())
            ordersHolder.itemView.setBackgroundResource(R.color.activeTask);

        setTag(task.getOrders().get(i),ordersHolder.tagForOrderLayout);

        setDetailsListener(ordersHolder, i);
    }

    private void setTag(Order order, LinearLayout tagForOrderLayout) {
        for (String tag : order.getTags()) {
            int res = -1;
            if (tag.equals("soup")) {
                res = R.drawable.soup;
            } else if (tag.equals("alco")) {
                res = R.drawable.alco;
            } else if (tag.equals("gift")) {
                res = R.drawable.gift;
            } else if (tag.equals("deliveryToDoor")) {
                res = R.drawable.door;
            } else if (tag.equals("info")) {
                res = R.drawable.info;
            }

            if (res != -1) {
                ImageView icon = new ImageView(context);
                icon.setImageResource(res);
                tagForOrderLayout.addView(icon);
            }
        }
    }

    private void setDetailsListener(OrdersHolder ordersHolder, int i) {
        final int orderId = task.getOrders().get(i).getId();
        ordersHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdersFragment.getInstance().showDetailsOrder(task.getOrders());
            }
        });
    }

    @Override
    public int getItemCount() {
        return task.getOrders().size();
    }
}