package auger.antoine.a97cartes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static DataBaseHelper instance;
    private SQLiteDatabase database;

    public static DataBaseHelper getInstance(Context context) {
        if(instance == null){
            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    private DataBaseHelper(Context context){
        super(context, "db", null,1);
        openDB();
    }






    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table score (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, score INTEGER);");
        addScore(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table score");
        onCreate(db);
    }

    public void addScore(SQLiteDatabase db){
        ContentValues cv= new ContentValues();
        cv.put("nom","Antoine");
        cv.put("score", 54);

        db.insert("score",null,cv);
    }



    public void openDB(){
        database = this.getWritableDatabase();
    }

    public void closeDB(){
        database.close();
    }


    public Vector<String> returnLastElement(){
        Cursor c = database.rawQuery("select * from score",null);
        Vector<String> result = new Vector<>();

        c.moveToLast();

        for(int i = 0;i<c.getCount();i++){
            result.add(c.getString(i));
        }

        return result;


    }
}
