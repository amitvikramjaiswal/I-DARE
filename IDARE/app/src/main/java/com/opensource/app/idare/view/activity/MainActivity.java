package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.ActivityMainBinding;
import com.opensource.app.idare.databinding.NavHeaderMainBinding;
import com.opensource.app.idare.utils.Utility;
import com.opensource.app.idare.view.fragment.ActiveProfileFragment;
import com.opensource.app.idare.view.fragment.AppTourFragment;
import com.opensource.app.idare.view.fragment.CoreListFragment;
import com.opensource.app.idare.view.fragment.DonateFragment;
import com.opensource.app.idare.view.fragment.InviteToIDareFragment;
import com.opensource.app.idare.view.fragment.PassiveFragment;
import com.opensource.app.idare.view.fragment.SettingsFragment;
import com.opensource.app.idare.viewmodel.MainActivityViewModel;
import com.opensource.app.idare.viewmodel.NavigationMenuHeaderViewModel;

/**
 * Created by akokala on 10/31/2017.
 */

public class MainActivity extends BaseActivity implements MainActivityViewModel.DataListener, NavigationView.OnNavigationItemSelectedListener,
        ActiveProfileFragment.OnFragmentInteractionListener, AppTourFragment.OnFragmentInteractionListener, CoreListFragment.OnFragmentInteractionListener,
        InviteToIDareFragment.OnFragmentInteractionListener, DonateFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener, PassiveFragment.OnFragmentInteractionListener, NavigationMenuHeaderViewModel.DataListener {
    private Fragment currentFragment;
    private ActivityMainBinding binding;
    private Context context;
    private MainActivityViewModel viewModel;
    private NavHeaderMainBinding navigationMenuHeaderBinding;
    private Toolbar toolbar;
    private AlertDialog alertDialog;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationMenuHeaderViewModel navigationMenuHeaderViewModel;

    public static Intent getStartIntent(Context context, String name) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Utility.USER_NAME, name);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.context = this.getApplicationContext();
        String nameFromBundle = getIntent().getStringExtra(Utility.USER_NAME);
        viewModel = new MainActivityViewModel(this, this,nameFromBundle);
        binding.setViewModel(viewModel);
        toolbar = (Toolbar) binding.toolbar.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationMenuHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, binding.navigationView, false);
        binding.navigationView.addHeaderView(navigationMenuHeaderBinding.getRoot());


        navigationMenuHeaderBinding.setHeaderViewModel(new NavigationMenuHeaderViewModel(context, this, nameFromBundle));
        setUpNavigationDrawer();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new PassiveFragment()).commit();
        }
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        currentFragment = fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public DrawerLayout getDrawer() {
        return binding.drawerLayout;
    }

    @Override
    public void getUserName(String userName) {
        navigationMenuHeaderViewModel = new NavigationMenuHeaderViewModel(this,this,userName);
    }

    private void setUpNavigationDrawer() {
        binding.navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                viewModel.toggleMakePassiveButton();
            }
        };

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //Bydefault first item should be selected
        binding.navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = getDrawer();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        binding.drawerLayout.closeDrawers();
        return viewModel.onNavigationItemSelected(item, getSupportFragmentManager().beginTransaction());
    }

}
