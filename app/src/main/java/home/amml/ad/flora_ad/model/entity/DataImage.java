package home.amml.ad.flora_ad.model.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class DataImage implements Parcelable {

    //CAMBIAR A STRING, UITLIZAR Uri.parse(String aux) PARA CREAR URI
    public Uri uri;
    public String nombre;
    public String descripcion;

    public DataImage(Uri uri, String nombre, String descripcion) {
        this.uri = uri;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    protected DataImage(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
        nombre = in.readString();
        descripcion = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
        dest.writeString(nombre);
        dest.writeString(descripcion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataImage> CREATOR = new Creator<DataImage>() {
        @Override
        public DataImage createFromParcel(Parcel in) {
            return new DataImage(in);
        }

        @Override
        public DataImage[] newArray(int size) {
            return new DataImage[size];
        }
    };
}
