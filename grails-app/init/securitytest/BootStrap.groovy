package securitytest

import org.springframework.transaction.annotation.*
class BootStrap {

    def init = {
        addTestUsers()
    }

    @Transactional
    void addTestUsers() {
        def adminRole = new Role(authority: 'ROLE_ADMIN').save()

        def testUser = new User(username: 'me', password: 'password').save()

        UserRole.create testUser, adminRole

        UserRole.withSession {
           it.flush()
           it.clear()
        }

        assert User.count() == 1
        assert Role.count() == 1
        assert UserRole.count() == 1
    } 
}
