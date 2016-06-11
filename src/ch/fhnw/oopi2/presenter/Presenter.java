package ch.fhnw.oopi2.presenter;

import java.util.Date;

/**
 * Created by Ajanth on 09.06.2016.
 */
public interface Presenter {
    void onSelectedItemChanged(Integer movieId);
    void onSaveClicked();
    void onAddNewItemClicked();
    void onDeleteSelectedItemClicked();
    void onUndoClicked();
    void onRedoClicked();
    void onSearchTextChanged(String searchText);
    void onYearChanged(Integer year);
    void onTitleChanged(String title);
    void onDirectorChanged(String director);
    void onMainActorChanged(String mainActor);
    void onTitleEnglishChanged(String titleEnglish);
    void onGenreChanged(String genre);
    void onYearOfProductionChanged(Integer yearOfProduction);
    void onCountryChanged(String country);
    void onDurationChanged(Integer duration);
    void onFskChanged(Integer fsk);
    void onStartDateChanged(Date startDate);
    void onNumberOfOscarsChanged(Integer numberOfOscars);
}
