package net.ssanj.preso

import io.circe.Json

object AlgaebraicBlindness {

  //[1]
  final case class InvalidRequest(kvp: Map[String, String])
  final case class ValidRequestWithPayload(kvp: Map[String, String], body: Json)

  final class Request
  final class RequestError(reason: String)

  def getMessage(request: Request): Either[RequestError, Either[InvalidRequest, ValidRequestWithPayload]] = ???

  def processMessage(request: Request) = {  
    getMessage(request) match {
      case Left(error) => ???
      case Right(Left(invalid)) => ???
      case Right(Right(payload)) => ???
    }
  }


























//[2]

  final case class HeadersOnly(kvp: Map[String, String])
  final case class HeadersAndBody(kvp: Map[String, String], body: Json)


  sealed trait RequestType
  object RequestType {
    final case class Error(reason: String) extends RequestType
    final case class Invalid(value: HeadersOnly) extends RequestType
    final case class Valid(value: HeadersAndBody) extends RequestType
  }

  def getMessage2(request: Request): RequestType = ???

  def processMessage2(request: Request) = {
    getMessage2(request) match {
      case RequestType.Error(error) => ???
      case RequestType.Invalid(HeadersOnly(kvp)) => ???
      case RequestType.Valid(HeadersAndBody(kvp, json)) => ???
    }
  }


























  //[3]
  sealed trait RequestType3
  object RequestType3 {
    final case class Invalid(value: HeadersOnly) extends RequestType3
    final case class Valid(value: HeadersAndBody) extends RequestType3
  }

  def getMessage3(request: Request): Either[RequestError, RequestType3] = ???

  def processMessage3(request: Request) = {
    getMessage3(request) match {
      case Left(error) => ???
      case Right(RequestType3.Invalid(HeadersOnly(kvp))) => ???
      case Right(RequestType3.Valid(HeadersAndBody(kvp, json))) => ???
    }
  }  































  //None of these solutions is a clear winner.

}