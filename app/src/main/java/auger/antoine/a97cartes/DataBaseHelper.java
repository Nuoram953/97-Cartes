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
        db.execSQL("create table highscore (id INTEGER PRIMARY KEY AUTOINCREMENT, score INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table score");
        onCreate(db);
    }

    public void addScore(SQLiteDatabase db,int score){
        ContentValues cv= new ContentValues();
        cv.put("score", score);

        db.insert("highscore",null,cv);
    }



    public void openDB(){
        database = this.getWritableDatabase();
    }

    public void closeDB(){
        database.close();
    }


    public String returnLastElement(){
        Cursor c = database.rawQuery("select score from highscore order by score asc",null);

        if(c.moveToLast()){
            return c.getString(0);
        }else{
            return "0";
        }






    }
}
