package com.devh.common.util.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Description :
 *     URL을 통해 Document객체를 반환해주는 객체
 * ===============================================
 * Member fields :
 *      MTUUtils mtuUtils
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-02-01
 * </pre>
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class JsoupUtils {
    private final MTUUtils mtuUtils;

    /**
     * <pre>
     * Description
     *     Jsoup으로 url로부터 Document 객체를 반환
     * ===============================================
     * Parameters
     *     String url
     * Returns
     *     정상적으로 연결된 경우 Docuemnt, 404 에러시 null 반환
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-02-01
     * </pre>
     */
    public Document getDocumentFromURL(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (HttpStatusException httpStatusException) {
            try {Thread.sleep(1000L);} catch(InterruptedException ignored) {}
            if(httpStatusException.getStatusCode() == 404) {    /* 404 Not Found */
                log.warn("Page Not Found. url: " + url);
                return null;
            }
            else {
                log.warn("[" + httpStatusException.getMessage() + "] try to reconnect... " + url);
                return getDocumentFromURL(url);
            }

        } catch (Exception e) {
            try {Thread.sleep(1000L);} catch(InterruptedException ignored) {}
            log.warn("[" + e.getMessage() + "] try to reconnect... " + url);
            return getDocumentFromURL(url);
        }
    }

    /**
     * <pre>
     * Description
     *     MTU값을 조절하며 Document 객체 반환
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
     * Date   : 2021-02-10
     * </pre>
     */
    public Document getDocumentFromURLWithChangeMTU(String url) {
        boolean isConnectPossible;

        do {
            isConnectPossible = checkConnectPossible(url);
            if(!isConnectPossible)
                mtuUtils.changeMTU();

        } while(!isConnectPossible);
        return getDocumentFromURL(url);
    }

    private boolean checkConnectPossible(String url) {
        boolean result = false;

        try {
            Jsoup.connect(url).get();
            result = true;
        } catch (Exception e) {
            if(e instanceof HttpStatusException && ((HttpStatusException) e).getStatusCode() == 404) {
                HttpStatusException hse = (HttpStatusException) e;
                log.warn(hse.getMessage() + ", " + hse.getUrl() + ", Status=" + hse.getStatusCode());
                result = true;
            } else
                log.debug(e.getMessage());
        }

        return result;
    }
}
