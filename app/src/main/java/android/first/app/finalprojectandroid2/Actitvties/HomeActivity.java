package android.first.app.finalprojectandroid2.Actitvties;


import android.content.Intent;
import android.content.SharedPreferences;
import android.first.app.finalprojectandroid2.Adapter.LastNewsAdapter;
import android.first.app.finalprojectandroid2.Adapter.ViewPagerHomeAdapter;
import android.first.app.finalprojectandroid2.Fragments.GeneralChatFragment;
import android.first.app.finalprojectandroid2.Fragments.LatestNewsFragment;
import android.first.app.finalprojectandroid2.Fragments.NewsSourcesFragment;
import android.first.app.finalprojectandroid2.Fragments.PreviousNewsFragment;
import android.first.app.finalprojectandroid2.R;
import android.first.app.finalprojectandroid2.Service.ServiceConnection;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {
    private ArrayList<Fragment> screenFragment;
    private ArrayList<String> titleScreenFragment;
    private RequestQueue requestQueue;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        screenFragment = new ArrayList<>();
        titleScreenFragment = new ArrayList<>();
        titleScreenFragment.add("آخر الأخبار");
        titleScreenFragment.add("الأخبار السابقة");
        titleScreenFragment.add("مصادر الأخبار");
        titleScreenFragment.add("المفضلة");

         tabLayout = findViewById(R.id.tabLayout);
         viewPager = findViewById(R.id.viewPager);
        LatestNewsFragment latestNewsFragment = new LatestNewsFragment();
        PreviousNewsFragment previousNewsFragment = new PreviousNewsFragment();
        NewsSourcesFragment newsSourcesFragment = new NewsSourcesFragment();
        GeneralChatFragment chat = new GeneralChatFragment();
        screenFragment.add(latestNewsFragment);
        screenFragment.add(previousNewsFragment);
        screenFragment.add(newsSourcesFragment);
        screenFragment.add(chat);
        ViewPagerHomeAdapter viewPagerHomeAdapter = new ViewPagerHomeAdapter(getSupportFragmentManager(),screenFragment,titleScreenFragment);
        viewPager.setAdapter(viewPagerHomeAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = new Intent(getApplicationContext(), ServiceConnection.class);
        startService(intent);

        SharedPreferences sp = getSharedPreferences("Favorites", MODE_PRIVATE);
        Set<String> set = sp.getStringSet("Favorite",null);
        boolean isEmpty = false;
        if (set != null){
            ArrayList<String> list = new ArrayList<>(set);
            for (String s:list){
                if (LastNewsAdapter.Favorites.isEmpty()){
                    isEmpty = true;
                    LastNewsAdapter.Favorites.add(s);
                }else{
                    if (isEmpty){
                        LastNewsAdapter.Favorites.add(s);
                    }
                }
            }
        }




    }






}