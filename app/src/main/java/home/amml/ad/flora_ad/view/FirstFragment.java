package home.amml.ad.flora_ad.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    //Componente que abrirá a su vez dos componentes de la clase FloatingActionButton
    private ExtendedFloatingActionButton extended_fab_Add;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    private void initialize(){
        initializeFABs();
        initializeRecycler();
    }

    /**
     * Método que declara, inicializa y da funcionalidad a los componentes de la clase
     * FloatingActionButton, presentes en el interfaz xml fragment_first
     */
    private void initializeFABs() {
        extended_fab_Add = binding.extendedFabAdd;
        FloatingActionButton fab_AddFlora = binding.fabAddFlora;
        TextView tv_AddFlora = binding.tvAddFlora;
        FloatingActionButton fab_AddImage = binding.fabAddImage;
        TextView tv_AddImage = binding.tvAddImage;

        //No muestra los FABs
        extended_fab_Add.shrink();
        extended_fab_Add.setOnClickListener(v -> {
            if(!extended_fab_Add.isExtended()){
                fab_AddFlora.setVisibility(View.VISIBLE);
                tv_AddFlora.setVisibility(View.VISIBLE);
                fab_AddImage.setVisibility(View.VISIBLE);
                tv_AddImage.setVisibility(View.VISIBLE);
                extended_fab_Add.extend();
            } else{
                fab_AddFlora.setVisibility(View.GONE);
                tv_AddFlora.setVisibility(View.GONE);
                fab_AddImage.setVisibility(View.GONE);
                tv_AddImage.setVisibility(View.GONE);
                extended_fab_Add.shrink();
            }
        });
        fab_AddFlora.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_addFloraFragment));

        fab_AddImage.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_addImagenFragment));
    }

    /**
     * Método que carga el RecyclerView. Para ello invoca ....
     */
    private void initializeRecycler() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}