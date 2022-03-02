package home.amml.ad.flora_ad.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.databinding.FragmentAddImagenBinding;
import home.amml.ad.flora_ad.model.entity.Imagen;
import home.amml.ad.flora_ad.viewmodel.AddImagenViewModel;

public class AddImagenFragment extends Fragment {

    private FragmentAddImagenBinding binding;

    private ActivityResultLauncher<Intent> launcher;
    private Bundle bundle;
    private Intent resultadoImagen = null;

    private TextInputEditText et_Nombre_AddImagen, et_Descripcion_AddImagen;

    private Uri imageUri = null;

    private boolean imageSelected;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddImagenBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    private void addNewImage(){
        selectImage();
        if(imageSelected && imageUri != null){
            bundle.putBoolean("completeBundle", true);
            //Añadir el resto al bundle
            String[] dataImage = new String[3];
            //Uri de la imagen
            dataImage[0] = imageUri.toString();
            //Nombre de la imagen
            String nombre = String.valueOf(new Date().getTime());
            if(et_Nombre_AddImagen.getText() != null
                    && !et_Nombre_AddImagen.getText().toString().isEmpty()){
                nombre = et_Nombre_AddImagen.getText().toString() + "_" + new Date().getTime();
            }
            dataImage[1] = nombre;
            //Descripcion de la imagen
            String descripcion = "flora bonita";
            if(et_Descripcion_AddImagen.getText() != null
                    && !et_Descripcion_AddImagen.getText().toString().isEmpty()){
                descripcion = et_Nombre_AddImagen.getText().toString();
            }
            dataImage[2] = descripcion;
            //Pasar array al bundle
            bundle.putStringArray("dataImage", dataImage);
        } else{
            bundle.putBoolean("completeBundle", false);
        }
        navigateToAddFloraFragment(bundle);
    }

    ActivityResultLauncher<Intent> getLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Respuesta al resultado de haber seleccionado una imagen
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        resultadoImagen = result.getData();
                        imageUri = resultadoImagen.getData();
                        imageSelected = true;
                    }
                }
        );
    }

    Intent getContentIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        return intent;
    }

    private void initialize(){
        bundle = getArguments();
        imageSelected = false;
        launcher = getLauncher();
        initializeButtons();
        initializeEditTexts();
        initializeImage();
    }

    private void initializeButtons(){
        binding.btCancelAddImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("completeBundle", false);
                navigateToAddFloraFragment(bundle);
            }
        });

        binding.btAddAddImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewImage();
            }
        });
    }

    private void initializeEditTexts() {
        et_Nombre_AddImagen = binding.etNombreAddImagen;
        et_Descripcion_AddImagen = binding.etDescripcionAddImagen;
    }

    private void initializeImage(){
        binding.ivUploadAddImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void navigateToAddFloraFragment(Bundle bundle){
        NavHostFragment.findNavController(AddImagenFragment.this)
                .navigate(R.id.action_addImagenFragment_to_addFloraFragment, bundle);
    }

    private void selectImage(){
        Intent intent = getContentIntent();
        launcher.launch(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}