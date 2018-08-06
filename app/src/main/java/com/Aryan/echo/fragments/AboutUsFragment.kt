package com.Aryan.echo.fragments


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.Aryan.echo.R


/**
 * A simple [Fragment] subclass.
 *
 */
class AboutUsFragment : Fragment() {


    var myActivity: Activity? = null
    var image: ImageView? = null
    var about: TextView? = null
    var version: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater!!.inflate(R.layout.fragment_about_us, container, false)
        activity.title = "About Us"
        image =view?.findViewById(R.id.info_icon)
        about = view?.findViewById(R.id.info_about)
        version = view?.findViewById(R.id.appVersion)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_blank, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val item = menu?.findItem(R.id.action_sort)
        item?.isVisible = false
    }

}// Required empty public constructor
