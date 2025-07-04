package com.liskovsoft.smartyoutubetv2.common.app.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import com.liskovsoft.mediaserviceinterfaces.ServiceManager;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv2.common.app.presenters.base.BasePresenter;
import com.liskovsoft.smartyoutubetv2.common.app.views.AddDeviceView;
import com.liskovsoft.sharedutils.rx.RxHelper;
import com.liskovsoft.youtubeapi.service.YouTubeServiceManager;
import io.reactivex.disposables.Disposable;

public class AddDevicePresenter extends BasePresenter<AddDeviceView> {
    private static final String TAG = AddDevicePresenter.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static AddDevicePresenter sInstance;
    private final ServiceManager mService;
    private Disposable mDeviceCodeAction;

    private AddDevicePresenter(Context context) {
        super(context);
        mService = YouTubeServiceManager.instance();
    }

    public static AddDevicePresenter instance(Context context) {
        if (sInstance == null) {
            sInstance = new AddDevicePresenter(context);
        }

        sInstance.setContext(context);

        return sInstance;
    }

    public void unhold() {
        RxHelper.disposeActions(mDeviceCodeAction);
        sInstance = null;
    }

    @Override
    public void onViewDestroyed() {
        super.onViewDestroyed();
        unhold();
    }

    @Override
    public void onViewInitialized() {
        RxHelper.disposeActions(mDeviceCodeAction);
        updateDeviceCode();
    }

    public void onActionClicked() {
        if (getView() != null) {
            getView().close();
        }
    }

    private void updateDeviceCode() {
        mDeviceCodeAction = mService.getRemoteControlService().getPairingCodeObserve()
                .subscribe(
                        deviceCode -> getView().showCode(deviceCode),
                        error -> Log.e(TAG, "Get pairing code error: %s", error.getMessage())
                );
    }

    public void start() {
        RxHelper.disposeActions(mDeviceCodeAction);
        getViewManager().startView(AddDeviceView.class);
    }
}
