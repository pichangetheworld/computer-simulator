package pichangetheworld.tententest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.stack)
    ListView instructionsListView;

    @Bind(R.id.arg)
    EditText argument;

    @OnClick({R.id.push, R.id.print, R.id.stop, R.id.ret, R.id.call, R.id.mult})
    public void addItemToStack(View button) {
        String arg = argument.getText().toString();
        int argVal;

        switch (button.getId()) {
            case R.id.push:
                if (TextUtils.isEmpty(arg)) {
                    Toast.makeText(this, "PUSH needs an argument", Toast.LENGTH_SHORT).show();
                    return;
                }
                argVal = Integer.parseInt(arg);
                Log.d("TENTEN", "Adding push item to stack");
                computer.addInstruction(Instruction.createInstance(InstructionType.PUSH, argVal));

                // clear after creating
                argument.setText("");
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
                if (TextUtils.isEmpty(arg)) {
                    Toast.makeText(this, "CALL needs an argument", Toast.LENGTH_SHORT).show();
                    return;
                }
                argVal = Integer.parseInt(arg);
                Log.d("TENTEN", "Adding call item to stack");
                computer.addInstruction(Instruction.createInstance(InstructionType.CALL, argVal));

                // clear after creating
                argument.setText("");
                break;
            case R.id.mult:
                Log.d("TENTEN", "Adding mult item to stack");
                computer.addInstruction(Instruction.createInstance(InstructionType.MULT, 0));
                break;
        }
        updateProgramCounter();
    }

    @OnClick(R.id.set_address)
    public void setAddress() {
        String arg = argument.getText().toString();

        if (TextUtils.isEmpty(arg)) {
            Toast.makeText(this, "SET ADDRESS needs an argument", Toast.LENGTH_SHORT).show();
            return;
        }

        int argVal = Integer.parseInt(arg);
        if (argVal < 0 || argVal >= adapter.getCount()) {
            if (TextUtils.isEmpty(arg)) {
                Toast.makeText(this, "This address is out of bounds.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // scroll to current address
        computer.setCurrentAddress(argVal);
        updateProgramCounter();

        // clear after setting
        argument.setText("");
    }

    @OnClick(R.id.execute)
    public void execute() {
        results.setVisibility(View.VISIBLE);

        // TODO: hide keyboard

        computer.executeInstruction();
        updateProgramCounter();

        results.setText(computer.getOutput());
    }

    @Bind(R.id.results)
    TextView results;

    private Computer computer;
    private StackAdapter adapter;

    private int numAddresses = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        computer = new Computer(numAddresses);
        adapter = new StackAdapter(this, computer);
        instructionsListView.setAdapter(adapter);

        argument.requestFocus();

        // scroll to bottom, i.e. beginning of stack
        updateProgramCounter();
    }

    private void updateProgramCounter() {
        adapter.notifyDataSetChanged();

        instructionsListView.post(new Runnable() {
            @Override
            public void run() {
                int nextInstruction = computer.getCurrentAddress();
                int rpos = adapter.getCount() - 1 - nextInstruction;
                instructionsListView.setSelection(rpos);
            }
        });
    }

    private static class StackAdapter extends ArrayAdapter<Instruction> {
        private Computer computer;
        private LayoutInflater layoutInflater;

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
