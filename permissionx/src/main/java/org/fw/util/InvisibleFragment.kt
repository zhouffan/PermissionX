package org.fw.permissiontest

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/9 15:34
 *    desc   :
 *    version: 1.0
 */

/**
 * typealias给任意类型指定别名
 *
 */
typealias PermissionCallBack = (Boolean, List<String>)->Unit

class InvisibleFragment: Fragment() {
    private var callback: PermissionCallBack? = null

    fun request(cb: PermissionCallBack, vararg permissions: String){
        callback = cb
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()){
                if(result != PackageManager.PERMISSION_GRANTED){
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }
    }
}


object PermissionX{
    private const val TAG = "InvisibleFragment"
    fun request(activity: FragmentActivity, vararg permissions: String, callback: PermissionCallBack){
        val fragmentManager = activity.supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(TAG)
        val invisibleFragment = if(fragment != null){
            fragment as InvisibleFragment
        }else{
            val invisibleFrag = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFrag, TAG)
                .commitNow() //马上提交
            invisibleFrag
        }
        //*permissions    * 将数组转换成可变长度传递
        invisibleFragment.request(callback, *permissions)
    }
}

