package cc.trixey.invero.core.script.override

import cc.trixey.invero.core.Context
import cc.trixey.invero.core.script.contextVar
import cc.trixey.invero.core.script.loader.InveroKetherParser
import cc.trixey.invero.core.script.parse
import cc.trixey.invero.core.script.player
import cc.trixey.invero.core.util.fluentMessageComponent
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.kether.combinationParser
import taboolib.platform.util.sendActionBar

/**
 * Invero
 * cc.trixey.invero.core.script.kether.override.Actions
 *
 * @author Arasple
 * @since 2023/3/3 18:56
 */
@InveroKetherParser(["tell", "send", "message", "msg"])
fun actionTell() = combinationParser {
    it.group(
        text(),
    ).apply(it) { message ->
        now {
            val context = contextVar<Context?>("@context")?.variables ?: variables().toMap()
            val player = player()

            message.fluentMessageComponent(player, context, send = true)
        }
    }
}

@InveroKetherParser(["actionbar"])
fun actionActionBar() = combinationParser {
    it.group(text()).apply(it) { str ->
        now { player().sendActionBar(parse(str)) }
    }
}

@InveroKetherParser(["broadcast", "bc"])
fun actionBroadcast() = combinationParser {
    it.group(text()).apply(it) { str ->
        now { onlinePlayers().forEach { p -> p.sendMessage(parse(str)) } }
    }
}


@InveroKetherParser(["title"])
fun actionTitle() = combinationParser {
    it.group(
        text(),
        command("subtitle", then = text()).option(),
        command("by", "with", then = int().and(int(), int())).option().defaultsTo(Triple(0, 20, 0))
    ).apply(it) { t1, t2, time ->
        val (i, s, o) = time
        now { player().sendTitle(parse(t1), t2?.let { i -> parse(i) }, i, s, o) }
    }
}

@InveroKetherParser(["subtitle"])
fun actionSubtitle() = combinationParser {
    it.group(
        text(),
        command("by", "with", then = int().and(int(), int())).option().defaultsTo(Triple(0, 20, 0))
    ).apply(it) { text, time ->
        val (i, s, o) = time
        now { player().sendTitle("§r", text.replace("@sender", player().name), i, s, o) }
    }
}