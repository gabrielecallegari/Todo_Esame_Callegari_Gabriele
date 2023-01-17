package it.itsar.todo_esame_callegari_gabriele;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<ListaTipo> post = new ArrayList<>();

    private boolean finish = false;
    private RecyclerView recyclerView;
    private EditText testoInput;
    private Button crea;
    private ProgressBar pr;
    private Button modifica;
    private boolean staiModificando = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView.OnItemTouchListener listener = new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                ListaTipo postClic = post.get(position);
                Intent intent = new Intent(MainActivity.this, Modifica.class);
                intent.putExtra("passato",postClic);
                launcher.launch(intent);
            }

            @Override public void onLongItemClick(View view, int position) {

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modifica = findViewById(R.id.modifica);
        crea = findViewById(R.id.crea);
        testoInput = findViewById(R.id.inputtext);
        recyclerView = findViewById(R.id.miaRecycler);
        pr = findViewById(R.id.progressBar);
        modifica = findViewById(R.id.modifica);

        setVisibility();
        setLista();

        modifica.setOnClickListener(v->{

            staiModificando = !staiModificando;
            if (staiModificando == true){
                modifica.setText("fine modifica");

                recyclerView.addOnItemTouchListener(
                        listener
                );

            }else{
                modifica.setText("modifica");
                recyclerView.removeOnItemTouchListener(listener);
            }

        });




        crea.setOnClickListener( v -> {
            String passato = testoInput.getText().toString();
            if(!passato.isEmpty()) {
                boolean variabile = false;
                String lettere = "abcdefghijklmnopqrstuvwxyz1234567890";
                String splitted[] = lettere.split("");
                String id = "";
                Random random = new Random();
                for (int i = 0; i < 30; i++) {
                    id = id + splitted[random.nextInt(lettere.length())];
                }
                ListaTipo ls = new ListaTipo(passato, variabile, id);
                post.add(ls);
                new Database().salvaSuDatabase(ls);
                ListaAdapter adapter = new ListaAdapter(post);
                recyclerView.setAdapter(adapter);
                testoInput.setText("");
            }else{
                testoInput.setHint("Inserisci il titolo");
                testoInput.setHintTextColor(Color.RED);
            }
        });


    }


    public void setLista(){
        new Database().leggiDaDatabase(new Database.miaInterfaccia() {
            @Override
            public void onSuccess() {
                ListaAdapter adapter = new ListaAdapter(post);
                recyclerView.setAdapter(adapter);
                finish = true;
                setVisibility();
            }

            @Override
            public void onFail() {

            }
        });
    }

    private void setVisibility(){
        crea.setVisibility(finish == true? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(finish == true? View.VISIBLE : View.GONE);
        testoInput.setVisibility(finish == true? View.VISIBLE : View.GONE);
        modifica.setVisibility(finish == true? View.VISIBLE : View.GONE);
        pr.setVisibility(finish == true? View.GONE : View.VISIBLE);
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                setLista();
            });
}