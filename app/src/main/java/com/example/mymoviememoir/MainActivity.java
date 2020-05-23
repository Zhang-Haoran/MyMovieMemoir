package com.example.mymoviememoir;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mymoviememoir.fragment.AddToMemoirFragment;
import com.example.mymoviememoir.fragment.AdvancedFeatureFragment;
import com.example.mymoviememoir.fragment.HomeFragment;
import com.example.mymoviememoir.fragment.MapFragment;
import com.example.mymoviememoir.fragment.MovieMemoirFragment;
import com.example.mymoviememoir.fragment.MovieSearchFragment;
import com.example.mymoviememoir.fragment.MovieViewFragment;
import com.example.mymoviememoir.fragment.ReportFragment;
import com.example.mymoviememoir.fragment.WatchlistFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //show the navicon drawer icon top left hand side
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment());

    }

    //replace the fragment based on the parameter passed
    private void replaceFragment(Fragment nextFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.commit();
    }

    //executed each time the user selects a menu item
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.homeScreen:
                replaceFragment(new HomeFragment());
                break;
            case R.id.movieSearch:
                replaceFragment(new MovieSearchFragment());
                break;
//            case R.id.movieView:
//                replaceFragment(new MovieViewFragment());
//                break;
            case R.id.watchlist:
                replaceFragment(new WatchlistFragment());
                break;
//            case R.id.addToMemoir:
//                replaceFragment(new AddToMemoirFragment());
//                break;
            case R.id.movieMemoir:
                replaceFragment(new MovieMemoirFragment());
                break;
            case R.id.report:
                replaceFragment(new ReportFragment());
                break;
            case R.id.map:
                replaceFragment(new MapFragment());
                break;
//            case R.id.advancedFeatures:
//                replaceFragment(new AdvancedFeatureFragment());
//                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }

    //display the navigation drawer's icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
