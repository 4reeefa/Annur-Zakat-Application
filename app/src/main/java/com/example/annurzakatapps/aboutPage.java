package com.example.annurzakatapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class aboutPage extends AppCompatActivity {

    TextView linkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        linkTextView = findViewById(R.id.textView3);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home :

                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);

                break;


            case R.id.aboutus :

                //Intent intent = new Intent(this,aboutPage.class);
                //startActivity(intent);

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}