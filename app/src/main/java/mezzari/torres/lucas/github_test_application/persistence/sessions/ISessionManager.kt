package mezzari.torres.lucas.github_test_application.persistence.sessions

import mezzari.torres.lucas.github_test_application.model.User

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
interface ISessionManager {
    var user: User?
}