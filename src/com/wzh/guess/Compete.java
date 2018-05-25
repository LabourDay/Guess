package com.wzh.guess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Compete extends Activity {
	
	private TextView score=null;									//��������ı���
	private TextView time=null;										//����ʱ���ı���
	private Button soc=null;										//ѡ�������ť
	private Button rock=null;										//ѡ��ʯͷ��ť
	private Button bu=null;											//ѡ�񲼰�ť
	private ImageView imag1=null;									//��ʾ��ҳ�ʲô
	private ImageView imag2=null;									//��ʾ���Գ�ʲô
	private Button challenge=null;									//��ʼ��ս��ť
	private Button back=null;										//���ذ�ť
	
	private int computer;											//�������
	private int person;												//�������
	
	private MyCount count;											//�����ʱ����
	
	private int sco=0;												//��ʼ����Ϊ0
	private String playername;										//�������
	private String playerid;										//�������id
	private GuessSQLite db;											//�����ȭ���ݿ������
	private SQLiteDatabase sdb;										//�������ݿ����
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compete);							//ȡ�ò���
		 this.score=(TextView)findViewById(R.id.score);				//ȡ�����
		 this.time=(TextView)findViewById(R.id.time);				//ȡ�����
		 this.soc=(Button)findViewById(R.id.cs);					//ȡ�����
		 this.rock=(Button)findViewById(R.id.cr);					//ȡ�����
		 this.bu=(Button)findViewById(R.id.cb);						//ȡ�����
		 this.imag1=(ImageView) findViewById(R.id.c_myig1);			//ȡ�����
		 this.imag2=(ImageView) findViewById(R.id.c_myig2);			//ȡ�����
		 this.challenge=(Button)findViewById(R.id.challenge);		//ȡ�����
		 this.back=(Button)findViewById(R.id.c_back);				//ȡ�����
		 
		 this.soc.setOnClickListener(new ChooseListener());			//���ü����¼�
		 this.rock.setOnClickListener(new ChooseListener());		//���ü����¼�
		 this.bu.setOnClickListener(new ChooseListener());			//���ü����¼�
		 
		 
		 this.score.setText(sco+"");								//�ѷ����Ž������ı���

		 this.challenge.setOnClickListener(new ChallengeListener());//������ս��ť�����¼�
		 this.back.setOnClickListener(new BackListener());			//���÷��ذ�ť�����¼�
		 
		 this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);		//��ʼdb
		 this.sdb=db.getWritableDatabase();
		 
		 //��ȡ��ԭ�����洫��������Ϣ
		 Intent intent=getIntent();
		 playername=intent.getStringExtra("playersname");
		 playerid=intent.getStringExtra("playersid");
		 //System.out.println(playername);
		 //System.out.println(playerid);
		 
		 
	}
	
	 //�����ȭ��ť�����¼�
	 public class ChooseListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			switch(view.getId()){							//��ȡ��ťid
			case R.id.cs:{
				person=1;									//��person����1��������ҳ�����
				imag1.setImageResource(R.drawable.soc);		//��ʾ��ҳ�����					
				}
				break;
			case R.id.cr:{
					person=2;									//��person����1��������ҳ�����
					imag1.setImageResource(R.drawable.rock);		//��ʾ��ҳ�����	
				}
				break;
			case R.id.cb:{
					person=3;									//��person=3��������ҳ���
					imag1.setImageResource(R.drawable.bu);		//��ʾ��ҳ���
				}
				break;
			default:
				break;
				
			}
			
			Compete.this.computer=(int)(Math.random()*3)+1;	//���Բ���һ����������������ȭ
			switch(computer){
			case 1:
				imag2.setImageResource(R.drawable.soc);		//�������Ϊ1�����Գ�������
				break;
				
			case 2:
				imag2.setImageResource(R.drawable.rock);	//�������Ϊ2�����Գ�ʯͷ					
				break;
				
			case 3:
				imag2.setImageResource(R.drawable.bu);		//�������Ϊ3�����Գ���
				break;
				
			default:
					break;
			}
			//ͨ���Ƚ�computer��person�Ĵ�С��������ҵķ���
            if(computer==person){												//�����Һ͵��Գ�һ���Ļ������ӷ�		
            	    	sco=sco+0;
            	    	Compete.this.score.setText(sco+"");
            	    	
            	}	
            
            else if(person==1&&computer==2||person==2&&computer==3				//���������˿�һ��
            		||person==3&&computer==1){							
            		    sco=sco-1;
            		    Compete.this.score.setText(sco+"");
            	}
            else{
            	sco=sco+1;														//������Ӯ�ˣ���һ��
            	Compete.this.score.setText(sco+"");								
            }
			
		}
			
	}
	 
	 //��ʼ��ս��ť�¼�
	 private class ChallengeListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Compete.this.soc.setEnabled(true);												//���ü�����ť����
			Compete.this.rock.setEnabled(true);												//����ʯͷ��ť����
			Compete.this.bu.setEnabled(true);												//���ò���ť����
			Compete.this.score.setText("0");												//����ʾ����Ϊ0
			Compete.this.sco=0;																//���÷���Ϊ0
			Compete.this.challenge.setEnabled(false);										//���ÿ�ʼ��ս��ť������
			Compete.this.back.setEnabled(false);											//���÷��ذ�ť����
			count=new MyCount(16000, 1000);													//ʵ��count������ʱ16��
			count.start();																	//��ʱ��ʼ
			
		}
		 
	 }
	 
	 //���巵�ذ�ť�¼�
	 private class BackListener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//����Һ����id����ԭ����
				Intent intent=new Intent();		
				intent.putExtra("playername", playername);
				intent.putExtra("playerid", playerid);
				intent.setClass(Compete.this, ChooseMode.class);	//���ؿ�ʼʱ�Ľ���
				startActivity(intent);
				Compete.this.finish();
			}
			  
		  }

	private class MyCount extends CountDownTimer{
	//���췽��
	public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
	
	//��ʱ����
	@Override
	public void onFinish() {
		 Compete.this.time.setText("");									//����ʱ��Ϊ��
		 Compete.this.soc.setEnabled(false);							//���ü�����ť������
		 Compete.this.rock.setEnabled(false);							//����ʯͷ��ť������
		 Compete.this.bu.setEnabled(false);								//���ò���ť������
		 Compete.this.imag1.setImageResource(R.drawable.rock);			//����ʾ��ҳ���ȭ��Ϊʯͷ
		 Compete.this.imag2.setImageResource(R.drawable.rock);			//����ʾ���Գ���ȭͷ��Ϊʯͷ
		 
		 Compete.this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);
		 Compete.this.sdb=db.getWritableDatabase();										//���޸ĵķ�ʽ�����ݿ�
		 
		 //����Ի���
		 Dialog dialog=new AlertDialog.Builder(Compete.this)
		 			.setTitle("        ʱ�䵽")											//���öԻ������
		 			.setMessage("        �÷֣�"+score.getText().toString())				//���öԻ�����Ϣ
		 			.setPositiveButton("ȷ��", 											//ȷ����ť
		 					new DialogInterface.OnClickListener() {						//��ť�����¼�
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									Compete.this.score.setText("0");					//����ʾ������Ϊ0
									Compete.this.challenge.setEnabled(true);			//���ÿ�ʼ��ս��ť����
									Compete.this.back.setEnabled(true);					//���÷��ذ�ť����
									
								}
							}).setNegativeButton("�鿴���а�", 								//�������а�ť
									new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface arg0, int arg1) {
											Compete.this.score.setText("0");			//����ʾ������Ϊ0
											Compete.this.challenge.setEnabled(true);	//���ÿ�ʼ��ս��ť����
											Compete.this.back.setEnabled(true);			//���÷��ذ�ť����
											Intent intent=new Intent(getApplicationContext(), Rank.class);
											intent.putExtra("playername", playername);
											intent.putExtra("playerid", playerid);
											startActivity(intent);						//��������
											Compete.this.finish();
										}
									}).setCancelable(false).create();
		 dialog.show();
		 
		 String id=playerid;															//���id
		 String name=playername;														//����˻�
		 String playerscore=Compete.this.score.getText().toString();					//��ҵ÷�
		 
		 String select="select playername,score from user_score";		
		 Cursor cursor=sdb.rawQuery(select, null);										//��ѯ����
		 cursor.moveToFirst();															//�������ָ���Ƶ���һ��
		 
		 String pname=null;																//���ݿ��е����	
		 String pscore=null;															//���ݿ�����ҷ���
		 
		 //������ѯ
		 do{																			
			 try{
				 pname=cursor.getString(0);												//��ȡ���
				 pscore=cursor.getString(1);											//��ȡ����
			 }catch(Exception e){														//�쳣����
				 pname="";
				 pscore="";
			 }
			 if(pname.equals(name)){														//�������Ƿ��Ѵ���
				 	int ps=Integer.parseInt(pscore);										//�����ԭ����
				 	int psc=Integer.parseInt(playerscore);									//������·���
				 	if(psc>ps){																//����·�������ԭ������
					sdb.execSQL("update user_score set score='"+playerscore+"' where _id='"	//���·���
							+playerid+"' and playername='"+playername+"'");
				 	  }
					 cursor.close();														//�رղ�ѯ
					 break;
				 	}		
		 }while(cursor.moveToNext());														//�����ָ�뼯�Ƶ���һ��
		 
		 //��������������ң��Ѹ���Һͷ����ӽ����ݿ�
		 if(!pname.equals(name)){
			 sdb.execSQL("insert into user_score values('"+id+"','"+name+"','"+playerscore+"')"); 
			
		 }		 
		 cursor.close();																	//�رղ�ѯ
		 sdb.close();																		//�ر����ݿ�
	}

	@Override
	public void onTick(long surplus) {
		Compete.this.time.setText(surplus/1000+"");											//��ʾʣ����
		
	   }	 
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
