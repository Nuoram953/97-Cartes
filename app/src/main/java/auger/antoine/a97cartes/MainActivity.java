package auger.antoine.a97cartes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    //TODO: Base de donnée
    //TODO: Écran de fin de partie
    //TODO: Afficher le meilleur score dans le menu principal
    //TODO: Possibilité de finir la partie et de conserver le score?


    ConstraintLayout tl_obj,tr_obj,bl_obj,br_obj,cards;
    LinearLayout ll_cards;

    GameLogic gl;


    TextView numofCards,bl_text,br_text,tl_text,tr_text,score;
    Chronometer chronometer;
    //Vector <Card> everyCards = new Vector<>();

    int missingCards = 0;
    int totalScore=0;
    boolean cardValid = false;

    int firstCard,secondCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_cards = findViewById(R.id.ll_card);
        cards = findViewById(R.id.cards);

        chronometer = findViewById(R.id.chronometer);


        numofCards = findViewById(R.id.title);
        score = findViewById(R.id.score);

        gl = new GameLogic(cards,score,numofCards,chronometer);



        tl_obj = findViewById(R.id.tl_obj);
        tr_obj = findViewById(R.id.tr_obj);
        bl_obj = findViewById(R.id.bl_obj);
        br_obj = findViewById(R.id.br_obj);

        tl_text = findViewById(R.id.tl_text);
        tr_text = findViewById(R.id.tr_text);
        bl_text = findViewById(R.id.bl_text);
        br_text = findViewById(R.id.br_text);


        EcouteurTimer et = new EcouteurTimer();
        Ecouteur ec = new Ecouteur();

        chronometer.setOnChronometerTickListener(et);

        //Attribution des ecouteurs pour les cards
        for (int i = 0; i < ll_cards.getChildCount(); i++) {
            if (ll_cards.getChildAt(i) instanceof ConstraintLayout) {
                ConstraintLayout ly = (ConstraintLayout) ll_cards.getChildAt(i);
                ly.setOnDragListener(ec);
                for (int c =0;c<ly.getChildCount();c++){
                    if(ly.getChildAt(c) instanceof TextView){
                        ly.getChildAt(c).setOnTouchListener(ec);
                        gl.everyCards.add(new Card(ly.getChildAt(c)));
                        gl.numOfCards--;
                    }
                }
            }
        }

        tl_obj.setOnDragListener(ec);
        tr_obj.setOnDragListener(ec);
        bl_obj.setOnDragListener(ec);
        br_obj.setOnDragListener(ec);


        //Affichage du nombre de cartes de base.
        String text = String.valueOf(gl.numOfCards)+" cartes restantes";
        numofCards.setText(text);

    }


    private class EcouteurTimer implements Chronometer.OnChronometerTickListener{

        @Override
        public void onChronometerTick(Chronometer chronometer) {

            if (String.valueOf(chronometer.getText()).equals("01:00")){
                Context context = getApplicationContext();
                CharSequence text = "Vous pouvez le faire!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        }
    }




    private class Ecouteur implements View.OnTouchListener, View.OnDragListener{

        @Override
        public boolean onDrag(View conteneur, DragEvent dragEvent) {


            View card = (View) dragEvent.getLocalState();
            ConstraintLayout parent = (ConstraintLayout) card.getParent();
            parent.removeView(card);

            ConstraintLayout container = (ConstraintLayout) conteneur;
            container.addView(card);


            TextView oneCard = (TextView) card;
            int cardValue = Integer.parseInt(String.valueOf(oneCard.getText()));


            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:



                    System.out.println(container);


                    gl.cardValidation(container,cardValue,card);




                        //cards.addView(card);
                        //container.removeView(card);




                    gl.missingCard(card);



                    //On vérifie si il est encore possible de jouer
                    Vector <String> obj = new Vector<>();
                    obj.add(String.valueOf(bl_text.getText()));
                    obj.add(String.valueOf(br_text.getText()));
                    obj.add(String.valueOf(tl_text.getText()));
                    obj.add(String.valueOf(tr_text.getText()));



                    //Vérification de fin de partie.
                    boolean notEqual=false;
                    for(int i=0;i<gl.everyCards.size()-1;i++){
                        if(i != firstCard || i !=secondCard){
                            if(gl.everyCards.get(i).possiblePlayLeft(obj)){
                                notEqual = true;
                                break;
                            }
                        }
                    }

                    if(!notEqual || gl.numOfCards == 0){

                        endGame(conteneur);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    if(!gl.cardValid){
                        container.removeView(card);
                        card.setVisibility(View.VISIBLE);
                        cards.addView(card);
                    }










                    break;
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

    public void endGame(View view){

        Intent intent = new Intent(this,endScreen.class);
        String score = String.valueOf(totalScore);
        intent.putExtra("test",score);
        startActivity(intent);
    }



}