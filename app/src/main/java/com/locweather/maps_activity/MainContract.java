package com.locweather.maps_activity;

import com.locweather.model.Main;
import com.locweather.model.Notice;
import com.locweather.model.Wind;

import java.util.ArrayList;

public interface MainContract {
    /**
     * Call when user interact with the view and other when view OnDestroy()
     * */
    interface presenter{

        void onDestroy();

        void onRefreshButtonClick();

        void requestDataFromServer();

    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetNoticeInteractorImpl class
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(ArrayList<Notice> noticeArrayList, Main main, Wind wind);

        void onResponseFailure(Throwable throwable);

    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface GetNoticeIntractor {

        interface OnFinishedListener {
            void onFinished(ArrayList<Notice> noticeArrayList, Main main, Wind wind);
            void onFailure(Throwable t);
        }
        void getNoticeArrayList(OnFinishedListener onFinishedListener);

    }
}