package com.wzh.guess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PlayThree extends Activity{
	private static RadioGroup cho=null;
	private RadioButton s=null;
	private RadioButton r=null;
	private RadioButton b=null;
	private ImageView ima1=null;
	private ImageView ima2=null;
	private Button bc=null;
	private Button ct=null;
	private TextView tv1=null;
	private TextView tv2=null;
	private int comp;
	private int per;
	private int soe1=0;
	private int soe2=0;
	
	private String playername;
	private String playerid;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.playthree_activity);
		this.cho=(RadioGroup)findViewById(R.id.rg);
		this.s=(RadioButton)findViewById(R.id.rb1);
		this.r=(RadioButton)findViewById(R.id.rb2);
		this.b=(RadioButton)findViewById(R.id.rb3);
		this.ima1=(ImageView)findViewById(R.id.ig1);
		this.ima2=(ImageView)findViewById(R.id.ig2);
		this.bc=(Button)findViewById(R.id.mt);
		this.ct=(Button)findViewById(R.id.con);
		this.tv1=(TextView)findViewById(R.id.score1);
		this.tv2=(TextView)findViewById(R.id.score2);
		
		
		 
		this.cho.setOnCheckedChangeListener(new RadioListener());
		this.ct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				PlayThree.cho.clearCheck();												//清除选择
			}
		});
		this.bc.setOnClickListener(new BackListener());
		
		Intent intent=getIntent();
		playername=intent.getStringExtra("playersname");
		playerid=intent.getStringExtra("playersid");
	}
		 public class RadioListener implements OnCheckedChangeListener{

				@Override
				//用单选按钮与OnCheckedChangesListener选择你要出的拳
				public void onCheckedChanged(RadioGroup radiogroup, int checkId) {
					
					if(PlayThree.this.s.getId()==checkId){				//如果选择剪刀，
						per=1;									//置person等于1，代表玩家出剪刀
						ima1.setImageResource(R.drawable.soc);		//显示玩家出剪刀
					}
					if(PlayThree.this.r.getId()==checkId){			//如果选择石头，
						per=2;									//置person=2，代表玩家出石头
						ima1.setImageResource(R.drawable.rock);	//显示玩家出石头
					}
					if(PlayThree.this.b.getId()==checkId){				//如果选择布，
						per=3;									//置person=3，代表玩家出布
						 ima1.setImageResource(R.drawable.bu);		//显示玩家出布
					}
					if(PlayThree.this.s.isChecked()||PlayThree.this.r.isChecked()||PlayThree.this.b.isChecked()){
					PlayThree.this.comp=(int)(Math.random()*3)+1;//电脑产生一个随机数，代表出的拳
						
					switch(comp){
					case 1:
						ima2.setImageResource(R.drawable.soc);		//当随机数为1，电脑出剪刀，
						break;
						
					case 2:
						ima2.setImageResource(R.drawable.rock);	//当随机数为2，电脑出石头
						break;
						
					case 3:
						ima2.setImageResource(R.drawable.bu);		//当随机数为3，电脑出布
						break;
						
					default:
							break;
					}
					
					
				 if(comp==per){							//如果玩家和电脑出的拳一样代表平局，
		            	soe1=soe1+0;soe2=soe2+0;	//跳转到平局界面            	
		            	}	
		            
		            else if(per==1&&comp==2||per==2&&comp==3		//如果玩家输了，
		            		||per==3&&comp==1){							//跳转到输的界面
		            	soe2=soe2+1;
		            	tv2.setText(""+soe2);
		            	}
		            
		            else{											//否则，如果玩家赢了，
		            	soe1=soe1+1;	//跳转到赢的界面
		            	tv1.setText(""+soe1);
		            	}
				 
				Intent intent=new Intent();
				intent.putExtra("playername", playername);
				intent.putExtra("playerid", playerid);
				int num=3;	
				intent.setFlags(num);
				 if((soe1==2&&soe2==0)||(soe1==3&&soe2==1)||(soe1==3&&soe2==2)){
					 intent.setClass(PlayThree.this, Won.class);
					 startActivity(intent);
					 PlayThree.this.finish();
				 }
				 if((soe1==0&&soe2==2)||(soe1==1&&soe2==3)||(soe1==2&&soe2==3)){
					 	intent.setClass(PlayThree.this, Lose.class);
					 	startActivity(intent);
					 	PlayThree.this.finish();
				 		}
					}
				 	
				}
				
		
		 }
		 
		 private class BackListener implements OnClickListener{

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent();		
					intent.putExtra("playername", playername);
					intent.putExtra("playerid", playerid);
					intent.setClass(PlayThree.this, ChooseMode.class);	//返回开始时的界面
					startActivity(intent);
					PlayThree.this.finish();
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
