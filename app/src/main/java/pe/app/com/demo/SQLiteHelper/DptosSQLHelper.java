package pe.app.com.demo.SQLiteHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DptosSQLHelper extends SQLiteOpenHelper{

    String sqlCreate = "CREATE TABLE Departamentos (IdDpto TEXT , Descripcion TEXT, IdPais TEXT)";

    public DptosSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Departamentos");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
}
