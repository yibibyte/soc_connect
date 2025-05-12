package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.data.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.data.PostgreSQLHelper;
//import com.example.myapplication.data.FirebaseHelper;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PostgreSQLHelper postgreSQLHelper;
//    private FirebaseHelper firebaseHelper;

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
//        Log.d("MyApp: MainActivity", "message_tag:MainActivity");
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize helpers
        postgreSQLHelper = new PostgreSQLHelper();
//        firebaseHelper = new FirebaseHelper();

        // Example usage for PostgreSQL
        Item newItem = new Item(0, "Test Item", "This is a test description");
        postgreSQLHelper.createItem(newItem);

        postgreSQLHelper.getAllItems(new PostgreSQLHelper.DatabaseCallback<List<Item>>() {
            @Override
            public void onSuccess(List<Item> result) {
                runOnUiThread(() -> {
                    // Update UI with items
                    Toast.makeText(MainActivity.this,
                            "Got " + result.size() + " items from PostgreSQL",
                            Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this,
                            "Error: " + error,
                            Toast.LENGTH_SHORT).show();
                });
            }
        });

        // Example usage for Firebase
        Item firebaseItem = new Item();
        firebaseItem.setName("Firebase Item");
        firebaseItem.setDescription("Firebase test description");
//        firebaseHelper.createItem(firebaseItem);

//        firebaseHelper.getAllItems(new FirebaseHelper.DatabaseCallback<List<Item>>() {
//            @Override
//            public void onSuccess(List<Item> result) {
//                runOnUiThread(() -> {
//                    Toast.makeText(MainActivity.this,
//                            "Got " + result.size() + " items from Firebase",
//                            Toast.LENGTH_SHORT).show();
//                });
//            }
//
//            @Override
//            public void onFailure(String error) {
//                runOnUiThread(() -> {
//                    Toast.makeText(MainActivity.this,
//                            "Error: " + error,
//                            Toast.LENGTH_SHORT).show();
//                });
//            }
//        }
//        );
    }
}