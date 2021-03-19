package lunainc.com.mx.lunapp.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.com.mx.lunapp.R;

public class ClientHolder extends RecyclerView.ViewHolder{


    public @BindView(R.id.nameClient)
    TextView nameClient;

    public @BindView(R.id.phoneClient)
    TextView phoneClient;


    public ClientHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
