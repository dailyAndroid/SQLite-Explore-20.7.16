package com.example.hwhong.sqliteexplore;

import android.app.Fragment;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText id, name, city;
    Button insert, update, delete, load;
    TextView data;

    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper = new MyDBHelper(MainActivity.this);

        initialize();

    }

    public void initialize() {
        id = (EditText) findViewById(R.id.id);
        name = (EditText) findViewById(R.id.name);
        city = (EditText) findViewById(R.id.city);

        insert = (Button)findViewById(R.id.insertBut);
        update = (Button)findViewById(R.id.updateBut);
        delete = (Button)findViewById(R.id.deleteBut);
        load = (Button)findViewById(R.id.loadBut);

        data = (TextView) findViewById(R.id.dataTV);

        insert.setOnClickListener(dbButtonListener);
        update.setOnClickListener(dbButtonListener);
        delete.setOnClickListener(dbButtonListener);
        load.setOnClickListener(dbButtonListener);

    }

    public View.OnClickListener dbButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.insertBut:
                    long resultInsert = myDBHelper.insert(Integer.parseInt(getString(id)),
                            getString(name), getString(city));

                    if (resultInsert == -1) {
                        Toast.makeText(getApplicationContext(),
                                "Error Occurred", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Successfully inserted, with ID " + resultInsert, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.updateBut:
                    long resultUpdate = myDBHelper.update(Integer.parseInt(getString(id)),
                            getString(name), getString(city));

                    if (resultUpdate == 0) {
                        Toast.makeText(getApplicationContext(),
                                "Error Occurred", Toast.LENGTH_SHORT).show();
                    } else if (resultUpdate == 1){
                        Toast.makeText(getApplicationContext(),
                                "Successfully updated, with ID " + resultUpdate, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Multiple error occured" + resultUpdate, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.deleteBut:
                    long resultDelete = myDBHelper.delete(Integer.parseInt(getString(id)));

                    if (resultDelete == 0) {
                        Toast.makeText(getApplicationContext(),
                                "Error Occurred", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Successfully deleted" , Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.loadBut:
                    Cursor cursor = myDBHelper.getAllRecords();
                    //gets a specific data (or more than one) based on specific data in the database
                    //Cursor cursor = myDBHelper.getDataBasedOnQuery("SELECT * FROM " +
                            //myDBHelper.TABLENAME + " WHERE " + MyDBHelper.CITY + " = 'New York City' ");
                    StringBuffer finalData = new StringBuffer();

                    for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        finalData.append(cursor.getInt(cursor.getColumnIndex(myDBHelper.ID)));
                        finalData.append(" - ");
                        finalData.append(cursor.getString(cursor.getColumnIndex(myDBHelper.NAME)));
                        finalData.append(" - ");
                        finalData.append(cursor.getString(cursor.getColumnIndex(myDBHelper.CITY)));
                        finalData.append("\n");
                    }

                    data.setText(finalData);
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        myDBHelper.openDB();
    }

    //when activity gets destroyed close database
    @Override
    protected void onStop() {
        super.onStop();
        myDBHelper.closeDB();
    }

    public String getString(EditText editText) {
        return editText.getText().toString().trim();
    }
}
