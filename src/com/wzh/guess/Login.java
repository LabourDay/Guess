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
	private EditText playername=null;										//����TextView���������������
	private EditText playerpasswd=null;										//����TextView������������
	private Button login=null;												//�����¼��ť
	private Button register=null;											//����ע�ᰴť
	private CheckBox showpw=null;											//��ʾ����ѡ��
	
	private GuessSQLite db;													//����GuessSQLite����
	private SQLiteDatabase sdb;												//����SQLDatabase����
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);										//ȡ�ò���
		
		//ȡ�����
		this.playername=(EditText)findViewById(R.id.user_id);
		this.playerpasswd=(EditText)findViewById(R.id.user_pw);
		this.login=(Button)findViewById(R.id.login);
		this.register=(Button)findViewById(R.id.register);
		this.showpw=(CheckBox)findViewById(R.id.show_pw);
		
		//���ü����¼�
		this.showpw.setOnClickListener(new ShowpwListener());
		
		this.login.setOnClickListener(new LoginListener());
		this.register.setOnClickListener(new RegisterListener());
		
		this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);		//ʵ����db
		this.sdb=db.getWritableDatabase();											//���޸ķ�ʽ�����ݿ�
	}
	
	//��¼�¼�
	private class LoginListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			// ����ȡ���ݵ��ַ���
			String name = "";														
			String passwd = "";														
			String playerid="";
			//��ȡ��������˺�
			String user = playername.getText().toString();
			//��д���ݿ����
			String select_sql = "select _id,playername,password from player_info where playername = '"
					+ user + "'";
			//ִ�����
			Cursor cursor = sdb.rawQuery(select_sql, null);
			cursor.moveToFirst();
			// ����������ȡ�����û��������븳ֵ�������ַ�������
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

			//�ж��û����Ƿ�Ϊ��
			if (playername.getText().toString().equals(""))
			{
				toastShow("�û�������Ϊ��");
			}
			//�ж������Ƿ�Ϊ��
			else if (playerpasswd.getText().toString().equals(""))
			{
				toastShow("���벻��Ϊ��");
			} 
			//�ж��û����������Ƿ���ȷ
			else if (!(playername.getText().toString().equals(name) && playerpasswd
					.getText().toString().equals(passwd)))
			{
				toastShow("�û����������������������");
			}
			else{
			cursor.close();
			sdb.close();
			//System.out.println(name);
			//System.out.println(playerid);
			Intent intent=new Intent();								//ʵ����Intent
			intent.putExtra("name", name);							//������Ϣ
			intent.putExtra("id", playerid);						//������Ϣ
			intent.setClass(Login.this,Load.class);					//������ת����
			startActivity(intent);									//����Activity
			Login.this.finish();									//���ٵ�¼����
			}
		}
		
	}
	
	//����ע�ᰴť�����¼�
	private class RegisterListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			Intent intent=new Intent(Login.this,Register.class);		//ʵ����Intent
			startActivity(intent);										//����Activity
			Login.this.finish();										//���ٵ�¼����
			
		}
		
	}
	
	//��ʾ����
	private class ShowpwListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(showpw.isChecked()){												//��ʾ�����Ƿ�ѡ��
				Login.this.playerpasswd								
					.setTransformationMethod(HideReturnsTransformationMethod	//��ʾ����
								.getInstance());
			}
			else{																
				Login.this.playerpasswd			
					.setTransformationMethod(PasswordTransformationMethod		//��������
								.getInstance());
			}
			
		}
		
	}
	
	//��Ϣ��ʾ����
	public void toastShow(String s){
		Toast toast=Toast.makeText(Login.this, s, Toast.LENGTH_LONG);			//ʵ����Intent
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);						//������ʾ
		toast.show();															//��ʾ��Ϣ��ʾ��
	}
}
