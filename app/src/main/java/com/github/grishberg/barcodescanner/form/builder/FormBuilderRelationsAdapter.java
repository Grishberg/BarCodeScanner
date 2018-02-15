package com.github.grishberg.barcodescanner.form.builder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.form.CellRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grishberg on 06.02.18.
 */

public class FormBuilderRelationsAdapter extends RecyclerView.Adapter<FormBuilderRelationsAdapter.RelationViewHolder> {

    public FormBuilderRelationsAdapter(Context context) {
        this.context = context;
    }

    private final Context context;
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
        holder.cellAddress.setText(String.valueOf(cellRelation.getX() + 1));
        holder.cellType.setText(cellTypeToString(context, cellRelation.getCellType()));
        holder.readOnly.setChecked(cellRelation.isReadOnly());
        holder.cellLabel.setText(cellRelation.getLabel());
    }

    private String cellTypeToString(Context context, int cellType) {
        switch (cellType) {
            case 0:
                return context.getString(R.string.cell_type_number);
            default:
                return context.getString(R.string.cell_type_string);
        }
    }

    @Override
    public int getItemCount() {
        return relations != null ? relations.size() : 0;
    }

    static class RelationViewHolder extends RecyclerView.ViewHolder {
        TextView cellLabel;
        TextView cellAddress;
        TextView cellType;
        CheckBox readOnly;
        View removeButton;

        public RelationViewHolder(View itemView) {
            super(itemView);
            cellLabel = itemView.findViewById(R.id.cell_label);
            cellAddress = itemView.findViewById(R.id.cell_address);
            cellType = itemView.findViewById(R.id.cell_type);
            readOnly = itemView.findViewById(R.id.cell_readonly);
            removeButton = itemView.findViewById(R.id.remove_button);
        }
    }
}
