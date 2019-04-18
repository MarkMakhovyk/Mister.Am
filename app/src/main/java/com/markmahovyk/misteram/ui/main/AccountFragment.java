package com.markmahovyk.misteram.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.iid.FirebaseInstanceId;
import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.data.SharePreference;
import com.markmahovyk.misteram.ui.login.LoginActivity;

import java.io.IOException;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private static AccountFragment accountFragment = null;

    private BottomNavigationView bottomNavigationView;

    public static AccountFragment getInstance() {
        if (accountFragment == null) {
            accountFragment = new AccountFragment();
        }
        return accountFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setTitleFragment();

        View view = inflater.inflate(R.layout.fragment_account ,container,false);

        view.findViewById(R.id.exitLayout).setOnClickListener(this);

        return view;
    }

    private void setTitleFragment() {
        if (SharePreference.getUsername(getContext()) != null) {
            getActivity().setTitle(SharePreference.getUsername(getContext()));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.exitLayout) {
            logOut();
        }
    }

    private void logOut() {

        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharePreference.setTokenAppAuthToken(getContext(),null);

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();

    }
}
