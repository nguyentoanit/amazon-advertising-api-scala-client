package AmazonAdvertisingApi

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.specs2.mutable._
import play.api.libs.json.{JsValue, Json}

class ClientSpec extends Specification {
  val config = Config(
    "amzn1.application-oa2-client.xxxx",
    "xxxx",
    "Atzr|xxx",
    NA,
    "v2",
    true
  )
  val client = Client(config)

  "doRefreshToken" should  {
    "return new access token when Authentication is true" in {
      client.accessToken must beEmpty
      client.doRefreshToken
      client.accessToken.length must beGreaterThan(0)
    }
  }

  "requestReport" should {
    "return HTTPRequest contain a reportID" in {
      val reportDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))

      val data: JsValue = Json.obj(
        "campaignType" -> "sponsoredProducts",
        "reportDate" -> reportDate,
        "metrics" -> "campaignName,campaignId"
      )
      client.profileId = "123"
      client.doRefreshToken
      val response = client.requestReport("asins", data).asString.body
      val reportId: String = (Json.parse(response) \ "reportId").as[String]

      reportId must startWith("amzn1.clicksAPI")
    }
  }
}
