package ptns.ntu.notes_mobileapp;


import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;

//hien thi cac thong bao toast
public class notification {

    static void shawToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    //ghi chu cua tung ng id nguoi dung
    static CollectionReference getCollectionReferenceForNotes(){
        //nguoi dung hien tai
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes")
                .document(currentUser.getUid()).collection("my_notes");
    }
}
