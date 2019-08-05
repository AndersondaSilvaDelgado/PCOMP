package br.com.usinasantafe.pcomp.pst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	public static final String FORCA_DB_NAME = "pcomp_db";
	public static final int FORCA_BD_VERSION = 1;
	
	private static Database instance;
	
	public static Database getInstance(){
		return instance;
	}
	
	public static void init(Context context){
		if(instance != null){
			return;
		}
		instance = new Database(context);
	}
	
	public Database(Context context) {
		super(context, FORCA_DB_NAME, null, FORCA_BD_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
