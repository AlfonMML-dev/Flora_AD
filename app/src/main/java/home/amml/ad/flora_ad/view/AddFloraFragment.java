package home.amml.ad.flora_ad.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.databinding.FragmentAddFloraBinding;

public class AddFloraFragment extends Fragment {

    private FragmentAddFloraBinding binding;

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
        //Subimos la flora a la base de datos usando la api

        NavHostFragment.findNavController(AddFloraFragment.this)
                .navigate(R.id.action_addFloraFragment_to_FirstFragment);
    }

    private void initialize(){
        initializeButtons();
    }

    private void initializeButtons(){
        binding.btCancelAddFlora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddFloraFragment.this)
                        .navigate(R.id.action_addFloraFragment_to_FirstFragment);
            }
        });

        binding.btAddAddFlora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewFlora();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}