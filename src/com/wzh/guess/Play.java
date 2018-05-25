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
		 this.choose=(RadioGroup) findViewById(R.id.myrg);				//取得组件
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
		//用单选按钮与OnCheckedChangesListener选择你要出的拳
		public void onCheckedChanged(RadioGroup radiogroup, int checkId) {
			
			if(Play.this.soc.getId()==checkId){				//如果选择剪刀，
				person=1;									//置person等于1，代表玩家出剪刀
				imag1.setImageResource(R.drawable.soc);		//显示玩家出剪刀
			}
			if(Play.this.rock.getId()==checkId){			//如果选择石头，
				person=2;									//置person=2，代表玩家出石头
				imag1.setImageResource(R.drawable.rock);	//显示玩家出石头
			}
			if(Play.this.bu.getId()==checkId){				//如果选择布，
				person=3;									//置person=3，代表玩家出布
				 imag1.setImageResource(R.drawable.bu);		//显示玩家出布
			}
			
			
			Play.this.computer=(int)(Math.random()*3)+1;	//电脑产生一个随机数，代表出的拳
			switch(computer){
			case 1:
				imag2.setImageResource(R.drawable.soc);		//当随机数为1，电脑出剪刀，
				break;
				
			case 2:
				imag2.setImageResource(R.drawable.rock);	//当随机数为2，电脑出石头
				break;
				
			case 3:
				imag2.setImageResource(R.drawable.bu);		//当随机数为3，电脑出布
				break;
				
			default:
					break;
			}
			
			
	            Intent intent = new Intent();
	            intent.putExtra("playername", playername);
				intent.putExtra("playerid", playerid);
	            int num=1;         
	            intent.setFlags(num);
	            //通过比较computer和person的大小来判断输赢和平局
	            if(computer==person){							//如果玩家和电脑出的拳一样代表平局，
	            	intent.setClass(Play.this,Peace.class);		//跳转到平局界面            	
	            	}	
	            
	            else if(person==1&&computer==2||person==2&&computer==3		//如果玩家输了，
	            		||person==3&&computer==1){							//跳转到输的界面
	            	intent.setClass(Play.this, Lose.class);		            
	            	}
	            
	            else{											//否则，如果玩家赢了，
	            	intent.setClass(Play.this, Won.class);		//跳转到赢的界面
	            	}
	                        								
						startActivity(intent);					//开启界面跳转
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
			intent.setClass(Play.this, ChooseMode.class);	//返回开始时的界面
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