package com.markmahovyk.misteram.ui.main;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.data.SharePreference;
import com.markmahovyk.misteram.data.newtwork.App;
import com.markmahovyk.misteram.model.task.ActiveTasks;
import com.markmahovyk.misteram.model.task.Order;
import com.markmahovyk.misteram.ui.detailsOrder.MainDetailsFragment;
import com.markmahovyk.misteram.ui.main.adapter.ActiveTaskAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {
    private static OrdersFragment ordersFragment = null;

    private ArrayList<ActiveTasks> tasks = new ArrayList<>();
    private Boolean isVisibleFragment = false;

    private ArrayList<Integer> positionItemNotify = new ArrayList();

    private RecyclerView activeTaskRecyclerView;
    private ActiveTaskAdapter activeTaskAdapter;

    private ProgressBar loginProgress;
    private SwipeRefreshLayout refreshLayout;

    public static OrdersFragment getInstance() {
        if (ordersFragment == null) {
            ordersFragment = new OrdersFragment();
        }
        return ordersFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setTitleFragment();

        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        loginProgress = view.findViewById(R.id.loadProgress);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);

        activeTaskRecyclerView = (RecyclerView) view.findViewById(R.id.activeTaskRecyclerView);
        activeTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapterList();

        return view;
    }

    private void setTitleFragment() {
        getActivity().setTitle(R.string.titleFragmentOrders);
    }

    @Override
    public void onResume() {
        super.onResume();

        setData();
        updateData();

        isVisibleFragment = true;

        removeActiveNotification();
    }

    private void removeActiveNotification() {
        NotificationManager nMgr = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancel("fcm", 0);
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
        positionItemNotify.clear();
        loadData();
    }

    public void loadData() {
        loginProgress.setIndeterminate(true);
        App.getApi().getActiveTask(SharePreference.getAppAuthToken(getContext()))
                .enqueue(new Callback<ArrayList<ActiveTasks>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ActiveTasks>> call,
                                           Response<ArrayList<ActiveTasks>> response) {
                        setDefaultStage();

                        refreshLayout.setVisibility(View.GONE);
                        if (response != null && response.body() != null) {
                            tasks = response.body();
                            setData();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ActiveTasks>> call, Throwable t) {
                        setDefaultStage();

                        activeTaskRecyclerView.setVisibility(View.GONE);

                        initializeSwipeRefreshLayout();
                    }
                });
    }

    private void initializeSwipeRefreshLayout() {
        refreshLayout.setVisibility(View.VISIBLE);

        refreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout swipeRefreshLayout, @Nullable View view) {
                refreshLayout.setRefreshing(true);
                updateData();
                return false;
            }
        });
    }

    private void setDefaultStage() {
        loginProgress.setIndeterminate(false);
        loginProgress.setVisibility(View.GONE);

        refreshLayout.setRefreshing(false);
    }

    private void setData() {
        if (tasks.size() == 0) {
            setAdapterList();
            return;
        }

        activeTaskRecyclerView.setVisibility(View.VISIBLE);
        if (positionItemNotify.size() > 0) {
            updateItem();
        }
        else {
            setAdapterList();
        }
    }

    private void updateItem() {
        activeTaskAdapter.setTasks(tasks);
        int countItem = activeTaskAdapter.getTasks().size();

        for (int i : positionItemNotify) {
            if (i <= countItem)
                activeTaskAdapter.notifyItemChanged(i);
        }

        positionItemNotify.clear();
    }

    private void setAdapterList() {
        activeTaskAdapter = new ActiveTaskAdapter(tasks);
        activeTaskRecyclerView.setAdapter(activeTaskAdapter);
    }

    public void updateItem(int pos) {
        positionItemNotify.add(pos);
        loadData();
    }

    public void showDetailsOrder(List<Order> task) {
        ArrayList<String> listId = new ArrayList<>();

        for (Order order : task) {
            if (listId.indexOf(order.getId().toString()) == -1) {
                listId.add(order.getId().toString());
            }
        }


        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer,
                MainDetailsFragment.newInstance(listId))
//                OrderDetailsFragment.newInstance(String.valueOf(orderId)))
                .addToBackStack("DetailsFragment").commit();
    }
}