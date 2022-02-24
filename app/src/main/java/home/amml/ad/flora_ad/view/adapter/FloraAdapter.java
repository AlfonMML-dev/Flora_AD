package home.amml.ad.flora_ad.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.model.entity.Flora;
import home.amml.ad.flora_ad.model.entity.Imagen;
import home.amml.ad.flora_ad.view.adapter.viewholder.FloraViewHolder;
import home.amml.ad.flora_ad.viewmodel.MainActivityViewModel;

public class FloraAdapter extends RecyclerView.Adapter<FloraViewHolder> {

    private Context context;

    private MainActivityViewModel mavm;
    private List<Flora> floraList;
    private List<Imagen> imagenList;

    private View.OnClickListener listener;

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

        //mavm = new ViewModelProvider(context).get(MainActivityViewModel.class);
        floraList = mavm.getFloraLiveData().getValue();

        Flora flora = floraList.get(position);
        Imagen imagen = imagenList.get(position);

        //Emplear Glide
        //Glide.with(context).load(imagen.getImagenPrincipal()).into(holder.iV_Car_Item_Car);
        // holder.ivFlora.setImageResource(Integer.parseInt(imagen.getNombre()));
        holder.tv_IDFlora_ItemFlora.setText("ID Flora" + flora.getId());
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

    public void setFloraList(List<Flora> floraList) {
        this.floraList = floraList;
        notifyDataSetChanged();
    }
}
