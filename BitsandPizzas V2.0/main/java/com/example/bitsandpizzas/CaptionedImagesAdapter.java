package com.example.bitsandpizzas;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


// This class is a Custom RecyclerView Adapter.


public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    // Arrays that hold CardView data
    private String[] captions;
    private int[] imageIds;
    private CustomListener listener;

    // Create the ViewHolder class and specify what views shall it contain.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // In this case, it will contain a CardView.
        // Needs to be declared private or the app will crash.
        private CardView cardView;
        public ViewHolder(CardView v) { // Each ViewHolder will display a CardView.
            super(v);
            cardView = v;
        }
      }

    // Create a interface that will decouple the Adapter from the pizzaRecycler
    public interface CustomListener {
        void customOnClick(int position);
    }
    // Method used by RecyclerViews to set a new listener on their CaptionedImagesAdapter.
    public void setCustomListener(CustomListener listener) {
        this.listener = listener;
    }

    // Pass data to the adapter using it's constructor.
    public CaptionedImagesAdapter(String[] captions, int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }

    // Create the ViewHolder that holds a CardView
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // ViewGroup - The Recycler View itself.

        // Create a instance of the CardView by using inflate() method to transform card_captioned_image.xml into a CardView.
        // This simply creates a empty CardView.
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.card_captioned_image, parent, false);

        // Return a ViewHolder that holds the CardView just created.
        return new ViewHolder(cv);
    }


    // Bind the created ViewHolder(Instantiate every view declared in the CardView's layout).
    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get a reference to the CardView inside the ViewHolder.
        CardView cardView = holder.cardView;
        // Populate the CardView.
        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
        Drawable drawable = cardView.getResources().getDrawable(imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);

        // Set a listener on the CardView inside the ViewHolder to listen for clicks.
        final int finalPosition = position;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Once a CardView is clicked, the custom onClick() method from the interface will be called.
                if (listener != null) {
                    listener.customOnClick(finalPosition);
                }
            }
        });
    }

    // This method is used by the adaptor to know how many CardViews to create in the RecyclerView
    @Override
    public int getItemCount() {
        return captions.length;
    }

}
