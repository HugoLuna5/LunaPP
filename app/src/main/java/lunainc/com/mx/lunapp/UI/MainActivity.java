package lunainc.com.mx.lunapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;


import butterknife.BindView;
import butterknife.ButterKnife;
import lunainc.com.mx.lunapp.Holder.ClientHolder;
import lunainc.com.mx.lunapp.Model.Client;
import lunainc.com.mx.lunapp.R;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.actionCreateClient)
    FloatingActionButton actionCreateClient;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVars();
    }

    private void initVars() {

        firebaseFirestore = FirebaseFirestore.getInstance();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
loadData();
    }

    private void loadData() {
        Query query = firebaseFirestore.collection("clients").orderBy("name", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<Client> recyclerOptions = new FirestoreRecyclerOptions.Builder<Client>().
                setQuery(query, Client.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Client, ClientHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(ClientHolder holder, int position, final Client client) {

                holder.nameClient.setText(client.getName());
                holder.phoneClient.setText(client.getPhone());


                holder.itemView.setOnClickListener( v -> {

                    Intent intent = new Intent(MainActivity.this, ClientDetailActivity.class);
                    intent.putExtra("uuid", client.getUuid());
                    intent.putExtra("name", client.getName());
                    intent.putExtra("phone", client.getPhone());
                    startActivity(intent);

                });

            }

            @Override
            public ClientHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.item_client, group, false);

                return new ClientHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };


        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }




    @Override
    protected void onStart() {
        super.onStart();
        events();

    }

    private void events() {
        actionCreateClient.setOnClickListener( v -> {
            Intent intent = new Intent(this, CreateClientActivity.class);
            startActivity(intent);
            finish();
        });
    }
}