package com.igordanilchik.coroutinestest.common.log

import timber.log.Timber

/**
 * @author Igor Danilchik
 */


class ReleaseTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        //        if (priority >= Log.ERROR || priority == Log.INFO) {
        //            if (t == null) {
        //                Crashlytics.log(message);
        //            } else {
        //                Crashlytics.log(message + t);
        //            }
        //        }
    }
}