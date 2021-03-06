package com.wzh.guess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Lose extends Activity{
	private Button bt=null;
	private int number;
	
	private String playername;
	private String playerid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lose);
		
		try {
			Thread.sleep(2000);										//延迟两秒跳转到这个界面
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.bt=(Button)super.findViewById(R.id.mybt1);				//取得组件
		
		Intent intent=getIntent();
		this.number=intent.getFlags();
		playername=intent.getStringExtra("playername");
		playerid=intent.getStringExtra("playerid");
		
		bt.setOnClickListener(new OnClickListener() {				//设置监听实现调到Play界面
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inten=new Intent();
				
				if(Lose.this.number==1){
					inten.setClass(Lose.this, Play.class);
					
					}
					else if(Lose.this.number==3){
						inten.setClass(Lose.this, PlayThree.class);
					}
				inten.putExtra("playersname", playername);
				inten.putExtra("playersid", playerid);
				startActivity(inten);
				Lose.this.finish();
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
