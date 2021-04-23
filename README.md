

一、AspectJ框架简介
1、AspectJ：
	AspectJ 意思就是Java的Aspect，Java的AOP；
	AspectJ 是使用最为广泛的 AOP 实现方案，适用于 Java 平台，官网地址：http://www.eclipse.org/aspectj/ 。AspectJ 是在静态织入代码，即在编译期注入代码的。
	
2、开源工具
------AspectJX（沪江）
------Hugo（Jake Wharton）






二、原理 & 优缺点：
1、原理
————>编译期间扫描目标程序
————>匹配切入点（PointCut）：通过AspectJ框架的PointCut语法和匹配规则，找到要注入代码的位置（如直接找到方法位置，或通过注解找到方法位置，即JoinPoint）生成切入点PointCut
————>重构连接点（JoinPoint，即对目标程序作了重构），目的就是建立目标程序与Aspect程序的连接（获得执行的对象、方法、参数等上下文信息），从而达到AOP的目的
————>实现将要注入的代码编织（Weave）到目标程序的.class文件中，即：将Advise插入到目标JoinPoint中；
	这样在程序运行时被重构的连接点将会回调Advise方法，就实现了AspectJ代码与目标代码之间的连接。

2、优点：
------稳定可靠，自2001年发展以来，趋于成熟；
------简单：只需要按照AspectJ语法织入横切逻辑，无需关注字节码知识

3、缺点：
------JoinPoint固定：AspectJ只能对固定的JointPoint执行织入，所以其切入点相对固定，对于字节码文件的操作自由度以及开发的掌控度就大打折扣。
------性能较低：AspectJ并不是直接将“横切逻辑（要注入的代码）”插入代码，而是封装JoinPoint实现代码织入，会额外生成一些包装代码，对性能以及包大小有一定影响。
	如：对整个工程所有函数进行切入监听函数执行时间，会导致生成很多代码函数，影响性能能；
------PointCut规则类似正则表达式，可能会出现冗余匹配： 如两个PointCut规则有交集，导致重复对某类函数织入代码；











三、AspectJ中的重要概念
1、JoinPoint（连接点） ：表示程序运行过程中的一些执行点，可能作为代码注入目标，例如一个方法调用或者方法入口等。。。 
2、PointCut : 用于定位到指定的JoinPoint，告诉代码注入工具，在何处注入一段特定代码的表达式，
3、Advice : 要织入到指定位置的代码片段，典型的 Advice 类型有 before、after 和 around，分别表示在目标方法执行之前、执行后和完全替代目标方法执行的代码。 除了在方法中注入代码，也可能会对代码做其他修改，比如在一个class中增加字段或者接口。









四、语法：
语法参考：https://www.jianshu.com/p/691acc98c0b8

1、Join Point  &  Pointcut  
举例：
1）within(TypePattern)	符合 TypePattern 的代码中的 Join Point



2、匹配规则
------AspectJ类型匹配的通配符：
*：匹配任何数量字符；
..：匹配任何数量字符的重复，如在类型模式中匹配任何数量子包；而在方法参数模式中匹配任何数量参数。
+：匹配指定类型的子类型；仅能作为后缀放在类型模式后边。
AspectJ使用 且（&&）、或（||）、非（！）来组合切入点表达式。

------匹配模式
call(<注解？> <修饰符?> <返回值类型> <类型声明?>.<方法名>(参数列表) <异常列表>？)


3、匹配示例
------精确匹配
@Pointcut("call(@Describe public void com.davidkuper.MainActivity.init(Context))")
public void pointCut(){}

------单一模糊匹配
//表示匹配 com.davidkuper.MainActivity类中所有被@Describe注解的public void方法。
@Pointcut("call(@Describe public void com.davidkuper.MainActivity.*(..)) ")
public void pointCut(){}

//表示匹配调用Toast及其子类调用的show方法，不论返回类型以及参数列表，并且该子类在以com.meituan或者com.sankuai开头的包名内
@Pointcut("call(* android.widget.Toast+.show(..)) && (within(com.meituan..*)|| within(com.sankuai..*))")
public void toastShow() {
}

------组合模糊匹配
//表示匹配任意Activity或者其子类的onStart方法执行，不论返回类型以及参数列表，且该类在com.meituan.hotel.roadmap包名内
@Pointcut("execution(* *..Activity+.onStart(..))&& within(com.meituan.hotel.roadmap.*)")
public void onStart(){}

