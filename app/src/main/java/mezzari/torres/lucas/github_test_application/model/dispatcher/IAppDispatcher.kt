package mezzari.torres.lucas.github_test_application.model.dispatcher

import kotlin.coroutines.CoroutineContext

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
interface IAppDispatcher {
    val main: CoroutineContext
    val io: CoroutineContext
}