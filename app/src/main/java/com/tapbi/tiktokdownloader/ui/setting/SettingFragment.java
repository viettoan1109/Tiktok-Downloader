package com.tapbi.tiktokdownloader.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tapbi.tiktokdownloader.R;
import com.tapbi.tiktokdownloader.databinding.FragmentSettingBinding;
import com.tapbi.tiktokdownloader.ui.base.BaseFragment;

import java.math.BigInteger;

import company.librate.RateDialog;

public class SettingFragment extends BaseFragment<FragmentSettingBinding, SettingViewModel> {

    @Override
    protected Class<SettingViewModel> getViewModelClass() {
        return SettingViewModel.class;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.switchSettingOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.switchSettingOn.setVisibility(View.INVISIBLE);
                binding.switchSettingOff.setVisibility(View.VISIBLE);
            }
        });

        binding.switchSettingOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.switchSettingOff.setVisibility(View.INVISIBLE);
                binding.switchSettingOn.setVisibility(View.VISIBLE);
            }
        });
        rateApp();
        shareApp();
        feedBack();
        policy();
    }

    private void rateApp() {
        binding.rlSettingRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateDialog rateDialog = new RateDialog(getActivity(), "toan11998@gmail.com", true);
                rateDialog.show();

            }
        });
    }

    private void shareApp() {
        binding.rlSettingShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://play.google.com/store/apps/details?id=com.tapbi.tiktokdownloader";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void feedBack() {
        binding.rlSettingFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://mail.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
    }

    private void policy() {
        binding.rlSettingPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}