------通过声明参数语法arg()显示获取参数
Around(value = "execution(* BitmapFacade.picasso.init(java.lang.String,java.lang.String)) && args(arg1,arg2)"
public Object aroundArgs(String arg1,String arg2,ProceedingJoinPoint joinPoint){
   System.out.println("aspects arg = " + arg1.toString()+" " + arg2);
   Object resutObject = null;
   try {
      resutObject = joinPoint.proceed(new Object[]{arg1,arg2});
   } catch (Throwable e) {
      e.printStackTrace();
   }
   return resutObject;
}

------通过joinPoint.getArg()获取参数列表
@Around("execution(static * tBitmapFacade.picasso.init(..)) && !within(aspectj.*) ")
public void pointCutAround(ProceedingJoinPoint joinPoint){
   Object resutObject = null;
   try {
      //获取参数列表
      Object[] args = joinPoint.getArgs();
      resutObject = joinPoint.proceed(args);
   } catch (Throwable e) {
      e.printStackTrace();
   }
   return resutObject;
};

------异常匹配
/**
* 截获Exception及其子类报出的异常。
* @param e 异常参数
*/
@Pointcut("handler(java.lang.Exception+)&&args(e)")
public void handle(Exception e) {}







********************************** AspectJ 使用 **********************************


一、引入AspectJ： 
更详细参看教程地址：https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx

1、在项目根目录的build.gradle里依赖AspectJX
 dependencies {
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.10'
        }
或者使用product目录下的jar包，在你的项目根目录下新建目录plugins，把product/gradle-android-plugin-aspectjx-2.0.0.jar拷贝到plugins，依赖jar包
dependencies {
        classpath fileTree(dir:'plugins', include:['*.jar'])
        }


2、compile 'org.aspectj:aspectjrt:1.8.+' 必须添加到包含有AspectJ代码的module.
note：区别于旧版本，离线新版本不再需要依赖org.aspectj:aspectjtools:1.8.+


3、在app项目的build.gradle里应用插件
apply plugin: 'android-aspectjx'
//或者这样也可以
apply plugin: 'com.hujiang.android-aspectjx'









示例可参考Demo；

其他示例如下：
二、示例——————简单示例
在 Fragment 的 onResume 和 onPause 时打印 log 信息，新建一个 FragmentAspect 类：

//标注我们要通过Aspect语法生成代码的辅助类
@Aspect
public class FragmentAspect {

    private static final String TAG = "FragmentAspect";

	//步骤一：生成一个PointCut，该PointCut定位到指定的JoinPoint，JoinPoint即括号内的表示的方法
    @Pointcut("execution(void android.support.v4.app.Fragment.onResume()) && target(fragment)")
    public void onResume(Fragment fragment) {}

    @Pointcut("execution(void android.support.v4.app.Fragment.onPause()) && target(fragment)")
    public void onPause(Fragment fragment) {}

	//步骤二：插入到指定PointCut对应代码，这里具体是在onResume前插入日志
    @Before("onResume(fragment)")
    public void beforeOnResume(Fragment fragment) {
        Log.d(TAG, fragment.getClass().getSimpleName() + " onResume");
    }

    @Before("onPause(fragment)")
    public void beforeOnPause(Fragment fragment) {
        Log.d(TAG, fragment.getClass().getSimpleName() + " onPause");
    }
}










三、示例——————使用注解完成切面AOP（支持java，同时支持kotlin）：
1、自定义注解：
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PerformanceAnnotation(val value: String)


2、使用注解：将注解放到要注入代码的函数上
@PerformanceAnnotation("performance")
fun clickMe(view: View) {
    Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()


3、使用AspectJ框架，自定义切面内容，即要插入的内容：
@Aspect
class PerformanceAspect {
 
    companion object {
        val TAG = PerformanceAspect::class.java.simpleName
    }
 
    @Pointcut("execution(@ com.tongjin.myapplication.PerformanceAnnotation * *(..))")
    fun performancePointcut() {
    }
 
    @Around("performancePointcut()")
    @Throws(Throwable::class)
    fun wavePerformancePointcut(joinPoint: ProceedingJoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        // 类名
        val className = methodSignature.declaringType.simpleName
        // 方法名
        val methodName = methodSignature.name
        // 功能名
        val behaviorTrace = methodSignature.method.getAnnotation(PerformanceAnnotation::class.java)
        val value = behaviorTrace.value
        val start = System.currentTimeMillis()
        joinPoint.proceed()
        val duration = System.currentTimeMillis() - start
        Log.e(TAG, "${className}类中${methodName}方法执行${value}功能,耗时：${duration}ms")
    }

最终输出：
	MainActivity类中的clickMe方法执行performance功能,耗时：9ms
	show Toast










四、示例——————更多示例
1、日志打印/耗时监控/异常处理/降级替代方案——吐司
参看：https://blog.csdn.net/woshimalingyi/article/details/73252013

2、限制按钮快速点击
参看：https://www.jianshu.com/p/266e5a918c84








五、注意事项 & 问题：
1、切忌勿用：@Before("execution(* android.app.Activity.on**(..))")

使用BaseActivity
@Before("execution(* com.lihang.aoptestpro.BaseActivity.on**(..))")

因为android.app.Activity是framework的代码，不参与编译；
如果你BaseActivity不去实现系统生命周期，你会发现根本不走。所以比如要抓onStart、onPause生命周期时，一定要在BaseActivity去实现，即使方法体内是空也行

note：aspectjx没有改问题



2、编织（注入代码）速度
------debug环境不需要织入，比如埋点
------尽量使用精确的匹配规则,降低匹配时间。
------排除不需要扫描的包。
更详细参看教程地址：https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx
















