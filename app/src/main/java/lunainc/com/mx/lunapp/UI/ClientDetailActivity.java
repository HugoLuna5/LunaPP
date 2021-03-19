package lunainc.com.mx.lunapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import lunainc.com.mx.lunapp.Holder.CardHolder;
import lunainc.com.mx.lunapp.Holder.ClientHolder;
import lunainc.com.mx.lunapp.Model.Card;
import lunainc.com.mx.lunapp.Model.Client;
import lunainc.com.mx.lunapp.R;

public class ClientDetailActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.actionCreateClient)
    FloatingActionButton actionCreateClient;

    private String name;
    private String uuid;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);
        ButterKnife.bind(this);

        initVars();
    }

    private void initVars() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        name = getIntent().getStringExtra("name");
        uuid = getIntent().getStringExtra("uuid");
        firebaseFirestore = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
        events();
    }

    private void setData() {
        setTitle(name);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadData();
    }

    private void events() {
        actionCreateClient.setOnClickListener(v -> {

            Map<String, Object> card = new HashMap<>();

            card.put("client_uuid", uuid);
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            card.put("date", currentDate);
            String key = firebaseFirestore.collection("clients").document().getId();
            card.put("uuid", key);
            card.put("size", 0);

            firebaseFirestore.collection("cards")
                    .document(key)
                    .set(card)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toasty.success(ClientDetailActivity.this, "¡Tarjeta agregada con exito!", Toast.LENGTH_SHORT, true).show();

                        }
                    })
                    .addOnFailureListener(e -> Toasty.error(ClientDetailActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT, true).show());






        });
    }


    public void loadData(){
        Query query = firebaseFirestore.collection("cards").orderBy("date", Query.Direction.DESCENDING)
                .whereEqualTo("client_uuid", uuid);

        FirestoreRecyclerOptions<Card> recyclerOptions = new FirestoreRecyclerOptions.Builder<Card>().
                setQuery(query, Card.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Card, CardHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(CardHolder holder, int position, final Card card) {

            }

            @Override
            public CardHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.item_card, group, false);

                return new CardHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            goToHome();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        goToHome();
    }

    private void goToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}