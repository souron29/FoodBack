package GeneralCode;

import android.app.Fragment;

public interface OnBackPressListener {
    boolean onBackPressed(Fragment parent);

    void Reset();
}
