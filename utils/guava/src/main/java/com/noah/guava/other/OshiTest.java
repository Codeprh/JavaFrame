package com.noah.guava.other;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class OshiTest {

    public static void main(String[] args) {


        GlobalMemory memory = OshiUtil.getMemory();
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();

        OperatingSystem os = OshiUtil.getOs();
        HardwareAbstractionLayer hardware = OshiUtil.getHardware();
        ComputerSystem system = OshiUtil.getSystem();

        System.out.println(os);
        System.out.println(hardware);
        System.out.println(system);

        System.out.println(memory);

    }
}
