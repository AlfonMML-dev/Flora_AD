package home.amml.ad.flora_ad.model.api;

import home.amml.ad.flora_ad.model.entity.CreateResponse;
import home.amml.ad.flora_ad.model.entity.DeleteResponse;
import home.amml.ad.flora_ad.model.entity.Flora;
import home.amml.ad.flora_ad.model.entity.Imagen;
import home.amml.ad.flora_ad.model.entity.RowsResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FloraClient {

    @DELETE("api/flora/{id}")
    Call<DeleteResponse> deleteFlora(@Path("id") long id);

    @GET("api/flora/{id}")
    Call<Flora> getFlora(@Path("id") long id);

    @GET("api/flora")
    Call<ArrayList<Flora>> getFlora();

    @POST("api/flora")
    Call<CreateResponse> createFlora(@Body Flora flora);

    @PUT("api/flora/{id}")
    Call<RowsResponse> editFlora(@Path("id") long id, @Body Flora flora);

    @Multipart
    @POST("api/imagen/subir")
    Call<Long> subirImagen(@Part MultipartBody.Part file, @Part("idflora") long idFlora, @Part("descripcion") String descripcion);

    @GET("api/flora/{idflora}/imagen")
    Call<Imagen[]> getImages(@Path("idflora") long idflora);
}