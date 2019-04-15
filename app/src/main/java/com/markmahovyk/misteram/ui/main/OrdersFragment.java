package com.markmahovyk.misteram.ui.main;

import android.app.NotificationManager;
import android.content.Context;
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
import android.widget.ProgressBar;

import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.data.SharePreference;
import com.markmahovyk.misteram.data.newtwork.App;
import com.markmahovyk.misteram.model.ActiveTasks;
import com.markmahovyk.misteram.ui.main.adapter.ActiveTaskAdapter;
import com.markmahovyk.misteram.ui.main.adapter.OrdersAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {
    private static OrdersFragment ordersFragment = null;

    private ArrayList<ActiveTasks> tasks = new ArrayList<>();
    private Boolean isVisibleFragment = false;

    private ActiveTaskAdapter activeTaskAdapter;
    private ArrayList<Integer> positionItemNotify = new ArrayList();

    private RecyclerView activeTaskRecyclerView;
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

        getActivity().setTitle(R.string.titleFragmentOrders);

        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        loginProgress = view.findViewById(R.id.loginProgress);

        updateData();

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);

        activeTaskRecyclerView = (RecyclerView) view.findViewById(R.id.activeTaskRecyclerView);
        activeTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        activeTaskAdapter = new ActiveTaskAdapter(tasks);
        activeTaskRecyclerView.setAdapter(activeTaskAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
        isVisibleFragment = true;

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

                        activeTaskRecyclerView.setVisibility(View.GONE);

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
        if (tasks.size() == 0)
            return;
        activeTaskRecyclerView.setVisibility(View.VISIBLE);

        if (activeTaskAdapter.tasks.size() != tasks.size()) {

            activeTaskAdapter.tasks = tasks;

            activeTaskAdapter.notifyDataSetChanged();
        } else {
            for (int i : positionItemNotify) {
                activeTaskAdapter.notifyItemChanged(i);
                positionItemNotify.remove(i);
            }
        }

    }

    public void updateItem(int pos) {
        positionItemNotify.add(pos);
        updateData();
    }
}