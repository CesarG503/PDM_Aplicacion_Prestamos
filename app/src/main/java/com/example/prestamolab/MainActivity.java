package com.example.prestamolab;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.example.prestamolab.Database.DatabaseSeeder;
import com.example.prestamolab.Database.appDataBase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.deleteDatabase("db_prestamos");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        DatabaseSeeder.checkAndSeed(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView1 = findViewById(R.id.bottomNavigationView1);

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
            bottomNavigationView1.setSelectedItemId(R.id.home);
        }

        bottomNavigationView1.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.home) {
                    loadFragment(new HomeFragment());
                    return true;
                } else if (itemId == R.id.articulo) {
                    loadFragment(new ArticuloFragment());
                    return true;
                } else if (itemId == R.id.categoria) {
                    loadFragment(new CategoriaFragment());
                    return true;
                } else if (itemId == R.id.persona) {
                    loadFragment(new PersonaFragment());
                    return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout3, fragment)
                .commit();
    }
}