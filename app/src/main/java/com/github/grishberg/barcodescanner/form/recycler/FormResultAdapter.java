package com.github.grishberg.barcodescanner.form.recycler;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.github.grishberg.barcodescanner.form.CellRelation;

import java.util.List;

/**
 * Created by grishberg on 10.02.18.
 */

public class FormResultAdapter extends RecyclerView.Adapter<FormResultViewHolder> {
    private final ViewHolderFactory viewHolderFactory;
    @Nullable
    private List<CellRelation> relationsWithResult;

    public FormResultAdapter(ViewHolderFactory viewHolderFactory) {
        this.viewHolderFactory = viewHolderFactory;
    }

    public void setRelationsWithResult(@Nullable List<CellRelation> relationsWithResult) {
        this.relationsWithResult = relationsWithResult;
        notifyDataSetChanged();
    }

    @Override
    public FormResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewHolderFactory.provideViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(FormResultViewHolder holder, int position) {
        if (relationsWithResult == null) {
            throw new IllegalStateException("onBindViewHolder when relationsWithResult is empty");
        }
        holder.bind(relationsWithResult.get(position));
    }

    @Override
    public int getItemCount() {
        return relationsWithResult != null ? relationsWithResult.size() : 0;
    }
}
