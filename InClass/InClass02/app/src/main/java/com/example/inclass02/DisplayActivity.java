package com.example.inclass02;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;

import static com.example.inclass02.MainActivity.PROFILE_KEY;

public class DisplayActivity extends AppCompatActivity {

    public static Bitmap ByteArrayToBitmap(byte[] byteArray)
    {
        Bitmap bitmap_intent = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.length);
        return bitmap_intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Log.d("demo", "Made it to display");


        if (getIntent() != null && getIntent().getExtras() != null) {

            Profile profile = getIntent().getExtras().getParcelable(PROFILE_KEY);
            Log.d("demo", "PROFILE");


            TextView leName = findViewById(R.id.textView_sendName);
            leName.setText(profile.getName());

            TextView leEmail = findViewById(R.id.textView_sendEmail);
            leEmail.setText(profile.getEmail());

            TextView leDepartment = findViewById(R.id.textView_sendDepart);
            leDepartment.setText(profile.getDepartment());

            TextView leMood = findViewById(R.id.textView_sendMood);
            leMood.setText(profile.getMood());
            String leMood_text = (String) leMood.getText();

            ImageView imageView_mood = findViewById(R.id.imageView_moodDisplay);
            switch (leMood_text) {
                case "Happy": {
                    imageView_mood.setImageResource(R.drawable.happy);
                    break;
                }

                case "Sad": {
                    imageView_mood.setImageResource(R.drawable.sad);
                    break;
                }

                case "Angry": {
                    imageView_mood.setImageResource(R.drawable.angry);
                    break;
                }

                case "Awesome": {
                    imageView_mood.setImageResource(R.drawable.awesome);
                    break;
                }


                default: {
                    imageView_mood.setImageResource(R.drawable.happy);
                    break;
                }
            }

            ImageView imageView_catch = findViewById(R.id.imageView_catchIntent);
            Log.d("demo", "BAtoBIT");
//            Bitmap bitmap_intent = ByteArrayToBitmap(profile.getAvatar());
//            imageView_catch.setImageBitmap(bitmap_intent);
            Bitmap bitmap_intent = BitmapFactory.decodeByteArray(profile.getAvatar(), 0,
                    profile.getAvatar().length);
            imageView_catch.setImageBitmap(bitmap_intent);



//            Intent i = getIntent();
//            byte[] arr = i.getByteArrayExtra(PROFILE_KEY);
//
//            //Works up to this point
//            Log.d("demo", "Made it to the Decode");
//            Bitmap map = BitmapFactory.decodeByteArray(arr, 0, arr.length);
//            ImageView leAvatar = findViewById(R.id.imageView_catchIntent);
//            leAvatar.setImageBitmap(map);
//
//            Log.d("demo", "Made it to the end of display");
        }
    }
}
