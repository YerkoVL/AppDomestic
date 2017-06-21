package pe.app.com.demo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.app.com.demo.SQLiteHelper.DistritosSQLHelper;
import pe.app.com.demo.SQLiteHelper.DptosSQLHelper;
import pe.app.com.demo.SQLiteHelper.MapasSQLHelper;
import pe.app.com.demo.SQLiteHelper.ProvinciasSQLHelper;

public class RegistroActivity extends AppCompatActivity {

    @Bind(R.id.btnRegistrar) Button registro;

    @Bind(R.id.spnDepartamento) Spinner departamento;
    @Bind(R.id.spnProvincia) Spinner provincia;
    @Bind(R.id.spnDistrito) Spinner distrito;
    Context ctx;

    Long idDpto, idProvincia, idDistrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);

        ctx = this;

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pasarMenu = new Intent(ctx.getApplicationContext(),MenuPrincipalActivity.class);
                startActivity(pasarMenu);
            }
        });

        llenarSpinnerDepartamento();

        departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idDpto = adapterView.getItemIdAtPosition(i);
                llenarSpinnerProvincias(idDpto);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idProvincia = adapterView.getItemIdAtPosition(i);
                llenarSpinnerDistrito(idProvincia);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idDistrito = adapterView.getItemIdAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void llenarSpinnerDepartamento(){
        DptosSQLHelper usdbh =
                new DptosSQLHelper(ctx, "DBDepartamentos", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        try {
            Cursor c = db.rawQuery("select DISTINCT IdDpto AS _id, Descripcion,IdPais from Departamentos", null);
            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, new String[]{"Descripcion"}, new int[]{android.R.id.text1});
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            departamento.setAdapter(adaptador);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void llenarSpinnerProvincias(Long idDepartamento){
        ProvinciasSQLHelper usdbh =
                new ProvinciasSQLHelper(ctx, "DBProvincias", null, 1);

        try{
            SQLiteDatabase db = usdbh.getWritableDatabase();
            Cursor c = db.rawQuery("select DISTINCT IdProvincia AS _id , Descripcion, IdDpto from Provincias where IdDpto = '" + idDepartamento + "'", null);
            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,c,new String[] {"Descripcion"},    new int[] {android.R.id.text1});
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            provincia.setAdapter(adaptador);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void llenarSpinnerDistrito(Long idProvincia){
        try{
            DistritosSQLHelper usdbh =
                new DistritosSQLHelper(ctx, "DBDistritos", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            Cursor c = db.rawQuery("select DISTINCT IdDistrito AS _id , Descripcion, IdProvincia from Distritos where IdProvincia = '" + idProvincia + "'", null);
            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,c,new String[] {"Descripcion"},    new int[] {android.R.id.text1});
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            distrito.setAdapter(adaptador);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
