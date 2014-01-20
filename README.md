Demo Log Maker
==============

A quick demonstration of how to set up continuously rolling logging using logback.

The Logmaker is a demo app that logs using a custom LogRollingPolicy configured in logback.xml.

This policy logs to /tmp/loggings/bar.log but watches for the file /tmp/loggings/bar.log.top and, where it doesn't exist, will immedately rename the current bar.log to bar.log.top and then start logging to a fresh bar.log.  In this way, log.top acts as a safe buffer from which to consume log messages (e.g. for a log collector pumping messages into another system).

