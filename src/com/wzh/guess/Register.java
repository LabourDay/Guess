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
	private EditText userid=null;												//����EditText���󣬹������д�˺�
	private EditText userpw=null;												//����EditText���󣬹������������
	private EditText comfirmpw=null;											//����EditText���󣬹����ȷ������
	private Button submit=null;													//����Button����ʵ��ע�Ṧ�ܰ�ť
	private Button cancel=null;													//����Button����ʵ��ȡ��ע�ᰴť
	private GuessSQLite db;														//����GuessSQLite����
	private SQLiteDatabase sdb;													//����SQLiteDatabase����
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);										//ȡ�ò���
		
		//ȡ�����
		this.userid=(EditText)findViewById(R.id.register_id);
		this.userpw=(EditText)findViewById(R.id.register_pw);
		this.comfirmpw=(EditText)findViewById(R.id.comfirm_pw);
		
		this.submit=(Button)findViewById(R.id.submit);
		this.cancel=(Button)findViewById(R.id.cancel);
		
		//���ü����¼�
		this.submit.setOnClickListener(new SubmitListener());
		this.cancel.setOnClickListener(new CancelListener());
		
		this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);		//ʵ����db
		this.sdb=db.getWritableDatabase();											//���޸ķ�ʽ�����ݿ�
	}
	
	//ע������¼�
	private class SubmitListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Register.this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);
			Register.this.sdb=db.getWritableDatabase();								//���޸ķ�ʽ�����ݿ�
			if (userid.getText().toString().equals("")
					|| userpw.getText().toString().equals("")
					|| comfirmpw.getText().toString().equals("")) {					//�ж�������Ϣ�Ƿ�Ϊ��

				toastShow("����д������Ϣ");												//���Ϊ�գ�������Ϣ��ʾ

			} else if (!userpw.getText().toString()
					.equals(comfirmpw.getText().toString())) {						//�ж��������������Ƿ�һ��
				toastShow("�������벻һ��");												//�����һ�£�������Ϣ��ʾ
			} else {
				String name = userid.getText().toString();							//��ȡ���������˺�
				String passwd = userpw.getText().toString();						//��ȡ������������
				// ��ѯ���
				String selectStr = "select playername from player_info";				//�����ݿ�player_info��																					
				Cursor select_cursor = sdb.rawQuery(selectStr, null);				//�����Ѵ��ڵ�����˺�
				select_cursor.moveToFirst();
				String str = null;
				do {
					try {
						str = select_cursor.getString(0);							//�Ѳ��ҵ�������˺Ÿ���str
					} catch (Exception e) {
						// TODO: handle exception
						str = "";
					}
					if (str.equals(name)) {											//������˺��Ѵ���
						toastShow("������Ѵ��ڣ�����������");								//������Ϣ��ʾ
						select_cursor.close();										//�ر�select_cursor
						break;

					}
				} while (select_cursor.moveToNext());
				
				//������˺������ݿ�player_info�����ڣ���ʼע��
				if (!str.equals(name)) {
					// ����ID
					int id = 0;														//����id=0
					String select = "select max(_id) from player_info";				//�����ѯid���
					Cursor cur = sdb.rawQuery(select, null);						//ʵ����cur
					
					//��ȡ���ݿ����е�id
					try {
						cur.moveToFirst();
						id = Integer.parseInt(cur.getString(0));
						id += 1;
					} catch (Exception e) {											//�����쳣
						
						id = 0;
					}
					sdb.execSQL("insert into player_info values('" + id + "','"		//��id��������ƺ��������д�����ݿ�
							+ name + "','" + passwd + "')");
					toastShow("ע��ɹ�");												//��ʾע��ɹ�
					String pid=toString().valueOf(id);
					Intent intent=new Intent();										//ʵ����Intent
					intent.putExtra("name", name);									//��������Ʒŵ�intent
					intent.putExtra("id", pid);										//�����id�Ž�intent
					intent.setClass(Register.this,Load.class);						//������ת����
					startActivity(intent);											//����Activity
					Register.this.finish();											//����Register����
					cur.close();													//�ر�cur
					sdb.close();													//�ر����ݿ�
				}
			}
		}
		
	}
	
	//ȡ��ע������¼�
	private class CancelListener implements OnClickListener{						//������ȡ�������ص�¼����

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent(Register.this,Login.class);					//ʵ����Intent
			startActivity(intent);													//����Activity
			Register.this.finish();													//����Activity����
			
		}
		
	}
	
	//��Ϣ��ʾ�򷽷�
	public void toastShow(String s){
		Toast toast=Toast.makeText(Register.this, s, Toast.LENGTH_LONG);			//ʵ����Toast
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);							//������ʾ
		toast.show();																//��ʾ��ʾ
	}
}
