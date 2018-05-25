package com.wzh.guess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class Play extends Activity{
	private RadioGroup choose=null;
	private RadioButton soc=null;
	private RadioButton rock=null;
	private RadioButton bu=null;
	private ImageView imag1=null;
	private ImageView imag2=null;
	private Button back=null;
	private int computer;
	private int person;	
	private String playername;
	private String playerid;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.play_activity);
		 this.choose=(RadioGroup) findViewById(R.id.myrg);				//ȡ�����
		 this.soc=(RadioButton) findViewById(R.id.myrb1);
		 this.rock=(RadioButton) findViewById(R.id.myrb2);
		 this.bu=(RadioButton) findViewById(R.id.myrb3);
		 this.imag1=(ImageView) findViewById(R.id.myig1);
		 this.imag2=(ImageView) findViewById(R.id.myig2);
		 this.back=(Button) findViewById(R.id.mybut);
		 this.choose.setOnCheckedChangeListener(new RadiogroupListener());
		 
		 this.back.setOnClickListener(new BackListener());
		 
		 Intent intent=getIntent();
		 playername=intent.getStringExtra("playersname");
		 playerid=intent.getStringExtra("playersid");
	 }
	 
	  public class RadiogroupListener implements OnCheckedChangeListener{

		@Override
		//�õ�ѡ��ť��OnCheckedChangesListenerѡ����Ҫ����ȭ
		public void onCheckedChanged(RadioGroup radiogroup, int checkId) {
			
			if(Play.this.soc.getId()==checkId){				//���ѡ�������
				person=1;									//��person����1��������ҳ�����
				imag1.setImageResource(R.drawable.soc);		//��ʾ��ҳ�����
			}
			if(Play.this.rock.getId()==checkId){			//���ѡ��ʯͷ��
				person=2;									//��person=2��������ҳ�ʯͷ
				imag1.setImageResource(R.drawable.rock);	//��ʾ��ҳ�ʯͷ
			}
			if(Play.this.bu.getId()==checkId){				//���ѡ�񲼣�
				person=3;									//��person=3��������ҳ���
				 imag1.setImageResource(R.drawable.bu);		//��ʾ��ҳ���
			}
			
			
			Play.this.computer=(int)(Math.random()*3)+1;	//���Բ���һ����������������ȭ
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
			
			
	            Intent intent = new Intent();
	            intent.putExtra("playername", playername);
				intent.putExtra("playerid", playerid);
	            int num=1;         
	            intent.setFlags(num);
	            //ͨ���Ƚ�computer��person�Ĵ�С���ж���Ӯ��ƽ��
	            if(computer==person){							//�����Һ͵��Գ���ȭһ������ƽ�֣�
	            	intent.setClass(Play.this,Peace.class);		//��ת��ƽ�ֽ���            	
	            	}	
	            
	            else if(person==1&&computer==2||person==2&&computer==3		//���������ˣ�
	            		||person==3&&computer==1){							//��ת����Ľ���
	            	intent.setClass(Play.this, Lose.class);		            
	            	}
	            
	            else{											//����������Ӯ�ˣ�
	            	intent.setClass(Play.this, Won.class);		//��ת��Ӯ�Ľ���
	            	}
	                        								
						startActivity(intent);					//����������ת
						Play.this.finish();
					            		 		  
		}
	  }
	  
	  private class BackListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();		
			intent.putExtra("playername", playername);
			intent.putExtra("playerid", playerid);
			intent.setClass(Play.this, ChooseMode.class);	//���ؿ�ʼʱ�Ľ���
			startActivity(intent);
			Play.this.finish();
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