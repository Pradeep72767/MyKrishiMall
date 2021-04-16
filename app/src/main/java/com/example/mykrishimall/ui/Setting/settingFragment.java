package com.example.mykrishimall.ui.Setting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mykrishimall.HomeActivity;
import com.example.mykrishimall.MainActivity;
import com.example.mykrishimall.Prevalent.Prevalent;
import com.example.mykrishimall.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
//import com.example.mykrishimall.ui.gallery.GalleryViewModel;

public class settingFragment extends Fragment {

    private SettingViewModel settingViewModel;

    private CircleImageView profileImageView;
    private EditText userPhoneEditText, userFullNameEditText, userAddressEditText;
    private TextView profileChangeBtn, closeTextBtn, updateTextBtn;

    private Uri imageUri;
    private String myUrl;
    private StorageTask uploadTask;
    private StorageReference storageProfilePictureReference;
    private String checker = "";
    private Bitmap bitmap;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.setting_fragment, container, false);
        //final TextView textView = root.findViewById(R.id.text_setting);
//        Toast.makeText(getContext(), "Inside setting fragment", Toast.LENGTH_SHORT).show();

        storageProfilePictureReference = FirebaseStorage.getInstance().getReference();

        profileImageView = root.findViewById(R.id.setting_profile_image);
        userPhoneEditText = root.findViewById(R.id.setting_phone_number);
        userFullNameEditText = root.findViewById(R.id.setting_full_name);
        userAddressEditText = root.findViewById(R.id.setting_address);
        profileChangeBtn = root.findViewById(R.id.profile_image_change_btn);
        closeTextBtn = root.findViewById(R.id.close_setting_btn);
        updateTextBtn = root.findViewById(R.id.update_setting_btn);
        progressBar = root.findViewById(R.id.profile_image_change_progress);

//        userInfoDisplay(profileImageView, userFullNameEditText, userPhoneEditText, userAddressEditText);

//        closeTextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                getActivity().finish();
//            }
//        });

        updateTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                if (checker.equals("clicked"))
//                {
//                    userInfoSaved();
//                }
//                else
//                {
                    updateOnlyUserInfo();
//                }
            }
        });

        profileChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                checker = "clicked";
//                CropImage.startPickImageActivity(getActivity());
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(intent, 1);
                Toast.makeText(getContext(), "Inside button", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    private void updateOnlyUserInfo()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", userFullNameEditText.getText().toString());
        userMap.put("phoneOrder", userPhoneEditText.getText().toString());
        userMap.put("address", userAddressEditText.getText().toString());

        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);



        startActivity(new Intent(getActivity(), MainActivity.class));
        Toast.makeText(getContext(), "profile information updated", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==getActivity().RESULT_OK && data!=null)
//        {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            imageUri = result.getUri();
//
////            profileImageView.setImageURI(imageUri);
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
//                profileImageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        else
//        {
//            Toast.makeText(getContext(),"Error, try again", Toast.LENGTH_SHORT).show();
//
//            startActivity(new Intent(getActivity(),settingFragment.class));
//            getActivity().finish();
//        }
//    }
//
//    private void userInfoSaved()
//    {
//        if (TextUtils.isEmpty(userFullNameEditText.getText().toString()))
//        {
//            Toast.makeText(getContext(),"Name is mandatory", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(userPhoneEditText.getText().toString()))
//        {
//            Toast.makeText(getContext(),"Phone number is mandatory", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(userAddressEditText.getText().toString()))
//        {
//            Toast.makeText(getContext(),"Address is mandatory", Toast.LENGTH_SHORT).show();
//        }
//        else if (checker.equals("clicked"))
//        {
//            uploadImage();
//        }
//    }

//    private void uploadImage()
//    {
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle("Update Profile");
//        progressDialog.setMessage("Please wait! updating Your information");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//
//        if (imageUri != null)
//        {
//            final StorageReference fileRef = storageProfilePictureReference.child(Prevalent.currentOnlineUser.getPhone());
//
//            uploadTask = fileRef.putFile(imageUri);
//
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception
//                {
//                    if (!task.isSuccessful())
//                    {
//                        throw task.getException();
//                    }
//                    return fileRef.getDownloadUrl();
//                }
//            })
//                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task)
//                        {
//                            if (task.isSuccessful())
//                            {
//                                Uri downloadUrl = task.getResult();
//                                myUrl = downloadUrl.toString();
//
//                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
//
//                                HashMap<String, Object> userMap = new HashMap<>();
//                                userMap.put("name", userFullNameEditText.getText().toString());
//                                userMap.put("phoneOrder", userPhoneEditText.getText().toString());
//                                userMap.put("address", userAddressEditText.getText().toString());
//                                userMap.put("image", myUrl);
//
//                                ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);
//
//                                progressDialog.dismiss();
//
//                                startActivity(new Intent(getActivity(), HomeActivity.class));
//                                Toast.makeText(getContext(), "profile information updated", Toast.LENGTH_SHORT).show();
//                                getActivity().finish();
//                            }
//                            else
//                            {
//                                progressDialog.dismiss();
//                                Toast.makeText(getContext(), "Error.", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
//        }
//        else
//        {
//            Toast.makeText(getContext(), "image is not uploaded", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText userFullNameEditText, final EditText userPhoneEditText, final EditText userAddressEditText)
    {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    if (snapshot.child("image").exists())
                    {
//                        String image = snapshot.child("image").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();

//                        Picasso.get().load(image).into(profileImageView);

                        userFullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        userAddressEditText.setText(address);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getContext(), "inside activity result", Toast.LENGTH_SHORT).show();
        Log.d("my","inside activity result");
        profileChangeBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filepath);
                profileImageView.setImageBitmap(bitmap);
                final StorageReference storageReference1 = storageProfilePictureReference.child("Profile pictures").child(Prevalent.currentOnlineUser.getPhone()).child(filepath.getLastPathSegment());

                Toast.makeText(getContext(), "inside", Toast.LENGTH_SHORT).show();

                storageReference1.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        profileChangeBtn.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);

                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        storageReference1.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone())
                                        .child("profileImage").setValue(task.getResult().toString());

                            }
                        });
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            progressBar.setVisibility(View.GONE);
            profileChangeBtn.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Can't upload image", Toast.LENGTH_SHORT).show();
        }
    }



}