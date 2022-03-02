package home.amml.ad.flora_ad.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import home.amml.ad.flora_ad.model.Repository;
import home.amml.ad.flora_ad.model.entity.Flora;

public class AddFloraViewModel extends AndroidViewModel {

    private Repository repository;

    public AddFloraViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void createFlora(Flora flora) {
        repository.createFlora(flora);
    }

    public MutableLiveData<Long> getAddFloraLiveData() {
        return repository.getAddFloraLiveData();
    }
}