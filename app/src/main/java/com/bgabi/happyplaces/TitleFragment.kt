package com.bgabi.happyplaces

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bgabi.happyplaces.auth.SignInActivity
import com.bgabi.happyplaces.databinding.FragmentTitleBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.settings_activity.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TitleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title,container,false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_menu, menu)
        checkCurrentUser(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if ( id == R.id.redirect_sign_in) {
            val intent : Intent = Intent(activity, SignInActivity::class.java)
            activity?.startActivity(intent)
            return true
        }
        if ( id == R.id.settings_button) {
            val intent : Intent = Intent(activity, SettingsActivity::class.java)
            activity?.startActivity(intent)
            return true
        }
        else if (id == R.id.log_out_button) {
            Firebase.auth.signOut()
            val refresh = Intent(activity, MainActivity::class.java)
            activity?.startActivity(refresh)
            activity?.finish()
            return true
        }
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    fun checkCurrentUser(menu: Menu) {
        val user = Firebase.auth.currentUser
        if (user != null) {
            menu.getItem(0).setVisible(false)
            menu.getItem(1).setVisible(true)
        } else {
            menu.getItem(0).setVisible(true)
            menu.getItem(1).setVisible(false)
        }
    }
}