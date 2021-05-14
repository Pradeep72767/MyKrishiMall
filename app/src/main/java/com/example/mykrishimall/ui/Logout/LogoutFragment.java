package com.example.mykrishimall.ui.Logout;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.mykrishimall.R;
//import com.example.mykrishimall.ui.gallery.GalleryViewModel;

public class LogoutFragment extends Fragment {

    //private CategoryViewModel categoryViewModel;
    private TextView headingLogoout, scheme1;
    private Button useerLogutBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_logout, container, false);

        headingLogoout  = root.findViewById(R.id.logout_text1);
        headingLogoout.setMovementMethod(LinkMovementMethod.getInstance());



        return root;
    }

}