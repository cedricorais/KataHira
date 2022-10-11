package ths.learnjp.katahira.ui.guess;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GuessViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GuessViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is guess fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}