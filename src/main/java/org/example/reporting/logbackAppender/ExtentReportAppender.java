package org.example.reporting.logbackAppender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.aventstack.extentreports.ExtentTest;
import org.example.reporting.ExtentReporter;

import java.util.Optional;

public class ExtentReportAppender extends AppenderBase<ILoggingEvent> {

  @Override
  protected void append(ILoggingEvent event) {
    Optional<ILoggingEvent> eventOptional = Optional.ofNullable(event);
    Optional<ExtentTest> extentTestOptional = Optional.ofNullable(ExtentReporter.TEST_REPORT.get());

    eventOptional.flatMap(ILoggingEvent -> extentTestOptional)
            .ifPresent(extentTest -> extentTest.info(event.getFormattedMessage()));
  }
}
