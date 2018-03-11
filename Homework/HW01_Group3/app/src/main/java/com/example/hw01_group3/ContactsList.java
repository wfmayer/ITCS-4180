package com.example.hw01_group3;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.hw01_group3.MainActivity.contactArrayList;

public class ContactsList extends AppCompatActivity {

    //Keys
    public static final String EDIT_KEY_CONTACT = "editContact";
    public static final String EDIT_KEY_INDEX = "positionContact";

    public static final String DISPLAY_KEY_CONTACT = "displayContact";

    //Thread
    ExecutorService threadPool;

    public static Bitmap ByteArrayToBitmap(byte[] byteArray)
    {
        Bitmap bitmap_intent = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.length);
        return bitmap_intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        //Thread
        threadPool = Executors.newFixedThreadPool(2);

        final String string = getIntent().getStringExtra("StringName");

        //Generating ListView
        ListView listView = findViewById(R.id.listView_contact);
        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                switch (string) {

                    case "edit": {
                        Intent intent = new Intent(ContactsList.this, DetaliedEdit.class);
                        intent.putExtra(EDIT_KEY_CONTACT, contactArrayList.get(position));
                        intent.putExtra(EDIT_KEY_INDEX, position);
                        startActivity(intent);
                        break;
                    }

                    case "delete": {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ContactsList.this, MainActivity.class);
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        new DoWorkAsyncDelete().execute(position);
                                        startActivity(intent);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        startActivity(intent);
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ContactsList.this);
                        builder.setTitle("Are you sure you want to delete this?")
                                .setNegativeButton("No", dialogClickListener)
                                .setPositiveButton("Yes", dialogClickListener)
                                .setCancelable(false).show();
                        break;
                    }

                    case "display": {
                        Intent intent = new Intent(ContactsList.this, DisplayContact.class);
                        intent.putExtra(DISPLAY_KEY_CONTACT, contactArrayList.get(position));
                        startActivity(intent);
                    }

                }

            }
        });
    }

    class DoWorkAsyncDelete extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            Log.d("demo", "Delete Index is = " + integers[0]);
            contactArrayList.remove(contactArrayList.get(integers[0]));
            return null;
        }
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return contactArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.contact_listview_layout, null);

            Contact contact = contactArrayList.get(position);

            ImageView imageView = convertView.findViewById(R.id.imageView_contactList);
            TextView textView_first = convertView.findViewById(R.id.textView_firstNameContactList);
            TextView textView_last = convertView.findViewById(R.id.textView_lastNameContactList);
            TextView textView_phone = convertView.findViewById(R.id.textView_phoneContactList);

            imageView.setImageBitmap(ByteArrayToBitmap(contact.getSelfie()));
            textView_first.setText(contact.getFirstName());
            textView_last.setText(contact.getLastName());
            textView_phone.setText(contact.getPhone());
            return convertView;
        }
    }

}