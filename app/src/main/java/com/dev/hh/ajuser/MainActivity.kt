package com.dev.hh.ajuser

import android.app.Activity
import android.os.Bundle
import com.dev.hh.aspectj.annotation.PerformanceAnnotation
import com.dev.hh.aspectj.annotation.TraceDelay
import kotlinx.android.synthetic.main.activity_main.*


@TraceDelay
open class MainActivity : Activity() {

    open override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        aop_ability.setOnClickListener { startActivity(Intent(this@MainActivity, AOPAbilityActivity::class.java)) }
//        aop_activity.setOnClickListener {
//            startActivity(Intent(this@MainActivity, AOPActivity::class.java)) }
//        aop_fragment.setOnClickListener {
//            startActivity(Intent(this@MainActivity, FragmentActivity::class.java)) }
//        aop_kotlin.setOnClickListener {
//            Greeter().greet() }
//        aop_normal_class.setOnClickListener {
//            NormalClass("normalClass").work() }
//
        function_btn.setOnClickListener {
            Greeter().greet()
        }

        initView()
    }

    @PerformanceAnnotation("performance")
    private fun initView() {

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
