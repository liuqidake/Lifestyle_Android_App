package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicolemorris.lifestyle.Adapter.ProfileListAdapter;
import com.example.nicolemorris.lifestyle.Model.ListofProfileAndName;
import com.example.nicolemorris.lifestyle.Model.ProfilePicAndName;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment to list all users whenever the user profile picture is clicked
 */
public class ProfileListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ProfileListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_profile_list, container, false);

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.fl_frag_profilelist);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        ListofProfileAndName inputList = getArguments().getParcelable("item_list");
        List<ProfilePicAndName> profileList = inputList.getList();

        mAdapter = new ProfileListAdapter(profileList);
        mRecyclerView.setAdapter(mAdapter);

        return fragmentView;
    }


}