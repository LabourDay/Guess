package com.wzh.guess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class GuessSQLite extends SQLiteOpenHelper{
	
	//定义创建玩家表语句
	String createPlayerTable = "create table player_info(_id int auto_increment,playername char(20),"
			+ "password char(20),primary key('_id'));";
	//定义分数表语句
	String createPlayerScore = "create table user_score(_id int not null,playername char(20) not null,"
			+ "score int,primary key('_id'));";

	public GuessSQLite(Context context, String name, CursorFactory factory,			//定义构造
			int version) {
		super(context, name, factory, version);										//调用父类构造方法
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createPlayerTable);												//创建玩家信息表							
		db.execSQL(createPlayerScore);												//创建玩家分数表
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
