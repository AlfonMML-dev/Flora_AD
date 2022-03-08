package home.amml.ad.flora_ad.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.databinding.FragmentFirstBinding;
import home.amml.ad.flora_ad.view.adapter.FloraAdapter;
import home.amml.ad.flora_ad.viewmodel.MainActivityViewModel;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private MainActivityViewModel mavm;
    private FloraAdapter floraAdapter;

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
        initialize(view);
    }

    private void initialize(View view){
        hideMenu(view);
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

    private void hideMenu(View view){
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
        if(menu.findItem(R.id.delete_opt) != null){
//            menu.findItem(R.id.delete_opt).setEnabled(false);
            menu.findItem(R.id.delete_opt).setVisible(false);
        }
    }

    /**
     * Método que carga el RecyclerView. Para ello invoca ....
     */
    private void initializeRecycler() {
        binding.rvFlora.setLayoutManager(new LinearLayoutManager(getContext()));
        floraAdapter = new FloraAdapter(getContext());
        binding.rvFlora.setAdapter(floraAdapter);

        mavm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mavm.getFlora();
        mavm.getFloraLiveData().observe(this, floraPlural -> {
            Log.v("FirstFragment", floraPlural.toString());
            floraAdapter.setFloraList(floraPlural);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}