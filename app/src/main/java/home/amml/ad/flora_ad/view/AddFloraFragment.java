package home.amml.ad.flora_ad.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.databinding.FragmentAddFloraBinding;
import home.amml.ad.flora_ad.model.entity.DataImage;
import home.amml.ad.flora_ad.model.entity.Flora;
import home.amml.ad.flora_ad.model.entity.Imagen;
import home.amml.ad.flora_ad.viewmodel.AddFloraViewModel;
import home.amml.ad.flora_ad.viewmodel.AddImagenViewModel;

public class AddFloraFragment extends Fragment {

    private FragmentAddFloraBinding binding;

    private AddFloraViewModel afvm;
    private AddImagenViewModel aivm;
    private DataImage dataImage = null;

    private Bundle bundle;

    private TextInputEditText et_Nombre_AddFlora, et_Familia_AddFlora, et_Altitud_AddFlora,
            et_Habitat_AddFlora, et_Fitosociologia_AddFlora, et_Biotipo_AddFlora,
            et_BiologiaReproductiva_AddFlora, et_Floracion_AddFlora, et_Fructificacion_AddFlora,
            et_ExpresionSexual_AddFlora, et_Polinizacion_AddFlora, et_Dispersion_AddFlora,
            et_NumCromosomatico_AddFlora, et_ReproduccionAsexual_AddFlora, et_Distribucion_AddFlora,
            et_Biologia_AddFlora, et_Demografia_AddFlora, et_Amenazas_AddFlora,
            et_MedidasPropuestas_AddFlora;

    private ArrayList<TextInputEditText> editTextArrayList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddFloraBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    private void addNewFlora(){
        //Creamos un objeto Flora en el que guardar los datos
        Flora flora = new Flora();
        flora.setAtributtes(getEditTextsValues());
        //Subimos la flora a la base de datos usando la api
        afvm.createFlora(flora);
    }

    private void addNewImage(Long id){
        Imagen imagen = new Imagen();
        imagen.nombre = dataImage.nombre;
        imagen.descripcion = dataImage.descripcion;
        imagen.idflora = id;
        Log.v("AFF addNewImage", "flora id " + id);
        aivm.saveImagenWithoutIntent(imagen);
    }

    private void dataBundle(){
        if (getArguments() == null || getArguments().isEmpty()){
            bundle = new Bundle();
        } else{
            bundle = getArguments();
            ArrayList<String> editTextsValues = bundle.getStringArrayList("editTextsValues");
            fillEditTexts(editTextsValues);
            if(bundle.getBoolean("completeBundle")){
                dataImage = bundle.getParcelable("dataImage");
                Log.v("AFF datosBundle", dataImage.uri.toString());
                binding.ivUploadAddFlora.setImageURI(dataImage.uri);
                binding.ivUploadAddFlora.setVisibility(View.VISIBLE);
            }
        }
    }

    void dataObserver(){
        afvm.getAddFloraLiveData().observeForever(new Observer<Long>() {
            @Override
            public void onChanged(Long idFlora) {
                if(idFlora > 0){
                    if(dataImage != null){
                        addNewImage(idFlora);
                    }
                    Toast.makeText(getContext(), "Flora creada", Toast.LENGTH_SHORT).show();
                }
                afvm.getAddFloraLiveData().removeObserver(this);
                navigateToFirstFragment();
            }
        });
    }

    private void fillEditTextArrayList(){
        editTextArrayList = new ArrayList<>();

        editTextArrayList.add(et_Nombre_AddFlora);
        editTextArrayList.add(et_Familia_AddFlora);
        editTextArrayList.add(et_Altitud_AddFlora);

        editTextArrayList.add(et_Habitat_AddFlora);
        editTextArrayList.add(et_Fitosociologia_AddFlora);
        editTextArrayList.add(et_Biotipo_AddFlora);

        editTextArrayList.add(et_BiologiaReproductiva_AddFlora);
        editTextArrayList.add(et_Floracion_AddFlora);
        editTextArrayList.add(et_Fructificacion_AddFlora);

        editTextArrayList.add(et_ExpresionSexual_AddFlora);
        editTextArrayList.add(et_Polinizacion_AddFlora);
        editTextArrayList.add(et_Dispersion_AddFlora);

        editTextArrayList.add(et_NumCromosomatico_AddFlora);
        editTextArrayList.add(et_ReproduccionAsexual_AddFlora);
        editTextArrayList.add(et_Distribucion_AddFlora);

        editTextArrayList.add(et_BiologiaReproductiva_AddFlora);
        editTextArrayList.add(et_Demografia_AddFlora);
        editTextArrayList.add(et_Amenazas_AddFlora);

        editTextArrayList.add(et_MedidasPropuestas_AddFlora);
    }

