package com.suonk.oc_project5.ui.tasks.list;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.suonk.oc_project5.R;
import com.suonk.oc_project5.databinding.FragmentTasksListBinding;
import com.suonk.oc_project5.events.OnTaskEventListener;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@RequiresApi(api = Build.VERSION_CODES.N)
public class TasksListFragment extends Fragment implements OnTaskEventListener {

    private TasksViewModel viewModel;
    private FragmentTasksListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasksListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);

        setupActionBar();
        onFabClickListener();
        getTasksListFromViewModel();
    }

    //region =========================================== Action Bar =========================================

    private void setupActionBar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bluePrimary));
        binding.toolbar.setTitleTextColor(AppCompatResources.getColorStateList(requireActivity(), R.color.white));
        if (((AppCompatActivity) requireActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Todoc");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tasks_list_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() != R.id.sort) {
            item.setChecked(true);
            viewModel.setFilterIdLiveData(item.getItemId());
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion

    private void onFabClickListener() {
        binding.addTask.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(TasksListFragmentDirections.actionTasksListFragmentToCreateTaskDialogFragment());
        });
    }

    public void getTasksListFromViewModel() {
        TasksListAdapter listAdapter = new TasksListAdapter(this);

        viewModel.getAllTasks().observe(getViewLifecycleOwner(), listAdapter::submitList);

        viewModel.getIfListIsEmpty().observe(getViewLifecycleOwner(), isVisible -> {
            binding.noTaskImage.setVisibility(isVisible);
            binding.noTaskText.setVisibility(isVisible);
        });

        binding.tasksRv.setAdapter(listAdapter);
        binding.tasksRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.tasksRv.setHasFixedSize(true);
    }

    @Override
    public void onTaskClick(View view, long id) {
    }

    @Override
    public void onTaskDelete(long id) {
        viewModel.deleteTask(id);
    }
}