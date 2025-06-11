package ptns.ntu.notes_mobileapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter {
    //ke thua tu recyclerview.viewholder
    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView,contentTextView,timestampTextView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notes_title_text_view);
            contentTextView = itemView.findViewById(R.id.notes_content_text_view);
            timestampTextView = itemView.findViewById(R.id.notes_timestamp_text_view);
        }
    }
}
