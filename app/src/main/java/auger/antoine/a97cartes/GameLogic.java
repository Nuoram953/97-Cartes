package auger.antoine.a97cartes;

import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Arrays;
import java.util.Vector;

public class GameLogic {

    Vector<Card> everyCards; // Contient les 8 objects cartes
    Vector<Integer> obj;
    Vector<Integer> usedCard;
    Vector<Integer> valuesCard;
    Vector<Arrays> timestamp;

    int numOfCards,totalScore,missingCard;
    boolean cardValid;
    ConstraintLayout cards;
    TextView score,cardLeft;
    Chronometer chronometer;
    TextView objValue;


    public GameLogic(ConstraintLayout cards, TextView score, TextView cardLeft, Chronometer chronometer){
       this.everyCards = new Vector<>();
       this.usedCard = new Vector<>();
       this.valuesCard = new Vector<>();
       this.obj = new Vector<>();
       this.timestamp = new Vector<>();
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





    //Dans le cas ou nous avons des doublons, la carte en question est randomize à nouveau.
    public void getValues(){
        for (Card card:this.everyCards) {
            card.newValues();
            while(this.valuesCard.contains(card.getValue())){
                card.newValues();
            }
            this.valuesCard.add(card.getValue());

        }
    }


    //Vérifie si la carte est valide et que nous pouvons la placer sur le container choisi.
    public void cardValidation(ConstraintLayout container, int cardValue, View card){

        TextView temp= (TextView) container.getChildAt(0);

        this.objValue = temp;

        String operation = cardValidationForObj(container,cardValue,card);

        if(!operation.equals("noValid")){
            temp.setText(String.valueOf(cardValue));
            this.missingCard++;
            this.cardValid = true;
            this.numOfCards--;
            card.setVisibility(View.INVISIBLE);

        }
        else{
                this.cardValid=false;
                card.setVisibility(View.VISIBLE);
        }

        updateNumOfCard(this.numOfCards);

    }

    public void missingCard(View card){

        updateValues();

        TextView oneCard = (TextView) card;
        int cardValue = Integer.parseInt(String.valueOf(oneCard.getText()));

        if(this.missingCard==1 && this.cardValid){

            for(int i=0;i<this.everyCards.size();i++){
                if(this.everyCards.get(i).getValue() == cardValue){
                    this.usedCard.add(i);
                }
            }

            try{
                updateScore(this.usedCard.get(0));
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Array out of bound");
            }
        }

        if(this.missingCard == 2){

            for(int i=0;i<this.everyCards.size();i++){
                if(this.everyCards.get(i).getValue() == cardValue){
                    this.usedCard.add(i);
                }
            }

            updateScore(this.usedCard.get(1));

            for (Integer missingcard:this.usedCard) {
                this.everyCards.get(missingcard).newValues();
                if (this.valuesCard.contains(this.everyCards.get(missingcard).getValue())){
                    this.everyCards.get(missingcard).newValues();
                }
            }


            this.usedCard.removeAllElements();
            this.missingCard = 0;

            for (Card c:this.everyCards) {
                c.changeVisibility();
            }
        }
    }


    //Vérifie si le container choisi est située dans le bas des objectifs.
    public boolean isBottom(ConstraintLayout container){

        if(container.getId() == R.id.bl_obj || container.getId() == R.id.br_obj ){
            System.out.println("isBottom = YES");
            return true;
        }
        else{
            return false;
        }
    }



    //Vérification de fin de partie au niveau du jeu global
    public boolean possiblePlayLeft(Vector<String>obj){


        boolean notEqual=false;
        for(int i=0;i<this.everyCards.size()-1;i++){
            if(!this.usedCard.contains(i)){
                if(this.everyCards.get(i).possiblePlayLeft(obj)){
                    notEqual = true;
                    break;
                }
            }
        }

        return notEqual && this.numOfCards != 0;
    }


    public String cardValidationForObj(ConstraintLayout container, int cardValue, View card){

        TextView temp= (TextView) container.getChildAt(0);
        this.objValue = temp;
        int objValue=Integer.parseInt(String.valueOf(temp.getText()));

        if((cardValue>objValue && isBottom(container) || objValue-10 == cardValue) && this.obj.contains(container.getId())){
            return "botValid";
        }
        else if ((objValue > cardValue && !isBottom(container)  || objValue+10 == cardValue) && this.obj.contains(container.getId())){
            return "topValid";
        }
        else{
            return "noValid";
        }

    }


    //Update toutes les valeurs des cartes dans le vector "valuesCard"
    public void updateValues(){
        this.valuesCard.removeAllElements();

        for (Card card:this.everyCards) {
            this.valuesCard.add(card.getValue());
        }
    }

    public void updateScore(int index){
        totalScore += this.everyCards.get(index).score(this.numOfCards,Integer.parseInt(String.valueOf(this.objValue.getText())),String.valueOf(this.chronometer.getText()));
        this.score.setText(String.valueOf(totalScore + " pts"));
    }

    public void updateNumOfCard(int noc){
        this.cardLeft.setText(String.valueOf(noc+ " cartes restantes"));
    }


}