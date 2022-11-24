package ths.learnjp.katahira;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Toasts extends AppCompatActivity {

    public void showToast(Context context, String tag, String correct_answer_key) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, ((Activity)context).findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        switch (tag) {
            case "Welcome!":
                toastText.setText(tag);
                toastImage.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);
                break;
            case "start":
                toastText.setText(R.string.start_session);
                toastImage.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                break;
            case "noOption":
                toastText.setText(R.string.invalid_option_toast);
                toastImage.setImageResource(R.drawable.ic_baseline_error_24);
                break;
            case "correctAnswer":
                toastText.setText(R.string.correct_toast);
                toastImage.setImageResource(R.drawable.ic_baseline_done_24);
                break;
            case "wrongAnswer":
                toastText.setText(String.format(context.getString(R.string.wrong_toast), CharacterManager.getAnswer(correct_answer_key)));
                toastImage.setImageResource(R.drawable.ic_baseline_clear_24);
                break;
            case "reset":
                toastText.setText(String.format("%s%s", context.getString(R.string.reset), "!"));
                toastImage.setImageResource(R.drawable.ic_baseline_error_24);
                break;
            case "zeroAttempts":
                toastText.setText(R.string.zero_attempts_toast);
                toastImage.setImageResource(R.drawable.ic_baseline_error_24);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tag);
        }

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 65);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
