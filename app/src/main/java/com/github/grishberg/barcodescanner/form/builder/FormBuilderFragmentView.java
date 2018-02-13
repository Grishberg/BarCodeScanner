package com.github.grishberg.barcodescanner.form.builder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.di.DiManager;
import com.github.grishberg.barcodescanner.form.CellRelation;
import com.github.grishberg.barcodescanner.form.dialog.AddRelationDialogView;

import java.util.List;

/**
 * Created by grishberg on 05.02.18.
 */
public class FormBuilderFragmentView extends Fragment implements FormBuilderModelListener {
    private RecyclerView recyclerView;
    private FormBuilderRelationsAdapter adapter;
    private FormBuilderController controller;
    private FormBuilderService service;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = DiManager.getAppComponent().provideFormBuilderService();
        service.registerFormBuilderServiceListener(this);
        controller = new FormBuilderControllerImpl(this, service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        service.unregisterFormBuilderServiceListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form_builder, container, false);
        recyclerView = view.findViewById(R.id.form_builder_relations_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FormBuilderRelationsAdapter(getContext());
        recyclerView.setAdapter(adapter);

        Button addRelationButton = view.findViewById(R.id.form_builder_add_relation_button);
        addRelationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onAddRelationButtonClicked();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller.onFormCreated();
    }

    @Override
    public void onRelationsReceived(List<CellRelation> relations) {
        adapter.setRelations(relations);
    }

    @Override
    public void onShowAddRelationDialog() {
        showDialog();
    }

    private void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        AddRelationDialogView newFragment = new AddRelationDialogView();

        newFragment.show(fragmentManager, "dialog");
    }
}
