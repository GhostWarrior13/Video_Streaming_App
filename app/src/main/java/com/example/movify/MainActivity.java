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


        homeBannerList = new ArrayList<>();
        homeBannerList.add(new BannerMovies(1,"The Crimes of Grindelwald","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQp_F45VJd_Z1iHwWZoK9qGCt64kQPPpfxnw9eYOBggVzWn6FxNDoL3aWLa7TmAfqk3y-4d88WWI8NZmf1sQQFXFf-I.jpg?r=d6b", "https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Fantastic%20Beasts%20The%20Crimes%20of%20Grindelwald%20-%20Official%20Comic-Con%20Trailer.mp4?alt=media&token=0371cac5-9d16-4974-9bc6-b82c9fced675"));
        homeBannerList.add(new BannerMovies(2,"Clash of the Titans","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZBP8POvQ1GQbmAXzvYle2UvMTaGb5LV5gA1bdgKbJsPqmZxu43Set0fig0-I7DDrkikG8qxgTwj5HdRDj093AcwMWI.jpg?r=691", "https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Official%20Clash%20of%20the%20Titans%20Movie%20Trailer%202010%20%5BHD%5D.mp4?alt=media&token=18d9f80b-fd52-42b2-8638-043899085268"));
        homeBannerList.add(new BannerMovies(3,"IT","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABWSKHlBCbFClRWKUSWdjCBI6aOeWolBKt2XNdg6lzskGWQ0g1AL1V2hlmrpTtpHORGMR_ooVafqHUX3xiwH0TlmEp44.jpg?r=efc", "https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/It%20Trailer%20%231%20(2017)%20%20%20Movieclips%20Trailers.mp4?alt=media&token=b161e6b9-0c6b-435c-a21d-71e36be83a90"));

        videoBannerList = new ArrayList<>();
        videoBannerList.add(new BannerMovies(1,"SHREK 2","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABS2SrvalX8NS2C7tgbftN8yfVPaX3RWGQ_6TbxxnKKlUFsCQxSMzXnq5I78X0fm1KKH7W63ElWr2fmRmG_-M_PtgkZ0.jpg?r=f0d", "https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Shrek%202%20(Trailer).mp4?alt=media&token=c24fbbfc-a02a-493a-bec2-bf4fee29faae"));
        videoBannerList.add(new BannerMovies(2,"The Conjuring","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABffNTVimB9s4faS2cqIKG8e9tHrcy5y7S-x6QQBcMWXAO5sPhlA9-fzRCJGJFfp1iVE0lCZc87LCyM-KZ-fPWCigYts.jpg?r=fbf", "https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/The%20Conjuring%20-%20Official%20Main%20Trailer%20%5BHD%5D.mp4?alt=media&token=bd19bf8e-af7e-43cd-aba3-51fbd735bdaf"));
        videoBannerList.add(new BannerMovies(3,"Gemini Man","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABYrfj8uGl6OJZ4zUBW0zGR3ooLurHz76lPYDkHJQ32sZTfAyRh-qJwh2ceEr4aMk1fhSO_6RBH8eHY4r8SXJRr7WGrg.jpg?r=bcd", "https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Gemini%20Man%20(2019)%20-%20Official%20Trailer%20-%20Paramount%20Pictures.mp4?alt=media&token=c72ee64a-9f12-420f-b08b-64d3d7b2a2b2"));

        streamBannerList = new ArrayList<>();
        streamBannerList.add(new BannerMovies(1,"Tomb Raider: The cradle of life","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQBhYVIZ-bnn8mZzyFgJqcB8wKNnZxaYAhBtEy_Yxai9NWVQdCb4RjiZkR4aOCxfQ6R_L627nfPT9NHBoVopAtCCMYg.jpg?r=aaf", "https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Lara%20Croft%20Tomb%20Raider%20The%20Cradle%20of%20Life%20(2003)%20Official%20Trailer%20%231%20-%20Angelina%20Jolie%20Movie%20HD.mp4?alt=media&token=ccd0f96c-7674-47c9-b41b-94404373f50e"));
        streamBannerList.add(new BannerMovies(2,"Forest Gump","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABSYEAa82SB8pwIohZzDkxWkveGPVO4-xL8JbNKHTC6Q99-0iBb2-vkwolMaL-tD-2mVpxVDcN3xab6aKHI8-r1kwF58.jpg?r=188", "https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Forrest%20Gump%20-%20Trailer.mp4?alt=media&token=192343c4-7515-4949-9d69-5224847c8f99"));
        streamBannerList.add(new BannerMovies(3,"The Craft Legacy","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABWonZM3POj9Wd_q-5fyE020iTCyTCbo0xaPPSxmyyS_DXM0I2lGQyHL4kKLwDvZJC-TLit4VUInAOFFmAKHN6eF8H-o.jpg?r=5d3", "https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/THE%20CRAFT%20Legacy%20Trailer%20(2020).mp4?alt=media&token=37671669-cdb7-4331-9efb-50c193229d14"));

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
        homeCatListItem1.add(new CategoryItem(1,"The Dark Tower","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABW7vLrXgKEKvHkp-Jf-Za0aQhGtM5lR3yGoSPQRns4LdGXazLqILwyFpreWDQGuhQ0GRhqm77fNXN0oUsibad5HB-d0.jpg?r=d09","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/THE%20DARK%20TOWER%20-%20Official%20Trailer%20(HD).mp4?alt=media&token=9c85598b-78fb-40fe-b006-4e4f5d198124"));
        homeCatListItem1.add(new CategoryItem(3,"The 7th SON","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQXtAwyEvBK6XXA5Z-FOJosLn7uwGBbRCMi9a6ZVIBpckcnWj7flujGnpQtARF3yJ3cdGB-K5x4F-n-UBwPVDzsn4FI.jpg?r=7cc","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Seventh%20Son%20Official%20Trailer%20%232%20(2015)%20-%20Jeff%20Bridges%2C%20Julianne%20Moore%20Fantasy%20Adventure%20HD.mp4?alt=media&token=759dac44-bc0e-430d-a00b-7bf6a9231fd8"));
        homeCatListItem1.add(new CategoryItem(4,"Conan","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABXtBLc208bAp7I56KEmrAQh3212Sg-vum_-1xTR7NOy7kMcOCj2G7qzMN-fo8xthSgJyQEZuV-iByQ3jSeHJfhj6Y2Q.jpg?r=a9b","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Conan%20the%20Barbarian%20(2011)%20-%20Official%20Trailer%20-%20%20A%20Legend%20Will%20Rise.mp4?alt=media&token=45ffcc59-f225-4a20-928b-d07e1098515a"));

        List<CategoryItem> homeCatListItem2 = new ArrayList<>();
        homeCatListItem2.add(new CategoryItem(1,"Beowulf","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABThzRxusSei5ZvSHQDS4AtYZpBq1AK0PzVFPb225YMmlHCCBT8fx6zn1Huclm2KaCtRgTPl3jReqJlrKNfck1i-uXWk.jpg?r=c49","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Beowulf%20(2007)%20Trailer%20%231%20%20%20Movieclips%20Classic%20Trailers.mp4?alt=media&token=beedd165-ea82-4d23-a446-bc68c71c92db"));
        homeCatListItem2.add(new CategoryItem(2,"Saving Private Ryan","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZU9Kyo87jC4ykwO-j6jyDF0qdsSju63pfuPi2afEEE4fRdMVVB5ylZAtImfag165Z-CHXVa2v30xx6TfLIhUSMraco.jpg?r=d88","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Saving%20Private%20Ryan%20(1998)%20Trailer%20%231%20%20%20Movieclips%20Classic%20Trailers.mp4?alt=media&token=62be1e0b-1d05-49fe-af27-9c3c78e9486b"));
        homeCatListItem2.add(new CategoryItem(3,"The Losers","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABf3LzKoIPkED_kZR7p_8ClNLigQIHcn44fykEDWi-UVm8OGosiony8wS77-ZyeZOhNFWZkoDhEqZay4fvAMsIgChh2Y.jpg?r=92a","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/The%20Losers%20-%20Official%20Trailer%20%5BHD%5D.mp4?alt=media&token=174364a4-1fd1-4fcd-b375-26860c8ef583"));
        homeCatListItem2.add(new CategoryItem(4,"Jona Hex","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQOt_r9azB8eloTRlsxQs8nx1dKpBHkit3bBtOR32VvRYpDjX00JLhRYJDowIw7NfyxZQR2PF_I92U9chbK3ITqfdyk.jpg?r=62b","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/'Jonah%20Hex'%20Trailer%20HD.mp4?alt=media&token=c6e397ce-d07c-4e09-81c9-7b7241bfc819"));

        List<CategoryItem> homeCatListItem3 = new ArrayList<>();
        homeCatListItem3.add(new CategoryItem(1,"Wolf of Wall Street","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABVa0nRr27-p8qjJC2UiIGhxGLDztmMcyzsoP3h9pf4BvigT92njfRSy4VuTkMk3H1S7vLN7iInxIMo7Ce5QQFDqCxyM.jpg?r=4cc",""));
        homeCatListItem3.add(new CategoryItem(2,"RED","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABSCBa6FJg0KKpXoVAZ0XOc_0gdPp3RX0-2kvbt4ThLcd1-4Nc2DQvN1cASAOkVhWiXjlsJ-uk_5_fHd3eto6KLPqcrY.jpg?r=155","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Red%20(2010)%20Official%20Trailer%20-%20Bruce%20Willis%2C%20Morgan%20Freeman%20Action%20Movie%20HD.mp4?alt=media&token=191d5949-5a0a-4a30-a929-6b8651ef002a"));
        homeCatListItem3.add(new CategoryItem(3,"Flatliners","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABXeoSIq1umZOdvNsxrNcVi6Zjjkw4xGNqox997Rb9WIBm1Q-_DIPOp4MPKZjkXgcIzVpn7iYpLHBqM-lfI59wrkFJY8.jpg?r=886","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Flatliners%20Trailer%20%231%20(2017)%20%20%20Movieclips%20Trailers.mp4?alt=media&token=89be715c-098b-45de-8d7d-5b8587f7a85f"));
        homeCatListItem3.add(new CategoryItem(4,"21","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABUFYRUp_pteStuMGE6CphCNFP71nBFiUemTT93KriWW3pIW4Ywc2GJ9EjpSLPvs_VKIZAHuAcnDr10u9io8m5COqXdY.jpg?r=e73","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/21%20trailer.mp4?alt=media&token=fe57bdbd-b02f-46ac-a3ac-e073051f2c55"));

        List<CategoryItem> homeCatListItem4 = new ArrayList<>();
        homeCatListItem4.add(new CategoryItem(1,"Coach Carter","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZi0lKLCdafrlI879lbZ8hkHSFs7y8JmRAw0ua3zZM7Aw9cDGnBQ0SqhdE42HFD69OxaAGmtaGFQorXiNwpnFCUqUS0.jpg?r=8c1","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Coach%20Carter%20(2005)%20Trailer%20%231%20%20%20Movieclips%20Classic%20Trailers.mp4?alt=media&token=7c5c8c10-e144-4401-a54f-8b819396c76a"));
        homeCatListItem4.add(new CategoryItem(2,"The Mechanic","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABVznyTSQpcQNvdiWVnfAZBg--HVFcm_LdSVvfPq76Lcmp4Qd0NmRZ9Qm_Hck8yK6hhBDHfadKc-rWq6NHC6EejrF68c.jpg?r=48f","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Mechanic%20Resurrection%20Official%20Trailer%20%231%20(2016)%20-%20Jason%20Statham%2C%20Jessica%20Alba%20Movie%20HD.mp4?alt=media&token=14b383a1-7212-43b2-bde0-a4f69a21bc87"));
        homeCatListItem4.add(new CategoryItem(3,"Friends with Benefits","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABd7PipmIOgJabekfzS0DDcSNQgylUgbR-kWuJpXoyosNJZpf8_bkyUZZ6EItAiKBbLi9E1pCbbjuvwyfXk32IBKKDz8.jpg?r=029","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Friends%20with%20Benefits%20Movie%20Trailer%20Official.mp4?alt=media&token=eb30cfb4-7e84-40c3-b57b-a35d29c6fa70"));
        homeCatListItem4.add(new CategoryItem(4,"After Earth","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQBT8plK8hsc90kI14AxcfaKb6VbdqAKLaaBviyhiioTfvvwV3wYdYJKDAObWDY1UKKECAy0upSjuDoP_hcCdnZiWNQ.jpg?r=30b","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/After%20Earth%20Official%20Trailer%20%231%20(2013)%20-%20Will%20Smith%20Movie%20HD.mp4?alt=media&token=b9ed56b4-b2f7-4804-95fe-480b321e6a34"));

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