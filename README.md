# IBM-MQ-Message-Automation

IBM-MQ-Message-Automation
Message send/receive programmatically using JMS api and then perform assertion on retrieved message using AssertJ.

This is end to end automation framework to test the underlying system which have queue communication with external systems. We will inject message in one queue which will be processed and then listener will consume this message from another queue after transformation. IBM MQ is used for the communication.

Framework consists of Extent report, TestNg, JMS API, Document builder factory for parsing and AssertJ for test assertion.
