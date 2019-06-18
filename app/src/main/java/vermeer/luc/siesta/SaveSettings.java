package vermeer.luc.siesta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class SaveSettings extends SQLiteOpenHelper {

    private static SaveSettings instance;
    public SaveSettings(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Settings(_id INTEGER, " +
                "productivity INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE Siestas(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "minutes INTEGER, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");

        String insertEntry = "INSERT INTO Settings (_id, productivity) VALUES (0, 3)";
        sqLiteDatabase.execSQL(insertEntry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int getProductivity() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT productivity FROM Settings WHERE _id=?", new String[]{"0"});
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 2;
    }

    public void saveSiesta(int minutes) {
        SQLiteDatabase sqLiteDatabase = instance.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("minutes", minutes);
        sqLiteDatabase.insert("Siestas", null, content);
    }

    public Cursor getSiestas() {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT * FROM Siestas", null);
    }

    public void saveProductivity(int productivity) {
        SQLiteDatabase sqLiteDatabase = instance.getWritableDatabase();
        sqLiteDatabase.delete("Settings", "_id=?", new String[]{"0"});

        ContentValues content = new ContentValues();
        content.put("_id", 0);
        content.put("productivity", productivity);
        sqLiteDatabase.insert("Settings", null, content);
    }

    public static SaveSettings getInstance(Context context) {
        if (instance == null){
            instance = new SaveSettings(context, "databaseSettings", null, 1 );
        }
        return instance;
    }
}