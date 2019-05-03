package com.example.ghmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextViewResult;
    protected TextView mUnitTextViewResult;
    private RequestQueue mQueue;
    private LinearLayout ll;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView m_text_profil = (TextView) findViewById(R.id.text_profil);

        mTextViewResult = findViewById(R.id.text_view_result);
        mUnitTextViewResult = findViewById(R.id.unit_text_view_result);

        ll = findViewById(R.id.tt);

        mQueue = Volley.newRequestQueue(this);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        m_text_profil.setText( getNomProfil() );
    }

    protected void jsonParse() {
        String url2 = "http://88.136.244.147:8080/regulation/actuator.php";
        String url = "http://10.16.37.161/AndroidStudio/connecPHPv2.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        TextView tv = new TextView(MainActivity.this);
                        tv.setText(mTextViewResult.getText());
                        ll.addView(tv);
                        mTextViewResult.append("2");
                        JSONObject actionneur = jsonArray.getJSONObject(i);
                        mTextViewResult.append("3");
                        int id = actionneur.getInt("id");
                        mTextViewResult.append("4");
                        int port = actionneur.getInt("port");
                        mTextViewResult.append("5");
                        String name = actionneur.getString("nom");
                        mTextViewResult.append("6");
                        int pe_action = actionneur.getInt("Periode_Action");
                        int phys_measure_id = actionneur.getInt("Grandeur_Physique_id");
                        mTextViewResult.append("\n" + String.valueOf(id) + ", " + name + ", " + String.valueOf(port) + String.valueOf(pe_action) + ", " + phys_measure_id + "\n\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mTextViewResult.append("EXCEPTION");
                }
                mUnitTextViewResult.append("Ok");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mTextViewResult.append("onErrorResponse\n");
                mTextViewResult.append(error.toString());
                mTextViewResult.append("\n");
            }
        }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private CharSequence getNomProfil() {
            return "Nom profil";
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