    private void fillEditTexts(ArrayList<String> editTextsValues){
        for (int i = 0; i < editTextArrayList.size(); i++) {
            if(editTextsValues.get(i).equals("NO ESPECIFICADO")){
                editTextArrayList.get(i).setText("");
            } else{
                editTextArrayList.get(i).setText(editTextsValues.get(i));
            }
        }
    }

    //True nulo o vacío, false lleno
    private ArrayList<Boolean> getEditTextsNullsAndEmpties(){
        ArrayList<Boolean> result = new ArrayList<>();
        for (TextInputEditText editText: editTextArrayList) {
            if(editText.getText() == null || editText.getText().toString().trim().isEmpty()){
                result.add(true);
            } else{
                result.add(false);
            }
        }
        return result;
    }

    private ArrayList<String> getEditTextsValues(){
        ArrayList<String> editTextsValues = new ArrayList<>();
        ArrayList<Boolean> editTextsNullsOrEmpties = getEditTextsNullsAndEmpties();
        for (int i = 0; i < editTextsNullsOrEmpties.size(); i++) {
            if (editTextsNullsOrEmpties.get(i)){
                editTextsValues.add("NO ESPECIFICADO");
            } else{
                editTextsValues.add(editTextArrayList.get(i).getText().toString());
            }
        }
        return editTextsValues;
    }

    private void hideMenu(){
        setHasOptionsMenu(true);
    }

    private void initialize(){
        hideMenu();
        afvm = new ViewModelProvider(this).get(AddFloraViewModel.class);
        aivm = new ViewModelProvider(this).get(AddImagenViewModel.class);
        initializeButtons();
        initializeEditTexts();
        fillEditTextArrayList();
        dataBundle();
        dataObserver();
        textListener();
    }

    private void initializeButtons(){
        binding.btAddImageAddFlora.setOnClickListener(view -> {
            bundle = null;
            bundle = new Bundle();
            bundle.putByte("fragmentOrigin", (byte) 1);
            bundle.putStringArrayList("editTextsValues", getEditTextsValues());
            navigateToAddImagenFragment();
        });
        binding.btCancelAddFlora.setOnClickListener(view -> navigateToFirstFragment());

        binding.btAddAddFlora.setOnClickListener(view -> {
            if(et_Nombre_AddFlora.getText().toString().trim().isEmpty()
                    || et_Nombre_AddFlora.getText().toString().equals("NO ESPECIFICADO")){
                binding.tiNombreAddFlora.setErrorEnabled(true);
                binding.tiNombreAddFlora.setHelperText("* Es necesario rellenar este campo");
            } else{
                addNewFlora();
            }
        });
    }

    private void initializeEditTexts() {
        binding.tiNombreAddFlora.setErrorEnabled(false);
        binding.tiNombreAddFlora.setHelperText("* Este campo es obligatorio");

        et_Nombre_AddFlora = binding.etNombreAddFlora;
        et_Familia_AddFlora = binding.etFamiliaAddFlora;
        et_Altitud_AddFlora = binding.etAltitudAddFlora;

        et_Habitat_AddFlora = binding.etHabitatAddFlora;
        et_Fitosociologia_AddFlora = binding.etFitosociologiaAddFlora;
        et_Biotipo_AddFlora = binding.etBiotipoAddFlora;

        et_BiologiaReproductiva_AddFlora = binding.etBiologiaReproductivaAddFlora;
        et_Floracion_AddFlora = binding.etFloracionAddFlora;
        et_Fructificacion_AddFlora = binding.etFructificacionAddFlora;

        et_ExpresionSexual_AddFlora = binding.etExpresionSexualAddFlora;
        et_Polinizacion_AddFlora = binding.etPolinizacionAddFlora;
        et_Dispersion_AddFlora = binding.etDispersionAddFlora;

        et_NumCromosomatico_AddFlora = binding.etNumCromosomaticoAddFlora;
        et_ReproduccionAsexual_AddFlora = binding.etReproduccionAsexualAddFlora;
        et_Distribucion_AddFlora = binding.etDistribucionAddFlora;

        et_Biologia_AddFlora = binding.etBiologiaAddFlora;
        et_Demografia_AddFlora = binding.etDemografiaAddFlora;
        et_Amenazas_AddFlora = binding.etAmenazasAddFlora;

        et_MedidasPropuestas_AddFlora = binding.etMedidasPropuestasAddFlora;
    }

    private void navigateToFirstFragment(){
        NavHostFragment.findNavController(AddFloraFragment.this)
                .navigate(R.id.action_addFloraFragment_to_FirstFragment);
    }

    private void navigateToAddImagenFragment(){
        NavHostFragment.findNavController(AddFloraFragment.this)
                .navigate(R.id.action_addFloraFragment_to_addImagenFragment, bundle);
    }

    void textListener(){
        et_Nombre_AddFlora.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() >= 0){
                    binding.tiNombreAddFlora.setErrorEnabled(false);
                } else{
                    binding.tiNombreAddFlora.setErrorEnabled(true);
                }
            }
        });
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
        bundle = null;
    }
}