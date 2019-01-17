package hw1.demirtas.tarik.com.instacomm;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    Button btnMake;
    Button btnAdd;

    DatabaseHelper dbHelper;

    Dialog customDialog;

    ArrayList<Comment> mArrayList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter mRecyclerAdapter;

    GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        btnMake = findViewById(R.id.btnMake);
        btnAdd = findViewById(R.id.btnCreateYourOwn);

        dbHelper = new DatabaseHelper(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerComments);
        for(int i = 0; i<Commons.comments.size();i++){
            if(Commons.curType == Commons.comments.get(i).getType()){
                if(Commons.curUser.getType() != 1){
                    if(Commons.comments.get(i).getPremium() == 0)
                        mArrayList.add(Commons.comments.get(i));
                }
                else
                mArrayList.add(Commons.comments.get(i));
            }
        }
        mLayoutManager = new LinearLayoutManager(CommentActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerAdapter = new MyRecyclerViewAdapter(CommentActivity.this, mArrayList);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mDetector = new GestureDetectorCompat(CommentActivity.this, new CommentActivity.MyGestureListener());

        if(Commons.curUser.getType() == 0){
            btnAdd.setVisibility(View.INVISIBLE);
        }

        /* Create an intent E-Mail */
        btnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Commons.curUser.getUses() < 2 || Commons.curUser.getType() == 1) {
                    Commons.curUser.setUses(Commons.curUser.getUses() + 1);
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"tarik.emre.demirtas@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Instacomm Auto Message");
                    i.putExtra(Intent.EXTRA_TEXT, Commons.getCurrentComment());
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(CommentActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(CommentActivity.this,"This can all change if you buy me a coffee you know :)",
                            Toast.LENGTH_LONG).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog();
            }
        });
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            CommentActivity.this.finish();
            return true;
        }
    }

    /* Function to display a custom dialog */
            public void displayDialog(){
                customDialog = new Dialog(CommentActivity.this);
                customDialog.setContentView(R.layout.dialog_add);

                Button btnClose = customDialog.findViewById(R.id.btnClose);
                final EditText etComment = customDialog.findViewById(R.id.etNewText);
                Button btnInsert = customDialog.findViewById(R.id.btnAddComm);

                /* Add a new Comment*/
                btnInsert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ins = etComment.getText().toString();
                        int id = Commons.getID();
                        int type = Commons.curType;
                        int premium = 1;

                        CommentsDB.insertComment(dbHelper,id,ins,premium,type);
                        Toast.makeText(CommentActivity.this,"You have Inserted a Comment! Congratulations :)",Toast.LENGTH_LONG).show();
                        customDialog.dismiss();
                    }
                });

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });
        customDialog.show();
        }
}
