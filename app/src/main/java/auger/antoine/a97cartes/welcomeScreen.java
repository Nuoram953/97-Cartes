package auger.antoine.a97cartes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcomeScreen extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        start = findViewById(R.id.start);

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