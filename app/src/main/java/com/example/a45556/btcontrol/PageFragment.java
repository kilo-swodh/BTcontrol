package com.example.a45556.btcontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.a45556.btcontrol.R.layout.page;

public class PageFragment  extends Fragment{

	private Spinner spinner1,spinner2,spinner3,spinner4,spinner5;
	private SeekBar seekBar1,seekBar2,seekBar3,seekBar4,seekBar5;
	private EditText edText;
	private TextView tvLED1,tvLED2,tvLED3,tvLED4,tvLED5;

    private int position;
/*
	private int fragmentID;

	static PageFragment getInstance(int fragmentID) {
		PageFragment pageFragment = new PageFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("id", fragmentID);
		pageFragment.setArguments(bundle);
		return pageFragment;
	}
*/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//fragmentID = getArguments().getInt("id");
		RelativeLayout root = (RelativeLayout) inflater.inflate(page, null);
		spinner1 = (Spinner)root.findViewById(R.id.sp_1);
		spinner2 = (Spinner)root.findViewById(R.id.sp_2);
		spinner3 = (Spinner)root.findViewById(R.id.sp_3);
		spinner4 = (Spinner)root.findViewById(R.id.sp_4);
		spinner5 = (Spinner)root.findViewById(R.id.sp_5);
		edText = (EditText)root.findViewById(R.id.ed_time);
		seekBar1 = (SeekBar)root.findViewById(R.id.sk_led1);
		seekBar2 = (SeekBar)root.findViewById(R.id.sk_led2);
		seekBar3 = (SeekBar)root.findViewById(R.id.sk_led3);
		seekBar4 = (SeekBar)root.findViewById(R.id.sk_led4);
		seekBar5 = (SeekBar)root.findViewById(R.id.sk_led5);
		tvLED1 = (TextView)root.findViewById(R.id.tv_led1);
		tvLED2 = (TextView)root.findViewById(R.id.tv_led2);
		tvLED3 = (TextView)root.findViewById(R.id.tv_led3);
		tvLED4 = (TextView)root.findViewById(R.id.tv_led4);
		tvLED5 = (TextView)root.findViewById(R.id.tv_led5);
		initAdapter();
		initListener();
		return root;
    }

	private void initAdapter(){
		spinner1.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,
				new String[]{"较慢","一般","较快"}));
		spinner2.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,
				new String[]{"较慢","一般","较快"}));
		spinner3.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,
				new String[]{"较慢","一般","较快"}));
		spinner4.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,
				new String[]{"较慢","一般","较快"}));
		spinner5.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,
				new String[]{"较慢","一般","较快"}));
		spinner1.setSelection(1,true);
		spinner2.setSelection(1,true);
		spinner3.setSelection(1,true);
		spinner4.setSelection(1,true);
		spinner5.setSelection(1,true);
	}

	private void initListener(){
        //亮度************
		seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvLED1.setText((i+30)+"%亮度");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
                LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
                settingActivity.light[getPosition()][0] = seekBar.getProgress();
            }
		});

		seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvLED2.setText((i+30)+"%亮度");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
                LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
                settingActivity.light[getPosition()][1] = seekBar.getProgress();
            }
		});

		seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvLED3.setText((i+30)+"%亮度");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
                LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
                settingActivity.light[getPosition()][2] = seekBar.getProgress();
            }
		});

		seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvLED4.setText((i+30)+"%亮度");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
                LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
                settingActivity.light[getPosition()][3] = seekBar.getProgress();
            }
		});

		seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvLED5.setText((i+30)+"%亮度");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
                LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
                settingActivity.light[getPosition()][4] = seekBar.getProgress();}
		});

        //闪烁频率*********************
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
                settingActivity.rate[getPosition()][0] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
                settingActivity.rate[getPosition()][1] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
                settingActivity.rate[getPosition()][2] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
                settingActivity.rate[getPosition()][3] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
                settingActivity.rate[getPosition()][4] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});

        edText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				LightSettingActivity settingActivity = (LightSettingActivity)getActivity();
				settingActivity.time[getPosition()]= s.toString();
			}
		});
	}



	public Bundle getInfoBundle(){
		Bundle bundle = new Bundle();
		bundle.putString("time",edText.getText().toString());
		bundle.putIntArray("rate",new int[]{
				spinner1.getSelectedItemPosition(),
				spinner2.getSelectedItemPosition(),
				spinner3.getSelectedItemPosition(),
				spinner4.getSelectedItemPosition(),
				spinner5.getSelectedItemPosition()
		});
		bundle.putIntArray("light",new int[]{
				seekBar1.getProgress(),
				seekBar2.getProgress(),
				seekBar3.getProgress(),
				seekBar4.getProgress(),
				seekBar5.getProgress(),
		});
		return bundle;
	}

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }
}
