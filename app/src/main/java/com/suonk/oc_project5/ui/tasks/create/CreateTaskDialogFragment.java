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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.suonk.oc_project5.R;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateTaskDialogFragment extends DialogFragment {

    String spinnerName = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_task_dialog_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog()).getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CreateTaskViewModel viewModel = new ViewModelProvider(this).get(CreateTaskViewModel.class);

        setupSpinner(view.findViewById(R.id.add_task_spinner));

        AppCompatEditText taskName = view.findViewById(R.id.add_task_edit_text);

        view.findViewById(R.id.add_task_button).setOnClickListener(view1 -> {
            if (Objects.requireNonNull(taskName.getText()).toString().isEmpty()) {
                Toast.makeText(getContext(), "Task name should not be empty", Toast.LENGTH_LONG).show();
            } else {
                viewModel.insertNewTask(new CreateTaskViewState(taskName.getText().toString(), spinnerName));
                Objects.requireNonNull(getDialog()).dismiss();
                getDialog().cancel();
            }
        });

        view.findViewById(R.id.cancel_dialog_button).setOnClickListener(view1 -> {
            Objects.requireNonNull(getDialog()).dismiss();
            getDialog().cancel();
        });
    }

    //region ============================================ Spinner ===========================================

    private void setupSpinner(AppCompatSpinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.projects_array,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinnerName = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //endregion
}
