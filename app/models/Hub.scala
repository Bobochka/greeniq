package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Hub(id: Option[Long] = None, name: String, description: String, ip: String)


  object Hub {

    def all(): List[Hub] = DB.withConnection { implicit c =>
      SQL("select * from hub").as(hub *)
    }

    def find(id: Long): Option[Hub] = {
      DB.withConnection { implicit c =>
        SQL("select * from hub where id = {id}")
          .on('id -> id)
          .as(hub.singleOpt)
      }
    }

    def create(hub: Hub): Unit = {
      DB.withConnection { implicit c =>
        SQL("insert into hub (name, description, ip) values ({name},{description},{ip})")
          .on('name -> hub.name, 'description -> hub.description, 'ip -> hub.ip)
          .executeUpdate()
      }
    }

    def update(id: Long, hub: Hub) = {
      DB.withConnection { implicit connection =>
        SQL("update hub set name = {name}, description = {description} where id = {id}")
          .on('id -> id, 'name -> hub.name, 'description -> hub.description)
          .executeUpdate()
      }
    }

    def delete(id: Long) = {
      DB.withConnection { implicit connection =>
        SQL("delete from hub where id = {id}")
          .on('id -> id)
          .executeUpdate()
      }
    }


    val hub = {
      get[Option[Long]]("id") ~
        get[String]("name") ~
        get[String]("description") ~
        get[String]("ip") map {
        case id~name~description~ip => Hub(id, name, description, ip)
      }
    }
  }