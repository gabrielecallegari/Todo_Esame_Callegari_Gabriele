package it.itsar.todo_esame_callegari_gabriele;

import static it.itsar.todo_esame_callegari_gabriele.MainActivity.post;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    public interface miaInterfaccia{
        void onSuccess();
        void onFail();
    }
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("post");

    public void leggiDaDatabase(final miaInterfaccia callback){

        reference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                post.clear();
                for (QueryDocumentSnapshot document : task.getResult()){
                    ListaTipo passato = document.toObject(ListaTipo.class);
                    passato.setId(document.getId());
                    post.add(passato);
                }
                callback.onSuccess();
            }else{
                callback.onFail();
            }
        });

    }

    public void salvaSuDatabase(ListaTipo listaTipo){
        reference.add(listaTipo);
    }

    public void updateDatabase(ListaTipo ls){
        Map<String, Object> data = new HashMap<>();
        data.put("check",ls.isCheck());
        data.put("id",ls.getId());
        data.put("titolo",ls.getTitolo());
        reference.document(ls.getId()).update(data).addOnSuccessListener(d -> {
            Log.d("DATABASE AGGIORNATO", "updateDatabase: ");
        }).addOnFailureListener(e -> {
            Log.d("DATABASE NON AGGIORNATO", "updateDatabase: ");
        });
    }

}
