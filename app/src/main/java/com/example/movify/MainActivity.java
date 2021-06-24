package com.example.movify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    FirebaseDatabase database;
    DatabaseReference reference;




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
        homeBannerList.add(new BannerMovies(1,"SnowPiercer","", ""));
        homeBannerList.add(new BannerMovies(2,"Wanderlust","", ""));
        homeBannerList.add(new BannerMovies(3,"The Crown","", ""));

        videoBannerList = new ArrayList<>();
        videoBannerList.add(new BannerMovies(1,"Merlin","", ""));
        videoBannerList.add(new BannerMovies(2,"Jupiter's Legacy","", ""));
        videoBannerList.add(new BannerMovies(3,"Glitch","", ""));

        streamBannerList = new ArrayList<>();
        streamBannerList.add(new BannerMovies(1,"Shaft","", ""));
        streamBannerList.add(new BannerMovies(2,"Poseidon","", ""));
        streamBannerList.add(new BannerMovies(3,"The Tourist","", ""));

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
        homeCatListItem1.add(new CategoryItem(1,"Peaky Blinders","",""));
        homeCatListItem1.add(new CategoryItem(3,"Riverdale","",""));
        homeCatListItem1.add(new CategoryItem(4,"Suits","",""));

        List<CategoryItem> homeCatListItem2 = new ArrayList<>();
        homeCatListItem2.add(new CategoryItem(1,"Prison Break","",""));
        homeCatListItem2.add(new CategoryItem(2,"Originals","",""));
        homeCatListItem2.add(new CategoryItem(3,"Walking Dead","",""));
        homeCatListItem2.add(new CategoryItem(4,"Wild Child","",""));

        List<CategoryItem> homeCatListItem3 = new ArrayList<>();
        homeCatListItem3.add(new CategoryItem(1,"Project Power","",""));
        homeCatListItem3.add(new CategoryItem(2,"Rick and Morty","",""));
        homeCatListItem3.add(new CategoryItem(3,"Breaking Bad","",""));
        homeCatListItem3.add(new CategoryItem(4,"House of Cards","",""));

        List<CategoryItem> homeCatListItem4 = new ArrayList<>();
        homeCatListItem4.add(new CategoryItem(1,"Witcher","",""));
        homeCatListItem4.add(new CategoryItem(2,"Love and Monsters","",""));
        homeCatListItem4.add(new CategoryItem(3,"Wolf of Wall Street","",""));
        homeCatListItem4.add(new CategoryItem(4,"Daredevil","",""));

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