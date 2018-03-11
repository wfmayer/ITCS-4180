package com.example.hw01_group3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static com.example.hw01_group3.ContactsList.ByteArrayToBitmap;
import static com.example.hw01_group3.ContactsList.EDIT_KEY_CONTACT;
import static com.example.hw01_group3.ContactsList.EDIT_KEY_INDEX;

import static com.example.hw01_group3.CreateContact.isEmailValid;
import static com.example.hw01_group3.MainActivity.contactArrayList;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetaliedEdit extends AppCompatActivity {

    //This method gets a Byte Array PNG from an ImageButton
    public static byte[] createByteArrayPNG(ImageButton view) {
        BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] arr = baos.toByteArray();
        return arr;
    }

    //Camera Request Code//
    private final int cameraCode = 420;

    //Save Button//
    private Button saveButton;

    //ImageButton//
    private ImageButton imageButton;

    //EditTexts//
    private EditText firstText;
    private EditText lastText;
    private EditText companyText;
    private EditText phoneText;
    private EditText emailText;
    private EditText basicURLText;
    private EditText addressText ;
    private EditText birthdayText;
    private EditText nickNameText;
    private EditText facebookText;
    private EditText twitterText;
    private EditText skypeText;
    private EditText youtubeText;

    //Variables for DatePicker//
    private Calendar calendar = Calendar.getInstance();
    private String dateFormat = "dd.MM.yyyy";
    private DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);

    //Index
    private int indexPosition;

    //Thread stuff
    ExecutorService threadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        //Button//
        Button saveButton = findViewById(R.id.button_saveFinish);

        ////EditTexts////
        firstText = findViewById(R.id.editText_firstName);
        lastText = findViewById(R.id.editText_lastName);
        companyText = findViewById(R.id.editText_company);
        phoneText = findViewById(R.id.editText_phone);
        emailText = findViewById(R.id.editText_email);
        basicURLText = findViewById(R.id.editText_url);
        addressText = findViewById(R.id.editText_address);
        birthdayText = findViewById(R.id.editText_birthdayDate);
        nickNameText = findViewById(R.id.editText_nickname);
        facebookText = findViewById(R.id.editText_facebook);
        twitterText = findViewById(R.id.editText_twitter);
        skypeText = findViewById(R.id.editText_skype);
        youtubeText = findViewById(R.id.editText_youtube);

        //Thread pool
        threadPool = Executors.newFixedThreadPool(2);

        ////Image Button////
        imageButton = findViewById(R.id.imageButton_addPic);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, cameraCode);
            }
        });
        //END OF IMAGE BUTTON//

        ////DatePicker////
        //Setting time to current data
        //Set calendar date and update the birthday text
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };
        //on Click
        birthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DetaliedEdit.this, date, calendar.
                        get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        ////END OF DATE PICKER////

        if (getIntent() != null && getIntent().getExtras() != null) {

        indexPosition = getIntent().getIntExtra(EDIT_KEY_INDEX, 0);
        Contact contact = getIntent().getParcelableExtra(EDIT_KEY_CONTACT);

        Log.d("demo", "indexPostion = " + indexPosition);
        Log.d("demo", "Made it into Edit - Contact First Name = " + contact.getFirstName());
        new DoWorkAsyncEdit().execute(contact);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] selfie = createByteArrayPNG(imageButton);
                String first = firstText.getText().toString();
                String last = lastText.getText().toString();
                String company = companyText.getText().toString();
                String phone = phoneText.getText().toString();
                String email = emailText.getText().toString();
                String basicURL = basicURLText.getText().toString();
                String address = addressText.getText().toString();
                String birthday = birthdayText.getText().toString();
                String nickname = nickNameText.getText().toString();
                String facebook = facebookText.getText().toString();
                String twitter = twitterText.getText().toString();
                String skype = skypeText.getText().toString();
                String youtube = youtubeText.getText().toString();

                try {
                    if (first.equals("") || last.equals("") || phone.equals("") || ((!isEmailValid(email)) && !email.equals(""))) {
                        throw new InputMismatchException();
                    } else {
                        Contact contact = new Contact(selfie, first, last, company, phone, email,
                                basicURL, address, birthday, nickname, facebook, twitter, skype, youtube);
                        new DoWorkAsyncSubEdit().execute(contact);
                        Intent intent = new Intent(DetaliedEdit.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (InputMismatchException misMatch) {
                    if (first.equals("") && last.equals("") && phone.equals("") && ((!isEmailValid(email)) && !email.equals(""))) {
                        Toast.makeText(DetaliedEdit.this, "First Name, Last Name, and Phone Number Required | Email entered incorrectly", Toast.LENGTH_LONG).show();
                    } else if (first.equals("") || last.equals("") || phone.equals("")) {
                        Toast.makeText(DetaliedEdit.this, "First Name, Last Name, and Phone Number Required", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(DetaliedEdit.this, "Email entered incorrectly", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    }

    class DoWorkAsyncEdit extends AsyncTask<Contact, Integer, ArrayList<Contact>> {


        @Override
        protected ArrayList<Contact> doInBackground(Contact... contacts) {
            Log.d("demo", "Made it into Background");
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
            imageButton.setImageBitmap(ByteArrayToBitmap(contact.getSelfie()));

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

    class DoWorkAsyncSubEdit extends AsyncTask<Contact, Void, Contact> {

        @Override
        protected void onPostExecute(Contact contact) {
            super.onPostExecute(contact);
            contactArrayList.set(indexPosition, contact);
        }

        @Override
        protected Contact doInBackground(Contact... contacts) {
            return contacts[0];
        }
    }

    //For the camera App
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.cameraCode == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageButton.setImageBitmap(bitmap);
        }
    }

    //For the DatePicker
    private void updateDate() {
        birthdayText.setText(sdf.format(calendar.getTime()));
    }

}
