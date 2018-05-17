package de.fhe.wayinc.whereareyou;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import de.fhe.wayinc.whereareyou.utils.FontHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view = findViewById(R.id.textView);
        FontHelper.loadExternalTypefaceIntoTextView("fonts/BebasNeue-Regular.ttf", view, this);
    }
}
