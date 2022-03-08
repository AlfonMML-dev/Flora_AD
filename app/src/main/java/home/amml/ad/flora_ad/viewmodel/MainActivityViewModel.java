package home.amml.ad.flora_ad.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import home.amml.ad.flora_ad.model.Repository;
import home.amml.ad.flora_ad.model.entity.DeleteResponse;
import home.amml.ad.flora_ad.model.entity.Flora;
import home.amml.ad.flora_ad.model.entity.RowsResponse;

public class MainActivityViewModel extends AndroidViewModel {

    private Repository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
    }

    public MutableLiveData<DeleteResponse> getDeleteLiveData(){
        return repository.getDeleteLiveData();
    }

    public MutableLiveData<RowsResponse> getEditLiveData() {
        return repository.getEditLiveData();
    }

    public MutableLiveData<ArrayList<Flora>> getFloraLiveData() {
        return repository.getFloraLiveData();
    }

    public void createFlora(Flora flora) {
        repository.createFlora(flora);
    }

    public void deleteFlora(long id) {
        repository.deleteFlora(id);
    }

    public void editFlora(long id, Flora flora) {
        repository.editFlora(id, flora);
    }

    public void getFlora(long id) {
        repository.getFlora(id);
    }

    public void getFlora() {
        repository.getFlora();
    }

}
