package com.dennis.basicnewssecondary.viewmodel;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dennis.basicnewssecondary.R;
import com.dennis.basicnewssecondary.adapters.lists.NewsAdapter;
import com.dennis.basicnewssecondary.database.repositories.FavoriteRepository;
import com.dennis.basicnewssecondary.models.NewsItemModel;
import com.dennis.basicnewssecondary.models.NewsListModel;
import com.dennis.basicnewssecondary.utilities.Common;

import java.util.ArrayList;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {

    private NewsListModel newsListModel;
    private NewsAdapter newsAdapter;
    public ObservableBoolean refreshing;

    public MutableLiveData<NewsItemModel> selectedClick;
    public MutableLiveData<Integer> selectedLongClick;

    public void init() {
        newsListModel = new NewsListModel();
        newsAdapter = new NewsAdapter(this);
        refreshing = new ObservableBoolean(false);
        selectedClick = new MutableLiveData<>();
        selectedLongClick = new MutableLiveData<>();
    }

    public void fetchList(int page) {
        newsListModel.fetchList(page);
    }

    public void setDataInAdapter(ArrayList<NewsItemModel> newsItemModels) {
        refreshing.set(false);

        // TODO implement swipe refresh
        // (swipe refresh) if page is 1, clear list first
        /*
        if (newsAdapter.getNewsItems() != null && newsListModel.getPage_number() == 1) {
            newsAdapter.getNewsItems().clear();
        }
        */

        newsAdapter.setNewsItems(newsItemModels);
        newsAdapter.notifyDataSetChanged();
    }

    public MutableLiveData<ArrayList<NewsItemModel>> getStoriesMutable() {
        return newsListModel.getStoriesMutable();
    }

    public String getId(int index) {
        return newsListModel.getStoriesMutable().getValue().get(index).getId();
    }

    public String getImageUrl(int index) {
        String imgFileName = newsListModel.getStoriesMutable().getValue().get(index).getBase_filename();
        if (imgFileName != null) {
            imgFileName = imgFileName.replaceAll(" ", Common.LINK_SPACE);
        }

        return newsListModel.getStoriesMutable().getValue().get(index).getBase_url()
                + Common.LINK_PHOTO_PREFIX
                + imgFileName;
    }

    public String getTitle(int index) {
        return newsListModel.getStoriesMutable().getValue().get(index).getTitle();
    }

    public String getDate(int index) {
        return newsListModel.getStoriesMutable().getValue().get(index).getDate();
    }

    public String getTeaser(int index) {
        return newsListModel.getStoriesMutable().getValue().get(index).getTeaser();
    }

    public NewsListModel getNewsListModel() {
        return newsListModel;
    }

    public void setNewsListModel(NewsListModel newsListModel) {
        this.newsListModel = newsListModel;
    }

    public NewsAdapter getNewsAdapter() {
        return newsAdapter;
    }

    public int getPageNumber() {
        return newsListModel.getPage_number();
    }

    public int getTotalPages() {
        return newsListModel.getTotal_pages();
    }

    public MutableLiveData<NewsItemModel> getSelectedClick() {
        return selectedClick;
    }

    public void setSelectedClick(MutableLiveData<NewsItemModel> selectedClick) {
        this.selectedClick = selectedClick;
    }

    public MutableLiveData<Integer> getSelectedLongClick() {
        return selectedLongClick;
    }

    public void setSelectedLongClick(MutableLiveData<Integer> selectedLongClick) {
        this.selectedLongClick = selectedLongClick;
    }

    public void onItemClick(int position) {
        selectedClick.setValue(newsListModel.getStoriesMutable().getValue().get(position));
    }

    public boolean onItemLongClick(int position) {
        selectedLongClick.setValue(position);
        return false;
    }
}