package pichangetheworld.tententest;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Tenten Assignment
 * Author: pchan
 * Date: 05/02/16
 */
public class StartActivity extends Activity {
    @Bind(R.id.computer_size)
    EditText computerSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }

    public void start(View v) {
        String size = computerSize.getText().toString();
        if (TextUtils.isEmpty(size)) {
            showErrorMessage();
            return;
        }

        try {
            int numAddresses = Integer.parseInt(size);
            if (numAddresses < 1 || numAddresses > 100) {
                showErrorMessage();
                return;
            }

            startActivity(MainActivity.createIntent(this, numAddresses));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showErrorMessage();
        }
    }

    private void showErrorMessage() {
        Toast.makeText(this, "Please enter a valid computer size (1 - 100)", Toast.LENGTH_LONG).show();
    }
}
