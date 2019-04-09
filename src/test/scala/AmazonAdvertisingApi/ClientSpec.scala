package AmazonAdvertisingApi

import org.specs2.mutable._

class ClientSpec extends Specification {
  "doRefreshToken" should  {
    "return HTTP request false when Authentication is wrong" in {
      val config = Config(
        "amzn1.application-oa2-client.xxxx",
        "xxxx",
        "Atzr|xxx",
        NA,
        "v2",
        true
      )
      val client = Client(config)
      client.doRefreshToken
      1 must_== 0
    }
  }
}
