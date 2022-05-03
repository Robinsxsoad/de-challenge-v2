# Architecture diagram explanation

The diagram shows data coming from the devices in real time through the vendor servers.

The arrows represent an async communication channel using messages queue. An example of this could be Google Cloud Pub/Sub, although is not the only one. This allows the pipelines to communicate easily and to not have to wait between messages.

The processing of this data is done using an Apache Beam streaming jobs (receiving, processing, delivering and storing).

The receiving job will be getting the events in real time and forwards them to the processing job. Also, this event could send raw events from the devices to each team platforms.

[//]: # (TODO: add details about the processing job)

After the processing takes place, the events are sent to a storing job, which will store the data in the EPL database. Also, after processing, the events are sent to a job that delivers the information to two destinations: the bet sites (every 5 minutes) and the sports sites. Also, depending on the information that the EPL wants to provide to each team, the delivery job can send the processed information to the different team platforms for further processing.

