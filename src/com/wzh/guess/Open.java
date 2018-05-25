package com.wzh.guess;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Open extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.open);
		
		final Intent intent=new Intent(Open.this, Login.class);		//实例化Intent
		Timer timer=new Timer();								//定义一个计时器	
		TimerTask task=new TimerTask() {						//定义一个计时器任务
			
			@Override
			public void run() {
				startActivity(intent);							//启动Activity
				Open.this.finish(); 							//启动Activity后销毁Open界面
			}
		};
		timer.schedule(task, 3000); 							//把计时器任务放到计时器，3秒后跳转到Login界面
	}
}
