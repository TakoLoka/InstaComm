package hw1.demirtas.tarik.com.instacomm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CategoriesFragment extends Fragment {
    Spinner spinnerCategories;
    Button selectCategory;

    boolean isDefaultSelection = true;
    int selectedIndex = 0;

    interface  TopFragmentEventListener{
        public void imageChange(int index);
    }

    TopFragmentEventListener  listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof  TopFragmentEventListener)
            listener = (TopFragmentEventListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerCategories = view.findViewById(R.id.spCategories);
        selectCategory = view.findViewById(R.id.btnChoose);

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isDefaultSelection)
                    isDefaultSelection = false;
                else{
                     Commons.curType = position + 1;

                    //Call the imageChnage method which is implemented in the main activity
                    listener.imageChange(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String view = spinnerCategories.getSelectedItem().toString();
                Toast.makeText(getActivity(),"Selected the "+view+" category",Toast.LENGTH_SHORT).show();
                Commons.curType = spinnerCategories.getSelectedItemPosition();
            }
        });
    }
}
