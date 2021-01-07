package com.dawan.recyclerview

import androidx.annotation.IdRes

class ViewEvent() {
    @IdRes
    var viewId = 0
    lateinit var methodName: String
    lateinit var methodParameterTypes: Array<Class<*>>
    lateinit var methodParameterInstances: Array<Any>

    constructor(@IdRes viewId: Int, listener: Any) : this() {
        val listenerClass: Class<*> = listener.javaClass
        // 判断监听器是否是匿名类、父类是否是Any
        if (!listenerClass.isAnonymousClass || listenerClass.superclass != Any::class.java) {
            throw IllegalArgumentException(
                "参数listener仅支持继承了Any的匿名对象，" +
                        "其他情况请使用ViewEvent(@IdRes viewId: Int, methodName: String, listener: Any)"
            )
        }
        // 获取匿名类的接口列表
        val interfaceClassArray = listenerClass.interfaces
        if (interfaceClassArray.size == 1) {
            val interfaceClass = interfaceClassArray[0]
            this.viewId = viewId
            this.methodName = "set${interfaceClass.simpleName}"
            this.methodParameterTypes = arrayOf(interfaceClass)
            this.methodParameterInstances = arrayOf(listener)
        } else {
            throw IllegalArgumentException(
                "listener实现了多个接口，viewEvent无法推断准确的方法名称，" +
                        "请使用ViewEvent(@IdRes viewId: Int, methodName: String, listener: Any)，" +
                        "来指定准确的方法名称"
            )
        }
    }

    constructor(@IdRes viewId: Int, methodName: String, listener: Any) : this() {
        this.viewId = viewId
        this.methodName = methodName
        this.methodParameterTypes = arrayOf(listener.javaClass)
        this.methodParameterInstances = arrayOf(listener)
    }

    constructor(
        @IdRes viewId: Int,
        methodName: String,
        methodParameterTypes: Array<Class<*>>,
        methodParameterInstances: Array<Any>
    ) : this() {
        this.viewId = viewId
        this.methodName = methodName
        this.methodParameterTypes = methodParameterTypes
        this.methodParameterInstances = methodParameterInstances
    }
}