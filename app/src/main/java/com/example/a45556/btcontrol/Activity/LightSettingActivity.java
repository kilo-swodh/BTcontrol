package com.example.a45556.btcontrol.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.example.a45556.btcontrol.R;
import com.example.a45556.btcontrol.utils.ArrayUtil;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerManager;
import github.chenupt.springindicator.SpringIndicator;

public class LightSettingActivity extends FragmentActivity {
    List<Integer> validPositons;

	private ViewPager viewPager;
	private List<PageFragment> fragments;
	private FragmentManager fragmentManager;
	private ImageButton btnRun;
    private ArrayUtil arrayUtil;
    private ArrayList<String> allCmd;
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
                arrayUtil.rate[i][j] = 0;
                arrayUtil.light[i][j] = 0;
            }
            arrayUtil.times[i] = "0";
        }
        for (int i =0; i<5;i++){
            arrayUtil.lastLight[i] = 0;
        }
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseData();
                if (!allCmd.isEmpty()){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("cmd",allCmd);
                    intent.putExtras(bundle);
                    setResult(Activity.RESULT_OK,intent);
                }
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
        allCmd = new ArrayList<>();
        validPositons = new ArrayList<>();
        for (int i =0;i < arrayUtil.times.length;i++){
            String time = arrayUtil.times[i];
            if (time != "0" && time != "00" && time != "000"){
                validPositons.add(i);
            }
        }
        for (Integer i : validPositons){
            List<String> cmd = getCmdString(i.intValue());
            allCmd.add(arrayUtil.times[i]);
            //Log.d("Kilo", "Position: iiiiiiiiiiiiiiiiiiiiis "+ i);
            //Log.d("Kilo", "Time: iiiiiiiiiiiis "+ arrayUtil.times[i]);
            for (String str:cmd){
                allCmd.add(str);
                //Log.d("Kilo", str);
            }
        }
    }

    private List<String> getCmdString(int position){
        List<String> cmd = new ArrayList<>();
        StringBuilder builder;
        for (int i = 0;i < 5;i++){
            builder = new StringBuilder();
            if (arrayUtil.rate[position][i] == 0){
                if (position == 0){
                    builder.append("$$"+(i+1)+"#");
                    builder.append("0&"+arrayUtil.light[position][i]+"*");
                    cmd.add(builder.toString());
                    arrayUtil.lastLight[i] = arrayUtil.light[position][i];
                }else {
                    builder.append("$$"+(i+1)+"#");
                    builder.append(arrayUtil.lastLight[i]+"&"+arrayUtil.light[position][i]+"*");
                    cmd.add(builder.toString());
                    arrayUtil.lastLight[i] = arrayUtil.light[position][i];
                }
            }else {
                builder.append("%%"+(i+1));
                switch (arrayUtil.rate[position][i]){
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
