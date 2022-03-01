package home.amml.ad.flora_ad.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import home.amml.ad.flora_ad.R;

public class SliderImageViewHolder extends SliderViewAdapter.ViewHolder {
    public ImageView iv_Slide_Item;

    public SliderImageViewHolder(View itemView) {
        super(itemView);
        iv_Slide_Item = itemView.findViewById(R.id.iv_Slide_Item);
    }
}
