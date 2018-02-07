package com.github.grishberg.barcodescanner.form.builder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.grishberg.barcodescanner.R;
import com.github.grishberg.barcodescanner.di.DiManager;
import com.github.grishberg.barcodescanner.form.CellRelation;
import com.github.grishberg.barcodescanner.form.FormRelationsAdapter;
import com.github.grishberg.barcodescanner.form.dialog.AddRelationDialog;

import java.util.List;

/**
 * Created by grishberg on 05.02.18.
 */
public class FormBuilderFragment extends Fragment implements FormBuilderModelListener {
    private boolean mIsLargeLayout = true;
    private RecyclerView recyclerView;
    private FormRelationsAdapter adapter;
    private FormBuilderController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = DiManager.getAppComponent().provideForBuilderController();
        controller.registerFormBuilderListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        controller.unregisterFormBuilderListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form_builder, container, false);
        recyclerView = view.findViewById(R.id.form_builder_relations_list);
        adapter = new FormRelationsAdapter();
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
        AddRelationDialog newFragment = new AddRelationDialog();

        if (mIsLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "dialog");
        } else {
            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                    .addToBackStack(null).commit();
        }
    }
}
