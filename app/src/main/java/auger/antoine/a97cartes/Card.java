package auger.antoine.a97cartes;

import android.content.res.Resources;
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



        TextView text = (TextView) current;
        text.setText(String.valueOf(this.value));

        Drawable drawable = current.getResources().getDrawable(this.color);
        current.setBackground(drawable);

    }


}
