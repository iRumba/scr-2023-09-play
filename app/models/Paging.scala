package models

import play.api.mvc.QueryStringBindable

case class Paging(page: Int, size: Int)

object Paging{
  implicit def queryBinder(implicit intBinder: QueryStringBindable[Int]) = new QueryStringBindable[Paging] {
    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, Paging]] = for{
      pnE <- intBinder.bind(key + ".page", params)
      psE <- intBinder.bind(key + ".size", params)
    } yield (pnE, psE) match {
        case (Right(pn), Right(ps)) => Right(Paging(pn, ps))
        case _ => Left("Unable to bind Paging")
    }

    override def unbind(key: String, value: Paging): String = {
      intBinder.unbind(key + ".page", value.page) + "&" +
        intBinder.unbind(key + ".size", value.size)
    }
  }
}

