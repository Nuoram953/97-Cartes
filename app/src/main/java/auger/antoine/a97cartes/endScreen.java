package auger.antoine.a97cartes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class endScreen extends AppCompatActivity {


    DataBaseHelper instance;
    TextView finalscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);


        finalscore = findViewById(R.id.c);



        instance = DataBaseHelper.getInstance(this);

        Intent intent = getIntent();

        String test = intent.getStringExtra("test");

        finalscore.setText(test);

        DataBaseHelper.getInstance(this).addScore(Integer.parseInt(test));




        //DataBaseHelper.getInstance(this).addScore(totalScore);
    }
}