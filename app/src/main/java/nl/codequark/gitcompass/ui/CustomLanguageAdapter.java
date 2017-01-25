package nl.codequark.gitcompass.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.codequark.gitcompass.Constants;
import nl.codequark.gitcompass.R;
import nl.codequark.gitcompass.helper.ItemTouchHelperAdapter;
import nl.codequark.gitcompass.helper.ItemTouchHelperViewHolder;
import nl.codequark.gitcompass.helper.OnStartDragListener;
import nl.codequark.gitcompass.model.Language;

/**
 * Created by lao on 15/9/28.
 */
public class CustomLanguageAdapter extends RecyclerView.Adapter<CustomLanguageAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    Context context;

    List<Language> mItems = new ArrayList<>();

    private final OnStartDragListener mDragStartListener;

    int mode;

    public CustomLanguageAdapter(Context context, OnStartDragListener dragStartListener) {
        this.mDragStartListener = dragStartListener;
        this.context = context;
        mode = Constants.MODE_DRAGGABLE;
    }

    public void switchMode(int mode) {
        this.mode = mode;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_language_grid_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final Language language = mItems.get(position);
        viewHolder.text.setText(language.name);

        // Start a drag whenever the handle view it touched
        viewHolder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

        viewHolder.removeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mItems.indexOf(language);
                if (index >= 0) {
                    if (mItems.size() > 1) {
                        mItems.remove(index);
                        notifyItemRemoved(index);
                    } else {
                        ConfirmDialog confirmDialog = ConfirmDialog.newInstance("Invalid Operation", "You should keep at least one language.", 0, -1);
                        confirmDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "confirm");
                    }
                }
            }
        });

        switch (mode) {
            case Constants.MODE_DRAGGABLE:
                viewHolder.handleView.setVisibility(View.VISIBLE);
                viewHolder.removeView.setVisibility(View.GONE);
                break;
            case Constants.MODE_REMOVE:
                viewHolder.handleView.setVisibility(View.GONE);
                viewHolder.removeView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(Language[] items) {
        mItems.clear();
        mItems.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Language language = mItems.remove(fromPosition);
        mItems.add(toPosition, language);

        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        private TextView text;

        private View handleView;

        private View removeView;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            handleView = itemView.findViewById(R.id.handle);
            removeView = itemView.findViewById(R.id.remove);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
