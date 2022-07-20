package com.developer.live.shoppingapp.ui.logout;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.live.shoppingapp.R;
import com.developer.live.shoppingapp.databinding.FragmentLogoutBinding;
import com.developer.live.shoppingapp.store.LocalStorage;

public class LogoutFragment extends DialogFragment {

    private FragmentLogoutBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);

        binding.btnCancel.setOnClickListener(view -> dismiss());

        binding.btnConfirm.setOnClickListener(view -> {
            //if you are in a fragment , you need to use getActivity() or getContext() for referring your current context
            new LocalStorage(getActivity()).clearEverything();
        });

        return  binding.getRoot();
    }
}