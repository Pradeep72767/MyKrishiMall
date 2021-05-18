package com.example.mykrishimall.ui.Logout;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
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

import com.example.mykrishimall.MainActivity;
import com.example.mykrishimall.R;
import com.example.mykrishimall.SellerHomeActivity;
import com.example.mykrishimall.ui.Category.CategoryViewModel;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class LogoutFragment extends Fragment {

    private LogoutViewModel logoutViewModel;
    private Button logoutBtn;
    private TextView clickableText, clickableText2, clickableText3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.logout_fragment, container, false);

        logoutBtn = root.findViewById(R.id.customer_logoutbtn);
        clickableText = root.findViewById(R.id.logout_text2);
        clickableText2 = root.findViewById(R.id.logout_text3);
        clickableText3 = root.findViewById(R.id.logout_text4);
        clickableText.setMovementMethod(LinkMovementMethod.getInstance());
        clickableText2.setMovementMethod(LinkMovementMethod.getInstance());
        clickableText3.setMovementMethod(LinkMovementMethod.getInstance());






        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();

                Paper.book().destroy();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();


            }
        });
        return root;
    }

}