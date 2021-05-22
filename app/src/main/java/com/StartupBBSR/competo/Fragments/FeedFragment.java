package com.StartupBBSR.competo.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.StartupBBSR.competo.Models.BannerEvent;
import com.StartupBBSR.competo.R;
import com.StartupBBSR.competo.databinding.FragmentFeedBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {
View view;
TabLayout indicatorTab;
ViewPager bannerViewPager;
private FragmentFeedBinding fragmentFeedBinding;
    List<BannerEvent> homeBannerList;

    public FeedFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);
           fragmentFeedBinding=FragmentFeedBinding.inflate(getLayoutInflater());

        indicatorTab=view.findViewById(R.id.tab_indicator);

        homeBannerList =new ArrayList<>();
        homeBannerList.add(new BannerEvent(1,"PONMAGAL VANDHAL","https://media.glassdoor.com/l/90/f1/7b/b8/company-event.jpg",""));
        homeBannerList.add(new BannerEvent(2,"LITTLE WOMEN","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner2.png",""));
        homeBannerList.add(new BannerEvent(3,"BHOOT","https://png.pngtree.com/thumb_back/fw800/back_pic/05/06/22/87596c72e3222da.jpg",""));
        homeBannerList.add(new BannerEvent(4,"MIRZAPUR","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner4.png",""));
        homeBannerList.add(new BannerEvent(5,"PIKACHU","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner5.png",""));
        homeBannerList.add(new BannerEvent(6,"PONMAGAL VANDHAL","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner3.png",""));
        homeBannerList.add(new BannerEvent(7,"LITTLE WOMEN","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner2.png",""));
        homeBannerList.add(new BannerEvent(8,"BHOOT","","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner1.png"));
        homeBannerList.add(new BannerEvent(9,"MIRZAPUR","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner3.png",""));
        homeBannerList.add(new BannerEvent(10,"PIKACHU","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner4.png",""));

        setBannerEventPagerAdapter(homeBannerList);

        // Inflate the layout for this fragment
        return view;
    }

    private void setBannerEventPagerAdapter(List<BannerEvent> homeBannerList) {
        bannerViewPager=view.findViewById(R.id.banner_viewPager);

    }
}