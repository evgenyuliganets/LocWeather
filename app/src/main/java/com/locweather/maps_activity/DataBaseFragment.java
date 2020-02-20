package com.locweather.maps_activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.locweather.R;
import com.locweather.adapter.DataBaseAdapter;
import com.locweather.database.WeatherData;
import com.locweather.viewmodel.WeatherViewModel;

import java.util.Objects;

public class DataBaseFragment extends Fragment implements DataBaseAdapter.OnDeleteButtonClickListener{

    private OnFragmentInteractionListener mListener;
    private DataBaseAdapter datasAdapter;
    private WeatherViewModel weatherViewModel;
    public DataBaseFragment() {
        // Required empty public constructor
    }

    public static DataBaseFragment newInstance() {
        DataBaseFragment fragment = new DataBaseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datasAdapter= new DataBaseAdapter(Objects.requireNonNull(getContext()), this);

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getAllPosts().observe(this, posts -> datasAdapter.setData(posts));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_data_base, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.database_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(datasAdapter);
        Button buttonFragment = view.findViewById(R.id.back_button);
        buttonFragment.setOnClickListener(v -> {
            onButtonPressed();
        });
        return view;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDeleteButtonClicked(WeatherData weather) {
        weatherViewModel.deleteWeather(weather);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
