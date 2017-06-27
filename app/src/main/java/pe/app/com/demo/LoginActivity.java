package pe.app.com.demo;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.app.com.demo.SQLiteHelper.DistritosSQLHelper;
import pe.app.com.demo.SQLiteHelper.DptosSQLHelper;
import pe.app.com.demo.SQLiteHelper.ProvinciasSQLHelper;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.Departamento;
import pe.app.com.demo.entity.Distrito;
import pe.app.com.demo.entity.Provincia;
import pe.app.com.demo.entity.Respuesta;
import pe.app.com.demo.entity.Usuario;
import pe.app.com.demo.tools.GenericAlerts;

import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DESCRIPCION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DIRECCION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_DEPARTAMENTO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_DISTRITO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_PAIS;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_PERFIL;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_PROVINCIA;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_NRO_DOCUMENTO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_BUSQUEDA_SERVICIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_IMAGEN_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LATITUD_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LONGITUD_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NEGACION;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_RATING_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_COMPLETO_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_PASS_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_VALOR_BUSQUEDA_SERVICIO;
import static pe.app.com.demo.tools.GenericTools.GET_CONTINUO;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.GET_PASS;
import static pe.app.com.demo.tools.GenericTools.GET_USER;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_CONSULTA_DEPARTAMENTOS;
import static pe.app.com.demo.tools.GenericUrls.BASE_CONSULTA_DISTRITOS;
import static pe.app.com.demo.tools.GenericUrls.BASE_CONSULTA_PROVINCIAS;
import static pe.app.com.demo.tools.GenericUrls.BASE_LOGIN;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class LoginActivity extends AppCompatActivity {

    //SISTEMA
    private static final String TAG = LoginActivity.class.getSimpleName();
    Gson gson = new Gson();
    GenericAlerts alertas = new GenericAlerts();
    ProgressDialog progressDialog = null;
    Context mCtx;

    //COMPONENTES
    @Bind(R.id.txtUsuario)
    AppCompatAutoCompleteTextView txtUsuario;
    @Bind(R.id.txtPasword)
    AppCompatAutoCompleteTextView txtPassword;

    @Bind(R.id.btnIngresar)
    Button btnIngresar;
    @Bind(R.id.btnIngresarFB)
    Button btnIngresarFb;

    @Bind(R.id.btnfab)
    Button registrarUsuario;

    //CONEXIONES

    //VARIABLES
    String Usuario;
    String Password;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mCtx = this;

        progressDialog = new ProgressDialog(mCtx);

        eliminarData();

        obtenerDepartamentos();
        obtenerProvincias();
        obtenerDistritos();

        try{
            String mensaje = getIntent().getStringExtra("MENSAJE");
            if(mensaje!=null) {
                alertas.mensajeInfo("Gracias por unirte", mensaje, mCtx);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario = txtUsuario.getText().toString();
                Password = txtPassword.getText().toString();

                actualizarSP();

                if (validarTexto()) {
                    validarDatos(Usuario, Password);
                }
            }
        });

        btnIngresarFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        registrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(mCtx.getApplicationContext(), RegistroActivity.class));
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        if(validarSesionActiva()){
            startActivity( new Intent(mCtx.getApplicationContext(), MenuPrincipalActivity.class));
        }
    }

    public Boolean validarTexto() {
        boolean valor = false;

        if (!Usuario.equals("")) {
            if (!Password.equals("")) {
                valor = true;
            } else {
                Toast.makeText(mCtx, "Ingrese Password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mCtx, "Ingrese Usuario", Toast.LENGTH_SHORT).show();
        }
        return valor;
    }

    public void validarDatos(final String usu, final String pass) {

        final String url = URL_APP + BASE_URL + BASE_LOGIN + GET_INICIO +GET_USER + usu + GET_CONTINUO + GET_PASS + pass;

        progressDialog.show();
        progressDialog.setContentView(R.layout.content_progress_action);

        StringRequest respuestaLogin = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {

                        if(Response!=null) {
                            try {
                                Usuario usuario = gson.fromJson(Response, Usuario.class);
                                if(usuario.getNombres()!=null) {
                                    guardarDatos(usuario.getId(),usuario.getIdPerfil(), usuario.getNombreUsuario(), usuario.getPassword(),
                                                 usuario.getNroDocumento(), usuario.getDireccion(),
                                                 usuario.getNombres() + " " + usuario.getApellidos(), usuario.getLatitud(),
                                                 usuario.getLongitud() , String.valueOf(usuario.getRating()) , usuario.getImagen());
                                    progressDialog.dismiss();
                                    startActivity(new Intent(mCtx, MenuPrincipalActivity.class));
                                }else{
                                    Respuesta respuesta = gson.fromJson(Response,Respuesta.class);
                                    alertas.mensajeInfo("Fallo Login",respuesta.getMensaje(),mCtx);
                                    progressDialog.dismiss();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                alertas.mensajeInfo("Fallo Login","None",mCtx);
                                progressDialog.dismiss();
                            }
                        }else{
                            Respuesta respuesta = gson.fromJson(Response,Respuesta.class);
                            alertas.mensajeInfo("Fallo Login",respuesta.getMensaje(),mCtx);
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                progressDialog.dismiss();
                alertas.mensajeInfo("Fallo Login","Error Desconocido",mCtx);
            }
        });

        Singleton.getInstance(this).addToRequestQueue(respuestaLogin);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    public void guardarDatos(int idUsuario, int idPerfil, String usuario, String password, String nroDocUsu , String dirUsua, String nombreCompleto, String latitud, String longitud, String rating, String imagen) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFERENCIA_USUARIO, MODE_PRIVATE).edit();
        editor.putInt(PREFERENCIA_ID_USUARIO, idUsuario);
        editor.putInt(OBJETO_ID_PERFIL,idPerfil);
        editor.putString(PREFERENCIA_NOMBRE_USUARIO, usuario);
        editor.putString(PREFERENCIA_PASS_USUARIO, password);
        editor.putString(PREFERENCIA_NOMBRE_COMPLETO_USUARIO, nombreCompleto);
        editor.putString(OBJETO_NRO_DOCUMENTO,nroDocUsu);
        editor.putString(OBJETO_DIRECCION, dirUsua);
        editor.putString(PREFERENCIA_LATITUD_USUARIO, latitud);
        editor.putString(PREFERENCIA_LONGITUD_USUARIO, longitud);
        editor.putString(PREFERENCIA_RATING_USUARIO, rating);
        editor.putString(PREFERENCIA_IMAGEN_USUARIO, imagen);
        editor.commit();
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void actualizarSP(){
        SharedPreferences prefs = mCtx.getSharedPreferences(PREFERENCIA_BUSQUEDA_SERVICIO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFERENCIA_VALOR_BUSQUEDA_SERVICIO, PREFERENCIA_NEGACION);
        editor.commit();
    }

    public Boolean validarSesionActiva(){
        boolean valor = false;
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
        String validarTexto = preferencia.getString(PREFERENCIA_NOMBRE_USUARIO,null);
        if(validarTexto!=null){
            valor = true;
        }
        return valor;
    }

    public void obtenerDepartamentos() {

        final String url = URL_APP + BASE_URL + BASE_CONSULTA_DEPARTAMENTOS;

        progressDialog.show();
        progressDialog.setContentView(R.layout.content_progress_action);

        JsonArrayRequest respuestaDepartamentos = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Departamento> departamentoArrayList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject object = (JSONObject) response.get(i);

                                Departamento resultadoDepartamento = new Departamento(
                                        object.getString(OBJETO_ID).trim(),
                                        object.getString(OBJETO_DESCRIPCION).trim(),
                                        object.getString(OBJETO_ID_PAIS).trim()
                                );
                                departamentoArrayList.add(resultadoDepartamento);
                            }
                            insertarDatosDepartamentos(departamentoArrayList);
                            progressDialog.dismiss();

                        }catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        });

        Singleton.getInstance(mCtx).addToRequestQueue(respuestaDepartamentos);
    }

    public void obtenerProvincias() {

        final String url = URL_APP + BASE_URL + BASE_CONSULTA_PROVINCIAS;

        progressDialog.show();
        progressDialog.setContentView(R.layout.content_progress_action);

        JsonArrayRequest respuestaProvincias = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Provincia> provinciaArrayList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = (JSONObject) response.get(i);
                                Provincia resultadoProvincia = new Provincia(
                                        object.getString(OBJETO_ID).trim(),
                                        object.getString(OBJETO_DESCRIPCION).trim(),
                                        object.getString(OBJETO_ID_DEPARTAMENTO).trim()
                                );
                                    provinciaArrayList.add(resultadoProvincia);
                                }
                            insertarDatosProvincias(provinciaArrayList);
                            progressDialog.dismiss();

                        }catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        });

        Singleton.getInstance(mCtx).addToRequestQueue(respuestaProvincias);
    }

    public void obtenerDistritos() {

        final String url = URL_APP + BASE_URL + BASE_CONSULTA_DISTRITOS;

        progressDialog.show();
        progressDialog.setContentView(R.layout.content_progress_action);

        JsonArrayRequest respuestaDistritos = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Distrito> distritoArrayList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject object = (JSONObject) response.get(i);

                                Distrito resultadoDistrito = new Distrito(
                                        object.getString(OBJETO_ID).trim(),
                                        object.getString(OBJETO_DESCRIPCION).trim(),
                                        object.getString(OBJETO_ID_PROVINCIA).trim()
                                );
                                distritoArrayList.add(resultadoDistrito);
                            }
                            insertarDatosDistritos(distritoArrayList);
                            progressDialog.dismiss();

                        }catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        });

        Singleton.getInstance(mCtx).addToRequestQueue(respuestaDistritos);
    }

    public void insertarDatosDepartamentos(ArrayList<Departamento> departamento){
        DptosSQLHelper usdbh =
                new DptosSQLHelper(mCtx, "DBDepartamentos", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        for(int i=0;i<departamento.size();i++) {

            Departamento nuevoDpto = departamento.get(i);

            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put(OBJETO_ID_DEPARTAMENTO, nuevoDpto.getId());
            nuevoRegistro.put(OBJETO_DESCRIPCION, nuevoDpto.getDescripcion());
            nuevoRegistro.put(OBJETO_ID_PAIS, nuevoDpto.getIdPais());
            db.insert("Departamentos", null, nuevoRegistro);
        }

        db.close();
    }

    public void insertarDatosProvincias(ArrayList<Provincia> provincia){
        ProvinciasSQLHelper usdbh =
                new ProvinciasSQLHelper(mCtx, "DBProvincias", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        for(int i=0;i<provincia.size();i++) {

            Provincia nuevaProvincia = provincia.get(i);

            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put(OBJETO_ID_PROVINCIA, nuevaProvincia.getId());
            nuevoRegistro.put(OBJETO_DESCRIPCION, nuevaProvincia.getDescripcion());
            nuevoRegistro.put(OBJETO_ID_DEPARTAMENTO, nuevaProvincia.getIdDpto());
            db.insert("Provincias", null, nuevoRegistro);
        }

        db.close();
    }

    public void insertarDatosDistritos(ArrayList<Distrito> distrito){
        DistritosSQLHelper usdbh =
                new DistritosSQLHelper(mCtx, "DBDistritos", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        for(int i=0;i<distrito.size();i++) {

            Distrito nuevaDistrito = distrito.get(i);

            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put(OBJETO_ID_DISTRITO, nuevaDistrito.getId());
            nuevoRegistro.put(OBJETO_DESCRIPCION, nuevaDistrito.getDescripcion());
            nuevoRegistro.put(OBJETO_ID_PROVINCIA, nuevaDistrito.getIdProvincia());
            db.insert("Distritos", null, nuevoRegistro);
        }

        db.close();
    }

    public void eliminarData(){
        DptosSQLHelper dpt =
                new DptosSQLHelper(mCtx, "DBDepartamentos", null, 1);
        ProvinciasSQLHelper provincia =
                new ProvinciasSQLHelper(mCtx, "DBProvincias", null, 1);
        DistritosSQLHelper distrito =
                new DistritosSQLHelper(mCtx, "DBDistritos", null, 1);

        SQLiteDatabase dbDpto = dpt.getWritableDatabase();
        SQLiteDatabase dbProvincia = provincia.getWritableDatabase();
        SQLiteDatabase dbDistrito = distrito.getWritableDatabase();

        dbDpto.delete("Departamentos", null, null);
        dbProvincia.delete("Provincias", null, null);
        dbDistrito.delete("Distritos", null, null);
    }
}
