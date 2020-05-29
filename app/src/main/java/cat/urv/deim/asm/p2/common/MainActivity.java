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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import cat.urv.deim.asm.libraries.commanagerdc.providers.DataProvider;
import cat.urv.deim.asm.p3.common.EventsFragment;
import cat.urv.deim.asm.p3.common.FaqsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ImageView favIcon1, favIcon2, bookmarkIcon1, bookmarkIcon2;
    private boolean[] selectedIcons = {true, true, false, false};
    //Fragment references
    EventsFragment eventsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(   // Hamburger button
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        favIcon1 = findViewById(R.id.fav_icon_1);
        favIcon2 = findViewById(R.id.fav_icon_2);
        bookmarkIcon1 = findViewById(R.id.bookmark_icon_1);
        bookmarkIcon2 = findViewById(R.id.bookmark_icon_2);

        //Fragment references
        eventsFragment = new EventsFragment();


        //Fragment por defecto (en nuesro caso es EventsFragment)
        //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, eventsFragment).commit();


        // Listeners to change the icons when clicked
        favIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIcons[0]) {
                    favIcon1.setImageResource(R.drawable.favorite_border);
                    selectedIcons[0] = false;
                } else {
                    favIcon1.setImageResource(R.drawable.favorite_black);
                    selectedIcons[0] = true;
                }
            }
        });
        favIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIcons[1]) {
                    favIcon2.setImageResource(R.drawable.favorite_border);
                    selectedIcons[1] = false;
                } else {
                    favIcon2.setImageResource(R.drawable.favorite_black);
                    selectedIcons[1] = true;
                }
            }
        });
        bookmarkIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIcons[2]) {
                    bookmarkIcon1.setImageResource(R.drawable.bookmark_border);
                    selectedIcons[2] = false;
                } else {
                    bookmarkIcon1.setImageResource(R.drawable.bookmark_black);
                    selectedIcons[2] = true;
                }
            }
        });
        bookmarkIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIcons[3]) {
                    bookmarkIcon2.setImageResource(R.drawable.bookmark_border);
                    selectedIcons[3] = false;
                } else {
                    bookmarkIcon2.setImageResource(R.drawable.bookmark_black);
                    selectedIcons[3] = true;
                }
            }
        });

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
            //Cambiamos el titulo
            mainTitle.setText(R.string.menu_events);
            //Sustituimos el fragment actual por el de Eventos
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, eventsFragment);
            transaction.commit();
        } else if (id == R.id.nav_calendar) {
        } else if (id == R.id.nav_profile) {
            // Only functional menu option at the moment
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
