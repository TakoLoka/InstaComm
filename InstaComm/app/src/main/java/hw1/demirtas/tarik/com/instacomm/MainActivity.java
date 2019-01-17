package hw1.demirtas.tarik.com.instacomm;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoriesFragment.TopFragmentEventListener{
    GestureDetectorCompat mDetector;
    DatabaseHelper dbHelper;

    CategoriesFragment cf;
    BottomFragment bottomFragment;

    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(cf == null){
            cf = (CategoriesFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTop);
        }
        if(bottomFragment == null)
            bottomFragment = (BottomFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentBottom);


        mDetector = new GestureDetectorCompat(this,new MyGestureListener());
        dbHelper = new DatabaseHelper(MainActivity.this);

        // Copy Database for the Device for Data Persistence
        try {
            String fileToDatabase = "/data/data/" + getPackageName() + "/databases/"+DatabaseHelper.DATABASE_NAME;
            File file = new File(fileToDatabase);
            File pathToDatabasesFolder = new File("/data/data/" + getPackageName() + "/databases/");
            if (!file.exists()) {
                pathToDatabasesFolder.mkdirs();
                Log.d("BURDA", "BURDA");
                CopyDB( getResources().getAssets().open(DatabaseHelper.DATABASE_NAME),
                        new FileOutputStream(fileToDatabase));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Commons.comments =(ArrayList<Comment>) CommentsDB.getAllComments(dbHelper);
            Intent intent = new Intent(MainActivity.this, CommentActivity.class);
            startActivity(intent);
            return true;
        }
    }

    /* Copy bytes in a DB file */
    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        // Copy 1K bytes at a time
        byte[] buffer = new byte[1024];
        int length;
        Log.d("BURDA", "BURDA2");

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
            Log.d("BURDA", "BURDA3");

        }
        inputStream.close();
        outputStream.close();
    }

    /*Change the image on BottomFragment according to the selected spinner element
     in Categories Fragment*/
    @Override
    public void imageChange(int index) {
        if(bottomFragment !=null && bottomFragment.isInLayout())
            bottomFragment.changeImage(index);
    }
}
