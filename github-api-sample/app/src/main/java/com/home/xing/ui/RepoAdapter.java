package com.home.xing.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.home.xing.R;
import com.home.xing.network.commands.GetReposCommand;
import com.home.xing.storage.Contract;

/**
 * Created by Gleichmut on 9/4/2014.
 */
public class RepoAdapter extends ResourceCursorAdapter {

    public static final int ITEM_PER_PAGE = 10;

    private static final int WITHOUT_FLAG = 0;

    private int nameIdx;
    private int descIdx;
    private int ownerIdx;
    private int forkIdx;
    private int ownerUrlIdx;
    private int repoUrlIdx;

    private int colorForked;
    private int colorDefault;
    private int colorOwnerDefault;
    private int colorOwnerContrast;

    public RepoAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c, WITHOUT_FLAG);
        colorForked = context.getResources().getColor(android.R.color.holo_green_light);
        colorDefault = context.getResources().getColor(android.R.color.white);
        colorOwnerDefault = context.getResources().getColor(android.R.color.secondary_text_dark);
        colorOwnerContrast = context.getResources().getColor(android.R.color.white);
    }

    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        initColumns(newCursor);
        return super.swapCursor(newCursor);
    }

    private void initColumns(Cursor newCursor) {
        if (newCursor != null) {
            nameIdx = newCursor.getColumnIndex(Contract.RepoTable.REPO_NAME);
            descIdx = newCursor.getColumnIndex(Contract.RepoTable.DESCRIPTION);
            ownerIdx = newCursor.getColumnIndex(Contract.RepoTable.OWNER);
            forkIdx = newCursor.getColumnIndex(Contract.RepoTable.FORK);
            repoUrlIdx = newCursor.getColumnIndex(Contract.RepoTable.REPO_URL);
            ownerUrlIdx = newCursor.getColumnIndex(Contract.RepoTable.OWNER_URL);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = super.newView(context, cursor, parent);
        ViewHolder holder = new ViewHolder(
                view.findViewById(R.id.container),
                view.findViewById(R.id.name),
                view.findViewById(R.id.description),
                view.findViewById(R.id.owner)
        );
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(cursor.getString(nameIdx));
        holder.description.setText(cursor.getString(descIdx));
        holder.owner.setText(cursor.getString(ownerIdx));

        holder.repoUrl = cursor.getString(repoUrlIdx);
        holder.ownerUrl = cursor.getString(ownerUrlIdx);

        final boolean fork = cursor.getInt(forkIdx) == 1;
        holder.container.setBackgroundColor(fork ? colorForked : colorDefault);
        holder.owner.setTextColor(fork ? colorOwnerContrast : colorOwnerDefault);

        notifyListenerIfRequired(context, cursor);
    }

    private void notifyListenerIfRequired(Context context, Cursor cursor) {
        int position = cursor.getPosition();
        boolean end = position == (cursor.getCount() - 1);
        if (end) {
            NetworkHelper.nextPage(context, position);
        }
    }

    public class ViewHolder {
        private final ViewGroup container;
        private final TextView name;
        private final TextView description;
        private final TextView owner;

        private String repoUrl;
        private String ownerUrl;

        public ViewHolder(View container, View name, View desc, View owner) {
            this.container = (ViewGroup) container;
            this.name = (TextView) name;
            this.description = (TextView) desc;
            this.owner = (TextView) owner;
        }

        public String getRepoUrl() {
            return repoUrl;
        }

        public String getOwnerUrl() {
            return ownerUrl;
        }
    }

    private static class NetworkHelper {

        public static void nextPage(Context context, int position) {
            int current = position / RepoAdapter.ITEM_PER_PAGE;
            int next = ++current;
            // API recognize pages started with 1, so increment pages before start a command
            final int apiShift = 1;
            int nextAsPage = next + apiShift;
            new GetReposCommand(nextAsPage, RepoAdapter.ITEM_PER_PAGE).start(context, null);
        }

    }
}
