package home.amml.ad.flora_ad.viewmodel;


import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import home.amml.ad.flora_ad.model.Repository;
import home.amml.ad.flora_ad.model.entity.Imagen;

public class AddImagenViewModel extends AndroidViewModel {

    private Repository repository;

    public AddImagenViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public MutableLiveData<Long> getAddImagenLiveData() {
        return repository.getAddFloraLiveData();
    }

    public void saveImagen(Intent intent, Imagen imagen) {
        repository.saveImagen(intent, imagen);
    }

//    public void getImages(long id){
//        repository.getImages(id);
//    }
//
//    public MutableLiveData<ImageRowResponse> getImagesLiveData(){
//        return repository.getImagesLiveData();
//    }
}
