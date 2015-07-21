package weyewe.com.techgig;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import weyewe.com.techgig.pojo.SubReddit;

/**
 * Created by willy on 21/07/15.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {
    private List<SubReddit> feedItemList;
    private Context mContext;

    public MyRecyclerAdapter(Context context, List<SubReddit> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);

//            this.imageView = (ImageView) view.findViewById(R.id.img_thumbnail);
//            this.textView = (TextView) view.findViewById(R.id.tv_species);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        SubReddit feedItem = feedItemList.get(i);

        //Download image using picasso library
        Picasso.with(mContext).load(feedItem.getUrlImage())
                .fit().centerCrop()
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.imageView);

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(feedItem.getName()));

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomViewHolder holder = (CustomViewHolder) view.getTag();
                int position = holder.getPosition();

//                SubReddit feedItem = feedItemList.get(position);
//                Toast.makeText(mContext, feedItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        };

        customViewHolder.textView.setOnClickListener(clickListener);
        customViewHolder.imageView.setOnClickListener(clickListener);

        customViewHolder.textView.setTag(customViewHolder);
        customViewHolder.imageView.setTag(customViewHolder);
    }



    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
}
