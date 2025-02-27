package com.blockstream.green.ui.about

import android.os.Bundle
import android.view.View
import com.blockstream.common.Urls
import com.blockstream.green.BuildConfig
import com.blockstream.green.R
import com.blockstream.green.databinding.AboutFragmentBinding
import com.blockstream.green.extensions.copyToClipboard
import com.blockstream.green.extensions.showPopupMenu
import com.blockstream.green.extensions.toast
import com.blockstream.green.ui.AppFragment
import com.blockstream.green.utils.AppReviewHelper
import com.blockstream.green.utils.openBrowser
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class AboutFragment : AppFragment<AboutFragmentBinding>(R.layout.about_fragment, menuRes = 0) {
    override val screenName = "About"

    var clickCounter = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buildVersion = BuildConfig.VERSION_NAME
        binding.year = Calendar.getInstance().get(Calendar.YEAR).toString()

        binding.buttonTermsOfService.setOnClickListener {
            openBrowser(settingsManager.getApplicationSettings(), Urls.TERMS_OF_SERVICE)
        }

        binding.buttonPrivacyPolocy.setOnClickListener {
            openBrowser(settingsManager.getApplicationSettings(), Urls.PRIVACY_POLICY)
        }

        binding.website.setOnClickListener {
            openBrowser(settingsManager.getApplicationSettings(), Urls.BLOCKSTREAM_GREEN_WEBSITE)
        }

        binding.twitter.setOnClickListener {
            openBrowser(settingsManager.getApplicationSettings(), Urls.BLOCKSTREAM_TWITTER)
        }

        binding.linkedin.setOnClickListener {
            openBrowser(settingsManager.getApplicationSettings(), Urls.BLOCKSTREAM_LINKEDIN)
        }

        binding.facebook.setOnClickListener {
            openBrowser(settingsManager.getApplicationSettings(), Urls.BLOCKSTREAM_FACEBOOK)
        }

        binding.telegram.setOnClickListener {
            openBrowser(settingsManager.getApplicationSettings(), Urls.BLOCKSTREAM_TELEGRAM)
        }

        binding.github.setOnClickListener {
            openBrowser(settingsManager.getApplicationSettings(), Urls.BLOCKSTREAM_GITHUB)
        }

        binding.youtube.setOnClickListener {
            openBrowser(settingsManager.getApplicationSettings(), Urls.BLOCKSTREAM_YOUTUBE)
        }

        binding.buttonHelp.setOnClickListener {
            openBrowser(settingsManager.getApplicationSettings(), Urls.HELP_CENTER)
        }

        binding.buttonFeedback.setOnClickListener {
            AppReviewHelper.showFeedback(this)
        }

        binding.logo.setOnClickListener {
            clickCounter++
            if(clickCounter > 5){
                showPopupMenu(binding.logo, R.menu.menu_about) { item ->
                    clickCounter = 0
                    when (item.itemId) {
                        R.id.copy_device_id -> {
                            countly.deviceId.let { deviceId ->
                                copyToClipboard("DeviceID", deviceId, binding.copyright)
                                toast("DeviceID copied to Clipboard $deviceId")
                            }
                        }
                        R.id.reset_device_id -> {
                            countly.resetDeviceId()
                            toast("DeviceID reset. New DeviceId ${countly.deviceId}")
                        }
                        R.id.zero_offset -> {
                            settingsManager.zeroCountlyOffset()
                            countly.updateOffset()
                            toast("Countly offset reset to zero")
                        }
                    }
                    true
                }
            }
        }
    }
}