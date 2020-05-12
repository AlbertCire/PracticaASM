package cat.urv.deim.asm.p2.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ImageView favIcon1, favIcon2, bookmarkIcon1, bookmarkIcon2;
    private boolean[] selectedIcons = {true, true, false, false};

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
        int id = item.getItemId();
        if (id == R.id. nav_news) {
        } else if (id == R.id.nav_articles) {
        } else if (id == R.id.nav_events) {
        } else if (id == R.id.nav_calendar) {
        } else if (id == R.id.nav_profile) {
            // Only functional menu option at the moment
            /*startActivity(new Intent(
                    getApplicationContext(),
                    ProfileActivity.class));*/
        } else if (id == R.id.nav_faqs) {
        } else if (id == R.id.nav_settings) {
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout) ;
        drawer.closeDrawer(GravityCompat.START) ;
        return true;
    }
}
