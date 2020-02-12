package com.anggach.flutterbranchioplugin.src

import android.content.Intent
import android.util.Log
import com.anggach.flutterbranchioplugin.FlutterBranchIoPlugin
import io.branch.referral.Branch
import io.branch.referral.BranchError
import io.flutter.app.FlutterActivity
import org.json.JSONObject

open class FlutterBranchAndroidLifecycleActivity : FlutterActivity() {

    private fun setUpOnStartStream(params: String) {
        FlutterBranchIoPlugin.getDeeplinkParam(params)
    }

    override fun onStart() {
        super.onStart()
        // Branch init
        Branch.getInstance().initSession(object : Branch.BranchReferralInitListener {
            override fun onInitFinished(referringParams: JSONObject?, error: BranchError?) {
                if (error == null) {
                    setUpOnStartStream(referringParams.toString());
                    Log.i("BRANCH SDK", referringParams.toString())
                } else {
                    Log.e("BRANCH SDK", error.message)
                }
            }
        }, this.intent.data, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        this.intent = intent
        // Branch reinit (in case Activity is already in foreground when Branch link is clicked)
        Branch.getInstance().reInitSession(this, object : Branch.BranchReferralInitListener {
            override fun onInitFinished(referringParams: JSONObject?, error: BranchError?) {
                if (error == null) {
                    setUpOnStartStream(referringParams.toString());
                    Log.i("BRANCH SDK", referringParams.toString())
                } else {
                    Log.e("BRANCH SDK", error.message)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

}
