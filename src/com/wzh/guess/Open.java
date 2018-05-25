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
		
		final Intent intent=new Intent(Open.this, Login.class);		//ʵ����Intent
		Timer timer=new Timer();								//����һ����ʱ��	
		TimerTask task=new TimerTask() {						//����һ����ʱ������
			
			@Override
			public void run() {
				startActivity(intent);							//����Activity
				Open.this.finish(); 							//����Activity������Open����
			}
		};
		timer.schedule(task, 3000); 							//�Ѽ�ʱ������ŵ���ʱ����3�����ת��Login����
	}
}
