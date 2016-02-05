package pichangetheworld.tententest.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pichangetheworld.tententest.R;

/**
 * Tenten Assignment
 * Author: pchan
 * Date: 05/02/16
 */
public class StartActivity extends Activity {
    @Bind(R.id.computer_size)
    private EditText computerSize;

    @OnClick(R.id.start)
    public void start() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }

    private void showErrorMessage() {
        Toast.makeText(this, "Please enter a valid computer size (1 - 100)", Toast.LENGTH_LONG).show();
    }
}
