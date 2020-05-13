package com.anggach.flutterbranchioplugin.src

import android.content.Intent
import android.util.Log
import com.anggach.flutterbranchioplugin.FlutterBranchIoPlugin
import io.branch.referral.Branch
import io.flutter.app.FlutterActivity


open class FlutterBranchAndroidLifecycleActivity : FlutterActivity() {

    private fun setUpOnStartStream(params: String) {
        FlutterBranchIoPlugin.getDeeplinkParam(params)
    }

    override fun onStart() {
        super.onStart()
        // Branch init
        Branch.getInstance().enableFacebookAppLinkCheck();
        Branch.sessionBuilder(this).withCallback { linkProperties, error ->
            if (error == null) {
                setUpOnStartStream(linkProperties.toString());
                Log.i("BRANCH SDK", linkProperties.toString())
            } else {
                Log.e("BRANCH SDK", error.message)
            }
        }.withData(if (intent != null) intent.data else null).init()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        this.intent = intent
        Branch.getInstance().enableFacebookAppLinkCheck();
        Branch.sessionBuilder(this).withCallback { linkProperties, error ->
            if (error == null) {
                setUpOnStartStream(linkProperties.toString());
                Log.i("BRANCH SDK", linkProperties.toString())
            } else {
                Log.e("BRANCH SDK", error.message)
            }
        }.reInit();
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

}
