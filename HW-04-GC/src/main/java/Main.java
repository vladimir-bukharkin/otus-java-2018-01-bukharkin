import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

//        int size = 5 * 1000 * 1000;
        //int size = 50 * 1000 * 1000;//for OOM with -Xms512m
        int size = 50 * 1000 * 100; //for small dump

        List<GarbageCollectorMXBean> gcBeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbageCollectorMXBean : gcBeans) {
            NotificationEmitter emitter = (NotificationEmitter) garbageCollectorMXBean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    System.out.println("Message: " + notification.getMessage());
                    System.out.println("type: " + notification.getType());
                    System.out.println("timeshtamp: " + new Date(notification.getTimeStamp()));
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    System.out.println("gcAction: " + info.getGcAction());
                    System.out.println("gcCause: " + info.getGcCause());
                    System.out.println("gcName: " + info.getGcName());
                    System.out.println("Duration: " + info.getGcInfo().getDuration());
                    System.out.println("beforeGC: " + info.getGcInfo().getMemoryUsageBeforeGc());
                    System.out.println("afterGC: " + info.getGcInfo().getMemoryUsageAfterGc());
                    System.out.println("composionType: " + info.getGcInfo().getCompositeType().toString());

                }
            };
            emitter.addNotificationListener(listener, null, null);
        }

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("otus:type=Benchmark");
        Benchmark mbean = new Benchmark();
        mbs.registerMBean(mbean, name);

        mbean.setSize(size);
        mbean.run();

    }
}
