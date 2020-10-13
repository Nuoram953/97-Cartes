package auger.antoine.a97cartes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll_cards;
    TextView numofCards;
    Vector <Card> cards = new Vector<>();
    int numOfCards = 97;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_cards = findViewById(R.id.ll_card);

        numofCards = findViewById(R.id.title);


        Ecouteur ec = new Ecouteur();

        //Attribution des ecouteurs pour les cards
        for (int i = 0; i < ll_cards.getChildCount(); i++) {
            if (ll_cards.getChildAt(i) instanceof LinearLayout) {
                LinearLayout ly = (LinearLayout) ll_cards.getChildAt(i);
                ly.setOnDragListener(ec);
                for (int c =0;c<ly.getChildCount();c++){
                    ((TextView) ly.getChildAt(c)).setOnTouchListener(ec);
                    cards.add(new Card(ly.getChildAt(c)));
                    numOfCards--;
                }
            }
        }

        numofCards.setText(String.valueOf(numOfCards)+" cartes restantes");

    }

    private class Ecouteur implements View.OnTouchListener, View.OnDragListener{

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            return false;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }



}