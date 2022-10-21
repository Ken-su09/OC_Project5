package com.suonk.oc_project5.ui.tasks.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.suonk.oc_project5.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateTaskDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_task_dialog_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CreateTaskViewModel viewModel = new ViewModelProvider(this).get(CreateTaskViewModel.class);
        AppCompatSpinner spinner = view.findViewById(R.id.add_task_spinner);

        viewModel.getViewStatesLiveData().observe(getViewLifecycleOwner(), createTaskViewStates -> {
            ArrayAdapter<CreateTaskViewState> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    createTaskViewStates
            );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        });

        AppCompatEditText taskName = view.findViewById(R.id.add_task_edit_text);

        view.findViewById(R.id.add_task_button).setOnClickListener(button -> {
            viewModel.insertNewTask(((CreateTaskViewState) spinner.getSelectedItem()).getProjectId(), taskName.getText().toString());
        });

        view.findViewById(R.id.cancel_dialog_button).setOnClickListener(button -> {
            dismiss();
        });

        viewModel.getToastSingleLiveEvent().observe(this, errorToastMessage -> {
                    Toast.makeText(getContext(), errorToastMessage, Toast.LENGTH_SHORT).show();
                }
        );

        viewModel.getInsertTaskValidLiveEvent().observe(this, insertTaskValidLiveEvent -> {
                    if (insertTaskValidLiveEvent) {
                        dismiss();
                    }
                }
        );
    }
}
