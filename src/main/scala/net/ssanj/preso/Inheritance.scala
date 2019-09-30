package net.ssanj.preso

object Inheritance {

  //[1] a simplified implementation of Option
  sealed trait Option2[+A]

  object  Option2 {
    case object None extends Option2[Nothing]
    final case class Some[A](value: A) extends Option2[A]
  }


  final case class UserId(value: Long)
  final case class UserName(value: String)

  final case class User(id: UserId, userName: UserName)

  final class Result

  def getUser(userId: UserId): Option2[User] = ???


  






































  //[2] Have we seen code like this? If not why not?
  def processUser(someUser: Option2.Some[User]): Result = ???
  










































  //[3] this is what we usually see. Why?
  def processUser2(someUser: Option2[User]): Result = ???













































  //Liskov Substitution Principle:
  //Functions that use pointers to base classes must be able to use objects of derived classes without knowing it
  //Code to interfaces not implementations




























//[4] This seems to be the rule when we most ready-made Sum types:
//Option(Some, None)
//Either(Left, Right)
def processUser3(someUser: Right[Throwable, User]): Result = ???

//Try(Success, Failure)
def processUser4(someUser: util.Success[User]): Result = ???

//List(Nil, Cons)
def processUser5(someUser: ::[User]): Result = ???

//cats.Validated(Invalid, Valid)
//cats.Eval(Always, Defer, FlatMap, Later, Now)















































  //[5]
  final case class EventId(value: String)
  final case class EventName(value: String)
  final case class ListingId(value: String)
  final case class ListingProcessingFailure(value: String) //could be an ADT but let's keep it simple

  object v1 {

    sealed trait Event {
      val eventId: EventId
      val eventName: EventName
    }

    final case class ListingSubmittedEvent(val eventId: EventId, val eventName: EventName) extends Event
    final case class ListingProcessedEvent(val eventId: EventId, val eventName: EventName, listingId: ListingId) extends Event
    final case class ListingFailedToProcessedEvent(val eventId: EventId, val eventName: EventName, failure: ListingProcessingFailure) extends Event


    //encoders
    import io.circe.Encoder
    import io.circe.Json
    import io.circe.syntax._


    implicit val encoderEvent: Encoder[Event] = Encoder { 
      case event: ListingSubmittedEvent => event.asJson
      case event: ListingProcessedEvent => event.asJson
      case event: ListingFailedToProcessedEvent => event.asJson
    }

    implicit val encoderListingSubmittedEvent: Encoder[ListingSubmittedEvent] = Encoder { submittedEvent =>
      encodeEventDetails(submittedEvent, None)
    }

    implicit val encoderListingProcessedEvent: Encoder[ListingProcessedEvent] = Encoder { processedEvent =>
      encodeEventDetails(processedEvent, Some(Json.obj("listingId" -> processedEvent.listingId.asJson)))
    }

    implicit val encoderListingFailedToProcessedEvent: Encoder[ListingFailedToProcessedEvent] = Encoder { failureEvent =>
      encodeEventDetails(failureEvent, Some(Json.obj("failure" -> failureEvent.failure.asJson)))
    }


    implicit val encodeListingId: Encoder[ListingId] = Encoder { listingId =>
      Json.fromString(listingId.value)
    }

    implicit val encodeFailure: Encoder[ListingProcessingFailure] = Encoder { failure =>
      Json.fromString(failure.value)
    }

    def encodeEventDetails(commonEvent: Event, dataOp: Option[Json]): Json = {
      val commonJson = Json.obj(
        "eventId" -> Json.fromString(commonEvent.eventId.value),
        "eventName" -> Json.fromString(commonEvent.eventName.value)
      )

      dataOp match {
        case Some(extraJson) => commonJson.deepMerge(extraJson)
        case None => commonJson
      }
    }
  }//v1






















//[6] Can we encode the same information:
// 1. Without Inheritance
// 2. Without Using the values of a Sum type as proper types

  object v2 {

    sealed trait Event

    final case class EventDetails(eventId: EventId, eventName: EventName)

    final case class ListingSubmittedEvent(details: EventDetails) extends Event
    final case class ListingProcessedEvent(details: EventDetails, listingId: ListingId) extends Event
    final case class ListingFailedToProcessedEvent(details: EventDetails, failure: ListingProcessingFailure) extends Event

    //encoders
    import io.circe.Encoder
    import io.circe.Json
    import io.circe.syntax._


    implicit val encoderEvent: Encoder[Event] = Encoder { 
      case ListingSubmittedEvent(eventDetails) => eventDetails.asJson
      case ListingProcessedEvent(eventDetails, listingId) => listingProcessedEventToJson(eventDetails, listingId)
      case ListingFailedToProcessedEvent(eventDetails, failure) => listingFailedToProcessedEventToJson(eventDetails, failure)
    }

    def listingProcessedEventToJson(eventDetails: EventDetails, listingId: ListingId): Json = {
      Json.obj(
        "listingId" -> listingId.asJson
      ).deepMerge(eventDetails.asJson)
    }

    def listingFailedToProcessedEventToJson(eventDetails: EventDetails, failure: ListingProcessingFailure): Json = {
      Json.obj(
        "failure" -> failure.value.asJson
      ).deepMerge(eventDetails.asJson)
    }

    implicit val encodeEventDetails: Encoder[EventDetails] = Encoder { eventDetails =>
      Json.obj(
        "eventId" -> Json.fromString(eventDetails.eventId.value),
        "eventName" -> Json.fromString(eventDetails.eventName.value)
      )
    }

    implicit val encodeListingId: Encoder[ListingId] = Encoder { listingId =>
      Json.fromString(listingId.value)
    }

    implicit val encodeFailure: Encoder[ListingProcessingFailure] = Encoder { failure =>
      Json.fromString(failure.value)
    }
  }//v2







































//Choose your own adventure. But remember you can choose.


}