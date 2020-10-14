package auger.antoine.a97cartes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {


    ConstraintLayout tl_obj,tr_obj,bl_obj,br_obj;
    LinearLayout ll_cards;


    TextView numofCards,bl_text,br_text,tl_text,tr_text;
    Vector <Card> cards = new Vector<>();
    int numOfCards = 97;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_cards = findViewById(R.id.ll_card);

        numofCards = findViewById(R.id.title);

        tl_obj = findViewById(R.id.tl_obj);
        tr_obj = findViewById(R.id.tr_obj);
        bl_obj = findViewById(R.id.bl_obj);
        br_obj = findViewById(R.id.br_obj);

        tl_text = findViewById(R.id.tl_text);
        tr_text = findViewById(R.id.tr_text);
        bl_text = findViewById(R.id.bl_text);
        br_text = findViewById(R.id.br_text);



        Ecouteur ec = new Ecouteur();

        //Attribution des ecouteurs pour les cards
        for (int i = 0; i < ll_cards.getChildCount(); i++) {
            if (ll_cards.getChildAt(i) instanceof ConstraintLayout) {
                ConstraintLayout ly = (ConstraintLayout) ll_cards.getChildAt(i);
                ly.setOnDragListener(ec);
                for (int c =0;c<ly.getChildCount();c++){
                    if(ly.getChildAt(c) instanceof TextView){
                        ly.getChildAt(c).setOnTouchListener(ec);
                        cards.add(new Card(ly.getChildAt(c)));
                        numOfCards--;
                    }

                }
            }
        }

        tl_obj.setOnDragListener(ec);
        tr_obj.setOnDragListener(ec);
        bl_obj.setOnDragListener(ec);
        br_obj.setOnDragListener(ec);


        //Affichage du nombre de cartes de base.
        String text = String.valueOf(numOfCards)+" cartes restantes";
        numofCards.setText(text);

    }

    private class Ecouteur implements View.OnTouchListener, View.OnDragListener{
        Drawable normalShape = ContextCompat.getDrawable(MainActivity.this, R.drawable.background_card_mid);

        @Override
        public boolean onDrag(View conteneur, DragEvent dragEvent) {
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //v.setBackgroundDrawable(normalShape);

                    break;
                case DragEvent.ACTION_DRAG_EXITED:


                    break;
                case DragEvent.ACTION_DROP:

                    View card = (View) dragEvent.getLocalState();
                    ConstraintLayout parent = (ConstraintLayout) card.getParent();
                    parent.removeView(card);

                    ConstraintLayout container = (ConstraintLayout) conteneur;
                    container.addView(card);


                    TextView oneCard = (TextView) card;

                    int cardValue = Integer.parseInt(String.valueOf(oneCard.getText()));
                    int objValue = Integer.parseInt(String.valueOf(bl_text.getText()));

                    card.setVisibility(View.INVISIBLE);
                   if((container == bl_obj||container==br_obj) && cardValue>objValue){
                       bl_text.setText(String.valueOf(oneCard.getText()));
                   }
                   else if (container == tl_obj||container==tr_obj){
                       tl_text.setText(String.valueOf(oneCard.getText()));
                   }
                   else if (container == tr_obj){
                       tr_text.setText(String.valueOf(oneCard.getText()));
                   }
                   else{
                       card.setVisibility(View.VISIBLE);
                       container.removeView(card);
                       parent.addView(card);
                   }



                    break;
                case DragEvent.ACTION_DRAG_ENDED:


                default:
                    break;
            }
            return true;
        }


        @Override
        public boolean onTouch(View card, MotionEvent motionEvent) {


                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(card);
                card.startDrag(null, shadowBuilder, card, 0);
                card.setVisibility(View.INVISIBLE);
                return true;






        }

    }



}