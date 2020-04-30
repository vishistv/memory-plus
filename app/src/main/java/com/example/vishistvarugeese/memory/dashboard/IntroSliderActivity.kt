package com.example.vishistvarugeese.memory.dashboard

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.example.vishistvarugeese.memory.R
import kotlinx.android.synthetic.main.activity_intro_slider.*

class IntroSliderActivity : AppCompatActivity() {
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private var dots: Array<TextView?>? = null
    private var layouts: IntArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 22) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        setContentView(R.layout.activity_intro_slider)

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = intArrayOf(
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3)

        // adding bottom dots
        addBottomDots(0)

        // making notification bar transparent
        changeStatusBarColor()
        myViewPagerAdapter = MyViewPagerAdapter()
        view_pager?.adapter = myViewPagerAdapter
        view_pager?.addOnPageChangeListener(viewPagerPageChangeListener)
        btn_skip?.setOnClickListener {
            startActivity(Intent(this@IntroSliderActivity, DashboardActivity::class.java))
            finish()
        }
        btn_next?.setOnClickListener {
            // checking for last page
            // if last page home screen will be launched
            val current = getItem()
            layouts?.let {
                if (current < it.size) {
                    // move to next screen
                    view_pager?.currentItem = current
                } else {
                    startActivity(Intent(this@IntroSliderActivity, DashboardActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun addBottomDots(currentPage: Int) {
        dots = layouts?.let {
            arrayOfNulls(it.size)
        }

        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        layout_dots?.removeAllViews()

        dots?.let {
            for (i in it.indices) {
                it[i] = TextView(this)
                it[i]?.text = Html.fromHtml("&#8226;")
                it[i]?.textSize = 35f
                it[i]?.setTextColor(colorsInactive[currentPage])
                layout_dots?.addView(it[i])
            }

            if (it.isNotEmpty()) it[currentPage]?.setTextColor(colorsActive[currentPage])
        }
    }

    private fun getItem(): Int {
        view_pager?.let {
            return it.currentItem + 1
        }
        return 0
    }

    //  viewpager change listener
    private var viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            // changing the next button text 'NEXT' / 'GOT IT'
            layouts?.let {
                if (position == it.size - 1) {
                    // last page. make button text to GOT IT
                    btn_next?.text = "GOT IT"
                    btn_skip?.visibility = View.GONE
                } else {
                    // still pages are left
                    btn_next?.text = "NEXT"
                    btn_skip?.visibility = View.VISIBLE
                }
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }

    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layouts?.let { layoutInflater.inflate(it[position], container, false) }
            container.addView(view)
            return view!!
        }

        override fun getCount(): Int {
            layouts?.let {
                return it.size
            }
            return 0
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}