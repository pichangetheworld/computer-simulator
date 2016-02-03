package pichangetheworld.tententest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
            case R.id.mult:
                Log.d("TENTEN", "Adding mult item to stack");
                computer.addInstruction(Instruction.createInstance(InstructionType.MULT, 0));
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private Computer computer;
    private StackAdapter adapter;

    private int numAddresses = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        computer = new Computer(numAddresses);
        adapter = new StackAdapter(this, computer.getInstructions());
        instructionsListView.setAdapter(adapter);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class StackAdapter extends ArrayAdapter<Instruction> {

        public StackAdapter(Context context, Instruction[] instructions) {
            super(context, android.R.layout.simple_list_item_1, instructions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            if (getItem(position) != null) {
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(getItem(position).getInstructionString());
            }

            return v;
        }
    }
}
