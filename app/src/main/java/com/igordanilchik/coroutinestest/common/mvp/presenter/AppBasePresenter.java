package com.igordanilchik.coroutinestest.common.mvp.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.igordanilchik.coroutinestest.common.mvp.view.AppBaseView;

/**
 * @author Igor Danilchik
 */
public class AppBasePresenter<View extends AppBaseView> extends MvpPresenter<View> {
}
