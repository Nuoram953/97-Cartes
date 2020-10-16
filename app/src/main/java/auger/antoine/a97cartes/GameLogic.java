package auger.antoine.a97cartes;

import android.nfc.Tag;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Vector;

public class GameLogic {

    Vector<Card> everyCards;
    Vector<Integer> usedCard;
    int numOfCards,totalScore,missingCard;
    boolean cardValid;

    public GameLogic(){
       this.everyCards = new Vector<>();
       this.usedCard = new Vector<>();
       this.numOfCards = 97;
       this.totalScore = 0;
       this.missingCard = 0;
    }

    public void startGame(){

    }

    public void cardValidation(ConstraintLayout container, int cardValue, View card){

        TextView temp= (TextView) container.getChildAt(0);
        ConstraintLayout parent = (ConstraintLayout) card.getParent();

        card.setVisibility(View.INVISIBLE);


        int objValue=Integer.parseInt(String.valueOf(temp.getText()));

        if(cardValue>objValue && isBottom(container)){
            temp.setText(String.valueOf(cardValue));
            this.missingCard++;
            cardValid = true;
            container.removeView(card);
        }
        else if(objValue>cardValue && !isBottom(container)){
            temp.setText(String.valueOf(cardValue));
            this.missingCard++;
            cardValid = true;

            container.removeView(card);
        }
        else{
            card.setVisibility(View.VISIBLE);
            container.removeView(card);
            parent.addView(card);
            cardValid=false;
        }

    }

    public void missingCard(View card){

        TextView oneCard = (TextView) card;
        int cardValue = Integer.parseInt(String.valueOf(oneCard.getText()));

        if(usedCard.size()==0 && cardValid|| usedCard.size()==1){
            card.setVisibility(View.INVISIBLE);
            for(int i=0;i<everyCards.size();i++){
                if(everyCards.get(i).getValue() == cardValue){
                    usedCard.add(i);
                }
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



    public void endGame(){

    }


}
