package com.example.movify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.movify.adapter.Adapter;
import com.example.movify.adapter.MainRecyclerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Adapter adapter;
    TabLayout IndicatorTab, CategoryTab ;
    ViewPager banner_viewpager;
    List<BannerMovies> homeBannerList;
    List<BannerMovies> videoBannerList;
    List<BannerMovies> streamBannerList;

    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;
    FloatingActionButton btn_upload;


    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    List<AllCategory> allCategoryList;

    FirebaseAuth mAuth;
    FirebaseRemoteConfig remoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IndicatorTab = findViewById(R.id.tab_indicator);
        CategoryTab = findViewById(R.id.TabLayout);
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbar);
        btn_upload = findViewById(R.id.btn_upload);
        mAuth = FirebaseAuth.getInstance();


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Payment_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        //homeBannerList.add(new BannerMovies(1,"SnowPiercer","", ""));
        homeBannerList = new ArrayList<>();
        homeBannerList.add(new BannerMovies(1,"The Crimes of Grindelwald","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQp_F45VJd_Z1iHwWZoK9qGCt64kQPPpfxnw9eYOBggVzWn6FxNDoL3aWLa7TmAfqk3y-4d88WWI8NZmf1sQQFXFf-I.jpg?r=d6b", ""));
        homeBannerList.add(new BannerMovies(2,"Clash of the Titans","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZBP8POvQ1GQbmAXzvYle2UvMTaGb5LV5gA1bdgKbJsPqmZxu43Set0fig0-I7DDrkikG8qxgTwj5HdRDj093AcwMWI.jpg?r=691", ""));
        homeBannerList.add(new BannerMovies(3,"IT","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABWSKHlBCbFClRWKUSWdjCBI6aOeWolBKt2XNdg6lzskGWQ0g1AL1V2hlmrpTtpHORGMR_ooVafqHUX3xiwH0TlmEp44.jpg?r=efc", ""));

        videoBannerList = new ArrayList<>();
        videoBannerList.add(new BannerMovies(1,"SHREK 2","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABS2SrvalX8NS2C7tgbftN8yfVPaX3RWGQ_6TbxxnKKlUFsCQxSMzXnq5I78X0fm1KKH7W63ElWr2fmRmG_-M_PtgkZ0.jpg?r=f0d", ""));
        videoBannerList.add(new BannerMovies(2,"The Conjuring","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABffNTVimB9s4faS2cqIKG8e9tHrcy5y7S-x6QQBcMWXAO5sPhlA9-fzRCJGJFfp1iVE0lCZc87LCyM-KZ-fPWCigYts.jpg?r=fbf", ""));
        videoBannerList.add(new BannerMovies(3,"Gemini Man","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABYrfj8uGl6OJZ4zUBW0zGR3ooLurHz76lPYDkHJQ32sZTfAyRh-qJwh2ceEr4aMk1fhSO_6RBH8eHY4r8SXJRr7WGrg.jpg?r=bcd", ""));

        streamBannerList = new ArrayList<>();
        streamBannerList.add(new BannerMovies(1,"Tomb Raider: The cradle of life","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQBhYVIZ-bnn8mZzyFgJqcB8wKNnZxaYAhBtEy_Yxai9NWVQdCb4RjiZkR4aOCxfQ6R_L627nfPT9NHBoVopAtCCMYg.jpg?r=aaf", ""));
        streamBannerList.add(new BannerMovies(2,"Orphan","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABXKfesDaiV78eZdmwWuULhTAnyAOFvEqPeFBqaKPQSVesTRff8pcoFs7l8dK0BblexytK38-PFmhl1ojED9XTSfLFVA.jpg?r=d8d", ""));
        streamBannerList.add(new BannerMovies(3,"The Craft Legacy","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABWonZM3POj9Wd_q-5fyE020iTCyTCbo0xaPPSxmyyS_DXM0I2lGQyHL4kKLwDvZJC-TLit4VUInAOFFmAKHN6eF8H-o.jpg?r=5d3", ""));

        setAdapter(homeBannerList);

        CategoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 1:
                        SetScrollDefaultState();
                        setAdapter(videoBannerList);
                        return;

                    case 2:
                        SetScrollDefaultState();
                        setAdapter(streamBannerList);
                        return;

                    default:
                        SetScrollDefaultState();
                        setAdapter(homeBannerList);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        List<CategoryItem> homeCatListItem1 = new ArrayList<>();
        homeCatListItem1.add(new CategoryItem(1,"The Dark Tower","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABW7vLrXgKEKvHkp-Jf-Za0aQhGtM5lR3yGoSPQRns4LdGXazLqILwyFpreWDQGuhQ0GRhqm77fNXN0oUsibad5HB-d0.jpg?r=d09",""));
        homeCatListItem1.add(new CategoryItem(3,"The 7th SON","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQXtAwyEvBK6XXA5Z-FOJosLn7uwGBbRCMi9a6ZVIBpckcnWj7flujGnpQtARF3yJ3cdGB-K5x4F-n-UBwPVDzsn4FI.jpg?r=7cc",""));
        homeCatListItem1.add(new CategoryItem(4,"Conan","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABXtBLc208bAp7I56KEmrAQh3212Sg-vum_-1xTR7NOy7kMcOCj2G7qzMN-fo8xthSgJyQEZuV-iByQ3jSeHJfhj6Y2Q.jpg?r=a9b",""));

        List<CategoryItem> homeCatListItem2 = new ArrayList<>();
        homeCatListItem2.add(new CategoryItem(1,"Beowulf","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABThzRxusSei5ZvSHQDS4AtYZpBq1AK0PzVFPb225YMmlHCCBT8fx6zn1Huclm2KaCtRgTPl3jReqJlrKNfck1i-uXWk.jpg?r=c49",""));
        homeCatListItem2.add(new CategoryItem(2,"Saving Private Ryan","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZU9Kyo87jC4ykwO-j6jyDF0qdsSju63pfuPi2afEEE4fRdMVVB5ylZAtImfag165Z-CHXVa2v30xx6TfLIhUSMraco.jpg?r=d88",""));
        homeCatListItem2.add(new CategoryItem(3,"The Losers","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABf3LzKoIPkED_kZR7p_8ClNLigQIHcn44fykEDWi-UVm8OGosiony8wS77-ZyeZOhNFWZkoDhEqZay4fvAMsIgChh2Y.jpg?r=92a",""));
        homeCatListItem2.add(new CategoryItem(4,"Jona Hex","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQOt_r9azB8eloTRlsxQs8nx1dKpBHkit3bBtOR32VvRYpDjX00JLhRYJDowIw7NfyxZQR2PF_I92U9chbK3ITqfdyk.jpg?r=62b",""));

        List<CategoryItem> homeCatListItem3 = new ArrayList<>();
        homeCatListItem3.add(new CategoryItem(1,"Wolf of Wall Street","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABVa0nRr27-p8qjJC2UiIGhxGLDztmMcyzsoP3h9pf4BvigT92njfRSy4VuTkMk3H1S7vLN7iInxIMo7Ce5QQFDqCxyM.jpg?r=4cc",""));
        homeCatListItem3.add(new CategoryItem(2,"RED","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABSCBa6FJg0KKpXoVAZ0XOc_0gdPp3RX0-2kvbt4ThLcd1-4Nc2DQvN1cASAOkVhWiXjlsJ-uk_5_fHd3eto6KLPqcrY.jpg?r=155",""));
        homeCatListItem3.add(new CategoryItem(3,"Flatliners","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABXeoSIq1umZOdvNsxrNcVi6Zjjkw4xGNqox997Rb9WIBm1Q-_DIPOp4MPKZjkXgcIzVpn7iYpLHBqM-lfI59wrkFJY8.jpg?r=886",""));
        homeCatListItem3.add(new CategoryItem(4,"21","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABUFYRUp_pteStuMGE6CphCNFP71nBFiUemTT93KriWW3pIW4Ywc2GJ9EjpSLPvs_VKIZAHuAcnDr10u9io8m5COqXdY.jpg?r=e73",""));

        List<CategoryItem> homeCatListItem4 = new ArrayList<>();
        homeCatListItem4.add(new CategoryItem(1,"Coach Carter","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZi0lKLCdafrlI879lbZ8hkHSFs7y8JmRAw0ua3zZM7Aw9cDGnBQ0SqhdE42HFD69OxaAGmtaGFQorXiNwpnFCUqUS0.jpg?r=8c1",""));
        homeCatListItem4.add(new CategoryItem(2,"The Mechanic","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABVznyTSQpcQNvdiWVnfAZBg--HVFcm_LdSVvfPq76Lcmp4Qd0NmRZ9Qm_Hck8yK6hhBDHfadKc-rWq6NHC6EejrF68c.jpg?r=48f",""));
        homeCatListItem4.add(new CategoryItem(3,"Friends with Benefits","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABd7PipmIOgJabekfzS0DDcSNQgylUgbR-kWuJpXoyosNJZpf8_bkyUZZ6EItAiKBbLi9E1pCbbjuvwyfXk32IBKKDz8.jpg?r=029",""));
        homeCatListItem4.add(new CategoryItem(4,"After Earth","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQBT8plK8hsc90kI14AxcfaKb6VbdqAKLaaBviyhiioTfvvwV3wYdYJKDAObWDY1UKKECAy0upSjuDoP_hcCdnZiWNQ.jpg?r=30b",""));

        allCategoryList = new ArrayList<>();
        allCategoryList.add(new AllCategory(1,"Most Popular", homeCatListItem1));
        allCategoryList.add(new AllCategory(2,"Watch Again",homeCatListItem2));
        allCategoryList.add(new AllCategory(3,"Newly Added", homeCatListItem3));
        allCategoryList.add(new AllCategory(4,"Leaving Soon", homeCatListItem4));

        setMainRecycler(allCategoryList);
    }

    private void  setAdapter(List<BannerMovies> bannerMoviesList){

        banner_viewpager = findViewById(R.id.banner_viewpager);
        adapter = new Adapter(this,bannerMoviesList);
        banner_viewpager.setAdapter(adapter);
        IndicatorTab.setupWithViewPager(banner_viewpager);

        Timer SliderTimer = new Timer();
        SliderTimer.scheduleAtFixedRate(new AutoSlider(),4000, 6000);
        IndicatorTab.setupWithViewPager(banner_viewpager,true);

    }

    class AutoSlider extends TimerTask{

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(() -> {

                if (banner_viewpager.getCurrentItem() < homeBannerList.size() - 1){

                    banner_viewpager.setCurrentItem(banner_viewpager.getCurrentItem() + 1);

                }else {
                    banner_viewpager.setCurrentItem(0);
                }

            });

        }
    }
    public void setMainRecycler(List<AllCategory>allCategoryList){

        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this,allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);

    }

    private void SetScrollDefaultState(){

        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        
        if (user == null){
            startActivity(new Intent(MainActivity.this, Login_Activity.class));
        }

    }

}