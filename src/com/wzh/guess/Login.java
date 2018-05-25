package com.wzh.guess;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity{
	private EditText playername=null;										//定义TextView对象，输入玩家名称
	private EditText playerpasswd=null;										//定义TextView对象，输入密码
	private Button login=null;												//定义登录按钮
	private Button register=null;											//定义注册按钮
	private CheckBox showpw=null;											//显示密码选择
	
	private GuessSQLite db;													//定义GuessSQLite对象
	private SQLiteDatabase sdb;												//定义SQLDatabase对象
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);										//取得布局
		
		//取得组件
		this.playername=(EditText)findViewById(R.id.user_id);
		this.playerpasswd=(EditText)findViewById(R.id.user_pw);
		this.login=(Button)findViewById(R.id.login);
		this.register=(Button)findViewById(R.id.register);
		this.showpw=(CheckBox)findViewById(R.id.show_pw);
		
		//设置监听事件
		this.showpw.setOnClickListener(new ShowpwListener());
		
		this.login.setOnClickListener(new LoginListener());
		this.register.setOnClickListener(new RegisterListener());
		
		this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);		//实例化db
		this.sdb=db.getWritableDatabase();											//以修改方式打开数据库
	}
	
	//登录事件
	private class LoginListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			// 定义取数据的字符串
			String name = "";														
			String passwd = "";														
			String playerid="";
			//获取输入玩家账号
			String user = playername.getText().toString();
			//编写数据库语句
			String select_sql = "select _id,playername,password from player_info where playername = '"
					+ user + "'";
			//执行语句
			Cursor cursor = sdb.rawQuery(select_sql, null);
			cursor.moveToFirst();
			// 将从数据中取出的用户名和密码赋值给两个字符串变量
			try
			{
				playerid = cursor.getString(0);
				name = cursor.getString(1);
				passwd = cursor.getString(2);
			
			} catch (Exception e)
			{
				playerid = "";
				name = "";
				passwd = "";
			}

			//判断用户名是否为空
			if (playername.getText().toString().equals(""))
			{
				toastShow("用户名不能为空");
			}
			//判断密码是否为空
			else if (playerpasswd.getText().toString().equals(""))
			{
				toastShow("密码不能为空");
			} 
			//判断用户名和密码是否正确
			else if (!(playername.getText().toString().equals(name) && playerpasswd
					.getText().toString().equals(passwd)))
			{
				toastShow("用户名或密码错误，请重新输入");
			}
			else{
			cursor.close();
			sdb.close();
			//System.out.println(name);
			//System.out.println(playerid);
			Intent intent=new Intent();								//实例化Intent
			intent.putExtra("name", name);							//附加信息
			intent.putExtra("id", playerid);						//附加信息
			intent.setClass(Login.this,Load.class);					//设置跳转界面
			startActivity(intent);									//启动Activity
			Login.this.finish();									//销毁登录界面
			}
		}
		
	}
	
	//定义注册按钮监听事件
	private class RegisterListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			Intent intent=new Intent(Login.this,Register.class);		//实例化Intent
			startActivity(intent);										//启动Activity
			Login.this.finish();										//销毁登录界面
			
		}
		
	}
	
	//显示密码
	private class ShowpwListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(showpw.isChecked()){												//显示密码是否选中
				Login.this.playerpasswd								
					.setTransformationMethod(HideReturnsTransformationMethod	//显示密码
								.getInstance());
			}
			else{																
				Login.this.playerpasswd			
					.setTransformationMethod(PasswordTransformationMethod		//隐藏密码
								.getInstance());
			}
			
		}
		
	}
	
	//信息提示方法
	public void toastShow(String s){
		Toast toast=Toast.makeText(Login.this, s, Toast.LENGTH_LONG);			//实例化Intent
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);						//居中显示
		toast.show();															//显示信息提示框
	}
}
