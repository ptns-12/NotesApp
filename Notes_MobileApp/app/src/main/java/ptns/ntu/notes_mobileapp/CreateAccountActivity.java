package ptns.ntu.notes_mobileapp;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    EditText emailEditText,passwordEditText,confirmPasswordEditText;
    Button createAccountBtn;
    ProgressBar progressBar;
    TextView loginBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        createAccountBtn = findViewById(R.id.create_account_btn);
        progressBar = findViewById(R.id.progress_bar);
        loginBtnTextView = findViewById(R.id.login_text_view_btn);

        //
        createAccountBtn.setOnClickListener(v-> createAccount());
        //
        loginBtnTextView.setOnClickListener(v->finish());

    }

    void createAccount(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        boolean isValidated = validateData(email,password,confirmPassword);
        if(!isValidated){
            return;
        }

        //xac thuc Firebase

        createAccountInFirebase(email,password);

    }

    void createAccountInFirebase(String email,String password){
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(CreateAccountActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        changeInProgress(false);
                        if(task.isSuccessful()){
                            //tao tk hoan tat
                            notification.shawToast(CreateAccountActivity.this,"Succesfully create account,Check email to verify");
                            //Toast.makeText(CreateAccountActivity.this,"Succesfully create account,Check email to verify",Toast.LENGTH_LONG).show();
                            //firebaseAuth.getCurrentUser().sendEmailVerification();  //xac minh email
                            firebaseAuth.signOut();
                            finish();
                        }else{
                            //that bai
                            notification.shawToast(CreateAccountActivity.this,task.getException().getLocalizedMessage());
                            //Toast.makeText(CreateAccountActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    //tao tk mat mot thoi gian hien thi vong xoay tron đợi
    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            createAccountBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            createAccountBtn.setVisibility(View.VISIBLE);
        }
    }



    //phuong thuc tra ve dung hay sai
    boolean validateData(String email,String password,String confirmPassword){
        //xác thực dữ liệu được người dùng nhập vào
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email is invalid");
            return false;
        }
        //do dai mat khau
        if(password.length()<6){
            passwordEditText.setError("Password length is invalid");
            return false;
        }
        //kiem tra mat khau co giong nhau hay khong
        if(!password.equals(confirmPassword)){
            confirmPasswordEditText.setError("Password not matched");
            return false;
        }
        return true;
    }

}