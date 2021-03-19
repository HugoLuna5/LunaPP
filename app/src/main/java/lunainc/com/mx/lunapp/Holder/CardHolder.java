package lunainc.com.mx.lunapp.Holder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public class CardHolder extends RecyclerView.ViewHolder {


    public CardHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
