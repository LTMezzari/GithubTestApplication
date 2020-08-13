package mezzari.torres.lucas.github_test_application.persistence.sessions

import mezzari.torres.lucas.github_test_application.model.User
import mezzari.torres.lucas.github_test_application.persistence.preferences.IPreferencesManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
@Singleton
class SessionManager @Inject constructor(private val preferencesManager: IPreferencesManager): ISessionManager {

    private var _user: User? = null
    override var user: User? set(value) {
        _user = value
    } get() {
        if (_user == null) {
            _user = preferencesManager.user
        }
        return _user
    }
}