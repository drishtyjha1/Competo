package com.StartupBBSR.competo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.StartupBBSR.competo.Activity.LoginActivity;
import com.StartupBBSR.competo.Adapters.BannerEventPagerAdapter;
import com.StartupBBSR.competo.Models.BannerEvent;
import com.StartupBBSR.competo.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    BannerEventPagerAdapter bannerEventPagerAdapter;
    TabLayout tabLayout;
    ViewPager bannerViewPager;
    List<BannerEvent> bannerEventList;


    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        tabLayout=findViewById(R.id.tab_indicator);

        bannerEventList=new ArrayList<>();
        bannerEventList.add(new BannerEvent(1,"PONMAGAL VANDHAL","https://media.glassdoor.com/l/90/f1/7b/b8/company-event.jpg",""));
        bannerEventList.add(new BannerEvent(2,"LITTLE WOMEN","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner2.png",""));
        bannerEventList.add(new BannerEvent(3,"BHOOT","https://png.pngtree.com/thumb_back/fw800/back_pic/05/06/22/87596c72e3222da.jpg",""));
        bannerEventList.add(new BannerEvent(4,"MIRZAPUR","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner4.png",""));
        bannerEventList.add(new BannerEvent(5,"PIKACHU","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner5.png",""));
        bannerEventList.add(new BannerEvent(6,"PONMAGAL VANDHAL","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner3.png",""));
        bannerEventList.add(new BannerEvent(7,"LITTLE WOMEN","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner2.png",""));
        bannerEventList.add(new BannerEvent(8,"BHOOT","","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner1.png"));
        bannerEventList.add(new BannerEvent(9,"MIRZAPUR","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner3.png",""));
        bannerEventList.add(new BannerEvent(10,"PIKACHU","http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/homebanner4.png",""));


        setBannerEventPagerAdapter(bannerEventList);

    }
    private void setBannerEventPagerAdapter(List<BannerEvent>bannerEventList){

        bannerViewPager = findViewById(R.id.banner_viewPager);
        bannerEventPagerAdapter=new BannerEventPagerAdapter(this,bannerEventList);
        bannerViewPager.setAdapter(bannerEventPagerAdapter);

        tabLayout.setupWithViewPager(bannerViewPager);


        Timer sliderTimer =new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlide(),4000,6000);
        tabLayout.setupWithViewPager(bannerViewPager, true);



    }
    class  AutoSlide extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override

                public void run() {

                    if (bannerViewPager.getCurrentItem() < bannerEventList.size() - 1) {
                        bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1);
                    } else {
                        bannerViewPager.setCurrentItem(0);
                    }

                }

            });


            activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            });
        }
    }
}