package de.fhe.wayinc.whereareyou.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.fhe.wayinc.whereareyou.R;
import de.fhe.wayinc.whereareyou.util.FontHelper;

public class AchievementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        TextView title = findViewById(R.id.achievements_title);
        FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", title);

        List<TextView> achievements = new ArrayList<>();
        achievements.add((TextView) findViewById(R.id.achievements_text_image_name_01));
        achievements.add((TextView) findViewById(R.id.achievements_text_image_desc_01));
        for (TextView view : achievements) {
            FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", view);
        }

        // icons

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
