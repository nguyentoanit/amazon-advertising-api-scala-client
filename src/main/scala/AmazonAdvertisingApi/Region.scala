package AmazonAdvertisingApi

import java.net.URL

sealed abstract class Region {
  val production: URL
  val sandbox: URL
  val tokenUrl: URL
}

case object NA extends Region {
   val production: URL = new URL("https://advertising-api.amazon.com")
   val sandbox: URL = new URL("https://advertising-api-test.amazon.com")
   val tokenUrl: URL = new URL("https://api.amazon.com/auth/o2/token")
}

case object EU extends Region {
  val production: URL = new URL("https://advertising-api-eu.amazon.com")
  val sandbox: URL = new URL("https://advertising-api-test.amazon.com")
  val tokenUrl: URL = new URL("https://api.amazon.com/auth/o2/token")
}

case object FE extends Region {
  val production: URL = new URL("https://advertising-api-fe.amazon.com")
  val sandbox: URL = new URL("https://advertising-api-test.amazon.com")
  val tokenUrl: URL = new URL("https://api.amazon.com/auth/o2/token")
}
