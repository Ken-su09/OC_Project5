//package com.suonk.oc_project5.utils;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.suonk.oc_project5.repositories.task.TaskRepository;
//import com.suonk.oc_project5.repositories.task.TaskRepositoryImpl;
//import com.suonk.oc_project5.ui.tasks.list.TasksViewModel;
//
//import javax.inject.Inject;
//
//public class ViewModelFactory implements ViewModelProvider.Factory {
//
//    private static ViewModelFactory factory;
//
//    @NonNull
//    private final TaskRepository repository;
//
//    @Inject
//    public ViewModelFactory(@NonNull TaskRepository repository) {
//        this.repository = repository;
//    }
//
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        return (T) new TasksViewModel(repository);
////        if (modelClass.isAssignableFrom(TasksViewModel.class)) {
////        }
//    }
//
//    public static ViewModelFactory getInstance() {
//        if (factory == null) {
//            synchronized (ViewModelFactory.class) {
//                factory = new ViewModelFactory(new TaskRepositoryImpl());
//            }
//        }
//
//        return factory;
//    }
//}
//
