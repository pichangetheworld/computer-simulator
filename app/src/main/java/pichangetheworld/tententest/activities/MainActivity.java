package pichangetheworld.tententest.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pichangetheworld.tententest.models.Instruction;
import pichangetheworld.tententest.models.InstructionType;
import pichangetheworld.tententest.R;
import pichangetheworld.tententest.computer.Computer;
import pichangetheworld.tententest.fragments.ButtonsFragment;
import pichangetheworld.tententest.fragments.ComputerStackFragment;

public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_NUM_ADDRESSES = "numAddresses";

    @Bind(R.id.stack)
    ListView instructionsListView;

    @Bind(R.id.results)
    TextView results;

    private Computer computer;
    private StackAdapter adapter;

    private boolean isActive = false;

    public static Intent createIntent(Context context, int numAddresses) {
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(EXTRA_NUM_ADDRESSES, numAddresses);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        int numAddresses = getIntent().getIntExtra(EXTRA_NUM_ADDRESSES, 100);

        computer = new Computer(numAddresses);
        adapter = new StackAdapter(this, computer);
        instructionsListView.setAdapter(adapter);

        // to keep the listview at the bottom even when the keyboard appears
        instructionsListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);
        instructionsListView.setStackFromBottom(true);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.right_side, ButtonsFragment.newInstance())
                .commit();

        // scroll to bottom, i.e. beginning of stack
        updateProgramCounter();
    }

    private void updateProgramCounter() {
        adapter.notifyDataSetChanged();

        // Delay one second because sometimes the keyboard is appearing
        instructionsListView.post(new Runnable() {
            @Override
            public void run() {
                int nextInstruction = computer.getCurrentAddress();
                int rpos = adapter.getCount() - 1 - nextInstruction;
                instructionsListView.setSelection(rpos);
            }
        });
    }

    // functions
    public void addItemToStack(int buttonId, String arg) {
        int argVal;
        switch (buttonId) {
            case R.id.push:
                argVal = Integer.parseInt(arg);
                Log.d("TENTEN", "Adding push item to stack");
                computer.addInstruction(Instruction.createInstance(InstructionType.PUSH, argVal));
                break;
            case R.id.print:
                Log.d("TENTEN", "Adding print item to stack");
                computer.addInstruction(Instruction.createInstance(InstructionType.PRINT, 0));
                break;
            case R.id.stop:
                Log.d("TENTEN", "Adding stop item to stack");
                computer.addInstruction(Instruction.createInstance(InstructionType.STOP, 0));
                break;
            case R.id.ret:
                Log.d("TENTEN", "Adding ret item to stack");
                computer.addInstruction(Instruction.createInstance(InstructionType.RET, 0));
                break;
            case R.id.call:
                argVal = Integer.parseInt(arg);
                Log.d("TENTEN", "Adding call item to stack");
                computer.addInstruction(Instruction.createInstance(InstructionType.CALL, argVal));
                break;
            case R.id.mult:
                Log.d("TENTEN", "Adding mult item to stack");
                computer.addInstruction(Instruction.createInstance(InstructionType.MULT, 0));
                break;
        }
        updateProgramCounter();
    }

    public void setAddress(int address) {
        if (address < 0 || address >= adapter.getCount()) {
            Toast.makeText(this, "This address is out of bounds.", Toast.LENGTH_SHORT).show();
            return;
        }

        // scroll to current address
        computer.setCurrentAddress(address);
        updateProgramCounter();
    }

    public void execute() {
        results.setVisibility(View.VISIBLE);
        isActive = true;

        // hide keyboard
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_side, ComputerStackFragment.newInstance())
                .commit();
    }

    public boolean executeStep() {
        if (isActive) {
            isActive = computer.executeInstruction();
            updateProgramCounter();

            results.setText(computer.getOutput());
        } else {
            reset();
        }
        return isActive;
    }

    public List<Integer> getCurrentStackState() {
        return computer.getCurrentStackState();
    }

    private void reset() {
        computer.reset();
        updateProgramCounter();

        results.setText(computer.getOutput());

        results.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_side, ButtonsFragment.newInstance())
                .commit();
    }

    private static class StackAdapter extends ArrayAdapter<Instruction> {
        private final Computer computer;
        private final LayoutInflater layoutInflater;

        public StackAdapter(Context context, Computer computer) {
            super(context, 0, computer.getInstructions());
            this.computer = computer;

            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView == null) {
                v = layoutInflater.inflate(R.layout.instruction_view, parent, false);
            } else {
                v = convertView;
            }

            // stack is displayed from bottom to top
            int rpos = getCount() - position - 1;

            TextView addressView = (TextView) v.findViewById(R.id.address);
            addressView.setText(String.valueOf(rpos));

            TextView textView = (TextView) v.findViewById(R.id.text1);
            if (getItem(rpos) != null) {
                textView.setText(getItem(rpos).getInstructionString());
            } else {
                textView.setText("");
            }

            if (rpos == computer.getCurrentAddress()) {
                v.setBackgroundColor(getContext().getResources().getColor(R.color.currentInstructionBackground));
            } else {
                v.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
            }

            return v;
        }
    }
}
