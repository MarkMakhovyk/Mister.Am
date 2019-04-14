package com.markmahovyk.misteram.ui.main;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.data.SharePreference;
import com.markmahovyk.misteram.data.newtwork.App;
import com.markmahovyk.misteram.model.Action;
import com.markmahovyk.misteram.model.ActiveTasks;
import com.markmahovyk.misteram.model.Order;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {
    private static OrdersFragment ordersFragment = null;

    private ArrayList<ActiveTasks> tasks = null;
    private Boolean isVisibleFragment = false;

    RecyclerView complexTaskRecyclerView;
    RecyclerView standardTaskRecyclerView;
    ProgressBar loginProgress;
    SwipeRefreshLayout refreshLayout;

    public static OrdersFragment getInstance() {
        if (ordersFragment == null) {
            ordersFragment = new OrdersFragment();
        }
        return ordersFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle(R.string.titleFragmentOrders);

        View view = inflater.inflate(R.layout.fragment_orders,container,false);
        loginProgress = view.findViewById(R.id.loginProgress);

        updateData();

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);

        complexTaskRecyclerView = (RecyclerView) view.findViewById(R.id.complexTaskRecyclerView);
        complexTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        standardTaskRecyclerView = (RecyclerView) view.findViewById(R.id.standardTaskRecyclerView);
        standardTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
        isVisibleFragment = true;

        NotificationManager nMgr = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancel("fcm",0);
    }

    @Override
    public void onPause() {
        super.onPause();
        isVisibleFragment = false;
    }

    public Boolean getVisibleFragment() {
        return isVisibleFragment;
    }

    public void updateData() {
        loginProgress.setIndeterminate(true);
        App.getApi().getActiveTask(SharePreference.getAppAuthToken(getContext()))
                .enqueue(new Callback<ArrayList<ActiveTasks>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ActiveTasks>> call,
                                           Response<ArrayList<ActiveTasks>> response) {
                        loginProgress.setIndeterminate(false);
                        loginProgress.setVisibility(View.GONE);

                        refreshLayout.setRefreshing(false);
                        refreshLayout.setVisibility(View.GONE);


                        tasks = response.body();
                        setData();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ActiveTasks>> call, Throwable t) {
                        loginProgress.setIndeterminate(false);
                        loginProgress.setVisibility(View.GONE);

                        refreshLayout.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                        refreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
                            @Override
                            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout swipeRefreshLayout, @Nullable View view) {
                                refreshLayout.setRefreshing(true);
                                updateData();
                                return false;
                            }
                        });

                    }
                });

    }

    private void setData() {
        if (tasks == null)
            return;
        ArrayList<ActiveTasks> listStandardTask = new ArrayList<>();
        ArrayList<ActiveTasks> listComplexTask = new ArrayList<>();

        for (ActiveTasks t : tasks) {
            if (t.getOrders().size() == 4) {
                listComplexTask.add(t);
            }
            listStandardTask.add(t);
        }

        if (listStandardTask.size() > 0) {
            OrdersAdapter adapterStandard = new OrdersAdapter(listStandardTask, 2);
            standardTaskRecyclerView.setAdapter(adapterStandard);
            standardTaskRecyclerView.setVisibility(View.VISIBLE);
        } else
            standardTaskRecyclerView.setVisibility(View.GONE);

        if (listComplexTask.size() > 0) {
            OrdersAdapter adapterComplex = new OrdersAdapter(listComplexTask,4);
            complexTaskRecyclerView.setAdapter(adapterComplex);
            complexTaskRecyclerView.setVisibility(View.VISIBLE);
        } else
            complexTaskRecyclerView.setVisibility(View.GONE);

    }

    class OrdersAdapter extends RecyclerView.Adapter<OrdersHolder> {
        ArrayList<ActiveTasks> tasks;
        int countOrderInTask;

        public OrdersAdapter(ArrayList<ActiveTasks> orders, int countOrderInTask) {
            this.tasks = orders;
            this.countOrderInTask = countOrderInTask;
        }

        @NonNull
        @Override
        public OrdersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new OrdersHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_order, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull OrdersHolder ordersHolder, int i) {
            ordersHolder.bindView(tasks.get(i/countOrderInTask).getOrders().get(i % countOrderInTask));

            if (tasks.get(i/countOrderInTask).getOrders().get(i % countOrderInTask).getActive())
                ordersHolder.itemView.setBackgroundResource(R.color.activeTask);

            Action action = tasks.get(i/countOrderInTask).getAction();
            if (action.getTitle().equals("Прибыл в заведение")) {
                ordersHolder.orderStatusTextView.setText("Прибыл в заведение");
                ordersHolder.orderStatusLayout.setVisibility(View.VISIBLE);
            }
            else if (action.getTitle().equals("Заказ получен")) {
                ordersHolder.orderStatusTextView.setText("Заказ получен");
                ordersHolder.orderStatusLayout.setVisibility(View.VISIBLE);
            }
            else if (action.getTitle().equals("Заказ доставлен")) {
                ordersHolder.orderStatusTextView.setText("Заказ доставлен");
                ordersHolder.orderStatusLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return tasks.size()*countOrderInTask;
        }
    }

    class OrdersHolder extends RecyclerView.ViewHolder {
        TextView timeTv;
        TextView orderCodeTv;
        TextView nameTv;
        TextView addressTv;
        TextView financeTv;
        TextView orderStatusTextView;
        ImageView typeOrdersImageView;
        LinearLayout tagForOrderLayout;
        View orderStatusLayout;

        public OrdersHolder(@NonNull View itemView) {
            super(itemView);
            timeTv = (TextView) itemView.findViewById(R.id.timeTv);
            orderCodeTv = (TextView) itemView.findViewById(R.id.orderCodeTv);
            nameTv = (TextView) itemView.findViewById(R.id.nameTv);
            addressTv = (TextView) itemView.findViewById(R.id.adressTv);
            financeTv = (TextView) itemView.findViewById(R.id.financeTv);
            orderStatusTextView = (TextView) itemView.findViewById(R.id.orderStatusTextView);
            typeOrdersImageView = (ImageView) itemView.findViewById(R.id.typeOrderImageView);
            tagForOrderLayout = (LinearLayout) itemView.findViewById(R.id.tagForOrderLayout);
            orderStatusLayout = itemView.findViewById(R.id.orderStatusLayout);
        }

        public void bindView(Order order) {

            timeTv.setText(new SimpleDateFormat("HH:mm").format(new Time(order.getTime())));

            nameTv.setText(order.getAddress().getTitle());

            addressTv.setText(order.getAddress().getDescription());

            double price = order.getAmount();
            financeTv.setText(String.valueOf(price)+ "грн.");
            if (price > 0) {
                financeTv.setTextColor(Color.GREEN);
            } else if (price < 0) {
                financeTv.setTextColor(Color.RED);
            }

            orderCodeTv.setText(String.valueOf(order.getNumber().toCharArray(),
                    order.getNumber().length()-4,4));

            if (order.getType().equals("company")) {
                typeOrdersImageView.setImageResource(R.drawable.arrow_company);
            } else if (order.getType().equals("user")) {
                typeOrdersImageView.setImageResource(R.drawable.arrow_user);
            }

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
                    ImageView icon = new ImageView(getContext());
                    icon.setImageResource(res);
                    tagForOrderLayout.addView(icon);
                }
            }
        }
    }
}
