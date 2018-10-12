package com.company.setname.weather.adapter.every_day_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.company.setname.weather.R;
import com.company.setname.weather.adapter.model_of_items.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class EveryDayAdapter extends RecyclerView.Adapter<EveryDayAdapter.ViewHolder> {
    private List<Model> news_list;
    public Context context;

    public EveryDayAdapter(List<Model> news_list) {
        this.news_list = news_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_every_day, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Model model = news_list.get(position);

        holder.setDayOfTheWeek(model.getTimeMS());
        holder.setIcon(model.getImageId());
        holder.setTemp(model.getTemperature());

    }

    @Override
    public int getItemCount() {
        return news_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private TextView time;
        private ImageView icon;
        private TextView temp;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setDayOfTheWeek(long timeData) {

            time = mView.findViewById(R.id.item_every_day_day_of_the_week);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeData);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
            String timeInString = dateFormat.format(calendar.getTime());

            time.setText(timeInString);

        }

        void setIcon(String imageURL) {
            icon = mView.findViewById(R.id.item_every_day_icon);

            Glide.with(context.getApplicationContext())
                    .load("http://openweathermap.org/img/w/" + imageURL + ".png")
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(icon);

        }

        void setTemp(double tempData) {

            temp = mView.findViewById(R.id.item_every_day_temp);
            temp.setText(tempData + "°");

        }

    }

}
