package com.devh.common.util;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.ArrayList;

/**
 * <pre>
 * Description :
 *     명령어를 실행 객체
 * ===============================================
 * Member fields :
 *     Process mProcess
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2019-12-19
 * </pre>
 */
@Log4j2
public class CommandExecutor implements Closeable {

    private Process mProcess;

    public CommandExecutor(String command) throws Exception {
        execute(command);
    }

    /**
     * <pre>
     * Description :
     *     운영체제에 따른 명령어
     * ===============================================
     * Member fields :
     *     WINDOWS_COMMAND_1
     *     WINDOWS_COMMAND_2
     *     UNIX_COMMAND_1
     *     UNIX_COMMAND_2
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2019-12-19
     * </pre>
     */
    @Getter
    public enum Command {
        WINDOWS_COMMAND_1("cmd"),
        WINDOWS_COMMAND_2("/c"),
        UNIX_COMMAND_1("/bin/sh"),
        UNIX_COMMAND_2("-c");
        private final String value;
        Command(String value) {
            this.value = value;
        }
    }

    /**
     * <pre>
     * Description
     *     명령어 실행 메소드
     * ===============================================
     * Parameters
     *     String command 실행할 명령어 문자열
     * Returns
     *
     * Throws
     *     지원하지 않는 운영체제인 경우 예외 발생 (현재 윈도우, 유닉스 계열만 테스트)
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2019-12-19
     * </pre>
     */
    private void execute(String command) throws Exception {
        ArrayList<String> commandList = new ArrayList<>();

        if(OSUtils.getInstance().isWindows()) {
            commandList.add(Command.WINDOWS_COMMAND_1.getValue());
            commandList.add(Command.WINDOWS_COMMAND_2.getValue());
        } else if(OSUtils.getInstance().isUnix() || OSUtils.getInstance().isMac()) {
            commandList.add(Command.UNIX_COMMAND_1.getValue());
            commandList.add(Command.UNIX_COMMAND_2.getValue());
        } else {
            throw new RuntimeException("Unsupported operating system");
        }

        commandList.add(command);

        this.mProcess = Runtime.getRuntime().exec(commandList.toArray(new String[commandList.size()]));
    }

    @Override
    public void close() throws IOException {
        closeStream();
        destroy();
    }

    /**
     * <pre>
     * Description
     *     명령어 실행 후 관련 스트림 처리
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
     * Date   : 2019-12-19
     * </pre>
     */
    public void closeStream() {
        try {
            this.mProcess.getErrorStream().close();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }

        try {
            this.mProcess.getInputStream().close();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }

        try {
            this.mProcess.getOutputStream().close();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * <pre>
     * Description
     *     명령어 모두 실행 후 하위 Process까지 종료
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
     * Date   : 2019-12-19
     * </pre>
     */
    public void destroy() {
        this.mProcess.destroy();
    }

    /**
     * <pre>
     * Description
     *     명령어 실행 결과 스트림
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
     * Date   : 2019-12-19
     * </pre>
     */
    public BufferedReader getInputStream() throws UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(this.mProcess.getInputStream(), "EUC-KR"));
    }

    /**
     * <pre>
     * Description
     *     명령어 실행 결과 에러 스트림
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
     * Date   : 2019-12-19
     * </pre>
     */
    public BufferedReader getErrorStream() throws UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(this.mProcess.getErrorStream(), "EUC-KR"));
    }
}
