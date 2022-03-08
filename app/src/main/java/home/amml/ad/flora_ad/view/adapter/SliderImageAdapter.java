package home.amml.ad.flora_ad.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

import home.amml.ad.flora_ad.R;
import home.amml.ad.flora_ad.view.adapter.viewholder.SliderImageViewHolder;

public class SliderImageAdapter extends SliderViewAdapter<SliderImageViewHolder> {

    ArrayList<String> images;
    Context ctx;

    public SliderImageAdapter(ArrayList<String> images, Context ctx) {
        this.images = images;
        this.ctx = ctx;
    }

    @Override
    public SliderImageViewHolder onCreateViewHolder(ViewGroup viewGroup) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.slider_item, viewGroup, false);

        return new SliderImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderImageViewHolder viewHolder, int position) {
        Glide.with(ctx).load(images.get(position)).into(viewHolder.iv_Slide_Item);
    }

    @Override
    public int getCount() {
        if (images == null)
            return 0;
        return images.size();
    }

    public void setSliderList(ArrayList<String> sliderDataArrayList) {
        this.images = sliderDataArrayList;
        notifyDataSetChanged();
    }
}
