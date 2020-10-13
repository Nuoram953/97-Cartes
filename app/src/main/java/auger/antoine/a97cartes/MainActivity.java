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
    Vector <Card> cards = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_cards = findViewById(R.id.ll_card);


        Ecouteur ec = new Ecouteur();

        //Attribution des ecouteurs pour les cards
        for (int i = 0; i < ll_cards.getChildCount(); i++) {
            if (ll_cards.getChildAt(i) instanceof LinearLayout) {
                LinearLayout ly = (LinearLayout) ll_cards.getChildAt(i);
                ly.setOnDragListener(ec);
                ((TextView) ly.getChildAt(0)).setOnTouchListener(ec);
                cards.add(new Card(ly.getChildAt(0)));
                ((TextView) ly.getChildAt(1)).setOnTouchListener(ec);
                cards.add(new Card(ly.getChildAt(1)));

            }
        }

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