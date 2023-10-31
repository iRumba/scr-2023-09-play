package module

import com.google.inject.Scopes
import net.codingwell.scalaguice.ScalaModule

abstract class AppModule extends ScalaModule {

  protected def bindSingleton[T, C <: T](implicit tM: Manifest[T], tC: Manifest[C]) {
    bind(tM.runtimeClass.asInstanceOf[Class[T]]).to(tC.runtimeClass.asInstanceOf[Class[C]]).in(Scopes.SINGLETON)
  }
}
