package com.example.easyjob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    private Button btnSearch;
    private EditText txtSearch;
    public static final String EXTRA_TEXT = "com.example.easyjob.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
    }

    // open main activity
    public void openMainActivity(){
        txtSearch = findViewById(R.id.txtSearch);
        String txtSearchStr = txtSearch.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        // pass in variable to MainActivity
        intent.putExtra(EXTRA_TEXT, txtSearchStr);
        startActivity(intent);
    }
}