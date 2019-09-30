package net.ssanj.preso

object StringlyTyped {
  //[1]

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




  //[2]

  // filter_level: Option[String] = None

  //From the Twitter API docs:
  // Setting this parameter to one of none, low, or medium will set the minimum value of 
  // the filter_level Tweet attribute required to be included in the stream. 
  // The default value is none, which includes all available Tweets.

  //Simplified view of the Tweet class above
  final case class UnfilteredTweets(contributors: Vector[Contributor], filterLevel: Option[String])

  final class Contributor

  def getTweetLevel(tweets: UnfilteredTweets): Seq[Contributor] = {
    tweets.filterLevel match {
      case Some("low") =>  tweets.contributors.take(1)
      case Some("medium") => tweets.contributors.take(10)
      case Some(_) => Seq.empty[Contributor]
      case None => Seq.empty[Contributor]
    }
  } 


























  //[3]
  sealed trait FilterLevel
  object FilterLevel {
    case object None extends FilterLevel
    case object Low extends FilterLevel
    case object Medium extends FilterLevel
    //case object High extends FilterLevel
  }



  final case class UnfilteredTweets2(contributors: Vector[Contributor], filterLevel: FilterLevel)

  def getTweetLevel2(tweets: UnfilteredTweets2): Seq[Contributor] = {
    tweets.filterLevel match {
      case FilterLevel.None   => Seq.empty[Contributor]
      case FilterLevel.Low    => tweets.contributors.take(1)
      case FilterLevel.Medium => tweets.contributors.take(10)
    }
  } 
} 