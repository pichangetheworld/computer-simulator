package pichangetheworld.tententest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Tenten Assignment
 * Author: pchan
 * Date: 05/02/16
 */
public class ComputerStackFragment extends Fragment {
    @Bind(R.id.computer_stack)
    ListView listView;

    @OnClick(R.id.next_button)
    public void stepExecute(Button button) {
        boolean isActive = ((MainActivity) getActivity()).executeStep();
        List<Integer> data = ((MainActivity) getActivity()).getCurrentStackState();

        adapter.clear();
        adapter.addAll(data);

        if (isActive) {
            button.setText(R.string.next);
        } else {
            button.setText(R.string.restart);
        }
    }

    private ComputerStackAdapter adapter;

    public static ComputerStackFragment newInstance() {
        return new ComputerStackFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_computer_stack, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new ComputerStackAdapter(getActivity());
        listView.setAdapter(adapter);
    }

    private static class ComputerStackAdapter extends ArrayAdapter<Integer> {
        public ComputerStackAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            TextView textView = (TextView) v.findViewById(android.R.id.text1);
            textView.setText(String.valueOf(getItem(getCount() - position - 1)));

            return v;
        }
    }
}
