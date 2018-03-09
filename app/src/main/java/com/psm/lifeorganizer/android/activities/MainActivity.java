package com.psm.lifeorganizer.android.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.psm.lifeorganizer.R;
import com.psm.lifeorganizer.android.consts.FragmentName;
import com.psm.lifeorganizer.android.consts.MenuIndex;
import com.psm.lifeorganizer.android.fragments.ActivitiesFragment;
import com.psm.lifeorganizer.android.fragments.ParentFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ParentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Integer> menuPos;

    private FragmentManager fragmentManager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar mActionBar;

    private boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();

        menuPos = new ArrayList<>();
        menuPos.add(MenuIndex.ACTIVITIES_MENU);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //https://stackoverflow.com/questions/36566726/implementing-proper-back-navigation-and-home-button-handling-using-toolbar-in-an
        // On orientation change savedInstanceState will not be null.
        // Use this to show hamburger or up icon based on fragment back stack.
        if(savedInstanceState != null){
            resolveUpButtonWithFragmentStack();
        } else {
            // You probably want to add your ListFragment here.
        }

        fragmentManager = getSupportFragmentManager();
        replaceFragment(ActivitiesFragment.newInstance(false), MenuIndex.ACTIVITIES_MENU, FragmentName.ACTIVITIES,false);

    }

    @Override
    public void onBackPressed() {

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);

        } else {
            int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

            if (backStackCount >= 1) {
                getSupportFragmentManager().popBackStack();
                // Change to hamburger icon if at bottom of stack
                if(backStackCount == 2){
                    showUpButton(false);
                }

                if(backStackCount == 1){
                    //TODO: ver si conviene mostrar un popup para que el usuario confirme que se va
                    finish();
                }

            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (id == R.id.nav_activities) {
            replaceFragment(ActivitiesFragment.newInstance(false), MenuIndex.ACTIVITIES_MENU, FragmentName.ACTIVITIES, false);
        } else if (id == R.id.nav_completed_activities) {
            replaceFragment(ActivitiesFragment.newInstance(true), MenuIndex.ACTIVITIES_MENU, FragmentName.ACTIVITIES, false);
        } else if (id == R.id.nav_templates) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(ParentFragment fragment, Integer menuIndex, String backStackName, boolean showBackArrow) {

        if (menuIndex!=null) {
            menuPos.add(menuIndex);
        }
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(backStackName).commit();

        // The part that changes the hamburger icon to the up icon
        if (showBackArrow) {
            showUpButton(true);
        }

    }


    private void resolveUpButtonWithFragmentStack() {
        showUpButton(getSupportFragmentManager().getBackStackEntryCount() > 0);
    }

    public void showUpButton(boolean show) {
        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        if(show) {
            // Remove hamburger
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            // Show back button
            mActionBar.setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if(!mToolBarNavigationListenerIsRegistered) {
                mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            // Remove back button
            mActionBar.setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            mDrawerToggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }

        // So, one may think "Hmm why not simplify to:
        // .....
        // getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        // mDrawer.setDrawerIndicatorEnabled(!enable);
        // ......
        // To re-iterate, the order in which you enable and disable views IS important #dontSimplify.
    }

}
