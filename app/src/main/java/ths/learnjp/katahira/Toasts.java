package ths.learnjp.katahira;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Toasts extends AppCompatActivity {
    public void welcomeToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText("Hello!");
        toastImage.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);

        android.widget.Toast toast = new android.widget.Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
