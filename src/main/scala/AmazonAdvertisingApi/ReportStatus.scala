package AmazonAdvertisingApi

sealed abstract class ReportStatus {
  val reportId: String
  val status: String
  val statusDetails: String
}

case class ReportSuccess(reportId: String, status: String, statusDetails: String) extends ReportStatus
case class ReportInProgress(reportId: String, status: String, statusDetails: String) extends ReportStatus
case class ReportFailure(reportId: String, status: String, statusDetails: String) extends ReportStatus