package com.markmahovyk.misteram.ui.detailsOrder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;

import com.markmahovyk.misteram.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDetailsFragment extends Fragment {
    private static final String LIST_ORDER_ID = "order_id";
    @BindView(R.id.tabHostDetails)
    TabHost tabHostDetails;
    private ArrayList<String> listId;

    public static MainDetailsFragment newInstance(ArrayList<String> listOrderId) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(LIST_ORDER_ID, listOrderId);

        MainDetailsFragment fragment = new MainDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(LIST_ORDER_ID)) {
            listId = getArguments().getStringArrayList(LIST_ORDER_ID);
        }
        else
            getActivity().onBackPressed();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.container_for_details, container, false);
        ButterKnife.bind(this, view);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(android.R.id.tabcontent);

        initializeTabHost(frameLayout);

        return view;
    }

    private void initializeTabHost( FrameLayout parentframeLayout) {
        tabHostDetails.setup();

        for (final String id : listId) {

            String titleTab = getTitleTab(id);

            FrameLayout frameLayout = createFrameLayout(Integer.parseInt(id));

            parentframeLayout.addView(frameLayout);

            setContentOrderFragmentTab(id, frameLayout);

            createTab(titleTab, frameLayout);
        }

    }



    @NonNull
    private String getTitleTab(String id) {
        String titleTab = "";

        if (listId.size() > 1) {
            titleTab += (listId.indexOf(id) + 1) + " / ";
        }

        titleTab += String.valueOf(id.toCharArray(),
                id.length()-4,4);
        return titleTab;
    }

    private FrameLayout createFrameLayout(int id) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setId(id);
        return frameLayout;
    }

    private void setContentOrderFragmentTab(String id, FrameLayout frameLayout) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(),
                OrderDetailsFragment.newInstance(id)).commit();
    }

    private void createTab(String titleTab, FrameLayout frameLayout) {
        TabHost.TabSpec tabSpec;
        tabSpec = tabHostDetails.newTabSpec(titleTab);
        tabSpec.setIndicator(titleTab);
        tabSpec.setContent(frameLayout.getId());
        tabHostDetails.addTab(tabSpec);
    }
}
