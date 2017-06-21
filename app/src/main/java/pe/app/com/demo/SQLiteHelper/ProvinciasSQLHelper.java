package pe.app.com.demo.SQLiteHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DESCRIPCION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_PROVINCIA;

public class ProvinciasSQLHelper extends SQLiteOpenHelper{

    String sqlCreate = "CREATE TABLE Provincias (" + OBJETO_ID_PROVINCIA + " TEXT," + OBJETO_DESCRIPCION + " TEXT, IdDpto TEXT)";

    public ProvinciasSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Provincias");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
}
