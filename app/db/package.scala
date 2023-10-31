import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import liquibase.Liquibase
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.{ClassLoaderResourceAccessor, CompositeResourceAccessor, FileSystemResourceAccessor}
import liquibase.servicelocator.LiquibaseService
import org.squeryl.adapters.{H2Adapter, PostgreSqlAdapter}
import play.api.Play._
import slick.jdbc.JdbcProfile


package object db {

  private val config = new HikariConfig()
  (
    configuration.getString("db.default.url"),
    configuration.getString("db.default.user"),
    configuration.getString("db.default.password")
  ) match {
    case (Some(url), Some(user), Some(password)) =>
      config.setJdbcUrl(url)
      config.setUsername(user)
      config.setPassword(password)
    case t @ _ =>
      throw new Exception(s"Can't create config from $t")
  }


  val hikariDataSource = new HikariDataSource(config)

  object SquerylConfig
  {
    lazy val dbDefaultAdapter = configuration.getString("db.default.driver") match
    {
      case Some("org.h2.Driver") => new H2Adapter
      case Some("org.postgresql.Driver") => new PostgreSqlAdapter
      case _ => sys.error("Database driver must be either org.h2.Driver or org.postgresql.Driver")
    }


    lazy val logSql = configuration.getBoolean("log-sql").getOrElse(false)
  }

  trait SlickDatabase{
    val driver: JdbcProfile = slick.jdbc.PostgresProfile
    import driver.api._
    val db = Database.forConfig("sick.db")
  }

  object Liqui{
    def mkLiquibase: Liquibase = {
      val fileAccessor = new FileSystemResourceAccessor()
      val classLoader  = classOf[LiquibaseService].getClassLoader
      val classLoaderAccessor = new ClassLoaderResourceAccessor(classLoader)
      val fileOpener = new CompositeResourceAccessor(fileAccessor, classLoaderAccessor)
      val dbConnection: JdbcConnection = new JdbcConnection(hikariDataSource.getConnection)
      val liqui = new Liquibase(configuration.getString("changelog").get, fileOpener, dbConnection)
      liqui
    }
  }
}
