package home.amml.ad.flora_ad.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

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

    private void initialize(){
        initializeButtons();
        initializeFAB();
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

    private void initializeFAB(){
        binding.fabAddImageSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_addImagenFragment);
            }
        });
    }

    private void saveAction(){
        //Hay que subir la flora a la base de datos con la API

        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}