package com.example.day2_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.day2_fragment.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    public static final String TAG = "SecondFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.fragment_second, container,false );
        /**
         * 通过在 inflater.inflate() 方法中明确指定 false 作为第三个参数 (attachToRoot)，
         * 您告诉 LayoutInflater 只膨胀布局并返回视图，但不要立即将其附加到提供的 container。
         * 这个附加操作将由 FragmentManager 在 Fragment 事务执行过程中负责完成。
         * 这样就避免了视图被重复附加到父视图的问题，解决了 IllegalStateException 导致的崩溃。
         */

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}