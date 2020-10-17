package auger.antoine.a97cartes;

import android.nfc.Tag;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Vector;

public class GameLogic {

    Vector<Card> everyCards;
    Vector<Integer> obj;
    Vector<Integer> usedCard;
    int numOfCards,totalScore,missingCard;
    boolean cardValid;
    ConstraintLayout cards;
    TextView score,cardLeft;
    Chronometer chronometer;
    TextView objValue;


    public GameLogic(ConstraintLayout cards, TextView score, TextView cardLeft, Chronometer chronometer){
       this.everyCards = new Vector<>();
       this.usedCard = new Vector<>();
        this.obj = new Vector<>();
       this.numOfCards = 97;
       this.totalScore = 0;
       this.missingCard = 0;
       this.cardValid = false;
       this.cards = cards;
       this.score = score;
       this.cardLeft = cardLeft;

       this.chronometer = chronometer;
       this.chronometer.start();

       this.objValue = null;


       this.obj.add(R.id.bl_obj);
       this.obj.add(R.id.br_obj);
       this.obj.add(R.id.tl_obj);
       this.obj.add(R.id.tr_obj);



    }



    public void cardValidation(ConstraintLayout container, int cardValue, View card){


        TextView temp= (TextView) container.getChildAt(0);

        this.objValue = temp;

        //card.setVisibility(View.INVISIBLE);


        int objValue=Integer.parseInt(String.valueOf(temp.getText()));

        if(cardValue>objValue && isBottom(container)&& this.obj.contains(container.getId())){
            System.out.println("if 1");
            temp.setText(String.valueOf(cardValue));
            this.missingCard++;
            this.cardValid = true;
            container.removeView(card);
        }
        else if(objValue>cardValue && !isBottom(container) && this.obj.contains(container.getId())){
            System.out.println("if 2");
            temp.setText(String.valueOf(cardValue));
            this.missingCard++;
            this.cardValid = true;
            container.removeView(card);
        }
        else{

            if(container.getId() != R.id.cards_obj){
                System.out.println("if 3");

                this.cardValid=false;
                card.setVisibility(View.VISIBLE);
                container.removeView(card);
                this.cards.addView(card);
            }


        }




    }

    public void missingCard(View card){

        TextView oneCard = (TextView) card;
        int cardValue = Integer.parseInt(String.valueOf(oneCard.getText()));


        if(this.missingCard==1 && this.cardValid){

            //card.setVisibility(View.INVISIBLE);
            for(int i=0;i<this.everyCards.size();i++){
                if(this.everyCards.get(i).getValue() == cardValue){
                    this.usedCard.add(i);
                }
            }
            this.cards.addView(card);
            try{
                updateScore(this.usedCard.get(0));
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Array out of bound");
            }


        }

        if(this.missingCard == 2){

            card.setVisibility(View.INVISIBLE);
            for(int i=0;i<this.everyCards.size();i++){
                if(this.everyCards.get(i).getValue() == cardValue){
                    this.usedCard.add(i);
                }
            }

            this.cards.addView(card);
            updateScore(this.usedCard.get(1));

            for (Integer missingcard:this.usedCard) {
                this.everyCards.get(missingcard).newValues();
            }

            this.numOfCards-=2;
            this.cardLeft.setText(String.valueOf(this.numOfCards+ " cartes restantes"));

            this.usedCard.removeAllElements();
            this.missingCard = 0;

            for (Card c:this.everyCards) {
                c.changeVisibility();
            }
        }
    }


    public boolean isBottom(ConstraintLayout container){

        if(container.getId() == R.id.bl_obj || container.getId() == R.id.br_obj ){
            System.out.println("isBottom = YES");
            return true;
        }
        else{
            return false;
        }
    }


    public void updateScore(int index){



        totalScore += this.everyCards.get(index).score(this.numOfCards,Integer.parseInt(String.valueOf(this.objValue.getText())),String.valueOf(this.chronometer.getText()));
        this.score.setText(String.valueOf(totalScore));
    }


    public void endGame(){

    }


}
