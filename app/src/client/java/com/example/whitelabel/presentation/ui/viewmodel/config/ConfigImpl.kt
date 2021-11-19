package com.example.whitelabel.presentation.ui.viewmodel.config

import android.view.View
import com.example.whitelabel.config.Config
import javax.inject.Inject

class ConfigImpl @Inject constructor() : Config {

    override val addButtonVisibility: Int = View.GONE
}