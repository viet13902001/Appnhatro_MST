package activity;

import android.os.Bundle;
//import org.apache.commons.io.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mstappdemo.R;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.FileUtils;
//import com.google.firebase.crashlytics.buildtools.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Comment extends AppCompatActivity {

    ArrayList<String> mangListvieww;
    EditText editTextt;
    ListView listVieww;
    Button buttonn, button1,button2,buttonq;

    Toolbar toolbarcomment;

    TextView rateCount, showRating;
    RatingBar ratingBar;
    float rateValue;
    String temp;


    UserLocalStore userLocalStore;

    int vitri =100000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        listVieww = (ListView) findViewById(R.id.listView);
        mangListvieww = new ArrayList<String>();

        rateCount = findViewById(R.id.rateCount);
        ratingBar = findViewById(R.id.ratingBar);

        editTextt = findViewById(R.id.editText);
        buttonn = findViewById(R.id.button);
//        button1 = findViewById(R.id.button2);
//        button2 = findViewById(R.id.button3);
//        buttonq = findViewById(R.id.button4);

//        mangListview.add("Linh:\t\t\t\t\t\t\t\t\t\t" + "Good" + "\nCMT: " + "Nha dep 10 diem");
//        mangListview.add("Sang:\t\t\t\t\t\t\t\t\t\t" + "Bad" + "\nCMT: " + "Nha xau 4 diem");
//        mangListview.add("Tanh:\t\t\t\t\t\t\t\t\t\t" + "Good" + "\nCMT: " + "Nha dep 8 diem");
        readItems();
        ArrayAdapter adapter = new ArrayAdapter(
                Comment.this,
                android.R.layout.simple_list_item_1,
                mangListvieww
        );
        listVieww.setAdapter(adapter);
//        readItems(); // <---- Add this line

        userLocalStore  = new UserLocalStore(this);



//        listVieww.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                editTextt.setText(mangListvieww.get(i));
//                vitri = i;
//            }
//        });

//        buttonq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = userLocalStore.getLoggedInUser();

                temp = rateCount.getText().toString();
//                showRating.setText("Your Rating: \n" + temp + "\n" + editText.getText());



                String ten = editTextt.getText().toString();
                ten = user.username + " :\t\t\t\t\t\t\t\t\t\t" + temp + "\nCMT: \t" + ten;
                mangListvieww.add(ten);
                adapter.notifyDataSetChanged();
                editTextt.setText("");
                ratingBar.setRating(0);
                rateCount.setText("");
                writeItems();
            }
        });

//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String t = editText.getText().toString();
//                mangListview.set(vitri, t);
//                adapter.notifyDataSetChanged();
//                editText.setText("");
//                vitri=1000000;
//            }
//        });
//
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mangListvieww.remove(vitri);
//                adapter.notifyDataSetChanged();
//                writeItems();
//            }
//        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                rateValue = ratingBar.getRating();

                if (rateValue <= 1)
                    rateCount.setText("So Bad ");
                else if (rateValue <= 2)
                    rateCount.setText("Bad ");
                else if (rateValue <= 3)
                    rateCount.setText("Good ");
                else if (rateValue <= 4)
                    rateCount.setText("Very Good ");
                else if (rateValue <= 5)
                    rateCount.setText("Best ");
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                temp = rateCount.getText().toString();
//                showRating.setText("Your Rating: \n" + temp + "\n" + editText.getText());
//                editText.setText("");
//                ratingBar.setRating(0);
//                rateCount.setText("");
//            }
//        });

        toolbarcomment = findViewById(R.id.toolbarcomment);


    }
    private void ActionToolbar() {
        setSupportActionBar(toolbarcomment);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarcomment.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            ArrayList<String> tmp = new ArrayList<String>(FileUtils.readLines(todoFile));
//            mangListview = new ArrayList<String>(FileUtils.readLines(todoFile));
            for (int i=0;i<tmp.size()-1;i++){
                mangListvieww.add(tmp.get(i++)+"\n"+tmp.get(i));
//                mangListview.add("35345terge5yehgerth");
            }

        } catch (IOException e) {
            mangListvieww = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, mangListvieww);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
