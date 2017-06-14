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
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import pe.app.com.demo.comunicators.ComunicadorFragment;

import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_AFIRMACION;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_BUSQUEDA_SERVICIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NEGACION;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_VALOR_BUSQUEDA_SERVICIO;

public class MenuPrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ComunicadorFragment {

    SharedPreferences sharedPreferences;
    Context mCtx;
    ContenidoResultadoBusqueda contenidoResultadoBusqueda;

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

        nombreUsuario.setText("MARIO MORALES");

        if(validarRealizoBusqueda()) {
            asignarFragment(new datosBusqueda());
        }else{
            asignarFragment(new datosInicioCliente());
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
            asignarFragment(new datosInicioCliente());
            getSupportActionBar().setTitle("Inicio");
        } else if (id == R.id.ic_mi_perfil) {
            getSupportActionBar().setTitle("Perfil Usuario");
            asignarFragment(new PerfilActivity());
        } else if (id == R.id.ic_search) {
            getSupportActionBar().setTitle("Búsqueda de servicios");
            asignarFragment(new ContenidoBuscarServicios());
        } else if (id == R.id.ic_history) {
            getSupportActionBar().setTitle("Historial");
            asignarFragment(new datosInicioCliente());
        } else if (id == R.id.ic_sign_out) {
            actualizarSP();
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

    @Override
    public void comunicarBusquedaConResultado(String rubros) {
        List<android.support.v4.app.Fragment> fragmentList =  getSupportFragmentManager().getFragments();
        contenidoResultadoBusqueda = (ContenidoResultadoBusqueda) fragmentList.get(0);
        contenidoResultadoBusqueda.recibirListaRubros(rubros);
    }
}
