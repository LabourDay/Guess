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
	private TextView show=null;																	//������ʾ��ǰȭ���ı���
	private String id[];																		//����id����
	private String player[];																	//�����������
	private String score[];																		//�����������
	private ListView listView;																	//�����б�
	List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();							//����list����
	SimpleAdapter adapter=null;																	//����������
	private GuessSQLite db;																		//����GuessSQLite����
	private SQLiteDatabase sdb;																	//�������ݿ����
	
	String nm;
	String pi;
	int i=0;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank);															//ȡ�ò���
		this.show=(TextView)findViewById(R.id.show);											//ȡ�����
		this.r_back=(Button)findViewById(R.id.r_back);
		this.listView=(ListView)findViewById(R.id.rank);										//ȡ�����
		this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);					//ʵ����db
		this.sdb=db.getWritableDatabase();														//���޸ķ�ʽ�����ݿ�
		
		this.r_back.setOnClickListener(new BackListener());
		Intent intent=getIntent();
		this.nm=intent.getStringExtra("playername");
		this.pi=intent.getStringExtra("playerid");
		//System.out.println(pi);
		
		
		String selectScore="select _id,playername,score from user_score"
							+" order by score desc";												//�����ѯ���
		Cursor cursor=sdb.rawQuery(selectScore, null);											//����cursor����
		cursor.moveToFirst();																	//�������ָ���Ƶ���һ��
		
		int count=cursor.getCount();															//��ȡ���ݿ�����
		id=new String[count];																	//ʵ����id����
		player=new String[count];																//ʵ�����������
		score=new String[count];																//ʵ������������
		
		//������ѯ
		do{
			try{
				id[i]=cursor.getString(0);												//�����ݿ�idֵ�ŵ�id����
				player[i]=cursor.getString(1);											//�����ݿ��������ŵ�player����
				score[i]=cursor.getString(2);											//�����ݿ�����ŵ�score����
				i++;																	
			}catch(Exception e){																//�����쳣
				
			}
		}while(cursor.moveToNext());															//��������Ƶ���һ��
		
		//�Ѵ����ݿ��л�ȡ��ֵ�ŵ�list
		for(int j=0;j<id.length;j++){															//ѭ��
			Map<String, Object>map=new HashMap<String, Object>();								//����Map����
			map.put("id", id[j]);																//�ѻ�ȡ����id�ŵ�map
			map.put("player", player[j]);														//�ѻ�ȡ������ҷŵ�map
			map.put("score", score[j]);															//�ѻ�ȡ���ķ����ŵ�map
			list.add(map);																		//��map��ӵ�list
		}																				
		this.adapter=new SimpleAdapter(this,list,										//�����������
								R.layout.rankadpter,new String[]{"id","player","score"},
								new int[]{R.id.playerid,R.id.player,R.id.playerscore});
		listView.setAdapter(adapter);															//ΪlistView����������														
		this.show.setText("��ǰȭ����"+player[0]);													//��ʾ���ڵ�һ�����
		listView.setOnItemClickListener(new DeletePlayerListener());
		cursor.close();																			//�ر�cursor
		sdb.close();																			//�ر����ݿ�
		
	}
	
	//ɾ���Լ��ĵ÷ּ�¼
	private class DeletePlayerListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Map<String , String> map=(Map<String, String>)Rank.this								//ȡ���б���
						.adapter.getItem(position);
			String pid=map.get("id");															//ȡ��keyΪid������
			//System.out.println(pid);
			
			//������id��key��������ȣ���ɾ������ҵĵ÷ּ�¼	
			if(Rank.this.pi.equals(pid)){																							
				Dialog dialog=new AlertDialog.Builder(Rank.this)								//ʵ��������
							.setTitle("ɾ���Լ��ļ�¼")												//������ʾ����
							.setMessage("��ȷ����?")												//������ʾ����
							.setPositiveButton("Yes", 											//����ȷ����ť
									new DialogInterface.OnClickListener() {						//���ü�������
										
										@Override
										public void onClick(DialogInterface arg0, int arg1) {
											Rank.this.db=new GuessSQLite(getApplicationContext(), 
													"guess.db", null, 1);						//ʵ����db
											
											Rank.this.sdb=db.getWritableDatabase();				//���޸ķ�ʽ�����ݿ�
											Rank.this.sdb.execSQL("delete from user_score where _id='"+pi+"'");
											Rank.this.sdb.close();
											Toast.makeText(Rank.this, "ɾ���ɹ�", Toast.LENGTH_LONG).show();
											refresh();
											
											
										}
									}).setNegativeButton("No", 									//����ȡ����ť
											new DialogInterface.OnClickListener() {
												
												@Override
												public void onClick(DialogInterface arg0, int arg1) {
												
													
												}
											}).setCancelable(false).create();					//�����Ի���
				dialog.show();																	//��ʾ�Ի���
			}
			else{
				Toast.makeText(Rank.this, "���޴�Ȩ��", Toast.LENGTH_LONG).show();					//��Ϣ��ʾ��
			}
			
		}
	}
	
	//����Compete
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
	
	//ˢ�·���
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
