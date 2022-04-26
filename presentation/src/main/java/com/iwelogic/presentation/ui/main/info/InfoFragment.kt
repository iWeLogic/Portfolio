package com.iwelogic.presentation.ui.main.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.core.utils.openUrl
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.FragmentInfoBinding
import com.iwelogic.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<InfoViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dial.observe(this) {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it")))
        }

        viewModel.openSkype.observe(this) {
            startActivity(Intent("android.intent.action.VIEW").apply { data = Uri.parse("skype:$it") })
        }

        viewModel.openEmail.observe(this) {
            runCatching {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(it))
                startActivity(intent)
            }.onFailure {

            }
        }

        viewModel.openUrl.observe(this) {
            activity?.openUrl(it)
        }

        viewModel.openTelegram.observe(this) {
            runCatching {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$it")).apply { `package` = "org.telegram.messenger" })
            }.onFailure {

            }
        }
    }
}
