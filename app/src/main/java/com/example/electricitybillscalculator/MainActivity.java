package com.example.electricitybillscalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUnits, editTextRebate;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUnits = findViewById(R.id.editTextUnits);
        editTextRebate = findViewById(R.id.editTextRebate);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);

        // Show guide instruction pop-up
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Guide")
                .setMessage("Enter the units consumed and rebate percentage, then click the Calculate button to calculate the electricity bill.")
                .setPositiveButton(android.R.string.ok, null)
                .show();

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unitsInput = editTextUnits.getText().toString().trim();
                String rebateInput = editTextRebate.getText().toString().trim();

                if (unitsInput.isEmpty() || rebateInput.isEmpty()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Input Error")
                            .setMessage("Please provide values for both units and rebate.")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                } else {
                    calculateBill();
                }
            }
        });
    }


    private void calculateBill() {
        // Get the values from EditText fields
        int units = Integer.parseInt(editTextUnits.getText().toString());
        double rebate = Double.parseDouble(editTextRebate.getText().toString());

        // Perform the necessary calculations
        double charges = 0.0;
        if (units <= 200) {
            charges = units * 0.218;
        } else if (units <= 300) {
            charges = (200 * 0.218) + ((units - 200) * 0.334);
        } else if (units <= 600) {
            charges = (200 * 0.218) + (100 * 0.334) + ((units - 300) * 0.516);
        } else if (units > 600) {
            charges = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((units - 600) * 0.546);
        }

        // Calculate the final cost after applying the rebate
        double finalCost = charges - (charges * (rebate / 100));

        // Display the result
        textViewResult.setText(String.format("Total Charges: RM %.2f\nFinal Cost: RM %.2f", charges, finalCost));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about:

                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
