package com.example.activitytest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.activitytest.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    public static final String TAG = "FirstFragment";

    @Override
    public void onAttach(@NonNull Context context) {
        // Fragment 第一次附加到 Activity 时调用

        super.onAttach(context);

        Log.d(TAG,"fragment run onAttach");

    }


    @Override
    public void onCreate(@NonNull Bundle savedInstance ) {
        // 创建 Fragment 实例时调用

        super.onCreate(savedInstance);

        Log.d(TAG,"fragment run onCreate");


    }


    @Override
    public View onCreateView(
            // 创建 Fragment 的视图层次结构时调用

            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        Log.d(TAG,"fragment run onCreateView");

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Fragment 的视图已被创建并返回后调用


        super.onViewCreated(view, savedInstanceState);

        binding.ButtonOfFragment1.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

        Log.d(TAG,"fragment run onViewCreated");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}