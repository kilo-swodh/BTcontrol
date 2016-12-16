package com.example.a45556.btcontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class PageFragment  extends Fragment{

	private Spinner spinner1,spinner2,spinner3,spinner4,spinner5;
	private SeekBar seekBar1,seekBar2,seekBar3,seekBar4,seekBar5;
	private EditText edText;
	private TextView tvLED1,tvLED2,tvLED3,tvLED4,tvLED5;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RelativeLayout root = (RelativeLayout) inflater.inflate(R.layout.page, null);
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
	}

	private void initListener(){
		seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvLED1.setText((i+30)+"%亮度");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvLED2.setText((i+30)+"%亮度");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvLED3.setText((i+30)+"%亮度");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvLED4.setText((i+30)+"%亮度");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tvLED5.setText((i+30)+"%亮度");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

	}
}
