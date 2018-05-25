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
	
	private TextView score=null;									//定义分数文本框
	private TextView time=null;										//定义时间文本框
	private Button soc=null;										//选择剪刀按钮
	private Button rock=null;										//选择石头按钮
	private Button bu=null;											//选择布按钮
	private ImageView imag1=null;									//显示玩家出什么
	private ImageView imag2=null;									//显示电脑出什么
	private Button challenge=null;									//开始挑战按钮
	private Button back=null;										//返回按钮
	
	private int computer;											//定义电脑
	private int person;												//定义玩家
	
	private MyCount count;											//定义计时对象
	
	private int sco=0;												//初始分数为0
	private String playername;										//定义玩家
	private String playerid;										//定义玩家id
	private GuessSQLite db;											//定义猜拳数据库类对象
	private SQLiteDatabase sdb;										//定义数据库对象
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compete);							//取得布局
		 this.score=(TextView)findViewById(R.id.score);				//取得组件
		 this.time=(TextView)findViewById(R.id.time);				//取得组件
		 this.soc=(Button)findViewById(R.id.cs);					//取得组件
		 this.rock=(Button)findViewById(R.id.cr);					//取得组件
		 this.bu=(Button)findViewById(R.id.cb);						//取得组件
		 this.imag1=(ImageView) findViewById(R.id.c_myig1);			//取得组件
		 this.imag2=(ImageView) findViewById(R.id.c_myig2);			//取得组件
		 this.challenge=(Button)findViewById(R.id.challenge);		//取得组件
		 this.back=(Button)findViewById(R.id.c_back);				//取得组件
		 
		 this.soc.setOnClickListener(new ChooseListener());			//设置监听事件
		 this.rock.setOnClickListener(new ChooseListener());		//设置监听事件
		 this.bu.setOnClickListener(new ChooseListener());			//设置监听事件
		 
		 
		 this.score.setText(sco+"");								//把分数放进分数文本框

		 this.challenge.setOnClickListener(new ChallengeListener());//设置挑战按钮监听事件
		 this.back.setOnClickListener(new BackListener());			//设置返回按钮监听事件
		 
		 this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);		//初始db
		 this.sdb=db.getWritableDatabase();
		 
		 //获取从原来界面传过来的信息
		 Intent intent=getIntent();
		 playername=intent.getStringExtra("playersname");
		 playerid=intent.getStringExtra("playersid");
		 //System.out.println(playername);
		 //System.out.println(playerid);
		 
		 
	}
	
	 //定义出拳按钮监听事件
	 public class ChooseListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			switch(view.getId()){							//获取按钮id
			case R.id.cs:{
				person=1;									//置person等于1，代表玩家出剪刀
				imag1.setImageResource(R.drawable.soc);		//显示玩家出剪刀					
				}
				break;
			case R.id.cr:{
					person=2;									//置person等于1，代表玩家出剪刀
					imag1.setImageResource(R.drawable.rock);		//显示玩家出剪刀	
				}
				break;
			case R.id.cb:{
					person=3;									//置person=3，代表玩家出布
					imag1.setImageResource(R.drawable.bu);		//显示玩家出布
				}
				break;
			default:
				break;
				
			}
			
			Compete.this.computer=(int)(Math.random()*3)+1;	//电脑产生一个随机数，代表出的拳
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
			//通过比较computer和person的大小来设置玩家的分数
            if(computer==person){												//如果玩家和电脑出一样的话，布加分		
            	    	sco=sco+0;
            	    	Compete.this.score.setText(sco+"");
            	    	
            	}	
            
            else if(person==1&&computer==2||person==2&&computer==3				//如果玩家输了扣一分
            		||person==3&&computer==1){							
            		    sco=sco-1;
            		    Compete.this.score.setText(sco+"");
            	}
            else{
            	sco=sco+1;														//如果玩家赢了，加一分
            	Compete.this.score.setText(sco+"");								
            }
			
		}
			
	}
	 
	 //开始挑战按钮事件
	 private class ChallengeListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Compete.this.soc.setEnabled(true);												//设置剪刀按钮可用
			Compete.this.rock.setEnabled(true);												//设置石头按钮可用
			Compete.this.bu.setEnabled(true);												//设置布按钮可用
			Compete.this.score.setText("0");												//把显示分数为0
			Compete.this.sco=0;																//设置分数为0
			Compete.this.challenge.setEnabled(false);										//设置开始挑战按钮不可用
			Compete.this.back.setEnabled(false);											//设置返回按钮可用
			count=new MyCount(16000, 1000);													//实例count，倒计时16秒
			count.start();																	//计时开始
			
		}
		 
	 }
	 
	 //定义返回按钮事件
	 private class BackListener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//把玩家和玩家id传回原界面
				Intent intent=new Intent();		
				intent.putExtra("playername", playername);
				intent.putExtra("playerid", playerid);
				intent.setClass(Compete.this, ChooseMode.class);	//返回开始时的界面
				startActivity(intent);
				Compete.this.finish();
			}
			  
		  }

	private class MyCount extends CountDownTimer{
	//构造方法
	public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
	
	//计时结束
	@Override
	public void onFinish() {
		 Compete.this.time.setText("");									//设置时间为空
		 Compete.this.soc.setEnabled(false);							//设置剪刀按钮不可用
		 Compete.this.rock.setEnabled(false);							//设置石头按钮不可用
		 Compete.this.bu.setEnabled(false);								//设置布按钮不可用
		 Compete.this.imag1.setImageResource(R.drawable.rock);			//把显示玩家出的拳置为石头
		 Compete.this.imag2.setImageResource(R.drawable.rock);			//把显示电脑出的拳头置为石头
		 
		 Compete.this.db=new GuessSQLite(getApplicationContext(), "guess.db", null, 1);
		 Compete.this.sdb=db.getWritableDatabase();										//以修改的方式打开数据库
		 
		 //定义对话框
		 Dialog dialog=new AlertDialog.Builder(Compete.this)
		 			.setTitle("        时间到")											//设置对话框标题
		 			.setMessage("        得分："+score.getText().toString())				//设置对话框信息
		 			.setPositiveButton("确定", 											//确定按钮
		 					new DialogInterface.OnClickListener() {						//按钮监听事件
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									Compete.this.score.setText("0");					//把显示分数置为0
									Compete.this.challenge.setEnabled(true);			//设置开始挑战按钮可用
									Compete.this.back.setEnabled(true);					//设置返回按钮可用
									
								}
							}).setNegativeButton("查看排行榜", 								//设置排行榜按钮
									new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface arg0, int arg1) {
											Compete.this.score.setText("0");			//把显示分数置为0
											Compete.this.challenge.setEnabled(true);	//设置开始挑战按钮可用
											Compete.this.back.setEnabled(true);			//设置返回按钮可用
											Intent intent=new Intent(getApplicationContext(), Rank.class);
											intent.putExtra("playername", playername);
											intent.putExtra("playerid", playerid);
											startActivity(intent);						//启动界面
											Compete.this.finish();
										}
									}).setCancelable(false).create();
		 dialog.show();
		 
		 String id=playerid;															//玩家id
		 String name=playername;														//玩家账户
		 String playerscore=Compete.this.score.getText().toString();					//玩家得分
		 
		 String select="select playername,score from user_score";		
		 Cursor cursor=sdb.rawQuery(select, null);										//查询分数
		 cursor.moveToFirst();															//将结果集指针移到第一行
		 
		 String pname=null;																//数据库中的玩家	
		 String pscore=null;															//数据库中玩家分数
		 
		 //遍历查询
		 do{																			
			 try{
				 pname=cursor.getString(0);												//获取玩家
				 pscore=cursor.getString(1);											//获取分数
			 }catch(Exception e){														//异常处理
				 pname="";
				 pscore="";
			 }
			 if(pname.equals(name)){														//如果玩家是否已存在
				 	int ps=Integer.parseInt(pscore);										//该玩家原分数
				 	int psc=Integer.parseInt(playerscore);									//该玩家新分数
				 	if(psc>ps){																//如果新分数大于原来分数
					sdb.execSQL("update user_score set score='"+playerscore+"' where _id='"	//更新分数
							+playerid+"' and playername='"+playername+"'");
				 	  }
					 cursor.close();														//关闭查询
					 break;
				 	}		
		 }while(cursor.moveToNext());														//将结果指针集移到下一行
		 
		 //如果该玩家是新玩家，把该玩家和分数加进数据库
		 if(!pname.equals(name)){
			 sdb.execSQL("insert into user_score values('"+id+"','"+name+"','"+playerscore+"')"); 
			
		 }		 
		 cursor.close();																	//关闭查询
		 sdb.close();																		//关闭数据库
	}

	@Override
	public void onTick(long surplus) {
		Compete.this.time.setText(surplus/1000+"");											//显示剩几秒
		
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
