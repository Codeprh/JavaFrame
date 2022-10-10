package com.noah.spring.code.affinity;

import net.openhft.affinity.AffinityLock;

public class AffinityMain {

    public static void main(String[] args) {
        try (AffinityLock affinityLock = AffinityLock.acquireLock(5)) {
            // do some work while locked to a CPU.
            while (true) {
            }
        }

    }
}
