# CircleAssetView
自定义环形资产分布图，颜色，大小，半径均可自定义。

## 主要功能：
 - 支持内侧尺寸、外侧尺寸、颜色
 - 支持资产文本的显示或隐藏、字体大小、字体颜色
 - 支持设置角度、获取最大角度、最小角度、当前角度
 

### 添加Gradle

#### 1.在project的build.gradle下
```
  allprojects {   
	 		repositories {     
	 				...       
	 				maven { url 'https://jitpack.io' } 
	 		}
	 }
```

#### 2.在app 的 build.gradle下

```
dependencies {   
		compile 'com.github.Walll-E:CircleAssetView:v1.0'
	}
```

###用法：
布局文件中声明：
```
<com.walle.circleassetview.CircleAssetView   
		android:id="@+id/assetView"    
		app:radius="80dp"    
		app:insideRadius="15dp"  
		app:outsideRadius="20dp"  
		app:moneyTextHintSize="16dp"  
		app:moneyTextSize="16dp"   
		app:textSpace="10dp"   
		app:text_visibility="visible"  
		app:moneyText="￥2900.00"   
		app:moneyTextHint="总资产(元)"   
		app:degree="180"    
		android:layout_width="wrap_content"  
		android:layout_height="wrap_content"/>
```
		
代码中使用

```
	assetView = (CircleAssetView) findViewById(R.id.assetView);
	assetView.setMoneyText("￥2900.0");
	assetView.setMoneyTextColor(Color.rgb(33,33,33));
	assetView.setMoneyTextHint("总资产(元)");
	assetView.setTextVisibility(CircleAssetView.AssetTextVisibility.Visible);
```
	
## 关于我
个人邮箱：walle0228@163.com

[GitHub主页](https://github.com/Walll-E)

[简书主页](http://www.jianshu.com/u/f914004db506)

##如果觉得文章帮到你，不求打赏，喜欢我的文章可以关注我和朋友一起运营的微信公众号，将会定期推送优质技术文章，求关注~~~##

![欢迎关注“大话安卓”微信公众号](http://upload-images.jianshu.io/upload_images/1956769-2f49dcb0dc5195b6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##欢迎加入“大话安卓”技术交流群，一起分享，共同进步##
![欢迎加入“大话安卓”技术交流群，互相学习提升](http://upload-images.jianshu.io/upload_images/1956769-326c166b86ed8e94.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
