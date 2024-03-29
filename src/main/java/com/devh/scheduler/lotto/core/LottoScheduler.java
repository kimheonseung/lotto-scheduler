package com.devh.scheduler.lotto.core;

import com.devh.common.constant.ScheduleStatus;
import com.devh.common.interfaces.IScheduler;
import com.devh.common.util.ExceptionUtils;
import com.devh.common.util.component.JsoupUtils;
import com.devh.scheduler.lotto.constant.LottoConstant;
import com.devh.scheduler.lotto.service.LottoResultDetailService;
import com.devh.scheduler.lotto.service.LottoResultService;
import com.devh.scheduler.lotto.service.LottoResultStoreService;
import com.devh.scheduler.lotto.service.LottoWinningStoreService;
import com.devh.scheduler.lotto.vo.LottoResultDetailVO;
import com.devh.scheduler.lotto.vo.LottoResultVO;
import com.devh.scheduler.lotto.vo.LottoWinningStoreVO;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LottoScheduler implements IScheduler {

    /* 스케줄 스레드 */
    private Thread mScheduleThread;

    /* DI */
    private final LottoResultService lottoResultService;
    private final LottoResultDetailService lottoResultDetailService;
//    private final LottoResultStoreService lottoResultStoreService;
    private final LottoWinningStoreService lottoWinningStoreService;
    private final JsoupUtils jsoupUtils;
    private final LottoParser lottoParser;

    /* 홈페이지에 빈 데이터가 먼저 올라오는 문제로 임시 조치 */
    @Scheduled(cron = "0/30 45-59 21 * * 6")
    @Override
    public void scheduleStart() {
        if(mScheduleThread == null || !mScheduleThread.isAlive()) {
            mScheduleThread = new Thread(new LottoSchedulerThread());
            mScheduleThread.start();
        }
    }

    @Override
    public ScheduleStatus getScheduleStatus() {
        return mScheduleThread == null || !mScheduleThread.isAlive() ?
                ScheduleStatus.NOT_RUNNING :
                ScheduleStatus.RUNNING;
    }

    /**
     * <pre>
     * Description :
     *     실제 데이터를 파싱 및 적재하는 이너 클래스 스레드
     * ===============================================
     * Member fields :
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    class LottoSchedulerThread implements Runnable {
        private final Logger logger = LoggerFactory.getLogger(LottoSchedulerThread.class);

        @Override
        public void run() {
            try {

                boolean isLatest = false;

                while(!isLatest) {
                    /* DB 최신 회차 */
                    final Integer latestTurnInDB = lottoResultService.getLatestTurn();

                    /* 웹 페이지 최신 회차 */
                    final Document latestDocument = jsoupUtils.getDocumentFromURL(LottoConstant.LottoURL.LATEST.getUrl());
                    if(latestDocument == null) {
                        logger.error("Schedule will stop. Cause : Failed to get latest turn.");
                        break;
                    }
                    final Integer latestTurnInWeb = lottoParser.getTurnFromDocument(latestDocument);
                    if(latestTurnInDB.intValue() == latestTurnInWeb.intValue()) {
                        logger.warn("Database Information is up to date.");
                        break;
                    }

                    /* 파싱할 회차 */
                    final int targetTurn = latestTurnInDB + 1;
                    final String targetUrl = LottoConstant.LottoURL.PAST_PREFIX.getUrl() + targetTurn;
                    final Document targetDocument = jsoupUtils.getDocumentFromURL(targetUrl);
                    if(targetDocument == null) {
                        logger.error("Schedule will stop. Cause : Failed to get target turn. - " + targetTurn);
                        break;
                    }
                    LottoResultVO lottoResultVO = lottoParser.getLottoResultVOFromDocument(targetDocument);

                    List<LottoResultDetailVO> lottoResultDetailVOList = lottoParser.getLottoResultDetailVOListFromURL(targetDocument);
                    if(lottoResultDetailVOList == null || lottoResultDetailVOList.size() == 0) {
                        logger.error("Schedule will stop. Cause : Failed to parse LottoResultDetailVO list. - " + targetUrl);
                        break;
                    }

                    lottoResultService.saveVO(lottoResultVO);
                    lottoResultDetailService.saveVOList(lottoResultDetailVOList);

//                    List<LottoResultStoreVO> lottoResultStoreList = lottoParser.getLottoResultStoreVOListFromTurn(targetTurn);
//                    if(lottoResultStoreList != null && lottoResultStoreList.size() > 0) {
//                        boolean bulkResult = lottoResultStoreService.bulkLottoResultStoreVOList(lottoResultStoreList);
//                        logger.info("bulkRequest : " + bulkResult);
//                        JsonFileUtils.getInstance().createLottoResultStoreListJsonFile(lottoResultStoreList);
//                    }
                    List<LottoWinningStoreVO> lottoWinningStoreVOList = lottoParser.getLottoWinningStoreVOListFromTurn(targetTurn);
                    if(lottoWinningStoreVOList != null && lottoWinningStoreVOList.size() > 0) {
                        boolean result = lottoWinningStoreService.saveAll(lottoWinningStoreVOList);
                        logger.info("store saved : " + result + " / " + lottoWinningStoreVOList.size());
                    }

//                    JsonFileUtils.getInstance().createLottoResultJsonFile(lottoResultVO);
//                    JsonFileUtils.getInstance().createLottoResultDetailListJsonFile(lottoResultDetailVOList);
                    logger.info(targetTurn + " : Success to save.");

                    try {Thread.sleep(500L);} catch (InterruptedException ignored) {}
                }

            } catch (Exception e) {
                ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
            }
        }
    }
}
