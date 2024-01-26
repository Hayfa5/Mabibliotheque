package com.example.mabibliotheque;


import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mabibliotheque.R;
import com.example.mabibliotheque.bdhelperhistoire;
import com.example.mabibliotheque.histoire;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AjouterHistoire extends Fragment {

    private TextInputLayout editTextNom, editTextDescription, editTextURL;
    private Button btnAjouter;
    private ImageView imageViewHistoire;
    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int STORAGE_PERMISSION_CODE = 103;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajouter_histoire, container, false);

        editTextNom = view.findViewById(R.id.Nomhistoire);
        editTextDescription = view.findViewById(R.id.Description);
        editTextURL = view.findViewById(R.id.URL);
        btnAjouter = view.findViewById(R.id.Ajouter);
        imageViewHistoire = view.findViewById(R.id.imagehistoire);

        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterHistoire();
            }
        });

        imageViewHistoire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });
        return view;
    }

    private void ajouterHistoire() {

        String nom = editTextNom.getEditText().getText().toString();
        String Description = editTextDescription.getEditText().getText().toString();
        String URL = editTextURL.getEditText().getText().toString();
        Uri imageUri = (Uri) imageViewHistoire.getTag();

        if (nom.isEmpty() || Description.isEmpty() || URL.isEmpty()) {
            Toast.makeText(requireActivity(), R.string.champs_obligatoires, Toast.LENGTH_SHORT).show();
        } else if (imageUri == null) {
            Toast.makeText(requireActivity(), R.string.veuillez_choisir_image, Toast.LENGTH_SHORT).show();
        } else {

            histoire nouvelleHistoire = new histoire(nom, Description, URL, imageUri.toString());
            bdhelperhistoire dbHelper = new bdhelperhistoire(requireContext());
            dbHelper.ajouterHistoire(nouvelleHistoire);

            Toast.makeText(requireActivity(), R.string.histoire_ajoutee_succes, Toast.LENGTH_SHORT).show();

            editTextNom.getEditText().setText("");
            editTextDescription.getEditText().setText("");
            editTextURL.getEditText().setText("");
            imageViewHistoire.setImageResource(R.drawable.ic_menu_camera);
        }

    }


    private void pickImageFromGallery() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        } else {

            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                pickImageFromGallery();
            } else {

                Toast.makeText(requireActivity(), "La permission de lecture du stockage externe est nécessaire.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == requireActivity().RESULT_OK && requestCode == GALLERY_REQUEST_CODE && data != null) {
            Uri selectedImage = data.getData();
            imageViewHistoire.setImageURI(selectedImage);
            imageViewHistoire.setTag(selectedImage);

        }
    }

}


