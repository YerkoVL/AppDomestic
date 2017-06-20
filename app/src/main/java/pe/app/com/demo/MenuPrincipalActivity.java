package pe.app.com.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.ButterKnife;

import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_BUSQUEDA_SERVICIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_FRAGMENT;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_IMAGEN_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NEGACION;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_COMPLETO_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_VALOR_BUSQUEDA_SERVICIO;

public class MenuPrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    Context mCtx;

    String nomUsuario, valorFRAGMENT, valorRUBROS, valorID, valorNOMBRESOCIO, valorIMAGEN, valorNOMBRES,
    valorNOMBRECOMPLETO, valorDNI, valorREPUTACION, valorDIRECCION, valorLATITUD, valorLONGITUD,
    valorIDSOLICITUD, valorFECHASOLICITUD, valorSERVICIOS, valorFECHAINICIO, valorFECHAFIN, valorCOMENTARIOS, valorCALIFICACION;
    int valorACCION,idUsuario, valorIDSOCIO;
    int valorIDSOLICITUD_INSERTADA;
    ArrayList valorMAPAS;
    String imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCtx = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        TextView nombreUsuario = (TextView) hView.findViewById(R.id.nvgtdNombreUsuario);
        ImageView imagenUsuario = (ImageView) hView.findViewById(R.id.nvgtdImageView);

        obtenerDatosUsuario();

        try {
            Bundle bundle = getIntent().getExtras();
            if(bundle!=null) {
                valorACCION = bundle.getInt("VALOR_ACCION");
                if (valorACCION == 1) {
                    valorFRAGMENT = bundle.getString("VALOR_FRAGMENT");

                    if (valorFRAGMENT.equals("PERFIL_PERSONAL")) {
                        valorID = bundle.getString("ID");
                        valorNOMBRES = bundle.getString("NOMBRES");
                        valorNOMBRECOMPLETO = bundle.getString("NOMBRES_COMPLETOS");
                        valorREPUTACION = bundle.getString("REPUTACION");
                        valorIMAGEN= bundle.getString("IMAGEN");
                        valorDNI = bundle.getString("DNI");
                        valorDIRECCION = bundle.getString("DIRECCION");
                        valorLATITUD = bundle.getString("LATITUD");
                        valorLONGITUD = bundle.getString("LONGITUD");
                        enviarDatosPerfilPersonal(
                                valorID,
                                valorNOMBRES,
                                valorNOMBRECOMPLETO,
                                valorREPUTACION,
                                valorIMAGEN,
                                valorDNI,
                                valorDIRECCION,
                                valorLATITUD,
                                valorLONGITUD);
                    } else {
                        if (valorFRAGMENT.equals("PERFIL_BUSQUEDA")){
                            valorRUBROS = bundle.getString("VALOR_RUBROS");
                            valorIDSOLICITUD_INSERTADA = bundle.getInt("VALOR_ID_SOLICITUD");
                            enviarDatosBuscarServicios(valorRUBROS,valorIDSOLICITUD_INSERTADA);
                            asignarFragment(new datosBusqueda());
                        }else{
                            if(valorFRAGMENT.equals("PERFIL_HISTORIAL")){
                                valorIDSOCIO = bundle.getInt("ID_SOCIO");
                                valorNOMBRESOCIO = bundle.getString("NOMBRE_SOCIO");
                                getSupportActionBar().setTitle(valorNOMBRESOCIO);
                                enviarDatosHistorialTrabajos(valorIDSOCIO);
                            }else{
                                if(valorFRAGMENT.equals("PERFIL_DETALLE_SOLICITUD")){
                                    valorIDSOLICITUD = bundle.getString("VALOR_ID_SOLICITUD");
                                    valorFECHASOLICITUD = bundle.getString("VALOR_FECHA_SOLICITUD");
                                    valorSERVICIOS = bundle.getString("VALOR_SERVICIOS");
                                    valorRUBROS = bundle.getString("VALOR_RUBROS");
                                    valorFECHAINICIO = bundle.getString("VALOR_FECHA_INICIO");
                                    valorFECHAFIN = bundle.getString("VALOR_FECHA_FIN");
                                    getSupportActionBar().setTitle("Detalle Solicitud " + valorIDSOLICITUD);
                                    enviarDatosDetalleSolicitud(
                                            valorIDSOLICITUD,
                                            valorFECHASOLICITUD,
                                            valorSERVICIOS,
                                            valorRUBROS,
                                            valorFECHAINICIO,
                                            valorFECHAFIN);
                                }else{
                                    if(valorFRAGMENT.equals("PERFIL_DETALLE_HISTORIAL")){
                                        valorIMAGEN = bundle.getString("IMAGEN");
                                        valorNOMBRECOMPLETO = bundle.getString("NOMBRE");
                                        valorRUBROS = bundle.getString("RUBROS");
                                        valorFECHAINICIO = bundle.getString("FECHA_INICIO");
                                        valorFECHAFIN = bundle.getString("FECHA_FIN");
                                        valorSERVICIOS = bundle.getString("SERVICIOS");
                                        valorCOMENTARIOS = bundle.getString("COMENTARIOS");
                                        valorCALIFICACION = bundle.getString("CALIFICACION");
                                        enviarDatosDetalleHistorial(
                                                valorIMAGEN,
                                                valorNOMBRECOMPLETO,
                                                valorRUBROS,
                                                valorFECHAINICIO,
                                                valorFECHAFIN,
                                                valorSERVICIOS,
                                                valorCOMENTARIOS,
                                                valorCALIFICACION
                                        );
                                    }else{
                                        if(valorFRAGMENT.equals("PERFIL_MAPA")){
                                            valorID = bundle.getString("VALOR_ID");
                                            valorNOMBRECOMPLETO = bundle.getString("VALOR_NOMBRE");
                                            valorRUBROS = bundle.getString("VALOR_RUBROS");
                                            valorLATITUD = bundle.getString("VALOR_LATITUD");
                                            valorLONGITUD = bundle.getString("VALOR_LONGITUD");
                                            enviarDatosUbicacion(
                                                    valorID,
                                                    valorNOMBRECOMPLETO,
                                                    valorRUBROS,
                                                    Double.valueOf(valorLATITUD),
                                                    Double.valueOf(valorLONGITUD)
                                            );
                                        }else {
                                            //OTRO FRAGMENTO
                                        }
                                    }
                                }
                            }
                        }
                    }

                }else {
                    valorID = bundle.getString("ID");
                    valorIMAGEN = bundle.getString("IMAGEN");
                    if (valorIMAGEN != null) {
                        imagen = valorIMAGEN;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (valorACCION != 1){
            //if (validarRealizoBusqueda()) {
              //  asignarFragment(new datosBusqueda());
            //} else {
                asignarFragment(new datosInicioCliente());
            //}
        }

        nombreUsuario.setText(nomUsuario);
        Glide.with(mCtx).load(imagen).into(imagenUsuario);
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().popBackStack();//No se porqué puse lo mismo O.o
        }

        //if (drawer.isDrawerOpen(GravityCompat.START)) {
        //    drawer.closeDrawer(GravityCompat.START);
        //} else {
        //    super.onBackPressed();
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.ic_house){
            getSupportActionBar().setTitle("Inicio");
            eliminarPreferenciasFragmentos();
            asignarFragment(new datosInicioCliente());
        } else if (id == R.id.ic_mi_perfil) {
            getSupportActionBar().setTitle("Perfil Usuario");
            eliminarPreferenciasPersonal();
            eliminarPreferenciasFragmentos();
            asignarFragment(new PerfilActivity());
        } else if (id == R.id.ic_search) {
            getSupportActionBar().setTitle("Búsqueda de servicios");
            eliminarPreferenciasFragmentos();
            asignarFragment(new ContenidoBuscarServicios());
        } else if (id == R.id.ic_sign_out) {
            actualizarSP();
            eliminarPreferenciasFragmentos();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        } else if (id == R.id.ic_ayuda) {

        } else if (id == R.id.ic_informacion) {

        }
        return true;
    }

    public void asignarFragment(android.support.v4.app.Fragment fragmento){

        if(fragmento!=null){
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_menu_principal,fragmento);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void eliminarPreferenciasFragmentos() {
        SharedPreferences preferenciasFragmentos = getSharedPreferences(PREFERENCIA_FRAGMENT, MODE_PRIVATE);
        preferenciasFragmentos.edit().clear().commit();
    }

    public void eliminarPreferenciasPersonal() {
        SharedPreferences preferenciasFragmentos = getSharedPreferences(PREFERENCIA_PERSONAL, MODE_PRIVATE);
        preferenciasFragmentos.edit().clear().commit();
    }

    public void actualizarSP(){
        SharedPreferences preferenciasGenerales = getSharedPreferences(PREFERENCIA_USUARIO, MODE_PRIVATE);
        preferenciasGenerales.edit().clear().commit();

        SharedPreferences prefs = mCtx.getSharedPreferences(PREFERENCIA_BUSQUEDA_SERVICIO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFERENCIA_VALOR_BUSQUEDA_SERVICIO, PREFERENCIA_NEGACION);
        editor.commit();
    }

    public void obtenerDatosUsuario(){
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
        idUsuario = preferencia.getInt(PREFERENCIA_ID_USUARIO,0);
        nomUsuario = preferencia.getString(PREFERENCIA_NOMBRE_COMPLETO_USUARIO,"");
        imagen = preferencia.getString(PREFERENCIA_IMAGEN_USUARIO,"");
    }

    public void enviarDatosPerfilPersonal(String valorID, String valorNOMBRES, String valorNOMBRECOMPLETO, String valorREPUTACION, String valorIMAGEN, String valorDNI, String valorDIRECCION, String valorLATITUD, String valorLONGITUD){
        Bundle bundle = new Bundle();
            bundle.putString("VALOR_FRAGMENT","PERSONAL");
            bundle.putString("ID", valorID);
            bundle.putString("NOMBRES", valorNOMBRES);
            bundle.putString("NOMBRES_COMPLETOS", valorNOMBRECOMPLETO);
            bundle.putString("REPUTACION", valorREPUTACION);
            bundle.putString("IMAGEN", valorIMAGEN);
            bundle.putString("DNI", valorDNI);
            bundle.putString("DIRECCION", valorDIRECCION);
            bundle.putString("LATITUD", valorLATITUD);
            bundle.putString("LONGITUD", valorLONGITUD);
        PerfilActivity fragmentoObjeto = new PerfilActivity();
        fragmentoObjeto.setArguments(bundle);
        asignarFragment(fragmentoObjeto);
    }

    public void enviarDatosBuscarServicios(String listRubros,int IdSolicitud){
        Bundle bundle = new Bundle();
        bundle.putString("VALOR_RUBROS", listRubros);
        bundle.putInt("VALOR_ID_SOLICITUD", IdSolicitud);
        ContenidoResultadoBusqueda fragmentoObjeto = new ContenidoResultadoBusqueda();
        fragmentoObjeto.setArguments(bundle);
        asignarFragment(fragmentoObjeto);
    }

    public void enviarDatosHistorialTrabajos(int idSocio){
        Bundle bundle = new Bundle();
        bundle.putInt("ID_SOCIO", idSocio);
        ContenidoHistorialTrabajos fragmentoObjeto = new ContenidoHistorialTrabajos();
        fragmentoObjeto.setArguments(bundle);
        asignarFragment(fragmentoObjeto);
    }

    public void enviarDatosDetalleSolicitud(String idSolicitud, String fechaSolicitud, String servicios, String rubros, String fechaInicio, String fechaFin){
        Bundle bundle = new Bundle();
        bundle.putString("ID", idSolicitud);
        bundle.putString("FECHA_SOLICITUD", fechaSolicitud);
        bundle.putString("SERVICIOS", servicios);
        bundle.putString("RUBROS", rubros);
        bundle.putString("INICIO", fechaInicio);
        bundle.putString("FIN", fechaFin);
        ContenidoDetalleSolicitudes fragmentoObjeto = new ContenidoDetalleSolicitudes();
        fragmentoObjeto.setArguments(bundle);
        asignarFragment(fragmentoObjeto);
    }

    public void enviarDatosDetalleHistorial(String imagen, String nombre, String rubros, String fechaInicio, String fechaFin, String servicios, String comentarios, String calificacion){
        Bundle bundle = new Bundle();
        bundle.putString("IMAGEN", imagen);
        bundle.putString("NOMBRE", nombre);
        bundle.putString("RUBROS", rubros);
        bundle.putString("INICIO", fechaInicio);
        bundle.putString("FIN", fechaFin);
        bundle.putString("SERVICIOS", servicios);
        bundle.putString("COMENTARIOS", comentarios);
        bundle.putString("CALIFICACION", calificacion);
        ContenidoDetalleHistorial fragmentoObjeto = new ContenidoDetalleHistorial();
        fragmentoObjeto.setArguments(bundle);
        asignarFragment(fragmentoObjeto);
    }

    public void enviarDatosUbicacion(String idPersona, String nombreCompleto,String rubros, Double latitud, Double longitud){
        Bundle bundle = new Bundle();
        bundle.putString("ID", idPersona);
        bundle.putString("NOMBRES_COMPLETOS", nombreCompleto);
        bundle.putString("RUBROS", rubros);
        bundle.putDouble("LATITUD", latitud);
        bundle.putDouble("LONGITUD", longitud);
        ContenidoResultadoMapa fragmentoObjeto = new ContenidoResultadoMapa();
        fragmentoObjeto.setArguments(bundle);
        asignarFragment(fragmentoObjeto);
    }
}
