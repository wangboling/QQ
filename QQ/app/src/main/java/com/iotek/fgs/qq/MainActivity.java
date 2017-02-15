package com.iotek.fgs.qq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.iotek.fgs.util.GenUtil;
import com.nineoldandroids.view.ViewHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    DrawerLayout dr;

    TabHost tab, tabrecord;
    TextView tvLogin, tvPay;
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(GenUtil.LOGIN_CODE);
        startActivity(intent);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_main);
        Init();
        ListenerInit();
        tab.setup();
        tab.addTab(tab
                .newTabSpec("0")
                .setIndicator(
                        LayoutInflater.from(MainActivity.this).inflate(
                                R.layout.message_tv, null, true))
                .setContent(contentFactory));
        tab.addTab(tab
                .newTabSpec("1")
                .setIndicator(
                        LayoutInflater.from(MainActivity.this).inflate(
                                R.layout.contacts_tv, null, true))
                .setContent(contentFactory));

        tab.addTab(tab
                .newTabSpec("2")
                .setIndicator(
                        LayoutInflater.from(MainActivity.this).inflate(
                                R.layout.my_tv, null, true))
                .setContent(contentFactory));
        vp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        tab.setOnTabChangedListener(this);
        vp.setOnPageChangeListener(this);

    }
    private void ListenerInit() {
        dr.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = dr.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;
                float leftScale = 1 - 0.3f * scale;

                ViewHelper.setScaleX(mMenu, leftScale);
                ViewHelper.setScaleY(mMenu, leftScale);
                ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                ViewHelper.setTranslationX(mContent,
                        mMenu.getMeasuredWidth() * (1 - scale));
                ViewHelper.setPivotX(mContent, 0);
                ViewHelper.setPivotY(mContent,
                        mContent.getMeasuredHeight() / 2);
                mContent.invalidate();
                ViewHelper.setScaleX(mContent, rightScale);
                ViewHelper.setScaleY(mContent, rightScale);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }


    TabHost.TabContentFactory contentFactory = new TabHost.TabContentFactory() {

        @Override
        public View createTabContent(String arg0) {
            View view = new View(getApplicationContext());
            view.setMinimumHeight(0);
            view.setMinimumWidth(0);
            return view;
        }
    };

    private void Init() {
        tab = (TabHost) findViewById(R.id.main_tabhost);
        vp = (ViewPager) findViewById(R.id.mainView_pager);
        dr = (DrawerLayout)findViewById(R.id.content_main_dr);

    }



    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabChanged(String arg0) {
        if(arg0.equals("0")){
            vp.setCurrentItem(0);
        }else if(arg0.equals("1")){
            vp.setCurrentItem(1);
        }else if(arg0.equals("2")){
            vp.setCurrentItem(2);
        }

    }


}
