package com.example.movify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movify.adapter.Adapter;
import com.example.movify.adapter.MainRecyclerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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


    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    List<AllCategory> allCategoryList;

    FirebaseAuth mAuth;
    FirebaseStorage mStorage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IndicatorTab = findViewById(R.id.tab_indicator);
        CategoryTab = findViewById(R.id.TabLayout);
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbar);
        mAuth = FirebaseAuth.getInstance();


        homeBannerList = new ArrayList<>();
        homeBannerList.add(new BannerMovies(1,"SnowPiercer","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABUWo_UpJA3w7lRpb9VYKf3duMhqybKlX7Ok39xqQ2v0wljCNFmo4qx3pwFKsNook1ZE3UnbsvhAp9yXeT5I5Pbj8qDYAWQddT7-Tq-md8E-LqjDth_wlACIRGuVG.jpg?r=77c", ""));
        homeBannerList.add(new BannerMovies(2,"Wanderlust","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABWzHt7vY7atYoRKoInJ9RlBeV1xfHx-qmJcNDOYDuFYaPcjJSnFdLnMMhduI85p5uFUFQiaIc7q3BBoYLlfqLOET-Zk.jpg?r=ad9", ""));
        homeBannerList.add(new BannerMovies(3,"The Crown","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABdPrypf33QcVWc0DHxX7rDoTSbX7EgHSJd_VDlwIr81A40ua4l2F_esQS1u4zB9SkN5TAGO1Bw0SmoEqTICKfx9vbRu-qdKh9KGWuRLasXOrCIUpEYnMffVoebRS.jpg?r=d3b", ""));

        videoBannerList = new ArrayList<>();
        videoBannerList.add(new BannerMovies(1,"Merlin","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABeswLaLDMJhFbvJ9PVgS7XKb7YDdPAM4kDXXJTY7v-UOGjGOe90sfCgeb_z-Tnrg4Tae0sho0NiNr-uZW-NqWtxOEqU.jpg?r=465", ""));
        videoBannerList.add(new BannerMovies(2,"Jupiter's Legacy","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABbbaKS4SlpesdVYy0CoArMUgsNqw8oITrRKAp-jOt6bbCl8vXVinIxco_3d_FNuNfypLpn0sXjO_QZ0StjsWFdXPFfirOTf80RmU0yZzK9fPSi_Is9AApSn0J7Cv.jpg?r=b96", ""));
        videoBannerList.add(new BannerMovies(3,"Glitch","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABVOogXCb2yPJr66Aqtoaj24kx8gaF1hxNDbIsah3avqVsegPE1sj-fq0ZkgeXq-a2QW65wW0o4YK7M3ZBcRljb_68i-iFZDt-K6j7mj_mISgHlJ_h8d_PmF2gu9L4ynesA.jpg?r=397", ""));

        streamBannerList = new ArrayList<>();
        streamBannerList.add(new BannerMovies(1,"Shaft","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABbQCJGc9_cJ9vP5vwCXouMvl8Jcivb_itkFhlyCu7ZBkvQgpUg-eUF1wLHzPXzsNKNk2HHy7R0rsijyrqbRU8MXmjou7ah97BB4Tp9M3800Q38CzGpe7IZ50SUwI.jpg?r=7b5", ""));
        streamBannerList.add(new BannerMovies(2,"Poseidon","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABV7TrdH1UDk17oGZNz43nV6WcvjWmGrhIWd21knz-qisj81bA3tro8AXt3QZYm8W_1FErBrIpWTBygrPjdhUViE6I88.jpg?r=c7f", ""));
        streamBannerList.add(new BannerMovies(3,"The Tourist","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABes5u9sBczsVgZQoo-7S6Nj4OtMzr_YcQ9UhkwggvDcw4OaszK53yfRaVch4PL-HwPEupnt2DtSewz8qnGvb5r4dhxw.jpg?r=d2a", ""));

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
        homeCatListItem1.add(new CategoryItem(1,"Peaky Blinders","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABfD8kWeHWVmHuYZg4-BCE07W0lEJkx4GfzxrSv3Ha2BYGI9t4XvlJjdHRrSNyTCT2BXr-uqmMTak651KhgJBFDrjpl0.jpg?r=98c","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/If%20this%20is%20hell%2C%20it%20looks%20a%20lot%20like%20Margate%20Peaky%20Blinders%20-%20BBC.mp4?alt=media&token=2e366d22-4d73-48c4-9529-89ae9f767387"));
        homeCatListItem1.add(new CategoryItem(3,"Riverdale","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABfKAIuPIrsJqKVs0yARBXiUpdvjp9eforMPQN2Vwlii9o7qJqRMfiaimUHGOZTTU9rvZp7u5sUv6RvgaTGserdVi1g0--jXziDe-tnjtJAP5m0jMZMkpcq_KTCoBAcLeTdESvgTdrppT89TsLdGv9CmOaWD8Ym3Ffq_CN0J_FkK_DBbVLcsx3It_MNWD.jpg?r=e67","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Riverdale%20_%20Official%20Trailer%20%5BHD%5D%20_%20Netflix%20(1080p_24fps_AV1-128kbit_AAC).mp4?alt=media&token=39873dcd-e6f5-4145-9ab8-7775a38f0f29"));
        homeCatListItem1.add(new CategoryItem(4,"Suits","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZhvoyYRwPa4EryQ-ACo_KamR0EmauHxyYCdl51vTCWUWTnILFKZiuRqMK70p2K4m3RghrFnSd8r1MPNmoZ9CgyM4gQ.jpg?r=0a6","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Suits%20-%20(Season%201%20Trailer)%20(1080p_60fps_H264-128kbit_AAC).mp4?alt=media&token=da0801d2-c6a8-42a0-9e1d-145627c90403"));

        List<CategoryItem> homeCatListItem2 = new ArrayList<>();
        homeCatListItem2.add(new CategoryItem(1,"Prison Break","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZL9mBrZQ0u3BscQzzL3gF7dCJKVxVwxyMlctGsdeAxFkfjcwOp2tkEVfirF5x3TyEpYAoOU_G5Wo9NpB7gRQi8oUPg.jpg?r=c15","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Prison%20Break%20-%20Season%201%20Trailer%20(1066p_24fps_AV1-128kbit_AAC).mp4?alt=media&token=3982df81-fbb0-49b3-b714-d63ef04c1e62"));
        homeCatListItem2.add(new CategoryItem(2,"Originals","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABdCs7YAsBIf3-j6eMmexWuFp9zbHvQHksPRZWChDg6TVrQypwwnnZVoTS9O1qnyvWLhmx7v7SBdBhMc7k11eANd2vww.jpg?r=c0f","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/The%20Originals%20Season%201%20Trailer%20(1080p_24fps_AV1-128kbit_AAC).mp4?alt=media&token=0c34be31-93b3-488b-9b0a-0311d3038955"));
        homeCatListItem2.add(new CategoryItem(3,"Walking Dead","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABRIHY2h043aApn-G3-rK9gHlfdZtK2kvmcnCAmyF3z8Aw3wHX06KY-rOOjESfHCEzVuScAXpQlBA8QzlYy5VVau4XXwqxHj-rBtiCBfWa9WA8upJAvaRtx3VXkHmDXCrzA.jpg?r=76b","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/The%20Walking%20Dead%20Trailer%20(First%20Season)%20(720p_60fps_H264-128kbit_AAC).mp4?alt=media&token=6e8cfa56-62ca-45cb-a381-86c0a2e25f16"));
        homeCatListItem2.add(new CategoryItem(4,"Wild Child","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZ1q0Vvgb7CRYhLPRA80D_hqF2KnfG1St4-btK2GC6rpLyTlktcUIXJI5a-6NhV880VBGP7UhQjnd6A2XFU9vqg1BLM.jpg?r=a0b","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Wild%20Child%20(2008)%20Official%20Trailer%20%231%20-%20Emma%20Roberts%2C%20Aidan%20Quinn%20Movie%20HD%20(360p_24fps_H264-128kbit_AAC).mp4?alt=media&token=6f595a53-320e-415b-a4d7-2280bf4e27ef"));

        List<CategoryItem> homeCatListItem3 = new ArrayList<>();
        homeCatListItem3.add(new CategoryItem(1,"Project Power","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABcWBAe6LbU5hObsQlISfmBCUCGnXRR-i-_2ZEdlJWGslVpw8tew4BUtghBITh-zCoPx5xFbbXywi9ER87QIRnRx6IOcQHMvysw748XbiYxrw8UTokzegaXIYIVS6.jpg?r=742","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Project%20Power%20starring%20Jamie%20Foxx%20_%20Official%20Trailer%20_%20Netflix%20(1080p_24fps_AV1-128kbit_AAC).mp4?alt=media&token=939d1399-43fe-49bc-b19d-aa41feab2467"));
        homeCatListItem3.add(new CategoryItem(2,"Rick and Morty","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABSxsipDARw0A0W1KJ4Fv9F1Y_6Vr_oNCPBX-FQmTY03Sq5oP3XgzSa9P4obN99dSqwTuUd5y-L1es8OAi4uwxAq6QCE.jpg?r=7a5","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/OFFICIAL%20TRAILER%20%231_%20Rick%20and%20Morty%20Season%205%20_%20adult%20swim%20(1080p_30fps_AV1-128kbit_AAC).mp4?alt=media&token=f838ad15-0264-4dd2-836d-09e3407b925e"));
        homeCatListItem3.add(new CategoryItem(3,"Breaking Bad","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABUr3Y1-NF-H2vxX46gBEwdYqSlvDsY126karuPPoXnHjijxuk1cXPIb8lrLk8TfN3YXWzniCLhrTOROMXuLaKcji8lI.jpg?r=01d","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Breaking%20Bad%20Trailer%20(First%20Season)%20(720p_60fps_H264-128kbit_AAC).mp4?alt=media&token=84bbd849-9681-48ad-8a13-7c7a88d52624"));
        homeCatListItem3.add(new CategoryItem(4,"House of Cards","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQPZ9iyKbCPBlHYk1KeZvMbu9K0XcGGzwBB-CJZuXS_7bdS_lj8ZexJJChli_gvSAHXAGaq8Si_BeY-8Sfgfv-j8VAIKYdmDpuAPA7lSYOkYf0mOGEM8KUev_d1r.jpg?r=4ef","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/House%20of%20Cards%20_%20Series%20Trailer%20%5BHD%5D%20_%20Netflix%20(1080p_24fps_H264-128kbit_AAC).mp4?alt=media&token=2ead0e17-8c24-4dba-a1c1-6b8968f229ed"));

        List<CategoryItem> homeCatListItem4 = new ArrayList<>();
        homeCatListItem4.add(new CategoryItem(1,"Witcher","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABelflbF7hLllQWMP9vusyHxWklYqi-_xMHvad3rT2_mVkUWZoD_C1iDbNUCeeZ92dfng-rikd9HvJIUvZZ3oo32fhhKlwEdPt1CyR3cT7occykhrEeKHaHJglR78.jpg?r=382","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/THE%20WITCHER%20_%20MAIN%20TRAILER%20_%20NETFLIX%20(2160p_30fps_VP9%20LQ-128kbit_AAC).mkv?alt=media&token=142a2935-4300-443f-9bed-f5c6f5717f42"));
        homeCatListItem4.add(new CategoryItem(2,"Love and Monsters","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZSzxeJBv2SlGbstcVmyz8r3px4ZvSQ_coI1spnKuikaXmdGbYJGX35mCn5akbQpcQfi1BDGJ-f45DY-VbfItF_zSdsYkbcfxO_kCtQTpMbsciUxnH4zWp3Ti7j7.jpg?r=1b9","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/LOVE%20AND%20MONSTERS%20_%20Official%20Trailer%20(1080p_24fps_AV1-128kbit_AAC).mp4?alt=media&token=f08588af-0cb1-4ea7-bbe4-e8d13db9dadc"));
        homeCatListItem4.add(new CategoryItem(3,"Wolf of Wall Street","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABVa0nRr27-p8qjJC2UiIGhxGLDztmMcyzsoP3h9pf4BvigT92njfRSy4VuTkMk3H1S7vLN7iInxIMo7Ce5QQFDqCxyM.jpg?r=4cc","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/The%20Wolf%20of%20Wall%20Street%20Official%20Trailer%20(1080p_24fps_AV1-128kbit_AAC).mp4?alt=media&token=bfab8c5b-407b-414b-b834-165cbc644d08"));
        homeCatListItem4.add(new CategoryItem(4,"Daredevil","https://occ-0-32-34.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABSaCl59yo8cED0edHkHcRryQLc23XTU7ON6hVdO1GIkFm0LFy22cLtvC2R6__k3P5E7qmA_vYhCXr_ls1sw5a_-fFH3iuS1u88Lqto3BQwsgzabS2Kegri7SuDRv.jpg?r=80f","https://firebasestorage.googleapis.com/v0/b/video-streaming-app-bbab7.appspot.com/o/Marvel's%20Daredevil%20_%20Official%20Trailer%20%5BHD%5D%20_%20Netflix%20(1080p_24fps_AV1-128kbit_AAC).mp4?alt=media&token=a860922f-2926-422b-968d-4aa9fd021cf8"));

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