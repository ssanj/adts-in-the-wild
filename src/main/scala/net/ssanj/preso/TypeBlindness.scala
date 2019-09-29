package net.ssanj.preso

import concurrent.Future

object TypeBlindness {

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




































//[9]

//final case class Tweet(
// contributors: Seq[Contributor] = Seq.empty,
// coordinates: Option[Coordinates] = None,
// created_at: Instant,
// current_user_retweet: Option[TweetId] = None,
// entities: Option[Entities] = None,
// extended_entities: Option[Entities] = None,
// extended_tweet: Option[ExtendedTweet] = None,
// favorite_count: Int = 0,
// favorited: Boolean = false,
// filter_level: Option[String] = None,
// geo: Option[Geo] = None,
// id: Long,
// id_str: String,
// in_reply_to_screen_name: Option[String] = None,
// in_reply_to_status_id: Option[Long] = None,
// in_reply_to_status_id_str: Option[String] = None,
// in_reply_to_user_id: Option[Long] = None,
// in_reply_to_user_id_str: Option[String] = None,
// is_quote_status: Boolean = false,
// lang: Option[String] = None,
// place: Option[GeoPlace] = None,
// possibly_sensitive: Boolean = false,
// quoted_status_id: Option[Long] = None,
// quoted_status_id_str: Option[String] = None,
// quoted_status: Option[Tweet] = None,
// scopes: Map[String,
// Boolean] = Map.empty,
// retweet_count: Long = 0,
// retweeted: Boolean = false,
// retweeted_status: Option[Tweet] = None,
// source: String,
// text: String,
// truncated: Boolean = false,
// display_text_range: Option[Seq[Int]] = None,
// user: Option[User] = None,
// withheld_copyright: Boolean = false,
// withheld_in_countries: Seq[String] = Seq.empty,
// withheld_scope: Option[String] = None,
// metadata: Option[StatusMetadata] = None
// ) extends CommonStreamingMessage with Product with Serializable



//Trivia: what is the maximum number of fields allowed in a case class?



























//https://stackoverflow.com/questions/55492422/what-is-the-maximum-number-of-case-class-fields-in-scala/55498135#55498135




//[10]

// filter_level: Option[String] = None

//From the Twitter API docs:
// Setting this parameter to one of none, low, or medium will set the minimum value of 
// the filter_level Tweet attribute required to be included in the stream. 
// The default value is none, which includes all available Tweets.












}