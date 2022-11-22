package com.example.android;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDB {
    private final String TAG = "FIREBASE";

    private FirebaseFirestore db;

    public FirebaseDB() {
        db = FirebaseFirestore.getInstance();
    }

    public void insertIntoDocument(String collectionName, String documentName, Map<String, Object> data) {
        db.collection(collectionName).document(documentName)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void getDocumentFromCollection(String collectionName, String documentName, OnCompleteListener onCompleteListener) {
        DocumentReference docRef = db.collection(collectionName).document(documentName);
        docRef.get().addOnCompleteListener(onCompleteListener);
    }

    public void getDocumentFromCollectionWhereEqualTo(String collectionName, String whereField, Object equalTo, OnCompleteListener onCompleteListener) {
        Query query = db.collection(collectionName).whereEqualTo(whereField, equalTo);
        query.get().addOnCompleteListener(onCompleteListener);
    }
}
