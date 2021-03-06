package com.opensource.app.idare.view.activity;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.opensource.app.idare.R;
import com.opensource.app.idare.component.service.IDareLocationService;
import com.opensource.app.idare.databinding.ActivityMainBinding;
import com.opensource.app.idare.databinding.NavHeaderMainBinding;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.view.fragment.ActiveProfileFragment;
import com.opensource.app.idare.view.fragment.AppTourFragment;
import com.opensource.app.idare.view.fragment.CoreGroupFragment;
import com.opensource.app.idare.view.fragment.InviteToIDareFragment;
import com.opensource.app.idare.view.fragment.NotificationFragment;
import com.opensource.app.idare.view.fragment.PassiveFragment;
import com.opensource.app.idare.view.fragment.SafePracticesFragment;
import com.opensource.app.idare.view.fragment.SafePracticesPagerFragment;
import com.opensource.app.idare.view.fragment.SettingsFragment;
import com.opensource.app.idare.view.fragment.SimpleTextFragment;
import com.opensource.app.idare.viewmodel.MainActivityViewModel;
import com.opensource.app.idare.viewmodel.NavigationMenuHeaderViewModel;

/**
 * Created by akokala on 10/31/2017.
 */

public class MainActivity extends IDareBaseActivity implements MainActivityViewModel.DataListener, NavigationView.OnNavigationItemSelectedListener,
        ActiveProfileFragment.OnFragmentInteractionListener, AppTourFragment.OnFragmentInteractionListener, CoreGroupFragment.OnFragmentInteractionListener,
        SafePracticesFragment.OnFragmentInteractionListener, SafePracticesPagerFragment.OnFragmentInteractionListener, InviteToIDareFragment.OnFragmentInteractionListener,
        NotificationFragment.OnListFragmentInteractionListener, SimpleTextFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener, PassiveFragment.OnFragmentInteractionListener, NavigationMenuHeaderViewModel.DataListener {
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
        intent.putExtra(Constants.USER_NAME, name);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.context = this.getApplicationContext();
        String nameFromBundle = getIntent().getStringExtra(Constants.USER_NAME);
        viewModel = new MainActivityViewModel(this, this, nameFromBundle);
        binding.setViewModel(viewModel);
        toolbar = (Toolbar) binding.toolbar.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationMenuHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, binding.navigationView, false);
        binding.navigationView.addHeaderView(navigationMenuHeaderBinding.getRoot());

        if (!IDareLocationService.isIsRunning())
            startService(new Intent(this, IDareLocationService.class));

        navigationMenuHeaderBinding.setHeaderViewModel(new NavigationMenuHeaderViewModel(context, this, nameFromBundle));
        setUpNavigationDrawer();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new PassiveFragment()).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
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
        navigationMenuHeaderViewModel = new NavigationMenuHeaderViewModel(this, this, userName);
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
