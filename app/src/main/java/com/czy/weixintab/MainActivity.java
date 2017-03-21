package com.czy.weixintab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private List<Fragment> tabFragments;

    private List<ShadeTabView> tabIndicators;

    private ViewPager viewPager;

    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        viewPager.setAdapter(adapter);
    }

    private void initData() {
        tabFragments = new ArrayList<>();
        tabIndicators = new ArrayList<>();
        TabFragment weiXinFragment = TabFragment.newInstance("微信");
        TabFragment contactsFragment = TabFragment.newInstance("通讯录");
        TabFragment discoverFragment = TabFragment.newInstance("发现");
        TabFragment meFragment = TabFragment.newInstance("我");
        tabFragments.add(weiXinFragment);
        tabFragments.add(contactsFragment);
        tabFragments.add(discoverFragment);
        tabFragments.add(meFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return tabFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return tabFragments.get(arg0);
            }
        };
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(this);
        ShadeTabView stv_weiXin = (ShadeTabView) findViewById(R.id.stv_weiXin);
        ShadeTabView stv_address_book = (ShadeTabView) findViewById(R.id.stv_address_book);
        ShadeTabView stv_discover = (ShadeTabView) findViewById(R.id.stv_discover);
        ShadeTabView stv_me = (ShadeTabView) findViewById(R.id.stv_me);
        tabIndicators.add(stv_weiXin);
        tabIndicators.add(stv_address_book);
        tabIndicators.add(stv_discover);
        tabIndicators.add(stv_me);
        stv_weiXin.setOnClickListener(this);
        stv_address_book.setOnClickListener(this);
        stv_discover.setOnClickListener(this);
        stv_me.setOnClickListener(this);
        stv_weiXin.setIconAlpha(0);
    }

    /**
     * 重置Tab状态
     */
    private void resetTabsStatus() {
        for (int i = 0; i < tabIndicators.size(); i++) {
            tabIndicators.get(i).setIconAlpha(1);
        }
    }

    /**
     * 如果是直接点击图标来跳转页面的话，position值为0到3，positionOffset一直为0.0
     * 如果是通过滑动来跳转页面的话
     * 假如是从第一页滑动到第二页
     * 在这个过程中，positionOffset从接近0逐渐增大到接近1.0，滑动完成后又恢复到0.0，而position只有在滑动完成后才从0变为1
     * 假如是从第二页滑动到第一页
     * 在这个过程中，positionOffset从接近1.0逐渐减小到0.0，而position一直是0
     *
     * @param position             当前页面索引
     * @param positionOffset       偏移量
     * @param positionOffsetPixels 偏移量
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ShadeTabView leftTab = tabIndicators.get(position);
            ShadeTabView rightTab = tabIndicators.get(position + 1);
            leftTab.setIconAlpha(positionOffset);
            rightTab.setIconAlpha(1 - positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        resetTabsStatus();
        switch (v.getId()) {
            case R.id.stv_weiXin:
                tabIndicators.get(0).setIconAlpha(0);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.stv_address_book:
                tabIndicators.get(1).setIconAlpha(0);
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.stv_discover:
                tabIndicators.get(2).setIconAlpha(0);
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.stv_me:
                tabIndicators.get(3).setIconAlpha(0);
                viewPager.setCurrentItem(3, false);
                break;
        }
    }

}
