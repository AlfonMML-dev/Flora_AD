package home.amml.ad.flora_ad.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.databinding.FragmentFirstBinding;
import home.amml.ad.flora_ad.view.adapter.FloraAdapter;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

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
        initializeFAB();
        initializeRecycler();
    }

    /**
     * Método que declara, inicializa y da funcionalidad a al componente de la clase
     * FloatingActionButton, presente en la interfaz xml fragment_first
     */
    private void initializeFAB() {
        binding.fabAddFlora.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_addFloraFragment));
    }

    /**
     * Método que carga el RecyclerView. Para ello invoca ....
     */
    private void initializeRecycler() {
        binding.rvFlora.setLayoutManager(new LinearLayoutManager(getContext()));
        FloraAdapter floraAdapter = new FloraAdapter(getContext());
        binding.rvFlora.setAdapter(floraAdapter);
        //floraAdapter.setFloraList(listCars);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}