package pe.app.com.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.Respuesta;
import pe.app.com.demo.entity.Usuario;
import pe.app.com.demo.tools.GenericAlerts;

import static pe.app.com.demo.tools.GenericTools.GET_CONTINUO;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.GET_PASS;
import static pe.app.com.demo.tools.GenericTools.GET_USER;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_LOGIN;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class LoginActivity extends AppCompatActivity {

    //SISTEMA
    Context ctx;
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

        ctx = this;
        mCtx = this;

        progressDialog = new ProgressDialog(mCtx);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario = txtUsuario.getText().toString();
                Password = txtPassword.getText().toString();

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
                Intent pasarMenu = new Intent(ctx.getApplicationContext(), RegistroActivity.class);
                startActivity(pasarMenu);
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public Boolean validarTexto() {
        boolean valor = false;

        if (!Usuario.equals("")) {
            if (!Password.equals("")) {
                valor = true;
            } else {
                Toast.makeText(ctx, "Ingrese Password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ctx, "Ingrese Usuario", Toast.LENGTH_SHORT).show();
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
                                    guardarDatos(usuario.getNombreUsuario(), usuario.getPassword());
                                    progressDialog.dismiss();
                                    startActivity(new Intent(ctx, MenuPrincipalActivity.class));
                                }else{
                                    Respuesta respuesta = gson.fromJson(Response,Respuesta.class);
                                    alertas.mensajeInfo("Fallo Login",respuesta.getMensaje(),ctx);
                                    progressDialog.dismiss();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                alertas.mensajeInfo("Fallo Login","None",ctx);
                                progressDialog.dismiss();
                            }
                        }else{

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                progressDialog.dismiss();
                alertas.mensajeInfo("Fallo Login","Error Desconocido",ctx);
            }
        });

        Singleton.getInstance(this).addToRequestQueue(respuestaLogin);
    }

    public void guardarDatos(String usuario, String password) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
        editor.putString("nombreUsuario", usuario);
        editor.putString("password", password);
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
        SharedPreferences prefs = mCtx.getSharedPreferences("busquedaServicios",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("valor", "NO");
        editor.commit();
    }
}
