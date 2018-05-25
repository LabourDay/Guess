package com.wzh.guess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseMode extends Activity {
	private TextView player=null;													//定义TextView对象，显示当前玩家
	private TextView exit=null;														//定义TextView对象，实现注销功能
	private String name;															//定义String对象，接收原界面传来的消息
	private String playerid;														//定义String对象,接收原界面传来的消息
	private Button one=null;														//定义Button对象，选择一局定输赢模式
	private Button three=null;														//定义Button对象，选择三局两胜模式
	private Button compete=null;													//定义Button对象，选择拳王争霸模式
	private Button changepw=null;													//定义Button对象，用来修改密码
	private Button contact=null;													//定义Button对象，联系我
	private GuessSQLite db;															//定义GuessSQLite对象
	private SQLiteDatabase sdb;														//定义SQLiteDatabase对象
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosemode);										//取得布局
		
		//取得组件
		this.player=(TextView)findViewById(R.id.player);
		this.exit=(TextView)findViewById(R.id.exit);
		this.one=(Button)findViewById(R.id.one);
		this.three=(Button)findViewById(R.id.three);
		this.compete=(Button)findViewById(R.id.compete);
		this.changepw=(Button)findViewById(R.id.changepw);
		this.contact=(Button)findViewById(R.id.contact);
			
		//设置监听事件
		this.exit.setOnClickListener(new ExitListener());
		this.one.setOnClickListener(new OneListener());
		this.three.setOnClickListener(new ThreeListener());
		this.compete.setOnClickListener(new CompeteListener());
		this.changepw.setOnClickListener(new ChangePwListener());
		this.contact.setOnClickListener(new ContactListener());		
		
		//获取从原来界面发送的信息
		Intent intent=getIntent();
		this.name=intent.getStringExtra("playername");
		this.playerid=intent.getStringExtra("playerid");
		this.player.setText(name);
		//System.out.println(name);
		//System.out.println(playerid);
	}
	
	//选择一局定输赢模式
	private class OneListener implements OnClickListener{
		
		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent(ChooseMode.this,Play.class);				//实例化Intent
			intent.putExtra("playersname", name);								//附加信息，传玩家名称
			intent.putExtra("playersid", playerid);								//附加信息，传玩家id
			startActivity(intent);												//启动界面
			
						
		}
		
	}
	
	//选择三局两胜模式
	private class ThreeListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent(ChooseMode.this,PlayThree.class);			//实例化Intent
			intent.putExtra("playersname", name);								//附加信息，传玩家名称
			intent.putExtra("playersid", playerid);								//附加信息，传玩家名称
			startActivity(intent);												//附加信息，传玩家名称
										
			
		}
		
	}
	
	//选择拳王争霸模式
	private class CompeteListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();											//实例化Intent
			intent.putExtra("playersname", name);								//附加信息，传玩家名称
			intent.putExtra("playersid", playerid);								//附加信息，传玩家名称
			intent.setClass(ChooseMode.this,Compete.class);						//设置跳转界面
			startActivity(intent);												//附加信息，传玩家名称
		
			
		}
		
	}
	
	//修改密码
	private class ChangePwListener implements OnClickListener{
		
		@Override
		public void onClick(View arg0) {
			
			//自定义对话框
			LayoutInflater factory=LayoutInflater.from(ChooseMode.this);						//定义工厂
			View view=factory.inflate(R.layout.changepw, null);									//将布局转换为View
			final EditText old=(EditText)view.findViewById(R.id.old);					//定义EditText对象，原密码
			final EditText nw=(EditText)view.findViewById(R.id.nw);						//定义EditText对象，新密码
			Dialog dialog=new AlertDialog.Builder(ChooseMode.this)						//创建Dialog
				.setTitle("修改密码")														//设置标题
				.setView(view)															//设置组件
				.setPositiveButton("确认",												//设置确定按钮
						new DialogInterface.OnClickListener() {
					
							@Override
							public void onClick(DialogInterface dialog, int whichButton) {
								db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);		//实例化数据库
								sdb=db.getWritableDatabase();											//以修改方式打开数据库
								String selectpw = "select password from player_info"			//定义查询密码语句
								+" where playername='"+name+"'";
								Cursor cursor = sdb.rawQuery(selectpw, null);					//查询数据库
								cursor.moveToFirst();											//将结果集指针移动到第一行
								
								String pw=null;
								String oldpw=old.getText().toString();							//获取旧密码
								String newpw=nw.getText().toString();							//获取新密码
								if(oldpw.equals("")||newpw.equals("")){							//判断新旧密码是否为空
									toastShow("请输入完整信息");										//提示信息
								}

								else{
									do {														//遍历查询
										try {
											pw= cursor.getString(0);							//获取密码
											
											System.out.println("aaaaaa");
										} catch (Exception e) {
											
									     pw="";
										}
										if (!oldpw.equals(pw)) {								//判断原密码是否正确
											toastShow("原密码错误");
											cursor.close();
											break;

										}
										
									} while (cursor.moveToNext());
									
									//如果原密码正确开始修改密码
									if (oldpw.equals(pw)) {									
										
									
										sdb.execSQL("update player_info set password='"+newpw
												+"' where playername='"+name+"'");
										toastShow("修改成功");
										Intent intent=new Intent();								//实例化Intent
										intent.setClass(ChooseMode.this, Login.class);			//设置跳转界面
										startActivity(intent);									//启动Activity
										ChooseMode.this.finish();
									
									}
									
								}
								cursor.close();
								sdb.close();
								
							}
						}).setNegativeButton("取消",												//设置取消按钮 
								new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int whichButton) {
										
									}
								}).setCancelable(false).create();								//创建对话框
			dialog.show();																		//显示对话框
			
		}
		
	}
	
	//注销登录
	private class ExitListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			Dialog dialog=new AlertDialog.Builder(ChooseMode.this)								//创建Dialog
						.setTitle("注销登录")														//设置标题
						.setMessage("您确定要退出当前账号吗？")											//设置信息
						.setPositiveButton("确定", 												//设置确定按钮
								new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										Intent intent=new Intent(ChooseMode.this,Login.class);	//实例化Intent
										startActivity(intent);									//启动Activity
										ChooseMode.this.finish();								//销毁ChooseMode
										
									}
								})
						.setNegativeButton("取消", 												//设置取消按钮
								new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										
										
									}
								}).create();													//创建对话框
			dialog.show();																		//显示对话框
			
		}

		
	
	}
	
	//与我联系
	private class ContactListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Uri uri=Uri.parse("tel:9666888");								//指定数据
			Intent intent=new Intent();										//实例化Intent
			intent.setAction(Intent.ACTION_CALL);							//指定Action
			intent.setData(uri);											//设置数据
			ChooseMode.this.startActivity(intent);							//启动Activity
			
		}
		
	}
	
	//定义弹出对话框方法
	private void toastShow(String s){
		Toast toast=Toast.makeText(ChooseMode.this, s, Toast.LENGTH_LONG);
		toast.show();
	}
}
