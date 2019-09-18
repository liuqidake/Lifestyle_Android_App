package com.example.nicolemorris.lifestyle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class
ProfilePicFragment extends Fragment
        implements View.OnClickListener {

    public static final int PICK_IMAGE = 1;

    Button bTakePic,bSelectPic,bNext;
    ProfilePicOnDataPass mDataPasser;
    ImageView mIvPic;
    Bitmap image;




    //Callback interface
    public interface ProfilePicOnDataPass{
        public void onProfilePicDataPass(Bitmap image);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (ProfilePicOnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_pic, container, false);
        bNext = view.findViewById(R.id.b_next);
        bNext.setOnClickListener(this);

        bTakePic = view.findViewById(R.id.button_take_pic);
        bTakePic.setOnClickListener(this);

        bSelectPic = view.findViewById(R.id.button_select_pic);
        bSelectPic.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data == null){
                Toast.makeText(getContext(), "Unable to upload image", Toast.LENGTH_SHORT).show();
            }else{
                mIvPic = (ImageView) getActivity().findViewById(R.id.iv_profile);
                Bundle extras = data.getExtras();
                if(extras != null){
                    System.out.println("extrrrrrrrrrrrrrrrrrrrrra :"+extras==null);
                    image = (Bitmap) extras.get("data");
                    mIvPic.setImageBitmap(image);
                }else {
                    try {
                        Uri imageFilePath = data.getData();
                        image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageFilePath);
                        mIvPic.setImageBitmap(image);
                    }catch(Exception e){
                        Toast.makeText(getActivity(), "Not able to get the image from gallery", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }else{
            System.out.println("result is not ok");
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){

            case R.id.b_next: {
                //NEED TO ADD DATE TO PASS FOR STORAGE :)
                mDataPasser.onProfilePicDataPass(image);
                break;
            }
            case R.id.button_select_pic:{
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                // Launching the Intent
                startActivityForResult(intent,PICK_IMAGE);
                break;
            }
            case R.id.button_take_pic: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivityForResult(intent, PICK_IMAGE);
                }
                break;
            }
        }
    }

}
