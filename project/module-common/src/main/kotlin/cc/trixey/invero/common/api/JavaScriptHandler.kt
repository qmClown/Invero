package cc.trixey.invero.common.api

import java.util.function.Function
import javax.script.SimpleScriptContext

/**
 * Invero
 * cc.trixey.invero.common.api.JavaScriptHandler
 *
 * @author Arasple
 * @since 2023/2/8 10:53
 */
interface JavaScriptHandler {

    /**
     * 运行脚本
     */
    fun runScript(
        script: String,
        cache: Boolean = true,
        bindings: Map<String, Any?> = emptyMap(),
        block: SimpleScriptContext.() -> Unit = {}
    ): Any?

    /**
     * 注册持久函数
     */
    fun registerPersistFunction(name: String, function: Function<Any, Any>)

    /**
     * 注册持久绑定
     */
    fun registerPersistBindings(name: String, bindings: Map<String, Any>)

}