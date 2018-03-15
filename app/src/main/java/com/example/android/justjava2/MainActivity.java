package com.example.android.justjava2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //displayPrice(quantity * price);

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.check_box1);
        boolean hasWhippedChream = whippedCreamCheckBox.isChecked();
        CheckBox chocoCreamCheckBox = (CheckBox) findViewById(R.id.check_box2);
        boolean hasChoco = chocoCreamCheckBox.isChecked();
        EditText userInput = (EditText) findViewById(R.id.customer_name);
        String custName = userInput.getText().toString();
        int price = calculatePrice(hasWhippedChream, hasChoco);
        String priceMessage = createOrderSummary(price, hasWhippedChream, hasChoco, custName);
        //displayMessage(priceMessage); // η displayMessage με feed το priceMessage που περιεχει τα 4 values οπως οριζονται απο την createOrderSummary


    }

    public void increment(View view) {

        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;

        displayQuantity(quantity);


    }

    public void decrement(View view) {


        if (quantity <= 1) {

            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    private int calculatePrice(boolean cream, boolean choco) {
        int baseprice = 5;

        if (cream) {
            baseprice = baseprice + 1;
        }
        if (choco) {
            baseprice = baseprice + 2;
        }

        return baseprice * quantity;
    }


//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        //orderSummaryTextView.setText(message); //διακοπτω την εμφανιση του μηνύματος στη βασικη οθονη του app επειδη τα στελνω με email
//    }


    private String createOrderSummary(int number, boolean isChecked, boolean isChecked2, String customerName) {
//        //CheckBox whippedCreamCheckBox= (CheckBox) findViewById(R.id.check_box);
//        //boolean hasWhippedChream = whippedCreamCheckBox.isChecked();
        //TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        String priceMessage = "Name: " + customerName;
        priceMessage += "\nadd whipped cream? " + isChecked;
        priceMessage += "\nadd choco? " + isChecked2;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + number;
        priceMessage += "\nThank you!";

        /*
        *παρακατω ξεκιναει η intent οπου στελνει τα data στo email app
        * το EXTRA_EMAIL "A string array of all "To" recipient email addresses."
        * EXTRA_SUBJECT - self explanatory
        *   EXTRA_TEXT για να προσθέσω κειμενο στο σωμα του mail
        *
         */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+ customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent); /*αυτη εδω η if ελέγχει εαν υπάρχει application το οποιο μπορει να διαχειριστει αυτο που του ζηταω ποιο πανω πχ email app*/


        }

        return priceMessage;


    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int coffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + coffees);
    }
}