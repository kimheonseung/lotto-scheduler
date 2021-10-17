package com.devh.common.util.component;

import com.devh.common.constant.StringConstant;
import com.devh.common.util.CommandExecutor;
import com.devh.common.util.NetworkInterfaceUtils;
import com.devh.common.util.OSUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * <pre>
 * Description :
 *     Jsoup 접근 불가능한 곳을 우회하기 위해 MTU값을 변경하는 클래스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-09
 * </pre>
 */
@Log4j2
@Component
public class MTUUtils {
    private int mtu = 1420;
    private final boolean IS_WINDOWS = OSUtils.getInstance().isWindows();
    private final String MTU_CHANGE_COMMAND;

//    @Value("${server.ip}")
    private final String serverIp;

    /* Constructor */
    public MTUUtils(@Value("${server.ip}") String serverIp) throws Exception {
        log.info("SERVER IP : " + serverIp);
        this.serverIp = serverIp;
        this.MTU_CHANGE_COMMAND = getMTUChangeCommand();
        if(this.MTU_CHANGE_COMMAND == null)
            throw new Exception("Network Interface Exception. Check your ip and network interface");
    }
    /* Constructor */

    /**
     * <pre>
     * Description
     *     MTU 변경 명령어를 반환하는 메소드
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-11
     * </pre>
     */
    private String getMTUChangeCommand() {

        /* 2021-05 mtu 변경에 필요한 값을 조회하는 부분을 Java API로 변경 */
        final String interfaceId = IS_WINDOWS ?
                NetworkInterfaceUtils.getInstance().getNetworkInterfaceIndexByIPv4HostAddress(this.serverIp) :
                NetworkInterfaceUtils.getInstance().getNetworkInterfaceNameByIPv4HostAddress(this.serverIp);

        if(interfaceId == null)
            return null;

        final String command = IS_WINDOWS ?
                "netsh interface ipv4 set subinterface \"" + interfaceId + "\" mtu=" :
                "ifconfig " + interfaceId + " mtu ";
        return command;
    }

    public void changeMTU() {

        updateMTU();

        final String finalMTUChangeCommand = MTU_CHANGE_COMMAND + this.mtu;

        try (
                CommandExecutor mtuChangeCommandExecutor = new CommandExecutor(finalMTUChangeCommand);
                BufferedReader mtuChangeBufferedReader = mtuChangeCommandExecutor.getInputStream();
                BufferedReader mtuChangeBufferedErrorReader = mtuChangeCommandExecutor.getErrorStream()
        ) {

            log.info(finalMTUChangeCommand);

            String line;
            StringBuilder stringBuffer = new StringBuilder();
            while ( (line = mtuChangeBufferedReader.readLine()) != null ) {
                if(line.trim().length() == 0)
                    continue;
                stringBuffer.append(System.lineSeparator()).append(line);
            }
            if(!StringConstant.BLANK.getValue().equals(stringBuffer.toString().trim()))
                log.info(stringBuffer.toString().trim());

            checkError(mtuChangeBufferedErrorReader);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

    }

    /**
     * <pre>
     * Description
     *     MTU 값을 가장 최저로 변경
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-11
     * </pre>
     */
    private void resetMTU() {
        if(IS_WINDOWS)
            this.mtu = 576;
        else
            this.mtu = 220;

        log.info("resetMTU : " + this.mtu);
    }

    /**
     * <pre>
     * Description
     *     MTU 값을 10 증가. 1500을 넘으면 최저로 변경
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-11
     * </pre>
     */
    private void updateMTU() {
        if((this.mtu += 10) >= 1500)
            resetMTU();
        log.info("updateMTU " + this.mtu);
    }

    private void checkError(BufferedReader bufferedReader) throws IOException {
        String errorLine;
        StringBuilder errorStringBuffer = new StringBuilder();
        while ( (errorLine = bufferedReader.readLine()) != null ) {
            if(errorLine.trim().length() == 0)
                continue;
            errorStringBuffer.append(System.lineSeparator()).append(errorLine);
        }
        if(!StringConstant.BLANK.getValue().equals(errorStringBuffer.toString().trim()))
            log.warn(errorStringBuffer.toString().trim());
    }
}
