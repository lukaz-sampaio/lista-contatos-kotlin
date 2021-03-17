package lab.example.application

import android.app.Application
import lab.example.helpers.HelperDB

/**
 * Essa classe `Application` é instanciada antes de tudo (até mesmo antes das
 * activities).
 *
 * referências:
 *
 * https://developer.android.com/reference/android/app/Application
 * https://guides.codepath.com/android/Understanding-the-Android-Application-Class
 * https://medium.com/droid-log/android-application-class-a8a1d64c82d1
 * https://stackoverflow.com/questions/13400455/what-is-the-purpose-of-application-class-in-android
 */
class ContatoApplication : Application() {

    var helperDB: HelperDB? = null
        private set

    override fun onCreate() {
        super.onCreate()

        /**
         * O helper está sendo instanciado aqui para criar/atualizar o banco de
         * dados. Como essa classe é executada antes de tudo. Então, o banco vai
         * ser criado quando a aplicação iniciar.
         */
        helperDB = HelperDB(this)
    }
}
