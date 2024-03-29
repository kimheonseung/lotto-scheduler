package com.devh.scheduler.lotto.util;

import com.devh.common.util.ExceptionUtils;
import com.devh.scheduler.lotto.constant.LottoResultColumn;
import com.devh.scheduler.lotto.constant.LottoResultDetailColumn;
import com.devh.scheduler.lotto.constant.NewsResultStoreField;
import com.devh.scheduler.lotto.vo.LottoResultVO;
import com.devh.scheduler.lotto.vo.LottoResultDetailVO;
import com.devh.scheduler.lotto.vo.LottoResultStoreVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * <pre>
 * Description : 
 *     JSON 객체를 파일로 저장하기 위한 유틸
 * ===============================================
 * Member fields : 
 *     
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021-04-10
 * </pre>
 */
@SuppressWarnings("unchecked")
public class JsonFileUtils {
    private final Logger logger = LoggerFactory.getLogger(JsonFileUtils.class);

    private final String JSON_FILE_PATH = "lotto_json";
    private final String FILE_EXT = ".json";

    private final String fourDigitIntegerWithZeroFillFormat = "%04d";

    /* Singleton */
    private static JsonFileUtils instance;
    public static JsonFileUtils getInstance() {
        if(instance == null)
            instance = new JsonFileUtils();
        return instance;
    }

