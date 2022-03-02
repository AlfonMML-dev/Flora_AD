package home.amml.ad.flora_ad.view.adapter.viewholder;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.model.entity.Flora;

public class FloraViewHolder extends RecyclerView.ViewHolder {

    public Flora flora;
    public ImageView iv_ImageMain_ItemFlora;
    public TextView tv_NameValue_ItemFlora, tv_FamilyValue_ItemFlora;

    public FloraViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_ImageMain_ItemFlora = itemView.findViewById(R.id.iv_ImageMain_ItemFlora);
        tv_NameValue_ItemFlora = itemView.findViewById(R.id.tv_NameValue_ItemFlora);
        tv_FamilyValue_ItemFlora = itemView.findViewById(R.id.tv_FamilyValue_ItemFlora);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("flora", flora);
                Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
            }
        });
    }
}
