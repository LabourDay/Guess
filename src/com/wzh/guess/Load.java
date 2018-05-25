package com.wzh.guess;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Load extends Activity {
	private ProgressBar bar=null;													//定义进度条
	private TextView info=null;														//定义显示文本信息
	
	private String nm;																//定义String对象，用了获取玩家名
	private String pi;																//定义String对象，用了获取玩家id
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);												//取得组件
		this.bar=(ProgressBar)findViewById(R.id.bar);								//进度条组件
		this.info=(TextView)findViewById(R.id.info);								//文本组件
		ChildUpdate child=new ChildUpdate();										//子任务对象
		child.execute(100);															//为休眠时间
		
		Intent intent=getIntent();
		this.nm=intent.getStringExtra("name");										//获取玩家名
		this.pi=intent.getStringExtra("id");										//获取玩家id
		//System.out.println(nm);
		//System.out.println(pi);
	}
	
	
	private class ChildUpdate extends AsyncTask<Integer, Integer, String>{

		@Override
		protected String doInBackground(Integer... params) {						//处理后台任务
			for(int x=0;x<100;x++){													//进度条累加
				Load.this.bar.setProgress(x);										//设置进度
				this.publishProgress(x);											//传递每次更新内容
				try{
					Thread.sleep(params[0]);										//延缓执行
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			return "加载完成";															//返回执行结果
		}

		@Override
		protected void onPostExecute(String result) {								//任务执行后执行
			Load.this.info.setText(result);											//设置文本
			String str=info.getText().toString();									//获取文本内容	
			if(str.equals("加载完成")){												//如果文本内容为“加载完成”
				Intent intent=new Intent();				//实例化Intent
				intent.putExtra("playername", nm);
				intent.putExtra("playerid", pi);
				intent.setClass(Load.this,ChooseMode.class);
				startActivity(intent);												//启动Activity
				Load.this.finish();													//销毁操作
			}
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {						//每次更新后的数值
			Load.this.info.setText(String.valueOf(progress[0])+"%");				//更新文本信息
		}
		
	}
}
