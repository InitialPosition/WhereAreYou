package de.fhe.wayinc.whereareyou.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.util.FontHelper;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        TextView credits = findViewById(R.id.text_credits);
        credits.setText(R.string.str_credits);

        FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", credits);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_simple_back, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_edit_back_backOnly:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
