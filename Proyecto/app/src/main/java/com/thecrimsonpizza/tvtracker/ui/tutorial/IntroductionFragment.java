package com.thecrimsonpizza.tvtracker.ui.tutorial;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.appintro.SlidePolicy;
import com.thecrimsonpizza.tvtracker.R;

public class IntroductionFragment extends Fragment implements SlidePolicy {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button b;
    boolean pulsado = false;

    public IntroductionFragment() {
        // Required empty public constructor
    }

    public static IntroductionFragment newInstance(String param1, String param2) {
        IntroductionFragment fragment = new IntroductionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tutorial_slide5, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        b = view.findViewById(R.id.next_introduction);
//        b.setOnClickListener(v -> {
//            Log.e("HEHE","HEHE");
//            pulsado=true;
//        });
    }

    @Override
    public boolean isPolicyRespected() {
//        return pulsado;
        return true;
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        Log.e("q", "NO PULSADO");
    }
}
