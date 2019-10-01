package net.ssanj.preso

object Basics {

  //[1] Sum types
  sealed trait LunchPlace
  case object Ten90      extends LunchPlace
  case object CherryTree extends LunchPlace
  case object Dainty     extends LunchPlace
  case object MasterMa   extends LunchPlace
  case object Social     extends LunchPlace



































  //[2] An OR relationship.


 def name(lunchPlace: LunchPlace): String = lunchPlace match {
  case Ten90 => "1090 Burger"
  case CherryTree => "Cherry Tree Hotel"
  case Dainty => "Dainty Sichuan"
  case MasterMa => "Master Ma Kitchen"
  case Social => "Richmond Social"
 }



















  //[3] Exhaustivity checks

 // def name2(lunchPlace: LunchPlace): String = lunchPlace match {
 //  case Ten90 => "1090 Burger"
 //  case CherryTree => "Cherry Tree Hotel"
 //  case Dainty => "Dainty Sichuan"
 //  // case MasterMa => "Master Ma Kitchen"
 //  // case Social => "Richmond Social"
 // }





















//[4] Wildcards

 // def name3(lunchPlace: LunchPlace): String = lunchPlace match {
 //  case _ => "Cherry Tree Hotel"
 // }













//[5] An Either is a choice between two things.
//Either[Left, Right]






















//[6] Product types

final case class Name(value: String)
final case class Age(value: Int)

final case class Person(name: Name, age: Age)























//An AND relationship
val shaun = Person(Name("Shaun Whitely"), Age(24))



//A pair is the same thing - but does it have the same meaning?
val shaunPair = (Name("Shaun Whitely"), Age(24))


}