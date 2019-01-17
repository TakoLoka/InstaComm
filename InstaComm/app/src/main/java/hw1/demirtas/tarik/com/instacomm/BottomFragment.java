package hw1.demirtas.tarik.com.instacomm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class BottomFragment extends Fragment {
    ImageView img;
    TextView txtCategory;
    TextView txtDesc;
    View view;

    int images[] = new int[]{R.drawable.shocked, R.drawable.cute, R.drawable.funny,R.drawable.scared,R.drawable.supportive};
    String categories[] = {"Shocked","Cute","Funny","Scared","Supportive"};
    String descriptions[] = {"Well That was a Shocker","I must cuddle you for years!","Hilarious some peoples minds are",
            "I'm Out!","Hang in there bro!"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img = view.findViewById(R.id.imgCategory);
        txtCategory = view.findViewById(R.id.tvCategory);
        txtDesc = view.findViewById(R.id.tvDescription);

        txtCategory.setText(categories[0]);
        txtDesc.setText(descriptions[0]);
        Commons.curType = 1;
    }

    public void changeImage(int index){
        img.setImageResource(images[index]);
        txtCategory.setText(categories[index]);
        txtDesc.setText(descriptions[index]);
    }
}
