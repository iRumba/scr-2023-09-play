package module

import models.dao.repositories.{PhoneRecordRepository, PhoneRecordRepositoryImpl, ProductsRepository, ProductsRepositoryInMemoryImpl, ProductsRepositorySlickImpl}
import models.{LoginService, LoginServiceImpl, ProductsService, ProductsServiceImpl}

class ScrModule extends AppModule {
  override def configure(): Unit = {
    bindSingleton[LoginService, LoginServiceImpl]
    bindSingleton[PhoneRecordRepository, PhoneRecordRepositoryImpl]
    bindSingleton[ProductsService, ProductsServiceImpl]
    bindSingleton[ProductsRepository, ProductsRepositorySlickImpl]
  }
}
