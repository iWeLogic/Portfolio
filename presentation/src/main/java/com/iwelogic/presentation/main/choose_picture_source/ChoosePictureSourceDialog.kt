package com.iwelogic.presentation.main.choose_picture_source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.DialogChoosePictureSourceBinding
import com.iwelogic.presentation.base.BaseDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoosePictureSourceDialog : BaseDialog<ChoosePictureSourceViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: DialogChoosePictureSourceBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_choose_picture_source, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(ChoosePictureSourceViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }
}