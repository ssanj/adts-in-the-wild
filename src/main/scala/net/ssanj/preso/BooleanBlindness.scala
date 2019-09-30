package net.ssanj.preso

import concurrent.Future

object BooleanBlindness {

  //getTweet implementation copied from Twitter4s: Twitter4s:
  //http://danielasfregola.github.io/twitter4s/6.1/api/com/danielasfregola/twitter4s/TwitterRestClient.html#getTweet(id:Long,trim_user:Boolean,include_my_retweet:Boolean,include_entities:Boolean,tweet_mode:com.danielasfregola.twitter4s.entities.enums.TweetMode.TweetMode):scala.concurrent.Future[com.danielasfregola.twitter4s.entities.RatedData[com.danielasfregola.twitter4s.entities.Tweet]]

  //[2]
  //reimplementation because ..... my eyes!
  sealed trait TweetMode
  object TweetMode {
    case object Classic extends TweetMode
    case object Extended extends TweetMode
  }


  //dummy implementation
  final class Tweet

  //dummy implementation
  final class RatedData[T](value: T)

  //[1]
  def getTweet(id: Long, 
               trim_user: Boolean = false, 
               include_my_retweet: Boolean = false, 
               include_entities: Boolean = true, 
               tweet_mode: TweetMode = TweetMode.Classic): Future[RatedData[Tweet]] = ???


























  //[3]
  val result1 = getTweet(1000, true, false, true, TweetMode.Classic)


  //who can remember which boolean is which?
  //very easy to mix up
  //obviously we can use named parameters but that's not the purpose of this talk






  // def getTweet(id: Long, 
  //              trim_user: Boolean = false, 
  //              include_my_retweet: Boolean = false, 
  //              include_entities: Boolean = true, 
  //              tweet_mode: TweetMode = TweetMode.Classic): concurrent.Future[RatedData[Tweet]] = ???



//[4] Alternative implementation

sealed trait UserInclusion //trim_user
object UserInclusion { //object namespaces - @jack.low
  case object Trim extends UserInclusion
  case object Include extends UserInclusion
}

sealed trait RetweetInclusion //include_my_retweet
object RetweetInclusion { 
  case object DontInclude extends RetweetInclusion
  case object Include extends RetweetInclusion
}

sealed trait EntityInclusion //include_entities
object EntityInclusion {
  case object DontInclude extends EntityInclusion
  case object Include extends EntityInclusion
}

def getTweet2(id: Long, 
              trim_user: UserInclusion = UserInclusion.Trim, 
              include_my_retweet: RetweetInclusion = RetweetInclusion.DontInclude, 
              include_entities: EntityInclusion = EntityInclusion.Include, 
              tweet_mode: TweetMode = TweetMode.Classic): Future[RatedData[Tweet]] = ???


















//[5]
getTweet2(1000, 
         UserInclusion.Include, 
         RetweetInclusion.DontInclude, 
         EntityInclusion.Include, 
         TweetMode.Classic)




//can we distinguish the parameters from each other?











// getTweet(1000, 
//          UserInclusion.Include, 
//          RetweetInclusion.DontInclude, 
//          EntityInclusion.Include, 
//          TweetMode.Classic)

//[6] What is the 1000?
//From the Scaladoc: 
// Id: The numerical ID of the desired status.
// err... what?
//From the Twitter API docs:
//The numerical ID of the desired Tweet.












//[7] Making it better

final case class TweetId(value: Long) extends AnyVal


def getTweet3(id: TweetId, 
              trim_user: UserInclusion = UserInclusion.Trim, 
              include_my_retweet: RetweetInclusion = RetweetInclusion.DontInclude, 
              include_entities: EntityInclusion = EntityInclusion.Include, 
              tweet_mode: TweetMode = TweetMode.Classic): Future[RatedData[Tweet]] = ???
















//[8] 
getTweet3(TweetId(1000), 
         UserInclusion.Include, 
         RetweetInclusion.DontInclude, 
         EntityInclusion.Include, 
         TweetMode.Classic)


//does the api call tell us what everything is?


}