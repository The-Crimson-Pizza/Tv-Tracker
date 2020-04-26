package com.tracker.ui.series;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.R;
import com.tracker.adapters.NetworkGenreAdapter;
import com.tracker.models.BasicResponse;
import com.tracker.repositories.SeriesViewModel;
import com.tracker.repositories.TmdbRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import static com.tracker.util.Constants.ID_GENRE;


public class GenreFragment extends Fragment {

    private int idGenre;
    private Context mContext;
    private NetworkGenreAdapter genreAdapter;
    List<BasicResponse.SerieBasic> mSeriesByGenre = new ArrayList<>();

    public GenreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idGenre = getArguments().getInt(ID_GENRE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();

        return inflater.inflate(R.layout.fragment_genre, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewSwitcher switcher = view.findViewById(R.id.switcher_genres);
        SeriesViewModel model = new ViewModelProvider(getActivity()).get(SeriesViewModel.class);
        genreAdapter = new NetworkGenreAdapter(mContext, mSeriesByGenre, true);

        ImageView genre_icon = view.findViewById(R.id.genre_icon);

        setRecycler(view);
        getSeriesByGenre();

        // Title and icon genre

        String variable = "Comedy";

        if(variable.equalsIgnoreCase("Action & Adventure")){
            genre_icon.setImageResource(R.drawable.genre_adventure);
        }else if(variable.equalsIgnoreCase("Animation")){
            genre_icon.setImageResource(R.drawable.genre_animation);
        }else if(variable.equalsIgnoreCase("Comedy")){
            genre_icon.setImageResource(R.drawable.genre_comedy);
        }else if(variable.equalsIgnoreCase("Documental")){
            genre_icon.setImageResource(R.drawable.genre_documental);
        }else if(variable.equalsIgnoreCase("Drama")){
            genre_icon.setImageResource(R.drawable.genre_drama);
        }else if(variable.equalsIgnoreCase("Family")){
            genre_icon.setImageResource(R.drawable.genre_family);
        }else if(variable.equalsIgnoreCase("Kids")){
            genre_icon.setImageResource(R.drawable.genre_kids);
        }else if(variable.equalsIgnoreCase("Mistery")){
            genre_icon.setImageResource(R.drawable.genre_mistery);
        }else if(variable.equalsIgnoreCase("News")){
            genre_icon.setImageResource(R.drawable.genre_news);
        }else if(variable.equalsIgnoreCase("Reality")){
            genre_icon.setImageResource(R.drawable.genre_reality);
        }else if(variable.equalsIgnoreCase("Sci-Fi & Fantasy")){
            genre_icon.setImageResource(R.drawable.genre_sci_fi);
        }else if(variable.equalsIgnoreCase("Soap")){
            genre_icon.setImageResource(R.drawable.genre_soap);
        }else if(variable.equalsIgnoreCase("Talk")){
            genre_icon.setImageResource(R.drawable.genre_talk);
        }else if(variable.equalsIgnoreCase("War & Politics")){
            genre_icon.setImageResource(R.drawable.genre_war);
        }else if(variable.equalsIgnoreCase("Western")){
            genre_icon.setImageResource(R.drawable.genre_western);
        }else{
            genre_icon.setImageResource(R.drawable.amazon);

        }

    }

    private void getSeriesByGenre() {
        TmdbRepository.getInstance().getByGenre(idGenre)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasicResponse>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        mSeriesByGenre.clear();
                        genreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull BasicResponse basicResponse) {
                        mSeriesByGenre.clear();
                        mSeriesByGenre.addAll(basicResponse.basicSeries);
                        genreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        mSeriesByGenre.clear();
                        genreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                        // No hacer nada
                    }
                });
    }

    private void setRecycler(View view) {
        RecyclerView rvNetworks = view.findViewById(R.id.rv_genres);
        rvNetworks.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvNetworks.setHasFixedSize(true);
        rvNetworks.setItemViewCacheSize(20);
        rvNetworks.setSaveEnabled(true);
        rvNetworks.setAdapter(genreAdapter);
    }
}
