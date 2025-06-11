package ptns.ntu.notes_mobileapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.lang.annotation.Documented;
import com.google.firebase.Timestamp;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText titleEditText,contentEditText;
    ImageButton saveNoteBtn;
    TextView pageTitleTextView; // tieu de
    String title,content,docId;
    boolean isEditMode = false;
    TextView deleteNoteTextViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleEditText = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_content_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        pageTitleTextView = findViewById(R.id.page_title);
        deleteNoteTextViewBtn = findViewById(R.id.delete_note_text_view_btn);

        //nhan du lieu
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        //che do chinh noi dung chi tiet trong note sua true
        if(docId!=null && !docId.isEmpty()){
            isEditMode = true;
        }


        titleEditText.setText(title);
        contentEditText.setText(content);

        if(isEditMode){
            pageTitleTextView.setText("Edit your note");
            deleteNoteTextViewBtn.setVisibility(TextView.VISIBLE);
        }

        saveNoteBtn.setOnClickListener((v)-> saveNote());

        deleteNoteTextViewBtn.setOnClickListener((v)->deleteNoteFromFirebase());
    }
    //luu nut ghi chu
    void saveNote(){
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();
        //thong bao phai co tieu de
        if(noteTitle==null || noteTitle.isEmpty()){
            titleEditText.setError("Title is required");
            return;
        }
        //tao lop
        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);
    }
    //ghi chu cua tung acc
    //
    void saveNoteToFirebase(Note note){
        DocumentReference documentReference;
        //neu chinh sua
        if(isEditMode){
            //neu vo chinh sua thi se cap nhat ghi chu
            documentReference = notification.getCollectionReferenceForNotes().document(docId);
        }else{
            //neu kg phai chinh sua thi tao ghi chu moi
            documentReference = notification.getCollectionReferenceForNotes().document();
        }


        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //ghi chu da them
                    notification.shawToast(NoteDetailsActivity.this, "Note added successfully");
                    finish();  //them thanh cong quay lai trang chinh
                }else{
                    notification.shawToast(NoteDetailsActivity.this, "Failed while adding note");
                }
            }
        });

    }

    void deleteNoteFromFirebase(){
        DocumentReference documentReference;

            //neu vo chinh sua thi se cap nhat ghi chu
        documentReference = notification.getCollectionReferenceForNotes().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //ghi chu da xoa
                    notification.shawToast(NoteDetailsActivity.this, "Note deleted successfully");
                    finish();  //them thanh cong quay lai trang chinh
                }else{
                    notification.shawToast(NoteDetailsActivity.this, "Failed while deleting note");
                }
            }
        });
    }
}