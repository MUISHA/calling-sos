package com.example.callingsos.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.View;

import com.example.callingsos.frontend.MainActivity;
import com.example.callingsos.R;
import com.example.callingsos.addlocation.Addlocation;
import com.example.callingsos.login.Login;
import com.google.android.material.navigation.NavigationView;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import de.hdodenhof.circleimageview.CircleImageView;
public class Menu extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayouts;
    NavigationView navigationViews;
    Toolbar toolbars;
    private CircleImageView img_calling,img_addLocat,img_profile,img_helper,img_map;
    private ImageView img_tracking;

    /*------------------------Running activity ------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        /*------------------------Running Drawqble-------------*/


        /*------------------------Hooks----------------------*/
        drawerLayouts = findViewById(R.id.drawer_layout);
        navigationViews = findViewById(R.id.nav_view);

        toolbars = findViewById(R.id.toolbar);

        /*------------------------ImageView---------------------*/
        img_calling = (CircleImageView)findViewById(R.id.img_calling_sos);
        img_addLocat = (CircleImageView)findViewById(R.id.img_addlocation);
        img_profile = (CircleImageView)findViewById(R.id.img_profil);
        img_helper = (CircleImageView)findViewById(R.id.img_helper);
        img_map = (CircleImageView)findViewById(R.id.img_map);
        img_tracking = (ImageView)findViewById(R.id.img_traking_sos);

        img_calling.setOnClickListener(this);
        img_addLocat.setOnClickListener(this);
        img_profile.setOnClickListener(this);
        img_helper.setOnClickListener(this);
        img_map.setOnClickListener(this);
        img_tracking.setOnClickListener(this);
        /*------------------------ToolBar----------------------*/
        setSupportActionBar(toolbars);
        getSupportActionBar().setTitle("Menu");

        android.view.Menu menu = navigationViews.getMenu();
        menu.findItem(R.id.na_logout).setVisible(false);
        menu.findItem(R.id.na_profile).setVisible(false);
        /*------------------------Navigation Bar----------------------*/
        navigationViews.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayouts, toolbars,
                R.string.open_drawer, R.string.cloase_drawer);
        drawerLayouts.addDrawerListener(toggle);
        toggle.syncState();
        navigationViews.setNavigationItemSelectedListener(this);
        navigationViews.setCheckedItem(R.id.na_home);


    }
    @Override
    public void onClick(View v) {
        int index = v.getId();
        switch (index){
            case R.id.img_calling_sos :
                Intent calling = new Intent(this, MainActivity.class);
                startActivity(calling);
                break;
            case R.id.img_addlocation :
                Intent addlocation = new Intent(this, Addlocation.class);
                startActivity(addlocation);
                break;
            case R.id.img_profil :
                //Intent appropos = new Intent(this, Appros.class);
               // startActivity(appropos);
                break;
            case R.id.img_helper :
                //Intent apropos = new Intent(this, Apropos.class);
                //startActivity(apropos);
                break;
            case R.id.img_map :
                break;
            case R.id.img_traking_sos :
                break;


            default:
                break;

        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.na_calling_sos :
                Intent activity = new Intent(this, MainActivity.class);
                startActivity(activity);
                break;
            case R.id.na_login :
                Intent login_ = new Intent(this, Login.class);
                startActivity(login_);
                break ;
            case R.id.na_addlocation :
                Intent addLocat = new Intent(this, Addlocation.class);
                startActivity(addLocat);
                break;

            case R.id.na_appropos :
                //Intent addLocat = new Intent(this, AddEntreprise.class);
                //startActivity(addLocat);
                break;
            case R.id.na_exit :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.exit);
                builder.setMessage(R.string.etes_vous);

                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.show();
                break;
            default:
                break;
        }
        drawerLayouts.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search SOS...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                //adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent activity = null;
        int index = item.getItemId();
        switch (index){
            case R.id.action_settings :
                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_design :
                //Intent appopos = new Intent(this, Appros.class);
                //startActivity(appopos);
                break;
            case R.id.nav_helper :
                Toast.makeText(this, "Helper app", Toast.LENGTH_SHORT).show();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayouts.isDrawerOpen(GravityCompat.START)){
            drawerLayouts.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}