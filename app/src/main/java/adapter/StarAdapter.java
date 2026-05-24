package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.starsgallery.R;
import beans.Star;
import service.StarService;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements android.widget.Filterable {

    private List<Star> stars;
    private List<Star> starsFilter;
    private Context context;
    private StarFilter filter;

    public StarAdapter(Context context, List<Star> stars) {
        this.context = context;
        this.stars = stars;
        this.starsFilter = new ArrayList<>(stars);
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.star_item, parent, false);
        return new StarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        Star star = starsFilter.get(position);

        holder.tvName.setText(star.getName().toUpperCase());
        holder.ratingBar.setRating(star.getRating());

        Glide.with(context)
                .load(star.getImg())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgStar);

        // Clic pour modifier la note
        holder.itemView.setOnClickListener(v -> showEditRatingDialog(star, position));
    }

    private void showEditRatingDialog(Star star, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.star_edit_item, null);

        CircleImageView editImg = dialogView.findViewById(R.id.editImgStar);
        TextView editName = dialogView.findViewById(R.id.editTvName);
        RatingBar editRating = dialogView.findViewById(R.id.editRatingBar);

        Glide.with(context)
                .load(star.getImg())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(editImg);

        editName.setText(star.getName());
        editRating.setRating(star.getRating());

        builder.setTitle("Modifier la note")
                .setView(dialogView)
                .setPositiveButton("Valider", (dialog, which) -> {
                    float newRating = editRating.getRating();
                    star.setRating(newRating);
                    StarService.getInstance().update(star);
                    notifyItemChanged(position);
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return starsFilter.size();
    }

    @Override
    public android.widget.Filter getFilter() {
        if (filter == null) {
            filter = new StarFilter();
        }
        return filter;
    }

    class StarViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgStar;
        TextView tvName;
        RatingBar ratingBar;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStar = itemView.findViewById(R.id.imgStar);
            tvName = itemView.findViewById(R.id.tvName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    private class StarFilter extends android.widget.Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Star> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stars);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Star star : stars) {
                    if (star.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(star);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            starsFilter = (List<Star>) results.values;
            notifyDataSetChanged();
        }
    }
}