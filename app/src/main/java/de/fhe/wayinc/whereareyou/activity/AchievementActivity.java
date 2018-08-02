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
import de.fhe.wayinc.whereareyou.util.StatsHelper;

public class AchievementActivity extends AppCompatActivity {

    private List<TextView> achievements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        TextView title = findViewById(R.id.achievements_title);
        FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", title);


        achievements.add((TextView) findViewById(R.id.achievements_text_image_name_01));
        achievements.add((TextView) findViewById(R.id.achievements_text_image_desc_01));
        achievements.add((TextView) findViewById(R.id.achievements_text_image_name_02));
        achievements.add((TextView) findViewById(R.id.achievements_text_image_desc_02));
        achievements.add((TextView) findViewById(R.id.achievements_text_image_name_03));
        achievements.add((TextView) findViewById(R.id.achievements_text_image_desc_03));
        achievements.add((TextView) findViewById(R.id.achievements_text_city_name_01));
        achievements.add((TextView) findViewById(R.id.achievements_text_city_desc_01));
        achievements.add((TextView) findViewById(R.id.achievements_text_city_name_02));
        achievements.add((TextView) findViewById(R.id.achievements_text_city_desc_02));
        achievements.add((TextView) findViewById(R.id.achievements_text_city_name_03));
        achievements.add((TextView) findViewById(R.id.achievements_text_city_desc_03));
        achievements.add((TextView) findViewById(R.id.achievements_text_mystery_name));
        achievements.add((TextView) findViewById(R.id.achievements_text_mystery_desc));
        for (TextView view : achievements) {
            FontHelper.setExternalFont(this, "fonts/BebasNeue-Regular.ttf", view);
            view.setTextColor(getResources().getColor(R.color.col_grey));
            view.setAlpha(0.6f);
        }

        int imageCount = StatsHelper.getImageCount(this);
        int cityCount = StatsHelper.getCityCount(this);

        if (imageCount >= 1) {
            achieveAMilestone(0);
        }

        if (imageCount >= 50) {
            achieveAMilestone(2);
        }

        if (imageCount >= 200) {
            achieveAMilestone(4);
        }

        if (cityCount >= 1) {
            achieveAMilestone(6);
        }

        if (cityCount >= 10) {
            achieveAMilestone(8);
        }

        if (cityCount >= 50) {
            achieveAMilestone(10);
        }

        if (StatsHelper.mysteryAchievementUnlocked(this)) {
            achieveAMilestone(12);
        }

        // icons

    }

    public void achieveAMilestone(int ach) {
        for (int i = ach; i <= ach+1; i++) {
            achievements.get(i).setAlpha(1.0f);
            achievements.get(i).setTextColor(getResources().getColor(R.color.col_white));
        }
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
