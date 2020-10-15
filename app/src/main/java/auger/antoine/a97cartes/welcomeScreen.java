package auger.antoine.a97cartes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class welcomeScreen extends AppCompatActivity {

    Button start;
    DataBaseHelper instance;
    TextView bestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        start = findViewById(R.id.start);
        bestScore = findViewById(R.id.bestScore);
        instance = DataBaseHelper.getInstance(this);

        bestScore.setText(DataBaseHelper.getInstance(this).returnLastElement());

        Ecouteur ec = new Ecouteur();

        start.setOnClickListener(ec);

    }

    public void startGame(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            startGame(view);
        }
    }
}