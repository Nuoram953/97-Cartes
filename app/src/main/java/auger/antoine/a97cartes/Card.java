package auger.antoine.a97cartes;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;

public class Card {

    Random rand = new Random();
    int value,color;
    View current;


    public Card(View current){
        this.current = current;
        newValues();
    }

    public int getValue(){ return this.value; }

    public void changeVisibility(){
        this.current.setVisibility(View.VISIBLE);
    }

    public void newValues(){
        this.value = rand.nextInt(100);

        if (this.value <= 100){
            this.color = R.drawable.background_card_high;
            if(this.value<=75){
                this.color = R.drawable.background_card_mid;
                if(this.value <25){
                    this.color = R.drawable.background_card_low;
                }
            }
        }

        //Changement du texte
        TextView text = (TextView) this.current;
        text.setText(String.valueOf(this.value));

        //Changement de la couleur
        Drawable drawable = this.current.getResources().getDrawable(this.color);
        this.current.setBackground(drawable);
    }

    public int score(int numOfCard, int objValue, String chronometer){
        int score=0;

        //1.temps


        //2. le nombre de cartes restantes
        //Entre 1-10 selon 10 - la dizaine du nombre de cartes restante. Ex. 86 = 2 points, 54 = 5 points etc.
        double test = Math.floor((double)numOfCard/10);

        for (double i=0.0;i<10.00;i++){
            if(test == i) score += 10 - (int) i;
        }

        //3.la proximité
        //Entre 1 et 10
        if(Math.abs(objValue-this.value)<5){
            score +=10;
        }
        else if(Math.abs(objValue-this.value)<15){
            score +=5;
        }
        else if(Math.abs(objValue-this.value)<25){
            score +=3;
        }
        else{
            score +=1;
        }


        return score;



    }

    public boolean possiblePlayLeft(Vector<String> obj){
        return this.value > Integer.parseInt(obj.get(0)) || this.value > Integer.parseInt(obj.get(1)) || this.value < Integer.parseInt(obj.get(2)) || this.value < Integer.parseInt(obj.get(3));
    }



}
