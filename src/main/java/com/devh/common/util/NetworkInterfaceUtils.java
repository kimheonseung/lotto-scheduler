package com.devh.common.util;

import lombok.extern.log4j.Log4j2;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * <pre>
 * Description :
 *     네트워크 인터페이스 관련 유틸 클래스
 * ==============================
 * Memberfields :
 *
 * ==============================
 *
 * Author : HeonSeung Kim
 * Date   : 2021. 3. 29.
 * </pre>
 */
@Log4j2
public class NetworkInterfaceUtils {
    /* Singleton */
    private static NetworkInterfaceUtils instance;
    public static NetworkInterfaceUtils getInstance() {
        if(instance == null)
            instance = new NetworkInterfaceUtils();
        return instance;
    }
    /* Singleton */

    /**
     * <pre>
     * Description :
     *     네트워크 인터페이스 목록 출력
     * ==============================
     * Parameters :
     *
     * Returns :
     *
     * Throws :
     *
     * ==============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 3. 29.
     * </pre>
     */
    public void printNetworkInterfaces() {
        try {
            System.out.println("Printing all network interfaces... This may take a few seconds.");
            Enumeration<NetworkInterface> niEnums = NetworkInterface.getNetworkInterfaces();

            StringBuffer sbResult = new StringBuffer(System.lineSeparator());

            while(niEnums.hasMoreElements()) {
                NetworkInterface ni = niEnums.nextElement();
                Enumeration<InetAddress> iaEnums = ni.getInetAddresses();

                boolean isInetAddressExists = iaEnums.hasMoreElements();

                if(!isInetAddressExists)
                    continue;

                sbResult
                        .append(String.format("%-15s : %s", "Interface Name", ni.getName())).append(System.lineSeparator())
                        .append(String.format("%-15s : %s", "Display Name", ni.getDisplayName())).append(System.lineSeparator());

                while(iaEnums.hasMoreElements()) {
                    InetAddress inetAddress = iaEnums.nextElement();

                    sbResult
                            .append(String.format("%-15s : %s", "Host Name", inetAddress.getHostName())).append(System.lineSeparator())
                            .append(String.format("%-15s : %s", "Host Address", inetAddress.getHostAddress())).append(System.lineSeparator());
                }

                sbResult
                        .append("====================================================================").append(System.lineSeparator());

            }

            log.info(sbResult.toString());

        } catch (SocketException e) {
            System.out.println("Exception caught while getting network interfaces. " + e.getMessage());
            log.error(ExceptionUtils.getInstance().getPrintStackTraceToString(e));
        }

    }

    /**
     * <pre>
     * Description :
     *     인터페이스명에 해당하는 IPv4 주소 형식 반환
     * ==============================
     * Parameters :
     *     String interfaceName
     * Returns :
     *     String (IPv4 Host Address)
     *     가져오지 못하는 경우 null 반환
     * Throws :
     *
     * ==============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 3. 29.
     * </pre>
     */
    public String getIPv4HostAddressByInterfaceName(String interfaceName) {
        String hostAddress = null;

        try {
            NetworkInterface ni = NetworkInterface.getByName(interfaceName);
            Enumeration<InetAddress> iaEnums = ni.getInetAddresses();

            while(iaEnums.hasMoreElements()) {
                InetAddress inetAddress = iaEnums.nextElement();

                final boolean isTarget =
                        !inetAddress.isLoopbackAddress() &&
                                !inetAddress.isLinkLocalAddress() &&
                                inetAddress.isSiteLocalAddress();

                if(isTarget)
                    hostAddress = inetAddress.getHostAddress();
            }
        } catch (SocketException e) {
            System.out.println("Exception caught while getting network interfaces. " + e.getMessage());
            log.error(ExceptionUtils.getInstance().getPrintStackTraceToString(e));
        }

        return hostAddress;
    }

