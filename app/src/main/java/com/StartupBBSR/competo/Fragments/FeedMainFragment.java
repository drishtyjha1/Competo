package com.StartupBBSR.competo.Fragments;

import android.os.Bundle;

import androidx.core.widget.AutoScrollHelper;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.StartupBBSR.competo.Adapters.BannerEventPagerAdapter;
import com.StartupBBSR.competo.MainActivity;
import com.StartupBBSR.competo.Models.BannerEvent;
import com.StartupBBSR.competo.R;
import com.StartupBBSR.competo.databinding.FragmentFeedMainBinding;
import com.StartupBBSR.competo.databinding.FragmentProfileBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class FeedMainFragment extends Fragment {
    FragmentFeedMainBinding binding;
    BannerEventPagerAdapter bannerEventPagerAdapter;
    TabLayout indicatorTab,categoryTab;
     ViewPager bannerViewPager;
   List<BannerEvent>FeedBannerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFeedMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        bannerViewPager=view.findViewById(R.id.banner_viewPager);
        indicatorTab=view.findViewById(R.id.tab_indicator);

        FeedBannerList=new ArrayList<>();
        FeedBannerList.add(new BannerEvent(1,"SGWOMENS MONTH","https://media.glassdoor.com/l/90/f1/7b/b8/company-event.jpg",""));
        FeedBannerList.add(new BannerEvent(2,"SGWOMENS MONTH","https://image.shutterstock.com/image-vector/womens-history-month-annual-that-600w-1328422742.jpg",""));
        FeedBannerList.add(new BannerEvent(3,"SGWOMENS MONTH","https://media.glassdoor.com/l/90/f1/7b/b8/company-event.jpg",""));
        FeedBannerList.add(new BannerEvent(4,"SGWOMENS MONTH","https://media.glassdoor.com/l/90/f1/7b/b8/company-event.jpg",""));
        FeedBannerList.add(new BannerEvent(5,"SGWOMENS MONTH","https://media.glassdoor.com/l/90/f1/7b/b8/company-event.jpg",""));
        FeedBannerList.add(new BannerEvent(6,"SGWOMENS MONTH","https://media.glassdoor.com/l/90/f1/7b/b8/company-event.jpg",""));






        return view;
    }
    private void setBannerEventPagerAdapter(List<BannerEvent>feedBannerList){
        bannerEventPagerAdapter=new BannerEventPagerAdapter(this,FeedBannerList);
        bannerViewPager.setAdapter(bannerEventPagerAdapter);

        indicatorTab.setupWithViewPager(bannerViewPager);

        Timer sliderTimer =new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlide(),4000,6000);
        indicatorTab.setupWithViewPager(bannerViewPager,true);

    }
    class  AutoSlide extends TimerTask {

        @Override
        public void run() {
            FeedMainFragment.this.runOnUiThread(new Runnable() {
                @Override

                public void run() {

                    if (bannerViewPager.getCurrentItem() < FeedBannerList.size() - 1) {
                        bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1);
                    } else {
                        bannerViewPager.setCurrentItem(0);
                    }

                }

            });

        }

    }

    private void runOnUiThread(Runnable runnable) {

    }
}