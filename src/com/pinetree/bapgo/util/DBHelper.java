package com.pinetree.bapgo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	protected final static String DB_NAME = "BSNS_DB";
	protected final static int DB_VERSION = 1;
	
	public DBHelper(Context context){
		this(context, DB_NAME, null, DB_VERSION);
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String createBsnsContactTableSql = "CREATE TABLE BSNS_CONTACT (" +
				"contact_no INTEGER PRIMARY KEY AUTOINCREMENT," +
				"contact_id INTEGER NOT NULL," +
				"phone_no VARCHAR(16) NOT NULL)," +
				"user_name VARCHAR(50) NOT NULL," +
				"contact_name VARCHAR(50) NOT NULL," +
				"memo_type CHAR(1) NOT NULL;";
				
		String createBsnsChatTableSql = "CREATE TABLE BSNS_CHAT (" +
				"chat_no INTEGER PRIMARY KEY AUTOINCREMENT," +
				"to_no INTEGER NOT NULL," +
				"from_no INTEGER NOT NULL," +
				"message TEXT NULL," +
				"regdate VARCHAR(12) NOT NULL);";
		
		db.execSQL(createBsnsContactTableSql);
		db.execSQL(createBsnsChatTableSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS BSNS_CONTACT");
		db.execSQL("DROP TABLE IF EXISTS BSNS_CHAT");
		onCreate(db);
	}

}
