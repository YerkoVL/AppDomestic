package pe.app.com.demo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.app.com.demo.SQLiteHelper.DistritosSQLHelper;
import pe.app.com.demo.SQLiteHelper.DptosSQLHelper;
import pe.app.com.demo.SQLiteHelper.ProvinciasSQLHelper;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.Respuesta;
import pe.app.com.demo.tools.GenericAlerts;
import pe.app.com.demo.tools.GenericTools;

import static pe.app.com.demo.tools.GenericTools.GET_APELLIDOS;
import static pe.app.com.demo.tools.GenericTools.GET_CONTINUO;
import static pe.app.com.demo.tools.GenericTools.GET_CORREO;
import static pe.app.com.demo.tools.GenericTools.GET_DIRECCION;
import static pe.app.com.demo.tools.GenericTools.GET_FOTO;
import static pe.app.com.demo.tools.GenericTools.GET_ID_DISTRITO;
import static pe.app.com.demo.tools.GenericTools.GET_ID_PERFIL;
import static pe.app.com.demo.tools.GenericTools.GET_ID_TIPO_DOCUMENTO;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.GET_LATITUD;
import static pe.app.com.demo.tools.GenericTools.GET_LONGITUD;
import static pe.app.com.demo.tools.GenericTools.GET_NOMBRES;
import static pe.app.com.demo.tools.GenericTools.GET_NOMBRE_COMERCIAL;
import static pe.app.com.demo.tools.GenericTools.GET_NRO_DOCUMENTO;
import static pe.app.com.demo.tools.GenericTools.GET_RAZON_SOCIAL;
import static pe.app.com.demo.tools.GenericTools.GET_SERVICIO;
import static pe.app.com.demo.tools.GenericTools.GET_TELEFONO;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_REGISTRAR_USUARIO;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class RegistroActivity extends AppCompatActivity {

    @Bind(R.id.btnRegistrar)
    Button registro;
    @Bind(R.id.btnTomarFoto)
    Button tomarFoto;
    @Bind(R.id.btnSeleccionarFoto)
    Button seleccionarFoto;
    @Bind(R.id.btnSubirFoto)
    ImageView imagenMuestra;

    @Bind(R.id.spnDepartamento)
    Spinner departamento;
    @Bind(R.id.spnProvincia)
    Spinner provincia;
    @Bind(R.id.spnDistrito)
    Spinner distrito;

    @Bind(R.id.txtNombreUsuario)
    TextView nombreUsuario;
    @Bind(R.id.txtApellidoUsuario)
    TextView apellidoUsuario;
    @Bind(R.id.txtNroDocumento)
    TextView nroDocumento;
    @Bind(R.id.txtDireccionUsuario)
    TextView direccionUsuario;
    @Bind(R.id.txtEmailUsuario)
    TextView emailUsuario;
    @Bind(R.id.txtTelefonoUsuario)
    TextView telefonoUsuario;

    ProgressDialog progressDialog = null;
    Gson gson = new Gson();

    Bitmap imagenEnviar;
    Context ctx;

    GenericTools tools = new GenericTools();

    String latitud = "0.00", longitud = "0.00", image;

    private static final int RESULT_CAMERA = 0;
    private static final int RESULT_LOAD_IMAGE = 1;

    GenericAlerts alertas = new GenericAlerts();

    Long idDpto, idProvincia, idDistrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);

        ctx = this;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDatos();
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

        seleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
            }
        });

        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), RESULT_CAMERA);
            }
        });
    }

    public void llenarSpinnerDepartamento() {
        DptosSQLHelper usdbh =
                new DptosSQLHelper(ctx, "DBDepartamentos", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        try {
            Cursor c = db.rawQuery("select DISTINCT IdDpto AS _id, Descripcion,IdPais from Departamentos", null);
            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, new String[]{"Descripcion"}, new int[]{android.R.id.text1});
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            departamento.setAdapter(adaptador);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void llenarSpinnerProvincias(Long idDepartamento) {
        ProvinciasSQLHelper usdbh =
                new ProvinciasSQLHelper(ctx, "DBProvincias", null, 1);

        try {
            SQLiteDatabase db = usdbh.getWritableDatabase();
            Cursor c = db.rawQuery("select DISTINCT IdProvincia AS _id , Descripcion, IdDpto from Provincias where IdDpto = '" + idDepartamento + "'", null);
            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, new String[]{"Descripcion"}, new int[]{android.R.id.text1});
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            provincia.setAdapter(adaptador);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void llenarSpinnerDistrito(Long idProvincia) {
        try {
            DistritosSQLHelper usdbh =
                    new DistritosSQLHelper(ctx, "DBDistritos", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            Cursor c = db.rawQuery("select DISTINCT IdDistrito AS _id , Descripcion, IdProvincia from Distritos where IdProvincia = '" + idProvincia + "'", null);
            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, new String[]{"Descripcion"}, new int[]{android.R.id.text1});
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            distrito.setAdapter(adaptador);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validarDatos() {
        try {
            image = obtenerImagenConcatenado(imagenEnviar);
        } catch (Exception e) {
            e.printStackTrace();
            alertas.mensajeInfo("Error", "Debes ingresar foto", ctx);
        }
        String DNI = nroDocumento.getText().toString();
        String Nombres = nombreUsuario.getText().toString();
        String Apellidos = apellidoUsuario.getText().toString();
        String Direccion = direccionUsuario.getText().toString();
        String Email = emailUsuario.getText().toString();
        String Telefono = telefonoUsuario.getText().toString();
        Long valorDistrito = distrito.getSelectedItemId();

        if (!DNI.equals("")) {
            if (!Nombres.equals("")) {
                if (!Apellidos.equals("")) {
                    if (!Direccion.equals("")) {
                        if (valorDistrito != 0) {
                            if (!Email.equals("")) {
                                if (!Telefono.equals("")) {
                                    if (!image.equals("")) {
                                        registrarUsuario(Nombres, Apellidos, "1", DNI, Direccion, Email, Telefono, String.valueOf(valorDistrito),
                                                "1", "Social", "Comercial", longitud, latitud, "", "");
                                    } else {
                                        alertas.mensajeInfo("Error", "Debe ingresar foto de usuario", this);
                                    }
                                } else {
                                    alertas.mensajeInfo("Error", "Debe ingresar teléfono de usuario", this);
                                }
                            } else {
                                alertas.mensajeInfo("Error", "Debe ingresar email de usuario", this);
                            }
                        } else {
                            alertas.mensajeInfo("Error", "Debe ingresar un ditrito válido", this);
                        }
                    } else {
                        alertas.mensajeInfo("Error", "Debe ingresar direccion de usuario", this);
                    }
                } else {
                    alertas.mensajeInfo("Error", "Debe ingresar apellido de usuario", this);
                }
            } else {
                alertas.mensajeInfo("Error", "Debe ingresar nombre de usuario", this);
            }
        } else {
            alertas.mensajeInfo("Error", "Debe ingresar DNI de usuario", this);
        }
    }

    public void registrarUsuario(final String Nombres, final String Apellidos, final String IdTipoDocumento, final String NroDocumento,
                             final String Dirreccion, final String Correo, final String Telefono, final String IdDistrito, final String IdPerfil,
                             final String RazonSocial, final String NombreComercial, final String Longitud, final String Latitud, final String Servicio, final String Foto) {

        final String url = URL_APP + BASE_URL + BASE_REGISTRAR_USUARIO + GET_INICIO + GET_NOMBRES + Nombres + GET_CONTINUO + GET_APELLIDOS + Apellidos + GET_CONTINUO +
                            GET_ID_TIPO_DOCUMENTO + IdTipoDocumento + GET_CONTINUO + GET_NRO_DOCUMENTO + NroDocumento + GET_CONTINUO + GET_DIRECCION + Dirreccion + GET_CONTINUO +
                            GET_CORREO + Correo + GET_CONTINUO + GET_TELEFONO + Telefono + GET_CONTINUO + GET_ID_DISTRITO + IdDistrito + GET_CONTINUO + GET_ID_PERFIL + IdPerfil + GET_CONTINUO +
                            GET_RAZON_SOCIAL + RazonSocial + GET_CONTINUO + GET_NOMBRE_COMERCIAL + NombreComercial + GET_CONTINUO + GET_LONGITUD + Longitud + GET_CONTINUO + GET_LATITUD + Latitud + GET_CONTINUO +
                            GET_SERVICIO + Servicio + GET_CONTINUO + GET_FOTO + Foto;

        //?Nombres=Yerko&Apellidos=Vera&IdTipoDocumento=1&NroDocumento=7074835&Direccion=San&Correo=yveralezama@gmail.com&Telefono=982318699&IdDistrito=150101
        // &IdPerfil=1&RazonSocial=XXXX&NombreComercial=XXXX&Longitud=-77.04930804195533&Latitud=-12.080982952492553&SERVICIO=&Foto=

        progressDialog.show();
        progressDialog.setContentView(R.layout.content_progress_action);

        StringRequest respuestaRegistro = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {

                        if(Response!=null) {
                            try {
                                Respuesta respuesta = gson.fromJson(Response,Respuesta.class);
                                alertas.mensajeInfo("Exito",respuesta.getMensaje(),ctx);
                                progressDialog.dismiss();
                                startActivity(new Intent(ctx, LoginActivity.class));

                            }catch (Exception e){
                                e.printStackTrace();
                                alertas.mensajeInfo("Fallo Login","None",ctx);
                                progressDialog.dismiss();
                            }
                        }else{
                            Respuesta respuesta = gson.fromJson(Response,Respuesta.class);
                            alertas.mensajeInfo("Fallo Registro",respuesta.getMensaje(),ctx);
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                progressDialog.dismiss();
                alertas.mensajeInfo("Fallo Registro","Error Desconocido",ctx);
            }
        });

        Singleton.getInstance(this).addToRequestQueue(respuestaRegistro);
    }

    public String obtenerImagenConcatenado(Bitmap imagen){
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.JPEG,100,byteArray);
        byte[] imagenFormaByte = byteArray.toByteArray();
        String encode = Base64.encodeToString(imagenFormaByte,Base64.DEFAULT);
        return encode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri imagenSeleccionada = data.getData();
            try {
                imagenEnviar = MediaStore.Images.Media.getBitmap(getContentResolver(),imagenSeleccionada);
            } catch (IOException e) {
                e.printStackTrace();
                alertas.mensajeInfo("Error","Imagen no fue cargada correctamente",this);
            }
            imagenMuestra.setImageURI(imagenSeleccionada);
        }else{
            if(requestCode == RESULT_CAMERA && resultCode == RESULT_OK && data != null){
                imagenEnviar = (Bitmap) data.getExtras().get("data");
                imagenMuestra.setImageBitmap(imagenEnviar);
            }
        }
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

        alertas.mensajeInfo("","Localización agregada",ctx);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        RegistroActivity mainActivity;

        public RegistroActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(RegistroActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            latitud = String.valueOf(loc.getLatitude());
            longitud = String.valueOf(loc.getLongitude());

            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            this.mainActivity.setLocation(loc);

        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            alertas.mensajeInfo("Error","GPS desactivado",ctx);
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}
