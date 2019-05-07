package com.npc.news.ui.main


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.npc.news.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ivBack.setOnClickListener({
            if (nav_host_fragment.childFragmentManager.backStackEntryCount > 0){
                Navigation.findNavController(nav_host_fragment.view!!).popBackStack()
            }else{
                finishAffinity()
            }
        })
    }
}
