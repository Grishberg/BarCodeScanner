package com.github.grishberg.barcodescanner.form;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.grishberg.barcodescanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grishberg on 06.02.18.
 */

public class FormRelationsAdapter extends RecyclerView.Adapter<FormRelationsAdapter.RelationViewHolder> {

    @Nullable
    private List<CellRelation> relations;

    public void setRelations(@Nullable List<CellRelation> relations) {
        this.relations = new ArrayList<>(relations);
        notifyDataSetChanged();
    }

    @Override
    public RelationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_relation, parent, false);
        return new RelationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RelationViewHolder holder, int position) {
        CellRelation cellRelation = relations.get(position);
        holder.cellAddress.setText(String.valueOf(cellRelation.getX()));
        holder.cellType.setText(cellTypeToString(cellRelation.getCellType()));
        holder.readOnly.setChecked(cellRelation.isReadOnly());
    }

    private String cellTypeToString(FormCellType cellType) {
        return cellType.name();
    }

    @Override
    public int getItemCount() {
        return relations != null ? relations.size() : 0;
    }

    static class RelationViewHolder extends RecyclerView.ViewHolder {
        TextView cellAddress;
        TextView cellType;
        CheckBox readOnly;
        View removeButton;

        public RelationViewHolder(View itemView) {
            super(itemView);
            cellAddress = itemView.findViewById(R.id.cell_address);
            cellType = itemView.findViewById(R.id.cell_type);
            readOnly = itemView.findViewById(R.id.cell_readonly);
            removeButton = itemView.findViewById(R.id.remove_button);
        }
    }
}