    /**
     * <pre>
     * Description
     *     LottoResultDTO 객체를 JSON 객체로 변환
     * ===============================================
     * Parameters
     *     LottoResultDTO lottoResultDTO
     * Returns
     *     JSONObject
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    private JSONObject lottoResultDTOToJSONObject(LottoResultVO lottoResultVO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LottoResultColumn.TURN.getSnakeCase(), lottoResultVO.getTurn());
        jsonObject.put(LottoResultColumn.DATE.getSnakeCase(), lottoResultVO.getDate());
        jsonObject.put(LottoResultColumn.NUMBER1.getSnakeCase(), lottoResultVO.getNumber1());
        jsonObject.put(LottoResultColumn.NUMBER2.getSnakeCase(), lottoResultVO.getNumber2());
        jsonObject.put(LottoResultColumn.NUMBER3.getSnakeCase(), lottoResultVO.getNumber3());
        jsonObject.put(LottoResultColumn.NUMBER4.getSnakeCase(), lottoResultVO.getNumber4());
        jsonObject.put(LottoResultColumn.NUMBER5.getSnakeCase(), lottoResultVO.getNumber5());
        jsonObject.put(LottoResultColumn.NUMBER6.getSnakeCase(), lottoResultVO.getNumber6());
        jsonObject.put(LottoResultColumn.NUMBER7.getSnakeCase(), lottoResultVO.getNumber7());
        jsonObject.put(LottoResultColumn.TOTAL_SALES_PRICE.getSnakeCase(), lottoResultVO.getTotalSalesPrice());
        jsonObject.put(LottoResultColumn.AUTO_WINNER_COUNT.getSnakeCase(), lottoResultVO.getAutoWinnerCount());
        jsonObject.put(LottoResultColumn.SEMI_AUTO_WINNER_COUNT.getSnakeCase(), lottoResultVO.getSemiAutoWinnerCount());
        jsonObject.put(LottoResultColumn.MANUAL_WINNER_COUNT.getSnakeCase(), lottoResultVO.getManualWinnerCount());
        return jsonObject;
    }

    /**
     * <pre>
     * Description
     *     LottoResultDetailDTO 객체를 JSON 객체로 변환
     * ===============================================
     * Parameters
     *     LottoResultDetailDTO lottoResultDetailDTO
     * Returns
     *     JSONObject
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    private JSONObject lottoResultDetailDTOToJSONObject(LottoResultDetailVO lottoResultDetailVO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LottoResultDetailColumn.LOTTO_RESULT_TURN.getSnakeCase(), lottoResultDetailVO.getTurn());
        jsonObject.put(LottoResultDetailColumn.RANK.getSnakeCase(), lottoResultDetailVO.getRank());
        jsonObject.put(LottoResultDetailColumn.TOTAL_WINNER_COUNT.getSnakeCase(), lottoResultDetailVO.getTotalWinnerCount());
        jsonObject.put(LottoResultDetailColumn.TOTAL_PRIZE.getSnakeCase(), lottoResultDetailVO.getTotalPrize());
        jsonObject.put(LottoResultDetailColumn.PER_PERSON_PRIZE.getSnakeCase(), lottoResultDetailVO.getPerPersonPrize());
        return jsonObject;
    }

    /**
     * <pre>
     * Description
     *     LottoResultStoreDTO 객체를 JSON 객체로 변환
     * ===============================================
     * Parameters
     *     LottoResultStoreDTO lottoResultStoreDTO
     * Returns
     *     JSONObject
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    private JSONObject lottoResultStoreDTOToJSONObject(LottoResultStoreVO lottoResultStoreVO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NewsResultStoreField.ROW_ID.getSnakeCase(), lottoResultStoreVO.getRowId());
        jsonObject.put(NewsResultStoreField.TURN.getSnakeCase(), lottoResultStoreVO.getTurn());
        jsonObject.put(NewsResultStoreField.RANK.getSnakeCase(), lottoResultStoreVO.getRank());
        jsonObject.put(NewsResultStoreField.METHOD.getSnakeCase(), lottoResultStoreVO.getMethod());
        jsonObject.put(NewsResultStoreField.STORE_NUMBER.getSnakeCase(), lottoResultStoreVO.getStoreNumber());
        jsonObject.put(NewsResultStoreField.STORE_NAME.getSnakeCase(), lottoResultStoreVO.getStoreName());
        jsonObject.put(NewsResultStoreField.STORE_PHONE.getSnakeCase(), lottoResultStoreVO.getStorePhone());
        jsonObject.put(NewsResultStoreField.STORE_ADDRESS.getSnakeCase(), lottoResultStoreVO.getStoreAddress());
        jsonObject.put(NewsResultStoreField.STORE_ADDRESS1.getSnakeCase(), lottoResultStoreVO.getStoreAddress1());
        jsonObject.put(NewsResultStoreField.STORE_ADDRESS2.getSnakeCase(), lottoResultStoreVO.getStoreAddress2());
        jsonObject.put(NewsResultStoreField.STORE_ADDRESS3.getSnakeCase(), lottoResultStoreVO.getStoreAddress3());
        jsonObject.put(NewsResultStoreField.STORE_MAP_ID.getSnakeCase(), lottoResultStoreVO.getStoreMapId());
        JSONObject location = new JSONObject();
        location.put(NewsResultStoreField.STORE_LOCATION_LAT.getSnakeCase(), lottoResultStoreVO.getStoreLatitude());
        location.put(NewsResultStoreField.STORE_LOCATION_LON.getSnakeCase(), lottoResultStoreVO.getStoreLongitude());
        jsonObject.put(NewsResultStoreField.STORE_LOCATION.getSnakeCase(), location);
        return jsonObject;
    }

    /**
     * <pre>
     * Description
     *     LottoResultDTO를 JSON 파일로 저장
     * ===============================================
     * Parameters
     *     LottoResultDTO lottoResultDTO
     * Returns
     *     void
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    public void createLottoResultJsonFile(LottoResultVO lottoResultVO) {
        JSONObject json = lottoResultDTOToJSONObject(lottoResultVO);
        String RESULT_FILE_PATH = "result";
        final String filePath = JSON_FILE_PATH + File.separator + RESULT_FILE_PATH;
        final String fileName = String.format(fourDigitIntegerWithZeroFillFormat, lottoResultVO.getTurn()) + FILE_EXT;
        writeContentToFile(filePath, fileName, json.toJSONString());
    }

    /**
     * <pre>
     * Description
     *     LottoResultDetail 리스트를 JSON 파일로 저장
     * ===============================================
     * Parameters
     *     List<LottoResultDetailDTO> lottoResultDetailDTOList
     * Returns
     *     void
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    public void createLottoResultDetailListJsonFile(List<LottoResultDetailVO> lottoResultDetailVOList) {
        JSONArray json = new JSONArray();
        lottoResultDetailVOList.forEach(lottoResultDetailDTO -> json.add(lottoResultDetailDTOToJSONObject(lottoResultDetailDTO)));
        String DETAIL_FILE_PATH = "detail";
        final String filePath = JSON_FILE_PATH + File.separator + DETAIL_FILE_PATH;
        final String fileName = String.format(fourDigitIntegerWithZeroFillFormat, lottoResultDetailVOList.get(0).getTurn()) + FILE_EXT;
        writeContentToFile(filePath, fileName, json.toJSONString());
    }

    /**
     * <pre>
     * Description
     *     LottoResultStore 리스트를 JSON 파일로 저장
     * ===============================================
     * Parameters
     *     List<LottoResultStoreDTO> lottoResultStoreDTOList
     * Returns
     *     void
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    public void createLottoResultStoreListJsonFile(List<LottoResultStoreVO> lottoResultStoreVOList) {
        JSONArray json = new JSONArray();
        lottoResultStoreVOList.forEach(lottoResultStoreDTO -> json.add(lottoResultStoreDTOToJSONObject(lottoResultStoreDTO)));
        String STORE_FILE_PATH = "store";
        final String filePath = JSON_FILE_PATH + File.separator + STORE_FILE_PATH;
        final String fileName = String.format(fourDigitIntegerWithZeroFillFormat, lottoResultStoreVOList.get(0).getTurn()) + FILE_EXT;
        writeContentToFile(filePath, fileName, json.toJSONString());
    }


    /**
     * <pre>
     * Description
     *     파일경로, 파일명, 파일에 쓸 내용을 전달하여 파일을 생성
     * ===============================================
     * Parameters
     *     String filePath
     *     String fileName
     *     String content
     * Returns
     *     void
     * Throws
     *     Nothing
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-04-10
     * </pre>
     */
    private void writeContentToFile(String filePath, String fileName, String content) {
        File dir = new File(filePath);
        if(!dir.exists())
            dir.mkdirs();

        File resultFile = new File(filePath + File.separator + fileName);

        try (
                FileWriter fileWriter = new FileWriter(resultFile)
        ) {
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {
            ExceptionUtils.getInstance().printErrorLogWithException(logger, e);
        }
    }
}
