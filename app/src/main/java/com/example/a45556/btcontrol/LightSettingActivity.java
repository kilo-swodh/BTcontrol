package com.example.a45556.btcontrol;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.a45556.btcontrol.utils.ArrayUtil;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerManager;
import github.chenupt.springindicator.SpringIndicator;

import static com.example.a45556.btcontrol.utils.ArrayUtil.lastLight;
import static com.example.a45556.btcontrol.utils.ArrayUtil.light;
import static com.example.a45556.btcontrol.utils.ArrayUtil.rate;
import static com.example.a45556.btcontrol.utils.ArrayUtil.times;

public class LightSettingActivity extends FragmentActivity {
    List<Integer> validPositons;

	private ViewPager viewPager;
	private List<com.example.a45556.btcontrol.PageFragment> fragments;
	private FragmentManager fragmentManager;
	private ImageButton btnRun;
    private ArrayUtil arrayUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.light_setting);
		btnRun = (ImageButton)findViewById(R.id.btn_run);
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
        arrayUtil = ArrayUtil.getInstance();
        for (int i =0 ;i<6;i++){                        //清理上一次的记录
            for (int j = 0;j<5;j++){
                rate[i][j] = 0;
                light[i][j] = 0;
            }
            times[i] = "0";
        }
        for (int i =0; i<5;i++){
            lastLight[i] = 0;
        }
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseData();
                finish();
            }
        });
        springIndicator.setViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void ParseData(){
        validPositons = new ArrayList<>();
        for (int i =0;i < times.length;i++){
            String time = times[i];
            if (time != "0" && time != "00" && time != "000"){
                validPositons.add(i);
            }
        }
        for (Integer i : validPositons){
            List<String> cmd = getCmdString(i.intValue());
            Log.d("Kilo", "Position: iiiiiiiiiiiiiiiiiiiiis "+ i);
            Log.d("Kilo", "Time: iiiiiiiiiiiis "+ times[i]);
            for (String str:cmd)
                Log.d("Kilo", str);
        }
    }

    private List<String> getCmdString(int position){
        List<String> cmd = new ArrayList<>();
        StringBuilder builder;
        for (int i = 0;i < 5;i++){
            builder = new StringBuilder();
            if (rate[position][i] == 0){
                if (position == 0){
                    builder.append("$$"+(i+1)+"#");
                    builder.append("0&"+light[position][i]+"*");
                    cmd.add(builder.toString());
                    lastLight[i] = light[position][i];
                }else {
                    builder.append("$$"+(i+1)+"#");
                    builder.append(lastLight[i]+"&"+light[position][i]+"*");
                    cmd.add(builder.toString());
                    lastLight[i] = light[position][i];
                }
            }else {
                builder.append("%%"+(i+1));
                switch (rate[position][i]){
                    case 1:
                        builder.append("a*");
                        break;
                    case 2:
                        builder.append("b*");
                        break;
                    case 3:
                        builder.append("c*");
                        break;
                }
                cmd.add(builder.toString());
            }
        }
        return cmd;
    }

	private List<String> getTitles(){
        return Lists.newArrayList("Step1", "Step2", "Step3", "Step4","Step5","Step6");
    }

}
