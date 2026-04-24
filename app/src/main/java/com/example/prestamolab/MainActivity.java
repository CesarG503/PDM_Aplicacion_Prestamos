package com.example.prestamolab;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.prestamolab.Database.appDataBase;
import com.example.prestamolab.entitys.Categoria;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView1;

    private appDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataBase = appDataBase.getINSTANCE(getApplicationContext());

        dataBase.databaseWriteExecutor.execute(()->{

            //dataBase.categoriaDao().insertar(new Categoria("Cargadores"));
            runOnUiThread(()->{
                Toast.makeText(this, "si se insertor", Toast.LENGTH_SHORT).show();
            });
        });


        bottomNavigationView1 = findViewById(R.id.bottomNavigationView1);


        bottomNavigationView1.setOnItemSelectedListener( item ->
         {
             item.getItemId();

             return false;
         });

        bottomNavigationView1.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                menuItem.setCheckable(true);
                if(menuItem.getItemId() == R.id.home)
                {
                    loadFragment(new HomeFragment());
                    return(true);
                }
                if(menuItem.getItemId() == R.id.articulo)
                {
                    loadFragment(new ArticuloFragment());
                    return(true);
                }
                if(menuItem.getItemId() == R.id.categoria)
                {
                    loadFragment(new CategoriaFragment());
                    return(true);
                }

                return false;
            }
        });
    }
    private void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.main, fragment).commit();
    }
}