package it.itsar.todo_esame_callegari_gabriele;

import static it.itsar.todo_esame_callegari_gabriele.MainActivity.post;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Modifica extends AppCompatActivity {

    private Button salva;
    private Button indietro;
    private EditText testoNuovo;
    private TextView testoPassato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica);
        ListaTipo passato = (ListaTipo) getIntent().getSerializableExtra("passato");

        salva = findViewById(R.id.salva);
        indietro = findViewById(R.id.back);
        testoNuovo = findViewById(R.id.nuovoTesto);
        testoPassato = findViewById(R.id.vecchioTesto);

        testoPassato.setText("Vecchio testo:"+passato.getTitolo());

        indietro.setOnClickListener( v ->{
            finish();
        });

        salva.setOnClickListener(v -> {
            if(!testoNuovo.getText().toString().isEmpty()){
                passato.setTitolo(testoNuovo.getText().toString());
                new Database().updateDatabase(passato);
                for (int i = 0; i < post.size(); i++) {
                    if(post.get(i).getId() == passato.getId()){
                        post.set(i,passato);
                    }
                }
                finish();

            }else{
                testoNuovo.setHint("Inserisci qualcosa");
                testoNuovo.setHintTextColor(Color.RED);
            }
        });
    }
}