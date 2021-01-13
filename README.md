# RecyclerView Adapter
[![](https://jitpack.io/v/hedawan/RecyclerView.svg)](https://jitpack.io/#hedawan/RecyclerView)

对RecyclerView Adapter进行了扩展，能方便的进行item的事件设置和获取item的位置
## 添加依赖
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
```gradle
dependencies {
    implementation 'com.github.hedawan:RecyclerView:1.0'
}
```

## 基础使用
一般来说你无需实现ViewHolder，直接继承EventAdapter即可
```kotlin
// 1. 继承EventAdapter
class TextListAdapter : EventAdapter<EventViewHolder>() {
    private val textList = mutableListOf<String>()

    // 返回列表大小
    override fun getItemCount(): Int {
        return textList.size
    }

    // getItemViewType返回的必须是布局类型
    override fun getItemViewType(position: Int): Int {
        return R.layout.text_item
    }

    // 进行布局绑定
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        // 可以根据R.id从ViewHolder获取View实例来进行布局绑定
        val textView: TextView? = holder.findViewById(R.id.textView)
        textView?.text = textList[position]
        // 你可以通过EventViewHolder.setTag(...)来设置变量传递,通常id需要保持唯一，建议使用ids.xml来设置id，然后在代码中引用
        // holder.setTag(R.id.xxxx,"")
    }
}
```
为布局设置事件
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        val adapter = TextListAdapter()
        // 调用EventAdapter的addViewEvent来添加事件，addViewEvent可以一次设置多个事件
        adapter.addViewEvent(
            R.layout.text_item,// 布局id，一个Adapter可能有多种布局，你需要明确指明为哪个布局添加事件
            // 添加第一个事件
            ViewEvent(
                R.id.imageView, // view id，一个布局中一般都会有多个组件（View），你需要明确指明为哪个组件（View）添加事件
                View.OnClickListener { it:View ->
                    // 可以通过调用EventAdapter.getViewHolder(...)来获取ViewHolder
                    val viewHolder = adapter.getViewHolder(it)
                    if (viewHolder != null) {
                        // 获取到ViewHolder后，你可以方便的获取到当前ViewHolder的位置
                        val adapterPosition = viewHolder.adapterPosition
                        // 如果在adapter中设置了tag，你可以在这里通过EventViewHolder.getTag(...)来获取变量
                        // viewHolder.getTag(R.id.xxxx)
                    }
            }),
            // 添加第二个事件
            ViewEvent(R.id.root, View.OnClickListener {
                val viewHolder = adapter.getViewHolder(it)
                if (viewHolder != null) {
                    val adapterPosition = viewHolder.adapterPosition
                }
            })
        )
        // 设置好事件后将adapter绑定到RecyclerView中
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}
```

## License
```
Copyright (C) dawan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

