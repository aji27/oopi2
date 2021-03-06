package ch.fhnw.oopi2.presenter;

import ch.fhnw.oopi2.model.MockedModel;
import ch.fhnw.oopi2.model.Movie;
import ch.fhnw.oopi2.model.MovieImpl;
import ch.fhnw.oopi2.view.MockedView;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ajanth on 11.06.2016.
 */
public class OscarAppPresenterTest {

    private MockedView _view;
    private MockedModel _model;
    private Presenter _presenter;
    private Movie _selectedItem;

    public OscarAppPresenterTest() {
    }

    @Before
    public void setUp() {
        _view = new MockedView();
        _model = new MockedModel();
        _presenter = new OscarAppPresenter(_view, _model);

        for (Movie m : _model.getAll())
            _model.remove(m);

        MovieImpl m1 = new MovieImpl();
        m1.setId(1);
        m1.setTitle("Fight Club");
        _model.add(m1);

        MovieImpl m2 = new MovieImpl();
        m2.setId(2);
        m2.setTitle("Revolver");
        _model.add(m2);

        MovieImpl m3 = new MovieImpl();
        m3.setId(3);
        m3.setTitle("Inception");
        _model.add(m3);

        _model.saveChanges();
        _selectedItem = null;
    }

    @After
    public void tearDown() {
        _view = null;
        _model = null;
        _presenter = null;
    }

    @Test
    public void testOnSelectedItemChanged() {
        Assert.assertEquals(null, _view.getSelectedItem());
        _selectedItem = _model.getAll().get(0);
        _presenter.onSelectedItemChanged(_selectedItem.getId());
        Assert.assertEquals(_selectedItem, _view.getSelectedItem());
    }

    @Test
    public void testOnSaveClicked() {
        _presenter.onSaveClicked();
    }

    @Test
    public void testOnAddNewItemClicked() {
        _selectedItem = _view.getSelectedItem();
        int expectedSize = _model.getAll().size() + 1;
        _presenter.onAddNewItemClicked();
        Assert.assertEquals(expectedSize, _model.getAll().size());
        Assert.assertNotEquals(_selectedItem, _view.getSelectedItem());
    }

    @Test
    public void testOnDeleteSelectedItemClicked() {
        // nothing is deleted, if nothing is selected
        int expectedSize = _model.getAll().size();
        _presenter.onDeleteSelectedItemClicked();
        Assert.assertEquals(expectedSize, _model.getAll().size());

        // selected item is removed
        _selectedItem = _model.getAll().get(0);
        _presenter.onSelectedItemChanged(_selectedItem.getId());
        _presenter.onDeleteSelectedItemClicked();
        Assert.assertFalse(_model.getAll().contains(_selectedItem));
        Assert.assertNotEquals(_selectedItem, _view.getSelectedItem());
    }

    @Test
    public void testOnUndoClicked() {
        _selectedItem = _model.getAll().get(0);
        _presenter.onSelectedItemChanged(_selectedItem.getId());

        int oldYear = _selectedItem.getYearOfAward();
        _presenter.onYearChanged(2016);
        _presenter.onUndoClicked();
        Assert.assertEquals(oldYear, _selectedItem.getYearOfAward());
        
        String oldTitle = _selectedItem.getTitle();
        _presenter.onTitleChanged("Title");
        _presenter.onUndoClicked();
        Assert.assertEquals(oldTitle, _selectedItem.getTitle());

        String oldDirector = _selectedItem.getDirector();
        _presenter.onDirectorChanged("Director");
        _presenter.onUndoClicked();
        Assert.assertEquals(oldDirector, _selectedItem.getDirector());

        String oldMainActor = _selectedItem.getMainActor();
        _presenter.onMainActorChanged("MainActor");
        _presenter.onUndoClicked();
        Assert.assertEquals(oldMainActor, _selectedItem.getMainActor());

        String oldGenre = _selectedItem.getGenre();
        _presenter.onGenreChanged("Genre");
        _presenter.onUndoClicked();
        Assert.assertEquals(oldGenre, _selectedItem.getGenre());

        int oldYearOfProduction = _selectedItem.getYearOfProduction();
        _presenter.onYearOfProductionChanged(2016);
        _presenter.onUndoClicked();
        Assert.assertEquals(oldYearOfProduction, _selectedItem.getYearOfProduction());

        String oldCountry = _selectedItem.getCountry();
        _presenter.onCountryChanged("Country");
        _presenter.onUndoClicked();
        Assert.assertEquals(oldCountry, _selectedItem.getCountry());

        int oldDuration = _selectedItem.getDuration();
        _presenter.onDurationChanged(150);
        _presenter.onUndoClicked();
        Assert.assertEquals(oldDuration, _selectedItem.getDuration());

        int oldFsk = _selectedItem.getFsk();
        _presenter.onFskChanged(18);
        _presenter.onUndoClicked();
        Assert.assertEquals(oldFsk, _selectedItem.getFsk());

        String oldStartDate = _selectedItem.getStartDate();
        _presenter.onStartDateChanged(new Date());
        _presenter.onUndoClicked();
        Assert.assertEquals(oldStartDate, _selectedItem.getStartDate());

        int oldNumberOfOscars = _selectedItem.getNumberOfOscars();
        _presenter.onNumberOfOscarsChanged(5);
        _presenter.onUndoClicked();
        Assert.assertEquals(oldNumberOfOscars, _selectedItem.getNumberOfOscars());
    }

