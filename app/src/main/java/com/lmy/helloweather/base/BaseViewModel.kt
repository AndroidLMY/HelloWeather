package com.lmy.helloweather.base

import androidx.lifecycle.*
import com.lmy.helloweather.utils.e
import kotlinx.coroutines.*
import com.lmy.helloweather.model.Result
import kotlin.coroutines.CoroutineContext

/**
 * @author lmy
 * @功能: ViewModel层的顶层封装
 * @Creat 2020/11/6 10:37 AM
 * @Compony 465008238@qq.com
 */
open class BaseViewModel : ViewModel(), LifecycleObserver, CoroutineScope {
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main
    private val mLaunchManager: MutableList<Job> = mutableListOf()

    /**
     * tryBlock 数据请求函数参数
     * cacheBlock  错误回调的函数参数
     * finallyBlock 协程结束的函数回调参数
     * handleCancellationExceptionManually 用于判断是否手动处理 取消协程是发出的异常  true手动处理  fale不处理
     */

    protected fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        cacheBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, cacheBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    /**
     * 把启动任务launch添加刀mLaunchManager中
     */
    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        val job = launch {
            block()
        }
        mLaunchManager.add(job)
        job.invokeOnCompletion {
            mLaunchManager.remove(job)
        }

    }

    /**
     * 这里其实是一个CoroutineScope扩展函数
     * 用于捕获传进来中的函数是否有异常
     */
    private suspend fun CoroutineScope.tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        try {
            tryBlock()
        } catch (e: Throwable) {
            if (e !is CancellationException || handleCancellationExceptionManually) {
                catchBlock(e)
            } else {
                throw e
            }
        } finally {
            finallyBlock()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        "ViewModle onDestory".e()
        viewModelScope.cancel()
        clearLaunchTask()
    }

    private fun clearLaunchTask() {
        mLaunchManager.clear()
    }


    /**
     * 检查返回的Result成功或者失败
     */
    open fun <T : Any> checkResult(result: Result<T>, success: (T?) -> Unit, error: (String?) -> Unit) {
        if (result is Result.Success) {
            success(result.data)
        } else if (result is Result.Error) {
            error(result.exception.message)
        }
    }


}


