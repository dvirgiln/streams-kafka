# Goal
* Create kafka infrastructure using docker
* Create one simple producer and consumer.

#Instructions:
    1. sbt docker:publishLocal
    2. docker-compose  -f docker-compose-kafka.yml up -d

#Current Exception

[INFO] [04/29/2018 10:22:20.944] [default-akka.kafka.default-dispatcher-23] [akka://default/system/kafka-consumer-1] Reconciliation has found revoked assignments: Map() added assignments: Map(). Current subscriptions: Set(Subscribe(Set(test),ListenerCallbacks(akka.kafka.internal.SingleSourceLogic$$Lambda$199/440857521@df29a66,akka.kafka.internal.SingleSourceLogic$$Lambda$200/938275354@f7318c8)))
[WARN] [04/29/2018 10:22:31.033] [default-akka.actor.default-dispatcher-4] [akka://default/system/kafka-consumer-1] KafkaConsumer poll is taking significantly longer (10000ms) to return from poll then the configured poll interval (50ms). Waking up consumer to avoid thread starvation.
[WARN] [04/29/2018 10:22:31.036] [default-akka.actor.default-dispatcher-4] [akka://default/system/kafka-consumer-1] Wake up has been triggered. Dumping stacks: Thread[default-akka.kafka.default-dispatcher-18,5,main]
 sun.misc.Unsafe.park(Native Method)
java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2078)
java.util.concurrent.LinkedBlockingQueue.poll(LinkedBlockingQueue.java:467)
java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1066)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)

Thread[DestroyJavaVM,5,main]


Thread[default-akka.kafka.default-dispatcher-15,5,main]
 sun.misc.Unsafe.park(Native Method)
java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2078)
java.util.concurrent.LinkedBlockingQueue.poll(LinkedBlockingQueue.java:467)
java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1066)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)

Thread[default-akka.kafka.default-dispatcher-25,5,main]
 sun.nio.ch.KQueueArrayWrapper.kevent0(Native Method)
sun.nio.ch.KQueueArrayWrapper.poll(KQueueArrayWrapper.java:198)
sun.nio.ch.KQueueSelectorImpl.doSelect(KQueueSelectorImpl.java:117)
sun.nio.ch.SelectorImpl.lockAndDoSelect(SelectorImpl.java:86)
sun.nio.ch.SelectorImpl.select(SelectorImpl.java:97)
org.apache.kafka.common.network.Selector.select(Selector.java:672)
org.apache.kafka.common.network.Selector.poll(Selector.java:396)
org.apache.kafka.clients.NetworkClient.poll(NetworkClient.java:460)
org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient.poll(ConsumerNetworkClient.java:238)
org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient.poll(ConsumerNetworkClient.java:214)
org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient.poll(ConsumerNetworkClient.java:190)
org.apache.kafka.clients.consumer.internals.AbstractCoordinator.ensureCoordinatorReady(AbstractCoordinator.java:219)
org.apache.kafka.clients.consumer.internals.AbstractCoordinator.ensureCoordinatorReady(AbstractCoordinator.java:205)
org.apache.kafka.clients.consumer.internals.ConsumerCoordinator.poll(ConsumerCoordinator.java:284)
org.apache.kafka.clients.consumer.KafkaConsumer.pollOnce(KafkaConsumer.java:1146)
org.apache.kafka.clients.consumer.KafkaConsumer.poll(KafkaConsumer.java:1111)
akka.kafka.KafkaConsumerActor.tryPoll$1(KafkaConsumerActor.scala:298)
akka.kafka.KafkaConsumerActor.poll(KafkaConsumerActor.scala:345)
akka.kafka.KafkaConsumerActor.akka$kafka$KafkaConsumerActor$$receivePoll(KafkaConsumerActor.scala:267)
akka.kafka.KafkaConsumerActor$$anonfun$receive$1.applyOrElse(KafkaConsumerActor.scala:170)
akka.actor.Actor.aroundReceive(Actor.scala:517)
akka.actor.Actor.aroundReceive$(Actor.scala:515)
akka.kafka.KafkaConsumerActor.aroundReceive(KafkaConsumerActor.scala:85)
akka.actor.ActorCell.receiveMessage(ActorCell.scala:527)
akka.actor.ActorCell.invoke(ActorCell.scala:496)
akka.dispatch.Mailbox.processMailbox(Mailbox.scala:257)
akka.dispatch.Mailbox.run(Mailbox.scala:224)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)

Thread[default-akka.kafka.default-dispatcher-21,5,main]
 sun.misc.Unsafe.park(Native Method)
java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2078)
java.util.concurrent.LinkedBlockingQueue.poll(LinkedBlockingQueue.java:467)
java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1066)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)

Thread[default-akka.actor.default-dispatcher-4,5,main]
 java.lang.Thread.dumpThreads(Native Method)
java.lang.Thread.getAllStackTraces(Thread.java:1607)
akka.kafka.KafkaConsumerActor.$anonfun$poll$1(KafkaConsumerActor.scala:283)
akka.kafka.KafkaConsumerActor$$Lambda$203/1530539777.apply$mcV$sp(Unknown Source)
akka.actor.Scheduler$$anon$4.run(Scheduler.scala:140)
akka.dispatch.TaskInvocation.run(AbstractDispatcher.scala:40)
akka.dispatch.ForkJoinExecutorConfigurator$AkkaForkJoinTask.exec(ForkJoinExecutorConfigurator.scala:43)
akka.dispatch.forkjoin.ForkJoinTask.doExec(ForkJoinTask.java:260)
akka.dispatch.forkjoin.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1339)
akka.dispatch.forkjoin.ForkJoinPool.runWorker(ForkJoinPool.java:1979)
akka.dispatch.forkjoin.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:107)

