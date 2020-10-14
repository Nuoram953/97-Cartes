package auger.antoine.a97cartes;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class Card {

    Random rand = new Random();
    int value,color;
    View current;


    public Card(View current){
        this.current = current;
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


}
