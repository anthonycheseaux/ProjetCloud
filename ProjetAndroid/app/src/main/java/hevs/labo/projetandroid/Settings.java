package hevs.labo.projetandroid;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        String lang = conf.locale.getDisplayLanguage();
        lang = lang.substring(0,2);

        RadioButton radioButton;

        if(lang.equals("fr"))
        {
            radioButton = (RadioButton) findViewById(R.id.rb_settings_french);
            radioButton.setChecked(true);
        }
        else
        {
            radioButton = (RadioButton) findViewById(R.id.rb_settings_english);
            radioButton.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.accueil_menu:
                Intent intenthome = new Intent(this, MainActivity.class);
                startActivity(intenthome);
                return true;

            case R.id.artist_menu:
                Intent intentartist = new Intent(this, List_artist.class);
                startActivity(intentartist);
                return true;

            case R.id.list_artwork_menu:
                Intent intentartwork = new Intent(this, List_artwork.class);
                startActivity(intentartwork);
                return true;

            case R.id.exposition_menu:
                Intent intentexhibition = new Intent(this, List_exhibition.class);
                startActivity(intentexhibition);
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    public void show_list_room(View view){
        Intent intent = new Intent(this, List_room.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_settings_english:
                if (checked)
                    setLocale("en");
                    break;

            case R.id.rb_settings_french:
                if (checked)
                    setLocale("fr");
                    break;
        }
    }

    public void save_language(View view){
        Intent intent = new Intent(this, List_room.class);
        startActivity(intent);
    }

    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Settings.class);
        startActivity(refresh);
    }
}
