package home.amml.ad.flora_ad.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.databinding.FragmentAddImagenBinding;
import home.amml.ad.flora_ad.model.entity.DataImage;

public class AddImagenFragment extends Fragment {

    private FragmentAddImagenBinding binding;

    private ActivityResultLauncher<Intent> launcher;
    private Bundle bundle;
    private Intent resultadoImagen = null;

    private TextInputEditText et_Nombre_AddImagen, et_Descripcion_AddImagen;

    private Uri imageUri = null;

    private boolean imageSelected;

    //0 indica que esta clase es llamada desde SecondFragment, 1 desde AddFloraFragment
    private byte fragmentOrigin;

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
        if(imageSelected && imageUri != null){
            //Nombre de la imagen
            String nombre = "";
            if(checkEditTextContent(et_Nombre_AddImagen)){
                if(et_Nombre_AddImagen.getText().toString().length() >= 50){
                    nombre = et_Nombre_AddImagen.getText().toString().trim().substring(0, 40);
                } else{
                    nombre = et_Nombre_AddImagen.getText().toString().trim();
                }
                nombre = nombre.toLowerCase();
                nombre += "_" + new Date().getTime();
            } else{
                nombre = String.valueOf(new Date().getTime());
            }
            //Descripcion de la imagen
            String descripcion;
            if(checkEditTextContent(et_Descripcion_AddImagen)){
                descripcion = et_Nombre_AddImagen.getText().toString();
            } else{
                descripcion = "flora bonita";
            }
            //Creamos un objeto DataImage
            DataImage dataImage = new DataImage(imageUri, nombre, descripcion);
            //Indicamos que el bundle está completo
            bundle.putBoolean("completeBundle", true);
            bundle.putByte("decideBundle", (byte) 1);
            //Pasar objeto al bundle
            bundle.putParcelable("dataImage", dataImage);
        } else{
            bundle.putBoolean("completeBundle", false);
            bundle.putByte("decideBundle", (byte) 1);
        }
    }

    private boolean checkEditTextContent(TextInputEditText et){
        boolean result = false;
        if(et.getText() != null && !et.getText().toString().trim().isEmpty()){
            result = true;
        }
        return result;
    }

    ActivityResultLauncher<Intent> getLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Respuesta al resultado de haber seleccionado una imagen
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        resultadoImagen = result.getData();
                        imageUri = resultadoImagen.getData();
                        binding.ivUploadAddImagen.setImageURI(imageUri);
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

    private void hideMenu(){
        setHasOptionsMenu(true);
    }

    private void initialize(){
        bundle = getArguments();
        fragmentOrigin = bundle.getByte("fragmentOrigin");
        imageSelected = false;
        launcher = getLauncher();
        hideMenu();
        initializeButtons();
        initializeEditTexts();
        initializeImage();
    }

    private void initializeButtons(){
        binding.btCancelAddImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChildFragmentManager().popBackStack();
            }
        });

        binding.btAddAddImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewImage();
                Log.v("AIF", fragmentOrigin + "");
                if(fragmentOrigin == 0){
                    navigateToSecondFragment(bundle);
                } else{
                    navigateToAddFloraFragment(bundle);
                }
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

    private void navigateToSecondFragment(Bundle bundle){
        NavHostFragment.findNavController(AddImagenFragment.this)
                .navigate(R.id.action_addImagenFragment_to_SecondFragment, bundle);
    }

    private void selectImage(){
        Intent intent = getContentIntent();
        launcher.launch(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
        if(menu.findItem(R.id.delete_opt) != null){
            menu.findItem(R.id.delete_opt).setVisible(false);
        }
        if(menu.findItem(R.id.edit_opt) != null){
            menu.findItem(R.id.edit_opt).setVisible(false);
        }
        if(menu.findItem(R.id.edit_off_opt) != null){
            menu.findItem(R.id.edit_off_opt).setVisible(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}