    @Test
    public void testOnRedoClicked() {
        _selectedItem = _model.getAll().get(0);
        _presenter.onSelectedItemChanged(_selectedItem.getId());

        _presenter.onYearChanged(2016);
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals(2016, _selectedItem.getYearOfAward());

        _presenter.onTitleChanged("Title");
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals("Title", _selectedItem.getTitle());

        _presenter.onDirectorChanged("Director");
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals("Director", _selectedItem.getDirector());

        _presenter.onMainActorChanged("MainActor");
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals("MainActor", _selectedItem.getMainActor());

        _presenter.onGenreChanged("Genre");
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals("Genre", _selectedItem.getGenre());

        _presenter.onYearOfProductionChanged(2016);
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals(2016, _selectedItem.getYearOfProduction());

        _presenter.onCountryChanged("Country");
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals("Country", _selectedItem.getCountry());

        _presenter.onDurationChanged(150);
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals(150, _selectedItem.getDuration());

        _presenter.onFskChanged(18);
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals(18, _selectedItem.getFsk());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        _presenter.onStartDateChanged(new Date());
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals(dateFormat.format(new Date()), _selectedItem.getStartDate());

        _presenter.onNumberOfOscarsChanged(5);
        _presenter.onUndoClicked();
        _presenter.onRedoClicked();
        Assert.assertEquals(5, _selectedItem.getNumberOfOscars());
    }

    @Test
    public void testOnSearchTextChanged() {
        // show all items on empty search text
        _presenter.onSearchTextChanged("");
        Assert.assertEquals(_model.getAll().size(), _view.getItems().size());

        // show all items on 'null' search text
        _presenter.onSearchTextChanged(null);
        Assert.assertEquals(_model.getAll().size(), _view.getItems().size());

        _presenter.onSearchTextChanged("Fight Clup");
        Assert.assertEquals(1, _view.getItems().size());

        _presenter.onSearchTextChanged("Rewolver");
        Assert.assertEquals(1, _view.getItems().size());
    }

    @Test
    public void testOnYearChanged() {
        setUpSeletedItem();
        _presenter.onYearChanged(2016);
        Assert.assertEquals(2016, _selectedItem.getYearOfAward());
    }

    @Test
    public void testOnTitleChanged() {
        setUpSeletedItem();
        _presenter.onTitleChanged("Kung Fu Panda");
        Assert.assertEquals("Kung Fu Panda", _selectedItem.getTitle());
    }

    @Test
    public void testOnDirectorChanged() {
        setUpSeletedItem();
        _presenter.onDirectorChanged("David Fincher");
        Assert.assertEquals("David Fincher", _selectedItem.getDirector());
    }

    @Test
    public void testOnMainActorChanged() {
        setUpSeletedItem();
        _presenter.onMainActorChanged("Brad Pitt");
        Assert.assertEquals("Brad Pitt", _selectedItem.getMainActor());
    }

    @Test
    public void testOnTitleEnglishChanged() {
        setUpSeletedItem();
        _presenter.onTitleEnglishChanged("Fight Club (EN)");
        Assert.assertEquals("Fight Club (EN)", _selectedItem.getTitleEnglish());
    }

    @Test
    public void testOnGenreChanged() {
        setUpSeletedItem();
        _presenter.onGenreChanged("Thriller");
        Assert.assertEquals("Thriller", _selectedItem.getGenre());
    }

    @Test
    public void testOnYearOfProductionChanged() {
        setUpSeletedItem();
        _presenter.onYearOfProductionChanged(1995);
        Assert.assertEquals(1995, _selectedItem.getYearOfProduction());
    }

    @Test
    public void testOnCountryChanged() {
        setUpSeletedItem();
        _presenter.onCountryChanged("US");
        Assert.assertEquals("US", _selectedItem.getCountry());
    }

    @Test
    public void testOnDurationChanged() {
        setUpSeletedItem();
        _presenter.onDurationChanged(125);
        Assert.assertEquals(125, _selectedItem.getDuration());
    }

    @Test
    public void testOnFskChanged() {
        setUpSeletedItem();
        _presenter.onFskChanged(12);
        Assert.assertEquals(12, _selectedItem.getFsk());
    }

    @Test
    public void testOnStartDateChanged() {
        setUpSeletedItem();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        _presenter.onStartDateChanged(date);
        Assert.assertEquals(dateFormat.format(date), _selectedItem.getStartDate());
    }

    @Test
    public void testOnNumberOfOscarsChanged() {
        setUpSeletedItem();
        _presenter.onNumberOfOscarsChanged(5);
        Assert.assertEquals(5, _selectedItem.getNumberOfOscars());
    }

    private void setUpSeletedItem() {
        _selectedItem = _model.getAll().get(0);
        _presenter.onSelectedItemChanged(_selectedItem.getId());
    }

}
