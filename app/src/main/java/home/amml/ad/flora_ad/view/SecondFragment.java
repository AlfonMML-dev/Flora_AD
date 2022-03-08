package home.amml.ad.flora_ad.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.databinding.FragmentSecondBinding;
import home.amml.ad.flora_ad.model.entity.DataImage;
import home.amml.ad.flora_ad.model.entity.Flora;
import home.amml.ad.flora_ad.model.entity.Imagen;
import home.amml.ad.flora_ad.model.entity.RowsResponse;
import home.amml.ad.flora_ad.view.adapter.SliderImageAdapter;
import home.amml.ad.flora_ad.viewmodel.AddImagenViewModel;
import home.amml.ad.flora_ad.viewmodel.MainActivityViewModel;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private AddImagenViewModel aivm;
    private MainActivityViewModel mavm;
    private Bundle bundle;
    private DataImage dataImage = null;
    private Flora flora;


    private SliderView sliderView;
    private SliderImageAdapter sliderImageAdapter;

    private TextInputEditText et_Nombre_Second, et_Familia_Second, et_Altitud_Second,
            et_Habitat_Second, et_Fitosociologia_Second, et_Biotipo_Second,
            et_BiologiaReproductiva_Second, et_Floracion_Second, et_Fructificacion_Second,
            et_ExpresionSexual_Second, et_Polinizacion_Second, et_Dispersion_Second,
            et_NumCromosomatico_Second, et_ReproduccionAsexual_Second, et_Distribucion_Second,
            et_Biologia_Second, et_Demografia_Second, et_Amenazas_Second,
            et_MedidasPropuestas_Second;

    private ArrayList<TextInputEditText> editTextArrayList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    private void addNewImage(Long id){
        Imagen imagen = new Imagen();
        imagen.nombre = dataImage.nombre;
        imagen.descripcion = dataImage.descripcion;
        imagen.idflora = id;
        Log.v("ASF addNewImage", "flora id " + id);
        aivm.saveImagenWithoutIntent(dataImage.uri, imagen);
    }

    void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("¿Seguro de eliminar la flora?");
        builder.setMessage("Esta acción no se puede deshacer");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mavm.deleteFlora(flora.getId());
                mavm.getFloraLiveData().observe(SecondFragment.this, integer ->{
                    Toast.makeText(getContext(), "Flora borrada", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(SecondFragment.this).popBackStack();
                });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dataBundleFromAddImageFragment(){
        ArrayList<String> editTextsValues = bundle.getStringArrayList("editTextsValues");
        fillEditTexts(editTextsValues);
        if(bundle.getBoolean("completeBundle")){
            dataImage = bundle.getParcelable("dataImage");
            Log.v("AFF dataBundle", dataImage.uri.toString());
        }
    }

    private void dataBundleFromFirstFragment(){
        flora = bundle.getParcelable("flora");
        fillSlider();
        fillEditTexts(flora.getAtributtes());
    }

    void dataObserver(){
        mavm.getEditLiveData().observeForever(new Observer<RowsResponse>() {
            @Override
            public void onChanged(RowsResponse rowsResponse) {
                if(dataImage != null){
                    addNewImage(flora.getId());
                }
                Toast.makeText(getContext(), "Flora actualizada", Toast.LENGTH_SHORT).show();
                mavm.getEditLiveData().removeObserver(this);
                navigateToFirstFragment();
            }
        });
    }

    //0 from FirstFragment, 1 from AddImageFragment
    private byte decideBundle(){
        //Pasarlo desde viewholder también
        bundle = getArguments();
        return bundle.getByte("decideBundle");
    }

    private void enableEditTextArrayList(Boolean state){
        for (TextInputEditText editText: editTextArrayList) {
            editText.setEnabled(state);
        }
    }

    private void fillEditTextArrayList(){
        editTextArrayList = new ArrayList<>();

        editTextArrayList.add(et_Nombre_Second);
        editTextArrayList.add(et_Familia_Second);
        editTextArrayList.add(et_Altitud_Second);

        editTextArrayList.add(et_Habitat_Second);
        editTextArrayList.add(et_Fitosociologia_Second);
        editTextArrayList.add(et_Biotipo_Second);

        editTextArrayList.add(et_BiologiaReproductiva_Second);
        editTextArrayList.add(et_Floracion_Second);
        editTextArrayList.add(et_Fructificacion_Second);

        editTextArrayList.add(et_ExpresionSexual_Second);
        editTextArrayList.add(et_Polinizacion_Second);
        editTextArrayList.add(et_Dispersion_Second);

        editTextArrayList.add(et_NumCromosomatico_Second);
        editTextArrayList.add(et_ReproduccionAsexual_Second);
        editTextArrayList.add(et_Distribucion_Second);

        editTextArrayList.add(et_BiologiaReproductiva_Second);
        editTextArrayList.add(et_Demografia_Second);
        editTextArrayList.add(et_Amenazas_Second);

        editTextArrayList.add(et_MedidasPropuestas_Second);
    }

    private void fillEditTexts(ArrayList<String> editTextsValues){
        for (int i = 0; i < editTextArrayList.size(); i++) {
            if(editTextsValues.get(i) == null || editTextsValues.get(i).equals("NO ESPECIFICADO")){
                editTextArrayList.get(i).setText("");
            } else{
                editTextArrayList.get(i).setText(editTextsValues.get(i));
            }
        }
    }

    private void fillSlider(){
        MutableLiveData<Imagen[]> images = aivm.getImagesLiveData();
        ArrayList<String> sliderDataArrayList = new ArrayList<>();
        sliderView = binding.imageSlider;
        sliderImageAdapter = new SliderImageAdapter(sliderDataArrayList, getContext());
        sliderView.setSliderAdapter(sliderImageAdapter);

        //String url_img = "https://informatica.ieszaidinvergeles.org:10016/AD/felixRDLFapp/public/api/imagen/";
        String url_img = "https://informatica.ieszaidinvergeles.org:10099/ad/felixRDLFApp/public/api/imagen/";
        aivm.getImages(flora.getId());
        images.observe(this, image->{
            for (int i = 0; i < image.length; i++) {
                sliderDataArrayList.add(url_img + "/" + image[i].id);
            }
            sliderImageAdapter.setSliderList(sliderDataArrayList);
        });
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

    private void initialize(){
        aivm = new ViewModelProvider(this).get(AddImagenViewModel.class);
        mavm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        initializeButtons();
        initializeEditTexts();
        initializeFAB();
        fillEditTextArrayList();

        if(decideBundle() == 0){
            dataBundleFromFirstFragment();
        } else{
            dataBundleFromAddImageFragment();
        }
        enableEditTextArrayList(false);
        //Cargar el Slider
        fillSlider();


        dataObserver();
        textListener();

    }

    private void initializeButtons(){
        binding.btCancelSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.btEditSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btEditSecond.setVisibility(View.GONE);
                binding.btEditSecond.setEnabled(false);
                enableEditTextArrayList(true);
                binding.btSaveSecond.setVisibility(View.VISIBLE);
                binding.btSaveSecond.setEnabled(true);
            }
        });

        binding.btSaveSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAction();
            }
        });
    }

    private void initializeEditTexts() {
        binding.tiNombreSecond.setErrorEnabled(false);
        binding.tiNombreSecond.setHelperText("* Este campo es obligatorio");

        et_Nombre_Second = binding.etNombreSecond;
        et_Familia_Second = binding.etFamiliaSecond;
        et_Altitud_Second = binding.etAltitudSecond;

        et_Habitat_Second = binding.etHabitatSecond;
        et_Fitosociologia_Second = binding.etFitosociologiaSecond;
        et_Biotipo_Second = binding.etBiotipoSecond;

        et_BiologiaReproductiva_Second = binding.etBiologiaReproductivaSecond;
        et_Floracion_Second = binding.etFloracionSecond;
        et_Fructificacion_Second = binding.etFructificacionSecond;

        et_ExpresionSexual_Second = binding.etExpresionSexualSecond;
        et_Polinizacion_Second = binding.etPolinizacionSecond;
        et_Dispersion_Second = binding.etDispersionSecond;

        et_NumCromosomatico_Second = binding.etNumCromosomaticoSecond;
        et_ReproduccionAsexual_Second = binding.etReproduccionAsexualSecond;
        et_Distribucion_Second = binding.etDistribucionSecond;

        et_Biologia_Second = binding.etBiologiaSecond;
        et_Demografia_Second = binding.etDemografiaSecond;
        et_Amenazas_Second = binding.etAmenazasSecond;

        et_MedidasPropuestas_Second = binding.etMedidasPropuestasSecond;
    }

    private void initializeFAB(){
        binding.fabAddImageSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = null;
                bundle = new Bundle();
                bundle.putByte("fragmentOrigin", (byte) 0);
                bundle.putStringArrayList("editTextsValues", getEditTextsValues());
                navigateToAddImagenFragment();
            }
        });
    }

    private void navigateToFirstFragment(){
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    private void navigateToAddImagenFragment(){
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_addImagenFragment, bundle);
    }

    private void saveAction(){
        //Hay que actualizar la flora a la base de datos con la API
        if(et_Nombre_Second.getText().toString().trim().isEmpty()
                || et_Nombre_Second.getText().toString().equals("NO ESPECIFICADO")){
            binding.tiNombreSecond.setErrorEnabled(true);
            binding.tiNombreSecond.setHelperText("* Es necesario rellenar este campo");
        } else{
            flora.setAtributtes(getEditTextsValues());
            mavm.editFlora(flora.getId(), flora);
        }
    }

    void textListener(){
        et_Nombre_Second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() >= 0){
                    binding.tiNombreSecond.setErrorEnabled(false);
                } else{
                    binding.tiNombreSecond.setErrorEnabled(true);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_opt: {
                alertDialog();
                return true;
            }
        }

        return false;
    }

}