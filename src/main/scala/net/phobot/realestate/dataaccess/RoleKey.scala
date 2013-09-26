package net.phobot.realestate.dataaccess

abstract class RoleKey[IdType] {
  def id : IdType
  def equals(key: RoleKey[IdType]) : Boolean = { id == key.id }
}
