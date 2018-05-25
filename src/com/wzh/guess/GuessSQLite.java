package com.wzh.guess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class GuessSQLite extends SQLiteOpenHelper{
	
	//���崴����ұ����
	String createPlayerTable = "create table player_info(_id int auto_increment,playername char(20),"
			+ "password char(20),primary key('_id'));";
	//������������
	String createPlayerScore = "create table user_score(_id int not null,playername char(20) not null,"
			+ "score int,primary key('_id'));";

	public GuessSQLite(Context context, String name, CursorFactory factory,			//���幹��
			int version) {
		super(context, name, factory, version);										//���ø��๹�췽��
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createPlayerTable);												//���������Ϣ��							
		db.execSQL(createPlayerScore);												//������ҷ�����
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
