package com.wzh.guess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Peace extends Activity{
//	private TextView text1=null;
	private Button bt=null;
	private int number;	
	private String playername;
	private String playerid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.peace);
		
		try {
			Thread.sleep(2000);									//延迟两秒跳转到这个界面
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.bt=(Button)super.findViewById(R.id.mybt2);			//取得组件
		
		Intent intent=getIntent();
		this.number=intent.getFlags();
		playername=intent.getStringExtra("playername");
		playerid=intent.getStringExtra("playerid");
		
		bt.setOnClickListener(new OnClickListener() {			//设置监听实现调到Play界面
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				if(Peace.this.number==1){
						intent.setClass(Peace.this, Play.class);
					}
					else if(Peace.this.number==3){
						intent.setClass(Peace.this, PlayThree.class);
					}
				intent.putExtra("playersname", playername);
				intent.putExtra("playersid", playerid);
				startActivity(intent);
				Peace.this.finish();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		 if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode){
	           finish();
	           return true;
	        }
		return super.onKeyDown(keyCode, event);
	}
}
