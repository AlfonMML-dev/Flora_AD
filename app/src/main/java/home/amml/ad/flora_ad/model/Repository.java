package home.amml.ad.flora_ad.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import home.amml.ad.flora_ad.model.api.FloraClient;
import home.amml.ad.flora_ad.model.entity.CreateResponse;
import home.amml.ad.flora_ad.model.entity.DeleteResponse;
import home.amml.ad.flora_ad.model.entity.Flora;
import home.amml.ad.flora_ad.model.entity.ImageRowResponse;
import home.amml.ad.flora_ad.model.entity.Imagen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import home.amml.ad.flora_ad.model.entity.RowsResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private Context context;
    private FloraClient floraClient;

    private MutableLiveData<ArrayList<Flora>> floraLiveData = new MutableLiveData<>();
    private MutableLiveData<Flora> floraLiveDataId = new MutableLiveData<>();
    private MutableLiveData<Long> addFloraLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> addImagenLiveData = new MutableLiveData<>();
    private MutableLiveData<ImageRowResponse> imagesLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> deleteFloraLiveData = new MutableLiveData<>();
    private MutableLiveData<DeleteResponse> deleteLiveData = new MutableLiveData<>();
    private MutableLiveData<RowsResponse> editLiveData = new MutableLiveData<>();

//    private String url = "https://informatica.ieszaidinvergeles.org:10011/AD/felixRDLFApp/public/";
    private String url = "https://informatica.ieszaidinvergeles.org:10016/AD/felixRDLFapp/public/";
//    private String url = "https://informatica.ieszaidinvergeles.org:10099/ad/felixRDLFApp/public/";

    private String fileImageName = "subirFoto.abc";

    public Repository(Context context) {
        this.context = context;
        floraClient = getFloraClient();
    }

    private FloraClient getFloraClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(FloraClient.class);
    }

    public void createFlora(Flora flora) {
        Call<CreateResponse> call = floraClient.createFlora(flora);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                addFloraLiveData.setValue(response.body().id);
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {

            }
        });
    }

//    private boolean copyData(Intent data, String name) {
//        Log.v("xyzyx", "copyData");
//        boolean result = true;
//        Uri uri = data.getData();
//        InputStream in = null;
//        OutputStream out = null;
//        try {
//            in = context.getContentResolver().openInputStream(uri);
//            out = new FileOutputStream(new File(context.getExternalFilesDir(null), name));
//            byte[] buffer = new byte[1024];
//            int len;
//            int cont = 0;
//            while ((len = in.read(buffer)) != -1) {
//                cont++;
//                Log.v("xyzyx", "copyData" + cont);
//                out.write(buffer, 0, len);
//            }
//            in.close();
//            out.close();
//        } catch (IOException e) {
//            result = false;
//            Log.v("xyzyx", e.toString());
//        }
//        return result;
//    }

    public boolean copyDataWithoutIntent(Uri uri) {
        Log.v("Repo copyWithout", "copyData");
        boolean result = true;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = context.getContentResolver().openInputStream(uri);
            out = new FileOutputStream(new File(context.getExternalFilesDir(null), fileImageName));
            byte[] buffer = new byte[1024];
            int len;
            int cont = 0;
            while ((len = in.read(buffer)) != -1) {
                cont++;
                Log.v("xyzyx", "copyData" + cont);
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            result = false;
            Log.v("Repo copyWithoutException", e.toString());
        }
        return result;
    }

    public void deleteFlora(long id) {
        Call<DeleteResponse> delete = floraClient.deleteFlora(id);
        delete.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                deleteLiveData.setValue(response.body());
                Log.v("Repo deleteOnResponse", "body " + response.body() + ", value "
                        + deleteLiveData.getValue());
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.v("Repo failDeleteOnResponse", t.toString());
            }
        });
    }

    public void editFlora(long id, Flora flora) {
        Call<RowsResponse> edit = floraClient.editFlora(id, flora);
        edit.enqueue(new Callback<RowsResponse>() {
            @Override
            public void onResponse(Call<RowsResponse> call, Response<RowsResponse> response) {
                editLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RowsResponse> call, Throwable t) {

            }
        });
    }

    public void getFlora(long id) {
        Call<Flora> call = floraClient.getFlora(id);
        call.enqueue(new Callback<Flora>() {
            @Override
            public void onResponse(Call<Flora> call, Response<Flora> response) {
                floraLiveDataId.setValue(response.body());
                Log.v("xyzxyz", response.body().toString());
            }

            @Override
            public void onFailure(Call<Flora> call, Throwable t) {
                Log.v("xyzxyz", t.getLocalizedMessage());
            }
        });
    }

    public void getFlora() {
        Call<ArrayList<Flora>> call = floraClient.getFlora();
        call.enqueue(new Callback<ArrayList<Flora>>() {
            @Override
            public void onResponse(Call<ArrayList<Flora>> call, Response<ArrayList<Flora>> response) {
                floraLiveData.setValue(response.body());
                Log.v("Repository_OnResponse", response.body().toString());
            }

            @Override
            public void onFailure(Call<ArrayList<Flora>> call, Throwable t) {
                Log.v("Repository_OnFailure", t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<Flora>> getFloraLiveData() {
        return floraLiveData;
    }

    public MutableLiveData<Flora> getFloraLiveDataId() {
        return floraLiveDataId;
    }

    public MutableLiveData<Long> getAddFloraLiveData() {
        return addFloraLiveData;
    }

    public MutableLiveData<Long> getAddImagenLiveData() {
        return addImagenLiveData;
    }

    public MutableLiveData<RowsResponse> getEditLiveData() {
        return editLiveData;
    }

    public MutableLiveData<DeleteResponse> getDeleteLiveData(){
        return this.deleteLiveData;
    }

    public MutableLiveData<ImageRowResponse> getImagesLiveData(){
        return this.imagesLiveData;
    }

    public void getImages(long id){
        Call<ImageRowResponse> images = floraClient.getImages(id);
        images.enqueue(new Callback<ImageRowResponse>() {
            @Override
            public void onResponse(Call<ImageRowResponse> call, Response<ImageRowResponse> response) {
                imagesLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ImageRowResponse> call, Throwable t) {

            }
        });
    }

//    public void saveImagen(Intent intent, Imagen imagen) {
//        copyData(intent, fileImageName);
//        File file = new File(context.getExternalFilesDir(null), fileImageName);
//        Log.v("Repo fileImageName", file.getAbsolutePath());
//        subirImagen(file, imagen);
//    }

    public void saveImagenWithoutIntent(Imagen imagen) {
        File file = new File(context.getExternalFilesDir(null), fileImageName);
        Log.v("Repo fileImageName", file.getAbsolutePath());
        subirImagen(file, imagen);
    }

    private void subirImagen(File file, Imagen imagen) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", imagen.nombre + ".jpg", requestFile);
        Call<Long> call = floraClient.subirImagen(body, imagen.idflora, imagen.descripcion);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                addImagenLiveData.setValue(response.body());
                Log.v("Repo subirImagen", "ok");
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.v("Repo subirImagen", "error");
            }
        });
    }
}