package cat.urv.deim.asm.p2.common;

import cat.urv.deim.asm.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import cat.urv.deim.asm.p3.common.EventDetailActivity;
import cat.urv.deim.asm.p3.common.EventsFragment;
import cat.urv.deim.asm.p3.common.FaqsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    // Fragment references
    EventsFragment eventsFragment;
    FragmentTransaction transaction;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transaction = getSupportFragmentManager().beginTransaction();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(   // Hamburger button
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        //Fragment references
        eventsFragment = new EventsFragment();
        transaction.setPrimaryNavigationFragment(eventsFragment);


        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Some icons only appear if the user has logged in
        Menu menuNavigation = navigationView.getMenu();
        MenuItem menuFavorites = menuNavigation.findItem(R.id.nav_favorites);
        menuFavorites.setVisible(GlobalLoginClass.isLoginCorrect());
        MenuItem menuBookmarks = menuNavigation.findItem(R.id.nav_bookmarks);
        menuBookmarks.setVisible(GlobalLoginClass.isLoginCorrect());
    }

    // If user presses the "back" button, the menu is closed
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        TextView mainTitle = findViewById(R.id.main_title);

        int id = item.getItemId();
        if (id == R.id.nav_news) {
        } else if (id == R.id.nav_articles) {
        } else if (id == R.id.nav_events) {
            // We change the title
            mainTitle.setText(R.string.menu_events);
            // We change the fragment for the Event fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, eventsFragment);
            transaction.commit();
        } else if (id == R.id.nav_calendar) {
        } else if (id == R.id.nav_profile) {
            if (GlobalLoginClass.isLoginCorrect()) {
                startActivity(new Intent(
                        getApplicationContext(),
                        ProfileActivity.class));
            } else {
                startActivity(new Intent(
                        getApplicationContext(),
                        Login2Activity.class));
            }
        } else if (id == R.id.nav_faqs) {
            startActivity(new Intent(
                    getApplicationContext(),
                    FaqsActivity.class));
        } else if (id == R.id.nav_settings) {
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
