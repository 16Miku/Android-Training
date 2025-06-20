package com.example.day2_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.day2_fragment.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private static final String TAG = "FirstFragment";

    public interface OnNavigateToDetailListener {
        void onNavigateToDetail();

    }


    private OnNavigateToDetailListener navigateToDetailListener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        //  在 onAttach 中检查宿主 Activity 是否实现了接口
        if (context instanceof OnNavigateToDetailListener) {
            navigateToDetailListener = (OnNavigateToDetailListener) context;
            Log.d(TAG, "Activity implements OnNavigateToDetailListener");
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNavigateToDetailListener");
        }
    }



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        Log.d(TAG,"onCreateView");

        return inflater.inflate(R.layout.fragment_first, container, false );
        /**
         * 通过在 inflater.inflate() 方法中明确指定 false 作为第三个参数 (attachToRoot)，
         * 您告诉 LayoutInflater 只膨胀布局并返回视图，但不要立即将其附加到提供的 container。
         * 这个附加操作将由 FragmentManager 在 Fragment 事务执行过程中负责完成。
         * 这样就避免了视图被重复附加到父视图的问题，解决了 IllegalStateException 导致的崩溃。
         */

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG,"onViewCreated");


        Button navigateButton = view.findViewById(R.id.button_navigate_detail);

        if (navigateButton != null) {


            navigateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(TAG, "Navigate button clicked in FirstFragment");


                    if (navigateToDetailListener != null) {
                        navigateToDetailListener.onNavigateToDetail();
                    }
                }
            });
        } else {
            Log.w(TAG, "Navigate button not found in fragment_first.xml");
        }




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d(TAG,"onDestroyView");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        // !!! 关键步骤 9: 在 onDetach 中清空接口引用 !!!
        navigateToDetailListener = null;
        Log.d(TAG, "OnNavigateToDetailListener reference cleared");
    }



}