    /**
     * <pre>
     * Description :
     *     IPv4 주소로 인터페이스 인덱스 반환
     * ==============================
     * Parameters :
     *     String ipv4HostAddress (IPv4 Host Address)
     * Returns :
     *     String (index)
     *     가져오지 못하는 경우 null 반환
     * Throws :
     *
     * ==============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 3. 29.
     * </pre>
     */
    public String getNetworkInterfaceIndexByIPv4HostAddress(String ipv4HostAddress) {
        String result = null;
        if(ipv4HostAddress == null || ipv4HostAddress.length() == 0)
            return result;
        try {
            Enumeration<NetworkInterface> niEnums = NetworkInterface.getNetworkInterfaces();
            while(niEnums.hasMoreElements()) {
                NetworkInterface ni = niEnums.nextElement();
                Enumeration<InetAddress> iaEnums = ni.getInetAddresses();

                boolean isInetAddressExists = iaEnums.hasMoreElements();

                if(!isInetAddressExists)
                    continue;

                while(iaEnums.hasMoreElements()) {
                    InetAddress inetAddress = iaEnums.nextElement();
                    final String hostAddress = inetAddress.getHostAddress();
                    if(ipv4HostAddress.equals(hostAddress))
                        result = String.valueOf(ni.getIndex());
                }
            }
        } catch(Exception e) {
            System.out.println("Exception caught while getting network interfaces. " + e.getMessage());
            log.error(ExceptionUtils.getInstance().getPrintStackTraceToString(e));
        }
        return result;
    }

    /**
     * <pre>
     * Description :
     *     IPv4 주소로 인터페이스명 반환
     * ==============================
     * Parameters :
     *     String ipv4HostAddress (IPv4 Host Address)
     * Returns :
     *     String (interfaceName)
     *     가져오지 못하는 경우 null 반환
     * Throws :
     *
     * ==============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 3. 29.
     * </pre>
     */
    public String getNetworkInterfaceNameByIPv4HostAddress(String ipv4HostAddress) {
        String result = null;
        if(ipv4HostAddress == null || ipv4HostAddress.length() == 0)
            return result;
        try {
            Enumeration<NetworkInterface> niEnums = NetworkInterface.getNetworkInterfaces();
            while(niEnums.hasMoreElements()) {
                NetworkInterface ni = niEnums.nextElement();
                Enumeration<InetAddress> iaEnums = ni.getInetAddresses();

                boolean isInetAddressExists = iaEnums.hasMoreElements();

                if(!isInetAddressExists)
                    continue;

                while(iaEnums.hasMoreElements()) {
                    InetAddress inetAddress = iaEnums.nextElement();
                    final String hostAddress = inetAddress.getHostAddress();
                    if(ipv4HostAddress.equals(hostAddress))
                        result = ni.getName();
                }
            }
        } catch(Exception e) {
            System.out.println("Exception caught while getting network interfaces. " + e.getMessage());
            log.error(ExceptionUtils.getInstance().getPrintStackTraceToString(e));
        }
        return result;
    }

    /**
     * <pre>
     * Description :
     *     인터페이스명에 해당하는 MAC 주소 형식 반환
     * ==============================
     * Parameters :
     *     String interfaceName
     * Returns :
     *     String (-가 포함된 MAC 주소)
     * Throws :
     *
     * ==============================
     *
     * Author : HeonSeung Kim
     * Date   : 2021. 3. 29.
     * </pre>
     */
    public String getMacAddressByInterfaceName(String interfaceName) {
        String macAddress = "";

        try {
            NetworkInterface ni = NetworkInterface.getByName(interfaceName);
            byte[] macByteArray = ni.getHardwareAddress();
            int macByteArrayLength = macByteArray.length;
            for(int i = 0 ; i < macByteArrayLength ; ++i)
                macAddress += String.format("%02X%s", macByteArray[i], (i < macByteArrayLength - 1) ? "-" : "");

        } catch (SocketException e) {
            System.out.println("Exception caught while getting network interfaces. " + e.getMessage());
            log.error(ExceptionUtils.getInstance().getPrintStackTraceToString(e));
        }

        return macAddress;
    }

}
