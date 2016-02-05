package pichangetheworld.tententest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pichangetheworld.tententest.activities.MainActivity;
import pichangetheworld.tententest.R;

/**
 * Tenten Assignment
 * Author: pchan
 * Date: 05/02/16
 */
public class ButtonsFragment extends Fragment {
    @Bind(R.id.arg)
    private EditText argument;

    @OnClick({R.id.push, R.id.print, R.id.stop, R.id.ret, R.id.call, R.id.mult})
    public void addItemToStack(View button) {
        String arg = argument.getText().toString();

        switch (button.getId()) {
            case R.id.push:
                if (TextUtils.isEmpty(arg)) {
                    Toast.makeText(getActivity(), "PUSH needs an argument", Toast.LENGTH_SHORT).show();
                    return;
                }

                // clear after creating
                argument.setText("");
                break;
            case R.id.print:
            case R.id.stop:
            case R.id.ret:
                break;
            case R.id.call:
                if (TextUtils.isEmpty(arg)) {
                    Toast.makeText(getActivity(), "CALL needs an argument", Toast.LENGTH_SHORT).show();
                    return;
                }

                // clear after creating
                argument.setText("");
                break;
            case R.id.mult:
        }

        ((MainActivity) getActivity()).addItemToStack(button.getId(), arg);
    }

    @OnClick(R.id.set_address)
    public void setAddress() {
        String arg = argument.getText().toString();

        if (TextUtils.isEmpty(arg)) {
            Toast.makeText(getActivity(), "SET ADDRESS needs an argument", Toast.LENGTH_SHORT).show();
            return;
        }

        int argVal = Integer.parseInt(arg);
        ((MainActivity) getActivity()).setAddress(argVal);

        // clear after setting
        argument.setText("");
    }

    @OnClick(R.id.execute)
    public void execute() {
        ((MainActivity) getActivity()).execute();
    }

    public static ButtonsFragment newInstance() {
        return new ButtonsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buttons, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        argument.requestFocus();
    }
}
