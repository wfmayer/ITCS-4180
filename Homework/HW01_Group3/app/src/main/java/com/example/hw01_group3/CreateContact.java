package com.example.hw01_group3;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateContact extends AppCompatActivity {

    //For email validation
    public static boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    //This method gets a Byte Array PNG from an ImageButton
    public static byte[] createByteArrayPNGFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] arr = baos.toByteArray();
        return arr;
    }

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

    //Image Checker Boolean
    private boolean wasCameraClicked = false;

    //Variables for DatePicker//
    private Calendar calendar = Calendar.getInstance();
    private String dateFormat = "dd.MM.yyyy";
    private DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    //Thread
    ExecutorService threadPool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        //Thread
        threadPool = Executors.newFixedThreadPool(2);

        //Button//
        saveButton = findViewById(R.id.button_saveFinish);

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

        ////Image Button////
        imageButton = findViewById(R.id.imageButton_addPic);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wasCameraClicked = true;
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, cameraCode);
            }
        });


        ////DatePicker////
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        //on Birthday Click
        birthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateContact.this, date, calendar.
                get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(getMinDate());
                datePickerDialog.show();
            }
        });

        //SAVE BUTTON////
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte[] selfie;
                Bitmap staticDef = BitmapFactory.decodeResource(CreateContact.this.getResources(), R.drawable.default_image);
                if (!wasCameraClicked) {
                    selfie = createByteArrayPNGFromBitmap(staticDef);
                } else { selfie = createByteArrayPNG(imageButton); }

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
                        Intent intentContact = new Intent();
                        intentContact.putExtra(MainActivity.VALUE_KEY, new Contact(selfie, first, last, company, phone, email,
                                basicURL, address, birthday, nickname, facebook, twitter, skype, youtube));
                        setResult(RESULT_OK, intentContact);
                        finish();
                    }
                } catch (InputMismatchException misMatch) {
                    if (first.equals("") && last.equals("") && phone.equals("") && ((!isEmailValid(email)) && !email.equals(""))) {
                        Toast.makeText(CreateContact.this, "First Name, Last Name, and Phone Number Required | Email entered incorrectly", Toast.LENGTH_LONG).show();
                    } else if (first.equals("") || last.equals("") || phone.equals("")) {
                        Toast.makeText(CreateContact.this, "First Name, Last Name, and Phone Number Required", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(CreateContact.this, "Email entered incorrectly", Toast.LENGTH_LONG).show();
                }
            }
            //END OF SAVE BUTTON////
        });




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

    public long getMinDate() {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.YEAR, -168);
        c.add(Calendar.MONTH, -1);
        c.add(Calendar.DAY_OF_MONTH, -10);
        long minDate = c.getTime().getTime();
        return minDate;
    }

}
