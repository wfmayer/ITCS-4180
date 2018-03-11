package com.example.hw01_group3;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.hw01_group3.ContactsList.ByteArrayToBitmap;
import static com.example.hw01_group3.ContactsList.DISPLAY_KEY_CONTACT;

public class DisplayContact extends AppCompatActivity {

    //ImageButton//
    private ImageView imageView;

    //TextViews//
    private TextView  firstText;
    private TextView  lastText;
    private TextView  companyText;
    private TextView  phoneText;
    private TextView  emailText;
    private TextView  basicURLText;
    private TextView  addressText ;
    private TextView  birthdayText;
    private TextView  nickNameText;
    private TextView  facebookText;
    private TextView  twitterText;
    private TextView  skypeText;
    private TextView  youtubeText;

    //Thread
    ExecutorService threadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        ////EditTexts////
        firstText = findViewById(R.id.textView_displayFirst);
        lastText = findViewById(R.id.textView_displayLast);
        companyText = findViewById(R.id.textView_displayCompanyDynamic);
        phoneText = findViewById(R.id.textView_displayPhoneDynamic);
        emailText = findViewById(R.id.textView_displayEmailDynamic);
        basicURLText = findViewById(R.id.textView_displayBasicURLDynamic);
        addressText = findViewById(R.id.textView_displayAddressDynamic);
        birthdayText = findViewById(R.id.textView_displayBirthdayDynamic);
        nickNameText = findViewById(R.id.textView_displayNicknameDynamic);
        facebookText = findViewById(R.id.textView_displayFacebookDynamic);
        twitterText = findViewById(R.id.textView_displayTwitterDynamic);
        skypeText = findViewById(R.id.textView_displaySkypeDynamic);
        youtubeText = findViewById(R.id.textView_displayYoutubeDynamic);

        //Thread pool
        threadPool = Executors.newFixedThreadPool(2);

        ////Image Button////
        imageView = findViewById(R.id.imageView_displayImageView);

        if (getIntent() != null && getIntent().getExtras() != null) {

            Contact contact = getIntent().getParcelableExtra(DISPLAY_KEY_CONTACT);
            new DoWorkAsyncDisplay().execute(contact);

            basicURLText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + basicURLText.getText()));
                    startActivity(implicit);
                }
            });

            facebookText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + facebookText.getText()));
                    startActivity(implicit);
                }
            });

            twitterText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + twitterText.getText()));
                    startActivity(implicit);
                }
            });

            skypeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + skypeText.getText()));
                    startActivity(implicit);
                }
            });

            youtubeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + basicURLText.getText()));
                    startActivity(implicit);
                }
            });

        }
    }

    class DoWorkAsyncDisplay extends AsyncTask<Contact, Integer, ArrayList<Contact>> {


        @Override
        protected ArrayList<Contact> doInBackground(Contact... contacts) {
            ArrayList<Contact> contact = new ArrayList<>();
            contact.add(contacts[0]);
            return contact;
        }

        @Override
        protected void onPostExecute(ArrayList<Contact> postArrayList) {
            super.onPostExecute(postArrayList);
            Log.d("demo", "Made it into Post");

            Contact contact = postArrayList.get(0);

            firstText.setText(contact.getFirstName());
            imageView.setImageBitmap(ByteArrayToBitmap(contact.getSelfie()));

            lastText.setText(contact.getLastName());
            phoneText.setText(contact.getPhone());

            if (!contact.getEmail().equals("")) {
                emailText.setText(contact.getEmail());
            } if (!contact.getBasicURL().equals("")) {
                basicURLText.setText(contact.getBasicURL());
            } if (!contact.getAddress().equals("")) {
                addressText.setText(contact.getAddress());
            } if (!contact.getBirthday().equals("")) {
                birthdayText.setText(contact.getBirthday());
            } if (!contact.getNickname().equals("")) {
                nickNameText.setText(contact.getNickname());
            } if (!contact.getFacebookURL().equals("")) {
                facebookText.setText(contact.getFacebookURL());
            } if (!contact.getTwitterURL().equals("")) {
                twitterText.setText(contact.getTwitterURL());
            } if (!contact.getSkypeURL().equals("")) {
                skypeText.setText(contact.getSkypeURL());
            } if (!contact.getYoutubeURL().equals("")) {
                youtubeText.setText(contact.getYoutubeURL());
            } if (!contact.getCompany().equals("")) {
                companyText.setText(contact.getCompany());
            }
        }
    }
}
