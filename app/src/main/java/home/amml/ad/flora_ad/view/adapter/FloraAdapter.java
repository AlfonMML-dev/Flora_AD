package home.amml.ad.flora_ad.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.model.entity.Flora;
import home.amml.ad.flora_ad.model.entity.Imagen;
import home.amml.ad.flora_ad.view.adapter.viewholder.FloraViewHolder;
import home.amml.ad.flora_ad.viewmodel.MainActivityViewModel;

public class FloraAdapter extends RecyclerView.Adapter<FloraViewHolder> {

    private Context context;

    private MainActivityViewModel mavm;
    private ArrayList<Flora> floraList;
    private ArrayList<Imagen> imagenList;

    public FloraAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FloraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flora, parent, false);
        return new FloraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FloraViewHolder holder, int position) {

        Flora flora = floraList.get(position);
        holder.flora = flora;
//        String url_image = "https://informatica.ieszaidinvergeles.org:10011/AD/felixRDLFApp/public/api/imagen/";
        String url_image = "https://informatica.ieszaidinvergeles.org:10016/AD/felixRDLFapp/public/api/imagen/";
//        String url_image = "https://informatica.ieszaidinvergeles.org:10099/ad/felixRDLFApp/public/api/imagen/";
        url_image += flora.getId() + "/flora";
        //Emplear Glide
        Glide.with(context).load(url_image).into(holder.iv_ImageMain_ItemFlora);

        holder.tv_NameValue_ItemFlora.setText(flora.getNombre());
        holder.tv_FamilyValue_ItemFlora.setText(flora.getFamilia());
    }

    @Override
    public int getItemCount() {
        if(floraList == null) {
            return 0;
        }
        return floraList.size();
    }

    public void setFloraList(ArrayList<Flora> floraList) {
        this.floraList = floraList;
        notifyDataSetChanged();
    }
}
