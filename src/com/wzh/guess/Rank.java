package com.wzh.guess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Rank extends Activity {
	private Button r_back=null;
	private TextView show=null;																	//定义显示当前拳王文本框
	private String id[];																		//定义id数组
	private String player[];																	//定义玩家数组
	private String score[];																		//定义分数数组
	private ListView listView;																	//定义列表
	List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();							//定义list对象
	SimpleAdapter adapter=null;																	//定义适配器
	private GuessSQLite db;																		//定义GuessSQLite对象
	private SQLiteDatabase sdb;																	//定义数据库对象
	
	String nm;
	String pi;
	int i=0;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank);															//取得布局
		this.show=(TextView)findViewById(R.id.show);											//取得组件
		this.r_back=(Button)findViewById(R.id.r_back);
		this.listView=(ListView)findViewById(R.id.rank);										//取得组件
		this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);					//实例化db
		this.sdb=db.getWritableDatabase();														//以修改方式打开数据库
		
		this.r_back.setOnClickListener(new BackListener());
		Intent intent=getIntent();
		this.nm=intent.getStringExtra("playername");
		this.pi=intent.getStringExtra("playerid");
		//System.out.println(pi);
		
		
		String selectScore="select _id,playername,score from user_score"
							+" order by score desc";												//定义查询语句
		Cursor cursor=sdb.rawQuery(selectScore, null);											//定义cursor对象
		cursor.moveToFirst();																	//将结果集指针移到第一行
		
		int count=cursor.getCount();															//获取数据库行数
		id=new String[count];																	//实例化id数组
		player=new String[count];																//实例化玩家数组
		score=new String[count];																//实例化分数数组
		
		//遍历查询
		do{
			try{
				id[i]=cursor.getString(0);												//把数据库id值放到id数组
				player[i]=cursor.getString(1);											//把数据库玩家数组放到player数组
				score[i]=cursor.getString(2);											//把数据库分数放到score数组
				i++;																	
			}catch(Exception e){																//捕获异常
				
			}
		}while(cursor.moveToNext());															//将结果集移到下一行
		
		//把从数据库中获取的值放到list
		for(int j=0;j<id.length;j++){															//循环
			Map<String, Object>map=new HashMap<String, Object>();								//定义Map对象
			map.put("id", id[j]);																//把获取到的id放到map
			map.put("player", player[j]);														//把获取到的玩家放到map
			map.put("score", score[j]);															//把获取到的分数放到map
			list.add(map);																		//把map添加到list
		}																				
		this.adapter=new SimpleAdapter(this,list,										//定义简单适配器
								R.layout.rankadpter,new String[]{"id","player","score"},
								new int[]{R.id.playerid,R.id.player,R.id.playerscore});
		listView.setAdapter(adapter);															//为listView设置适配器														
		this.show.setText("当前拳王是"+player[0]);													//显示排在第一的玩家
		listView.setOnItemClickListener(new DeletePlayerListener());
		cursor.close();																			//关闭cursor
		sdb.close();																			//关闭数据库
		
	}
	
	//删除自己的得分记录
	private class DeletePlayerListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Map<String , String> map=(Map<String, String>)Rank.this								//取得列表项
						.adapter.getItem(position);
			String pid=map.get("id");															//取得key为id的内容
			//System.out.println(pid);
			
			//如果玩家id与key的内容相等，则删除该玩家的得分记录	
			if(Rank.this.pi.equals(pid)){																							
				Dialog dialog=new AlertDialog.Builder(Rank.this)								//实例化对象
							.setTitle("删除自己的记录")												//设置显示标题
							.setMessage("你确定吗?")												//设置显示内容
							.setPositiveButton("Yes", 											//增加确定按钮
									new DialogInterface.OnClickListener() {						//设置监听操作
										
										@Override
										public void onClick(DialogInterface arg0, int arg1) {
											Rank.this.db=new GuessSQLite(getApplicationContext(), 
													"guess.db", null, 1);						//实例化db
											
											Rank.this.sdb=db.getWritableDatabase();				//以修改方式打开数据库
											Rank.this.sdb.execSQL("delete from user_score where _id='"+pi+"'");
											Rank.this.sdb.close();
											Toast.makeText(Rank.this, "删除成功", Toast.LENGTH_LONG).show();
											refresh();
											
											
										}
									}).setNegativeButton("No", 									//增加取消按钮
											new DialogInterface.OnClickListener() {
												
												@Override
												public void onClick(DialogInterface arg0, int arg1) {
												
													
												}
											}).setCancelable(false).create();					//创建对话框
				dialog.show();																	//显示对话框
			}
			else{
				Toast.makeText(Rank.this, "你无此权限", Toast.LENGTH_LONG).show();					//信息提示框
			}
			
		}
	}
	
	//返回Compete
	private class BackListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent(Rank.this, Compete.class);
			intent.putExtra("playersname", nm);
			intent.putExtra("playersid", pi);
			startActivity(intent);
			Rank.this.finish();
			
		}
		
	}
	
	//刷新方法
	private void refresh(){
		Intent intent=new Intent(Rank.this, Rank.class);
		intent.putExtra("playername", nm);
		intent.putExtra("playerid", pi);
		startActivity(intent);
		Rank.this.finish();
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
