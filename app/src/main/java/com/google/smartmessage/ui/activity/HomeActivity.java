package com.google.smartmessage.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.smartmessage.R;
import com.google.smartmessage.adapter.MainPagerAdapter;
import com.google.smartmessage.ui.fragment.ConversationFragment;
import com.google.smartmessage.ui.fragment.GroupFragment;
import com.google.smartmessage.ui.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ============================================================
 * Copyright：Google有限公司版权所有 (c) 2017
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：SmartMessage
 * Package_Name：PACKAGE_NAME
 * Version：1.0
 * time：2016/2/16 12:35
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.tab)
    TabLayout mTab;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private MainPagerAdapter adapter;
    private List<Fragment> fragments;
    private int[] tabIcons = {
            R.mipmap.img_conversation,
            R.mipmap.img_group,
            R.mipmap.img_search
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupViewPager(mViewPager);
        mTab.setupWithViewPager(mViewPager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        fragments = new ArrayList<Fragment>();
        ConversationFragment fragment1 = new ConversationFragment();
        GroupFragment fragment2 = new GroupFragment();
        SearchFragment fragment3 = new SearchFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        mTab.getTabAt(0).setIcon(tabIcons[0]);
        mTab.getTabAt(1).setIcon(tabIcons[1]);
        mTab.getTabAt(2).setIcon(tabIcons[2]);
    }
}
