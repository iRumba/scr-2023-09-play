package controllers

import com.google.inject.Inject
import controllers.Assets.Ok
import models.dao.entities.{Address, PhoneRecord}
import models.dao.repositories.PhoneRecordRepository
import play.api.mvc.Action
import play.mvc.Controller

import java.util.UUID

class PhoneBookController @Inject()(val phoneRecordRepository: PhoneRecordRepository) extends Controller{

  def list() = Action{
      val recordUUId = UUID.randomUUID().toString
      val addressUUId = UUID.randomUUID().toString
      phoneRecordRepository.insert(Address(addressUUId, "230012", "Ленина 26"))
      phoneRecordRepository.insert(PhoneRecord(recordUUId, "123456", "Ivanov Ivan", addressUUId))
      val r = phoneRecordRepository.list()
      Ok(r.toString())
  }
}
