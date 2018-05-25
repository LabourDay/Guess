package com.wzh.guess;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity{
	private EditText userid=null;												//定义EditText对象，供玩家填写账号
	private EditText userpw=null;												//定义EditText对象，供玩家输入密码
	private EditText comfirmpw=null;											//定义EditText对象，供玩家确认密码
	private Button submit=null;													//定义Button对象，实现注册功能按钮
	private Button cancel=null;													//定义Button对象，实现取消注册按钮
	private GuessSQLite db;														//定义GuessSQLite对象
	private SQLiteDatabase sdb;													//定义SQLiteDatabase对象
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);										//取得布局
		
		//取得组件
		this.userid=(EditText)findViewById(R.id.register_id);
		this.userpw=(EditText)findViewById(R.id.register_pw);
		this.comfirmpw=(EditText)findViewById(R.id.comfirm_pw);
		
		this.submit=(Button)findViewById(R.id.submit);
		this.cancel=(Button)findViewById(R.id.cancel);
		
		//设置监听事件
		this.submit.setOnClickListener(new SubmitListener());
		this.cancel.setOnClickListener(new CancelListener());
		
		this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);		//实例化db
		this.sdb=db.getWritableDatabase();											//以修改方式打开数据库
	}
	
	//注册监听事件
	private class SubmitListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Register.this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);
			Register.this.sdb=db.getWritableDatabase();								//以修改方式打开数据库
			if (userid.getText().toString().equals("")
					|| userpw.getText().toString().equals("")
					|| comfirmpw.getText().toString().equals("")) {					//判断输入信息是否为空

				toastShow("请填写完整信息");												//如果为空，弹出信息提示

			} else if (!userpw.getText().toString()
					.equals(comfirmpw.getText().toString())) {						//判断输入两次密码是否一致
				toastShow("两次密码不一致");												//如果不一致，弹出信息提示
			} else {
				String name = userid.getText().toString();							//获取输入的玩家账号
				String passwd = userpw.getText().toString();						//获取输入的玩家密码
				// 查询语句
				String selectStr = "select playername from player_info";				//从数据库player_info表																					
				Cursor select_cursor = sdb.rawQuery(selectStr, null);				//查找已存在的玩家账号
				select_cursor.moveToFirst();
				String str = null;
				do {
					try {
						str = select_cursor.getString(0);							//把查找到的玩家账号赋给str
					} catch (Exception e) {
						// TODO: handle exception
						str = "";
					}
					if (str.equals(name)) {											//如果该账号已存在
						toastShow("该玩家已存在，请重新设置");								//弹出信息提示
						select_cursor.close();										//关闭select_cursor
						break;

					}
				} while (select_cursor.moveToNext());
				
				//如果该账号在数据库player_info表不存在，开始注册
				if (!str.equals(name)) {
					// 定义ID
					int id = 0;														//定义id=0
					String select = "select max(_id) from player_info";				//定义查询id语句
					Cursor cur = sdb.rawQuery(select, null);						//实例化cur
					
					//获取数据库已有的id
					try {
						cur.moveToFirst();
						id = Integer.parseInt(cur.getString(0));
						id += 1;
					} catch (Exception e) {											//捕获异常
						
						id = 0;
					}
					sdb.execSQL("insert into player_info values('" + id + "','"		//把id、玩家名称和玩家密码写进数据库
							+ name + "','" + passwd + "')");
					toastShow("注册成功");												//提示注册成功
					String pid=toString().valueOf(id);
					Intent intent=new Intent();										//实例化Intent
					intent.putExtra("name", name);									//把玩家名称放到intent
					intent.putExtra("id", pid);										//把玩家id放进intent
					intent.setClass(Register.this,Load.class);						//设置跳转界面
					startActivity(intent);											//启动Activity
					Register.this.finish();											//销毁Register界面
					cur.close();													//关闭cur
					sdb.close();													//关闭数据库
				}
			}
		}
		
	}
	
	//取消注册监听事件
	private class CancelListener implements OnClickListener{						//如果点击取消，返回登录界面

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent(Register.this,Login.class);					//实例化Intent
			startActivity(intent);													//启动Activity
			Register.this.finish();													//销毁Activity界面
			
		}
		
	}
	
	//信息提示框方法
	public void toastShow(String s){
		Toast toast=Toast.makeText(Register.this, s, Toast.LENGTH_LONG);			//实例化Toast
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);							//居中显示
		toast.show();																//显示提示
	}
}
