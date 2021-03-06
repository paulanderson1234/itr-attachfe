/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package connectors

import akka.stream.scaladsl.Source
import akka.util.ByteString
import config.FrontendAppConfig
import play.api.Logger
import play.api.libs.ws.{WS, WSResponse}
import uk.gov.hmrc.play.http._
import play.api.mvc.MultipartFormData.{DataPart, FilePart}
import play.api.Play.current

import scala.concurrent.Future
import uk.gov.hmrc.http.HeaderCarrier

object FileUploadConnector extends FileUploadConnector {
  override lazy val serviceURL = FrontendAppConfig.fileUploadUrl
}

trait FileUploadConnector {

  val serviceURL: String

  // $COVERAGE-OFF$
  def addFileContent(envelopeId: String, fileId: Int, fileName: String, content: ByteString, typeOfContent: String)
                    (implicit hc: HeaderCarrier): Future[WSResponse] = {
    val multipartFormData = Source(FilePart("attachment", fileName, Some(typeOfContent), Source(content :: List())) :: DataPart("", "") :: List())
    WS.url(s"$serviceURL/file-upload/upload/envelopes/$envelopeId/files/$fileId")
      .withHeaders(hc.copy(otherHeaders = Seq("CSRF-token" -> "nocheck")).headers: _*).post(multipartFormData)
  }

  // $COVERAGE-ON$
}
