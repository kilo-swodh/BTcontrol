package com.example.a45556.btcontrol;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.a45556.btcontrol.utils.PreferenceUtil;
import com.google.common.collect.Lists;

import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerManager;
import github.chenupt.springindicator.SpringIndicator;

public class LightSettingActivity extends FragmentActivity {
    public static int[][] rate = new int[6][5];
    public static int[][] light = new int[6][5];
    public static String[] time = new String[5];

	private ViewPager viewPager;
	private List<com.example.a45556.btcontrol.PageFragment> fragments;
	private FragmentManager fragmentManager;
	private ImageButton btnSave;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.light_setting);
		btnSave = (ImageButton)findViewById(R.id.btn_save);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        //初始化ViewPager
        final PagerManager manager = new PagerManager();
        manager.setTitles(getTitles());
        //添加4个ViewPager页面
        manager.addFragment(new PageFragment(),0);
        manager.addFragment(new PageFragment(),1);
        manager.addFragment(new PageFragment(),2);
        manager.addFragment(new PageFragment(),3);
        manager.addFragment(new PageFragment(),4);
        manager.addFragment(new PageFragment(),5);
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i :rate[0]){
                    Log.d("Rate", i+"");
                }
                for (int i :light[0]){
                    Log.d("Light", i+"");
                }
                Log.d("Time", time[0]);
                finish();
            }
        });
        springIndicator.setViewPager(viewPager);
    }

    private void savePre(PagerManager manager,int i){
        PreferenceUtil preferenceUtil = PreferenceUtil.getInstance();
        PageFragment fragment = (PageFragment)manager.getItem(i);
        Bundle info = fragment.getInfoBundle();
        StringBuilder builder = new StringBuilder();
        builder.append(info.getString("time","0")).append("&");
        int[] arr1 = info.getIntArray("rate");
        for (int temp:arr1){
            builder.append(temp).append("&");
        }
        int[] arr2 = info.getIntArray("rate");
        for (int temp:arr2){
            builder.append(temp).append("&");
        }
        preferenceUtil.writePreferences("step"+i,builder.toString());
    }

	private List<String> getTitles(){
        return Lists.newArrayList("Step1", "Step2", "Step3", "Step4","Step5","Step6");
    }
}
