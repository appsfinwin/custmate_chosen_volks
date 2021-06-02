package com.finwin.chosenvolks.custmate.activity_main;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finwin.chosenvolks.custmate.R;
import com.finwin.chosenvolks.custmate.databinding.ActivityMainBinding;


public class ActivityMainViewmodel extends AndroidViewModel {
    public ActivityMainViewmodel(@NonNull Application application) {
        super(application);
        this.application=application;
    }
    ActivityMainBinding binding;
    Application application;
    public ObservableField<String> selectedFragment= new ObservableField<>("home");
    MutableLiveData<String> mAction= new MutableLiveData<>();

    public MutableLiveData<String> getmAction() {
        return mAction;
    }

    public void setBinding(ActivityMainBinding binding) {
        this.binding=binding;
        binding.tvHome.setTextSize(13);
        binding.tvProfile.setTextSize(11);
        binding.tvScan.setTextSize(11);
    }

    public void clickHome(View view)
    {
            mAction.setValue("home");

    }

    public void setHome() {

        selectedFragment.set("home");
        binding.tvHome.setTextSize(13);
        binding.tvProfile.setTextSize(11);
        binding.tvScan.setTextSize(11);

        binding.tvHome.setTextColor(application.getResources().getColor(R.color.colorPrimary));
        binding.tvProfile.setTextColor(application.getResources().getColor(R.color.grey));
        binding.tvScan.setTextColor(application.getResources().getColor(R.color.grey));

        binding.imgHome.setColorFilter(ContextCompat.getColor(application, R.color.colorPrimary));
        binding.imgScan.setColorFilter(ContextCompat.getColor(application, R.color.grey));
        binding.imgProfile.setColorFilter(ContextCompat.getColor(application, R.color.grey));
    }

    public void clickScan(View view)
    {
        if (!selectedFragment.get().equals("scan")) {

            mAction.setValue("scan");
        }

    }

    public void setScan() {
        selectedFragment.set("scan");

        binding.tvHome.setTextSize(11);
        binding.tvProfile.setTextSize(11);
        binding.tvScan.setTextSize(13);

        binding.tvHome.setTextColor(application.getResources().getColor(R.color.grey));
        binding.tvProfile.setTextColor(application.getResources().getColor(R.color.grey));
        binding.tvScan.setTextColor(application.getResources().getColor(R.color.colorPrimary));

        binding.imgHome.setColorFilter(ContextCompat.getColor(application, R.color.grey));
        binding.imgScan.setColorFilter(ContextCompat.getColor(application, R.color.colorPrimary));
        binding.imgProfile.setColorFilter(ContextCompat.getColor(application, R.color.grey));
    }

    public void clickProfile(View view)
    {
        if (!selectedFragment.get().equals("profile")) {
            mAction.setValue("profile");
        }

    }

    public void setProfile() {

        selectedFragment.set("profile");
        binding.tvHome.setTextSize(11);
        binding.tvProfile.setTextSize(13);
        binding.tvScan.setTextSize(11);

        binding.tvHome.setTextColor(application.getResources().getColor(R.color.grey));
        binding.tvProfile.setTextColor(application.getResources().getColor(R.color.colorPrimary));
        binding.tvScan.setTextColor(application.getResources().getColor(R.color.grey));

        binding.imgHome.setColorFilter(ContextCompat.getColor(application, R.color.grey));
        binding.imgScan.setColorFilter(ContextCompat.getColor(application, R.color.grey));
        binding.imgProfile.setColorFilter(ContextCompat.getColor(application, R.color.colorPrimary));
    }
}
