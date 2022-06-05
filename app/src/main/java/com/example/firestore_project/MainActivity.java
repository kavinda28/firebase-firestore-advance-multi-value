package com.example.firestore_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText ed_title, ed_description;
    TextView showDetails,priority;
    private static final String TAG = "MainActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    DocumentReference notref = db.document("Notebook/my second note");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_title = findViewById(R.id.titles);
        ed_description = findViewById(R.id.description);
        showDetails = findViewById(R.id.show_details);
        priority =  findViewById(R.id.priority);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notebookRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                String data = "";

                for (QueryDocumentSnapshot documentSnapshot : value) {
                    note notes = documentSnapshot.toObject(note.class);

                    notes.setDocumentid(documentSnapshot.getId()); //add id

                    String title = notes.getTitle();
                    String description = notes.getDescription();
                    String Document_id = notes.getDocumentid();
                    int priority =  notes.getPriority();

                    data += "ID:: " + Document_id + "\n Title: " + title + "\n Description::" + description + "\nPriority::"+priority+"\n\n";
                }
                showDetails.setText(data);

            }
        });
    } //auto loading deta

    public void addNote(View view) {
        String title = ed_title.getText().toString();
        String description = ed_description.getText().toString();

//        Map<String, Object> note = new HashMap<>();
//        note.put(KEY_TITLE,title);
//        note.put(KEY_DESCRIPTION,description);

        if (priority.length() == 0){
            priority.setText("0");
        }
        int priorityvalue = Integer.parseInt(priority.getText().toString());
        note note = new note(title, description,priorityvalue);

        notebookRef.add(note);

    }


    public void loadnotes(View view) {
        System.out.println("loding notes>>>>>>>>>>");
        notebookRef.
                whereGreaterThan("priority",2).
                orderBy("priority", Query.Direction.DESCENDING).
                limit(3).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            note notes = documentSnapshot.toObject(note.class);

                            notes.setDocumentid(documentSnapshot.getId()); //add id

                            String title = notes.getTitle();
                            String description = notes.getDescription();
                            String Document_id = notes.getDocumentid();
                            int priority =  notes.getPriority();

                            data += "ID:: " + Document_id + "\n Title: " + title + "\n Description::" + description + "\nPriority::"+priority+"\n\n";
                        }
                        showDetails.setText(data);
                    }
                });
    }


}
