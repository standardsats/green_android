package com.blockstream.green.ui.wallet

import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.databinding.ViewDataBinding
import com.blockstream.green.NavGraphDirections
import com.blockstream.green.R
import com.blockstream.green.database.Wallet
import com.blockstream.green.database.WalletRepository
import com.blockstream.green.databinding.AbstractWalletsFragmentBinding
import com.blockstream.green.extensions.showPopupMenu
import com.blockstream.green.ui.AppFragment
import com.blockstream.green.ui.MainActivity
import com.blockstream.green.ui.bottomsheets.DeleteWalletBottomSheetDialogFragment
import com.blockstream.green.ui.bottomsheets.RenameWalletBottomSheetDialogFragment
import com.blockstream.green.ui.items.WalletListItem
import com.blockstream.green.ui.login.LoginFragment
import com.blockstream.green.utils.observeList
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.itemanimators.SlideDownAlphaAnimator
import javax.inject.Inject


abstract class AbstractWalletsFragment<T : ViewDataBinding> constructor(
    @LayoutRes layout: Int,
    @MenuRes menuRes: Int
) : AppFragment<T>(layout, menuRes) {

    open val isDrawer = false

    @Inject
    lateinit var walletRepository: WalletRepository

    fun init(
        binding: AbstractWalletsFragmentBinding,
        viewModel: WalletsViewModel
    ) {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val softwareWalletsModel = ModelAdapter { wallet: Wallet ->
            val session = sessionManager.getWalletSession(wallet)
            WalletListItem(wallet = wallet, session = session)
        }.observeList(viewLifecycleOwner, viewModel.softwareWalletsLiveData, useDiffUtil = false)

        val softwareWalletsAdapter = FastAdapter.with(softwareWalletsModel)

        softwareWalletsAdapter.onClickListener = { _, _, item, _ ->
            navigate(item.wallet)
            closeDrawer()
            true
        }

        val ephemeralWalletsModel = ModelAdapter { wallet: Wallet ->
            val session = sessionManager.getWalletSession(wallet)
            WalletListItem(wallet = wallet, session = session)
        }.observeList(viewLifecycleOwner, viewModel.ephemeralWalletsLiveData, useDiffUtil = false)

        val ephemeralWalletsAdapter = FastAdapter.with(ephemeralWalletsModel)

        ephemeralWalletsAdapter.onClickListener = { _, _, item, _ ->
            navigate(item.wallet)
            closeDrawer()
            true
        }

        val hardwareWalletsModel = ModelAdapter { wallet: Wallet ->
            val session = sessionManager.getWalletSession(wallet)
            WalletListItem(wallet = wallet, session = session)
        }.observeList(viewLifecycleOwner, viewModel.hardwareWalletsLiveData, useDiffUtil = false)

        val hardwareWalletsAdapter = FastAdapter.with(hardwareWalletsModel)

        hardwareWalletsAdapter.onClickListener = { _, _, item, _ ->
            navigate(item.wallet)
            closeDrawer()
            true
        }

        if (!isDrawer) {
            listOf(softwareWalletsAdapter, hardwareWalletsAdapter).forEach {
                it.onLongClickListener = { view, _, item, _ ->
                    showPopupMenu(view, R.menu.menu_wallet) { menuItem ->
                        when (menuItem.itemId) {
                            R.id.delete -> {
                                DeleteWalletBottomSheetDialogFragment.show(
                                    item.wallet,
                                    childFragmentManager
                                )
                            }

                            R.id.rename -> {
                                RenameWalletBottomSheetDialogFragment.show(
                                    item.wallet,
                                    childFragmentManager
                                )
                            }
                        }
                        true
                    }

                    true
                }
            }
        }

        binding.recyclerSoftwareWallets.apply {
            adapter = softwareWalletsAdapter
            itemAnimator = SlideDownAlphaAnimator()
        }

        binding.recyclerEphemeralWallets.apply {
            adapter = ephemeralWalletsAdapter
            itemAnimator = SlideDownAlphaAnimator()
        }

        binding.recyclerHardwareWallets.apply {
            adapter = hardwareWalletsAdapter
            itemAnimator = SlideDownAlphaAnimator()
        }

        sessionManager.connectionChangeEvent.observe(viewLifecycleOwner) {
            softwareWalletsAdapter.notifyAdapterDataSetChanged()
            ephemeralWalletsAdapter.notifyAdapterDataSetChanged()
            hardwareWalletsAdapter.notifyAdapterDataSetChanged()
        }
    }

    internal fun navigate(wallet: Wallet) {
        val walletSession = sessionManager.getWalletSession(wallet)

        (requireActivity() as MainActivity).getVisibleFragment()?.also {
            if(it is LoginFragment && it.walletOrNull == wallet){
                return
            }
        }

        if (walletSession.isConnected) {
            navigate(NavGraphDirections.actionGlobalWalletOverviewFragment(wallet))
        } else {
            if (wallet.isHardware) {
                navigate(
                    NavGraphDirections.actionGlobalDeviceScanFragment(
                        wallet = wallet
                    )
                )
            } else {
                navigate(
                    NavGraphDirections.actionGlobalLoginFragment(
                        wallet = wallet,
                        autoLogin = true
                    )
                )
            }
        }
    }
}