package com.example.melobit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.melobit.data.Song;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    Context context;
    List<Song> sliders;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context, List<Song> sliders) {
        this.context = context;
        this.sliders = sliders;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sliders.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.slider_item_view,container,false);
        ImageView imageView = view.findViewById(R.id.slider_image);
        Glide.with(context)
                .load(sliders.get(position).getImage().getSlider().getUrl())
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
