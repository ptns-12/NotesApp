package ptns.ntu.notes_mobileapp;


import android.content.Context;
import android.widget.Toast;

//hien thi cac thong bao toast
public class notification {

    static void shawToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
