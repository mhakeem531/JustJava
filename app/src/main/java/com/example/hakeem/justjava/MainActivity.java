package com.example.hakeem.justjava;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.hakeem.justjava.R.id.customerName;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
    }


    public String createOrderSummery() {
        String summery;
        CheckBox whippedOrNot = (CheckBox) findViewById(R.id.whippedCreamCheckBox);
        CheckBox chocolateOrNot = (CheckBox) findViewById(R.id.ChocolateCheckBox);
        EditText customerName = (EditText) findViewById(R.id.customerName);

        summery = getString(R.string.order_summary_name , customerName.getText().toString()) + "\n ";
        summery += "Add Whipped Cream? " + whippedOrNot.isChecked() + "\n";
        summery += "Add Chocolate? " + chocolateOrNot.isChecked() + "\n";
        summery += "Quantity : " + quantity + "\n";
        summery += "Total : " + calculatePrice(quantity, 5) + "$\n";
        summery += getString(R.string.thank_you) + "\n";

        return summery;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {


        String summeryMessage = createOrderSummery();
        EditText customerName = (EditText) findViewById(R.id.customerName);

        String subject = "Just Java Order For " + customerName.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setData(Uri.parse("mailto"));//only mails will handle this not message apps too

        //from
        intent.putExtra(Intent.EXTRA_EMAIL, "mahkeem531@gmail.com");

        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        intent.putExtra(Intent.EXTRA_TEXT, summeryMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "No Apps provided to complete the action", Toast.LENGTH_SHORT).show();
        }
       // displayMessage(summeryMessage);

    }

    private void displayQuantity(int NumberOfCoffee) {

        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + NumberOfCoffee);
    }


    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "you cant have  more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummeryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummeryTextView.setText(message);
//    }

    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(this, "you cant have  less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity, int priceForOne) {
        int price = quantity * priceForOne;
        CheckBox whippedOrNot = (CheckBox) findViewById(R.id.whippedCreamCheckBox);
        CheckBox chocolateOrNot = (CheckBox) findViewById(R.id.ChocolateCheckBox);
        if (whippedOrNot.isChecked()) {
            price = price + (quantity * 1);
        }
        if (chocolateOrNot.isChecked()) {
            price = price + (quantity * 2);
        }

        return price;
    }


    /**
     * Variables
     */
    private int quantity = 0;
}
