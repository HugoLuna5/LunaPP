package lunainc.com.mx.lunapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import lunainc.com.mx.lunapp.R;

public class CreateClientActivity extends AppCompatActivity {

    @BindView(R.id.inputName)
    TextInputEditText inputName;

    @BindView(R.id.inputPhone)
    TextInputEditText inputPhone;

    @BindView(R.id.actionCreate)
    ExtendedFloatingActionButton actionCreate;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.add_client));
        initVars();
    }

    private void initVars() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        events();
    }

    private void events() {
        actionCreate.setOnClickListener( v -> {
            String nameStr = inputName.getText().toString();
            String phoneStr = inputPhone.getText().toString();


            if ((nameStr.trim().length() > 0 && !nameStr.isEmpty()) && (phoneStr.trim().length() > 0 && !phoneStr.isEmpty())){
                Map<String, Object> client = new HashMap<>();
                client.put("name", nameStr);
                client.put("phone", phoneStr);
                String key = db.collection("clients").document().getId();
                client.put("uuid", key);

                db.collection("clients")
                        .document(key)
                        .set(client)
                        .addOnSuccessListener(documentReference -> {
                            Toasty.success(CreateClientActivity.this, "¡Cliente registrado con exito!", Toast.LENGTH_SHORT, true).show();
                            goToHome();
                        })
                .addOnFailureListener(e ->Toasty.error(CreateClientActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT, true).show());



            }



        });
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