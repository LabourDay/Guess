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
	private TextView player=null;													//����TextView������ʾ��ǰ���
	private TextView exit=null;														//����TextView����ʵ��ע������
	private String name;															//����String���󣬽���ԭ���洫������Ϣ
	private String playerid;														//����String����,����ԭ���洫������Ϣ
	private Button one=null;														//����Button����ѡ��һ�ֶ���Ӯģʽ
	private Button three=null;														//����Button����ѡ��������ʤģʽ
	private Button compete=null;													//����Button����ѡ��ȭ������ģʽ
	private Button changepw=null;													//����Button���������޸�����
	private Button contact=null;													//����Button������ϵ��
	private GuessSQLite db;															//����GuessSQLite����
	private SQLiteDatabase sdb;														//����SQLiteDatabase����
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosemode);										//ȡ�ò���
		
		//ȡ�����
		this.player=(TextView)findViewById(R.id.player);
		this.exit=(TextView)findViewById(R.id.exit);
		this.one=(Button)findViewById(R.id.one);
		this.three=(Button)findViewById(R.id.three);
		this.compete=(Button)findViewById(R.id.compete);
		this.changepw=(Button)findViewById(R.id.changepw);
		this.contact=(Button)findViewById(R.id.contact);
			
		//���ü����¼�
		this.exit.setOnClickListener(new ExitListener());
		this.one.setOnClickListener(new OneListener());
		this.three.setOnClickListener(new ThreeListener());
		this.compete.setOnClickListener(new CompeteListener());
		this.changepw.setOnClickListener(new ChangePwListener());
		this.contact.setOnClickListener(new ContactListener());		
		
		//��ȡ��ԭ�����淢�͵���Ϣ
		Intent intent=getIntent();
		this.name=intent.getStringExtra("playername");
		this.playerid=intent.getStringExtra("playerid");
		this.player.setText(name);
		//System.out.println(name);
		//System.out.println(playerid);
	}
	
	//ѡ��һ�ֶ���Ӯģʽ
	private class OneListener implements OnClickListener{
		
		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent(ChooseMode.this,Play.class);				//ʵ����Intent
			intent.putExtra("playersname", name);								//������Ϣ�����������
			intent.putExtra("playersid", playerid);								//������Ϣ�������id
			startActivity(intent);												//��������
			
						
		}
		
	}
	
	//ѡ��������ʤģʽ
	private class ThreeListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent(ChooseMode.this,PlayThree.class);			//ʵ����Intent
			intent.putExtra("playersname", name);								//������Ϣ�����������
			intent.putExtra("playersid", playerid);								//������Ϣ�����������
			startActivity(intent);												//������Ϣ�����������
										
			
		}
		
	}
	
	//ѡ��ȭ������ģʽ
	private class CompeteListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();											//ʵ����Intent
			intent.putExtra("playersname", name);								//������Ϣ�����������
			intent.putExtra("playersid", playerid);								//������Ϣ�����������
			intent.setClass(ChooseMode.this,Compete.class);						//������ת����
			startActivity(intent);												//������Ϣ�����������
		
			
		}
		
	}
	
	//�޸�����
	private class ChangePwListener implements OnClickListener{
		
		@Override
		public void onClick(View arg0) {
			
			//�Զ���Ի���
			LayoutInflater factory=LayoutInflater.from(ChooseMode.this);						//���幤��
			View view=factory.inflate(R.layout.changepw, null);									//������ת��ΪView
			final EditText old=(EditText)view.findViewById(R.id.old);					//����EditText����ԭ����
			final EditText nw=(EditText)view.findViewById(R.id.nw);						//����EditText����������
			Dialog dialog=new AlertDialog.Builder(ChooseMode.this)						//����Dialog
				.setTitle("�޸�����")														//���ñ���
				.setView(view)															//�������
				.setPositiveButton("ȷ��",												//����ȷ����ť
						new DialogInterface.OnClickListener() {
					
							@Override
							public void onClick(DialogInterface dialog, int whichButton) {
								db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);		//ʵ�������ݿ�
								sdb=db.getWritableDatabase();											//���޸ķ�ʽ�����ݿ�
								String selectpw = "select password from player_info"			//�����ѯ�������
								+" where playername='"+name+"'";
								Cursor cursor = sdb.rawQuery(selectpw, null);					//��ѯ���ݿ�
								cursor.moveToFirst();											//�������ָ���ƶ�����һ��
								
								String pw=null;
								String oldpw=old.getText().toString();							//��ȡ������
								String newpw=nw.getText().toString();							//��ȡ������
								if(oldpw.equals("")||newpw.equals("")){							//�ж��¾������Ƿ�Ϊ��
									toastShow("������������Ϣ");										//��ʾ��Ϣ
								}

								else{
									do {														//������ѯ
										try {
											pw= cursor.getString(0);							//��ȡ����
											
											System.out.println("aaaaaa");
										} catch (Exception e) {
											
									     pw="";
										}
										if (!oldpw.equals(pw)) {								//�ж�ԭ�����Ƿ���ȷ
											toastShow("ԭ�������");
											cursor.close();
											break;

										}
										
									} while (cursor.moveToNext());
									
									//���ԭ������ȷ��ʼ�޸�����
									if (oldpw.equals(pw)) {									
										
									
										sdb.execSQL("update player_info set password='"+newpw
												+"' where playername='"+name+"'");
										toastShow("�޸ĳɹ�");
										Intent intent=new Intent();								//ʵ����Intent
										intent.setClass(ChooseMode.this, Login.class);			//������ת����
										startActivity(intent);									//����Activity
										ChooseMode.this.finish();
									
									}
									
								}
								cursor.close();
								sdb.close();
								
							}
						}).setNegativeButton("ȡ��",												//����ȡ����ť 
								new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int whichButton) {
										
									}
								}).setCancelable(false).create();								//�����Ի���
			dialog.show();																		//��ʾ�Ի���
			
		}
		
	}
	
	//ע����¼
	private class ExitListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			Dialog dialog=new AlertDialog.Builder(ChooseMode.this)								//����Dialog
						.setTitle("ע����¼")														//���ñ���
						.setMessage("��ȷ��Ҫ�˳���ǰ�˺���")											//������Ϣ
						.setPositiveButton("ȷ��", 												//����ȷ����ť
								new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										Intent intent=new Intent(ChooseMode.this,Login.class);	//ʵ����Intent
										startActivity(intent);									//����Activity
										ChooseMode.this.finish();								//����ChooseMode
										
									}
								})
						.setNegativeButton("ȡ��", 												//����ȡ����ť
								new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										
										
									}
								}).create();													//�����Ի���
			dialog.show();																		//��ʾ�Ի���
			
		}

		
	
	}
	
	//������ϵ
	private class ContactListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Uri uri=Uri.parse("tel:9666888");								//ָ������
			Intent intent=new Intent();										//ʵ����Intent
			intent.setAction(Intent.ACTION_CALL);							//ָ��Action
			intent.setData(uri);											//��������
			ChooseMode.this.startActivity(intent);							//����Activity
			
		}
		
	}
	
	//���嵯���Ի��򷽷�
	private void toastShow(String s){
		Toast toast=Toast.makeText(ChooseMode.this, s, Toast.LENGTH_LONG);
		toast.show();
	}
}