Thread[Monitor Ctrl-Break,5,main]
 java.net.SocketInputStream.socketRead0(Native Method)
java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
java.net.SocketInputStream.read(SocketInputStream.java:170)
java.net.SocketInputStream.read(SocketInputStream.java:141)
sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
java.io.InputStreamReader.read(InputStreamReader.java:184)
java.io.BufferedReader.fill(BufferedReader.java:161)
java.io.BufferedReader.readLine(BufferedReader.java:324)
java.io.BufferedReader.readLine(BufferedReader.java:389)
com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:64)

Thread[Reference Handler,10,system]
 java.lang.Object.wait(Native Method)
java.lang.Object.wait(Object.java:502)
java.lang.ref.Reference.tryHandlePending(Reference.java:191)
java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

Thread[Finalizer,8,system]
 java.lang.Object.wait(Native Method)
java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

Thread[default-akka.actor.default-dispatcher-2,5,main]
 akka.dispatch.Dispatcher$$anon$1.systemDrain(Dispatcher.scala:89)
akka.dispatch.Mailbox.processAllSystemMessages(Mailbox.scala:275)
akka.dispatch.Mailbox.run(Mailbox.scala:223)
akka.dispatch.Mailbox.exec(Mailbox.scala:234)
akka.dispatch.forkjoin.ForkJoinTask.doExec(ForkJoinTask.java:260)
akka.dispatch.forkjoin.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1339)
akka.dispatch.forkjoin.ForkJoinPool.runWorker(ForkJoinPool.java:1979)
akka.dispatch.forkjoin.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:107)

Thread[default-akka.kafka.default-dispatcher-19,5,main]
 sun.misc.Unsafe.park(Native Method)
java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2078)
java.util.concurrent.LinkedBlockingQueue.poll(LinkedBlockingQueue.java:467)
java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1066)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)

Thread[default-akka.kafka.default-dispatcher-17,5,main]
 sun.misc.Unsafe.park(Native Method)
java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2078)
java.util.concurrent.LinkedBlockingQueue.poll(LinkedBlockingQueue.java:467)
java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1066)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)

Thread[default-akka.kafka.default-dispatcher-16,5,main]
 sun.misc.Unsafe.park(Native Method)
java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2078)
java.util.concurrent.LinkedBlockingQueue.poll(LinkedBlockingQueue.java:467)
java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1066)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)

Thread[default-akka.kafka.default-dispatcher-20,5,main]
 sun.misc.Unsafe.park(Native Method)
java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2078)
java.util.concurrent.LinkedBlockingQueue.poll(LinkedBlockingQueue.java:467)
java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1066)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)

Thread[Signal Dispatcher,9,system]


Thread[default-akka.kafka.default-dispatcher-23,5,main]
 sun.misc.Unsafe.park(Native Method)
java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2078)
java.util.concurrent.LinkedBlockingQueue.poll(LinkedBlockingQueue.java:467)
java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1066)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)

Thread[default-akka.actor.default-dispatcher-3,5,main]
 sun.misc.Unsafe.park(Native Method)
akka.dispatch.forkjoin.ForkJoinPool.scan(ForkJoinPool.java:2075)
akka.dispatch.forkjoin.ForkJoinPool.runWorker(ForkJoinPool.java:1979)
akka.dispatch.forkjoin.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:107)

Thread[default-akka.kafka.default-dispatcher-22,5,main]
 sun.misc.Unsafe.park(Native Method)
java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2078)
java.util.concurrent.LinkedBlockingQueue.poll(LinkedBlockingQueue.java:467)
java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1066)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)

Thread[default-scheduler-1,5,main]
 java.lang.Thread.sleep(Native Method)
akka.actor.LightArrayRevolverScheduler.waitNanos(LightArrayRevolverScheduler.scala:85)
akka.actor.LightArrayRevolverScheduler$$anon$4.nextTick(LightArrayRevolverScheduler.scala:265)
akka.actor.LightArrayRevolverScheduler$$anon$4.run(LightArrayRevolverScheduler.scala:235)
java.lang.Thread.run(Thread.java:745)

Thread[default-akka.kafka.default-dispatcher-24,5,main]
 sun.misc.Unsafe.park(Native Method)
java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:215)
java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitNanos(AbstractQueuedSynchronizer.java:2078)
java.util.concurrent.LinkedBlockingQueue.poll(LinkedBlockingQueue.java:467)
java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1066)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
java.lang.Thread.run(Thread.java:745)
[ERROR] [04/29/2018 10:22:31.038] [default-akka.kafka.default-dispatcher-25] [akka://default/system/kafka-consumer-1] WakeupException limit exceeded, stopping.
[INFO] [04/29/2018 10:22:31.059] [default-akka.actor.default-dispatcher-4] [akka://default/system/kafka-consumer-1] Message [akka.kafka.KafkaConsumerActor$Internal$Stop$] without sender to Actor[akka://default/system/kafka-consumer-1#1489233997] was not delivered. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.


