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

import java.util.List;

import butterknife.ButterKnife;
import pe.app.com.demo.comunicators.ComunicadorFragment;

import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_AFIRMACION;
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
        implements NavigationView.OnNavigationItemSelectedListener,ComunicadorFragment {

    SharedPreferences sharedPreferences;
    Context mCtx;

    String nomUsuario, valorFRAGMENT, valorID, valorIMAGEN, valorNOMBRES, valorNOMBRECOMPLETO, valorDNI, valorREPUTACION, valorDIRECCION, valorLATITUD, valorLONGITUD;
    int valorACCION,idUsuario;
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
        nombreUsuario.setText(nomUsuario);
        Glide.with(mCtx).load(imagen).into(imagenUsuario);

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
            if (validarRealizoBusqueda()) {
                asignarFragment(new datosBusqueda());
            } else {
                asignarFragment(new datosInicioCliente());
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        } else if (id == R.id.ic_history) {
            getSupportActionBar().setTitle("Historial");
            eliminarPreferenciasFragmentos();
            asignarFragment(new datosInicioCliente());
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

    public Boolean validarRealizoBusqueda(){
        boolean valor;

        try {
        sharedPreferences = getSharedPreferences(PREFERENCIA_BUSQUEDA_SERVICIO, mCtx.MODE_PRIVATE);
        String valorRecuperado = sharedPreferences.getString(PREFERENCIA_VALOR_BUSQUEDA_SERVICIO, null);

            if(valorRecuperado.equals(PREFERENCIA_AFIRMACION)){
                valor = true;
            }else{
                valor = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            valor = false;
        }

        return valor;
    }

    public void obtenerDatosUsuario(){
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
        idUsuario = preferencia.getInt(PREFERENCIA_ID_USUARIO,0);
        nomUsuario = preferencia.getString(PREFERENCIA_NOMBRE_COMPLETO_USUARIO,"");
        imagen = preferencia.getString(PREFERENCIA_IMAGEN_USUARIO,"");
    }

    @Override
    public void comunicarBusquedaConResultado(String rubros) {
        List<android.support.v4.app.Fragment> fragmentList =  getSupportFragmentManager().getFragments();
        //contenidoResultadoBusqueda = (ContenidoResultadoBusqueda) fragmentList.get(0);
        //contenidoResultadoBusqueda.recibirListaRubros(rubros);
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
        PerfilActivity fragobj = new PerfilActivity();
        fragobj.setArguments(bundle);
        asignarFragment(fragobj);
    }
}
