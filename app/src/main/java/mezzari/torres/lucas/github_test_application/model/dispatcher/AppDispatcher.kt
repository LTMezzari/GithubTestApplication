package mezzari.torres.lucas.github_test_application.model.dispatcher

import kotlin.coroutines.CoroutineContext

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
class AppDispatcher(
    override val main: CoroutineContext,
    override val io: CoroutineContext
): IAppDispatcher