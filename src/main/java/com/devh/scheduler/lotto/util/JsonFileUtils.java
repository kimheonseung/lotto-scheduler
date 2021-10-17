package com.devh.scheduler.lotto.util;

import com.devh.common.util.ExceptionUtils;
import com.devh.scheduler.lotto.constant.LottoResultColumn;
import com.devh.scheduler.lotto.constant.LottoResultDetailColumn;
import com.devh.scheduler.lotto.constant.NewsResultStoreField;
import com.devh.scheduler.lotto.dto.LottoResultDTO;
import com.devh.scheduler.lotto.dto.LottoResultDetailDTO;
import com.devh.scheduler.lotto.dto.LottoResultStoreDTO;
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
    private JSONObject lottoResultDTOToJSONObject(LottoResultDTO lottoResultDTO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LottoResultColumn.TURN.getSnakeCase(), lottoResultDTO.getTurn());
        jsonObject.put(LottoResultColumn.DATE.getSnakeCase(), lottoResultDTO.getDate());
        jsonObject.put(LottoResultColumn.NUMBER1.getSnakeCase(), lottoResultDTO.getNumber1());
        jsonObject.put(LottoResultColumn.NUMBER2.getSnakeCase(), lottoResultDTO.getNumber2());
        jsonObject.put(LottoResultColumn.NUMBER3.getSnakeCase(), lottoResultDTO.getNumber3());
        jsonObject.put(LottoResultColumn.NUMBER4.getSnakeCase(), lottoResultDTO.getNumber4());
        jsonObject.put(LottoResultColumn.NUMBER5.getSnakeCase(), lottoResultDTO.getNumber5());
        jsonObject.put(LottoResultColumn.NUMBER6.getSnakeCase(), lottoResultDTO.getNumber6());
        jsonObject.put(LottoResultColumn.NUMBER7.getSnakeCase(), lottoResultDTO.getNumber7());
        jsonObject.put(LottoResultColumn.TOTAL_SALES_PRICE.getSnakeCase(), lottoResultDTO.getTotalSalesPrice());
        jsonObject.put(LottoResultColumn.AUTO_WINNER_COUNT.getSnakeCase(), lottoResultDTO.getAutoWinnerCount());
        jsonObject.put(LottoResultColumn.SEMI_AUTO_WINNER_COUNT.getSnakeCase(), lottoResultDTO.getSemiAutoWinnerCount());
        jsonObject.put(LottoResultColumn.MANUAL_WINNER_COUNT.getSnakeCase(), lottoResultDTO.getManualWinnerCount());
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
    private JSONObject lottoResultDetailDTOToJSONObject(LottoResultDetailDTO lottoResultDetailDTO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LottoResultDetailColumn.LOTTO_RESULT_TURN.getSnakeCase(), lottoResultDetailDTO.getTurn());
        jsonObject.put(LottoResultDetailColumn.RANK.getSnakeCase(), lottoResultDetailDTO.getRank());
        jsonObject.put(LottoResultDetailColumn.TOTAL_WINNER_COUNT.getSnakeCase(), lottoResultDetailDTO.getTotalWinnerCount());
        jsonObject.put(LottoResultDetailColumn.TOTAL_PRIZE.getSnakeCase(), lottoResultDetailDTO.getTotalPrize());
        jsonObject.put(LottoResultDetailColumn.PER_PERSON_PRIZE.getSnakeCase(), lottoResultDetailDTO.getPerPersonPrize());
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
    private JSONObject lottoResultStoreDTOToJSONObject(LottoResultStoreDTO lottoResultStoreDTO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NewsResultStoreField.ROW_ID.getSnakeCase(), lottoResultStoreDTO.getRowId());
        jsonObject.put(NewsResultStoreField.TURN.getSnakeCase(), lottoResultStoreDTO.getTurn());
        jsonObject.put(NewsResultStoreField.RANK.getSnakeCase(), lottoResultStoreDTO.getRank());
        jsonObject.put(NewsResultStoreField.METHOD.getSnakeCase(), lottoResultStoreDTO.getMethod());
        jsonObject.put(NewsResultStoreField.STORE_NUMBER.getSnakeCase(), lottoResultStoreDTO.getStoreNumber());
        jsonObject.put(NewsResultStoreField.STORE_NAME.getSnakeCase(), lottoResultStoreDTO.getStoreName());
        jsonObject.put(NewsResultStoreField.STORE_PHONE.getSnakeCase(), lottoResultStoreDTO.getStorePhone());
        jsonObject.put(NewsResultStoreField.STORE_ADDRESS.getSnakeCase(), lottoResultStoreDTO.getStoreAddress());
        jsonObject.put(NewsResultStoreField.STORE_ADDRESS1.getSnakeCase(), lottoResultStoreDTO.getStoreAddress1());
        jsonObject.put(NewsResultStoreField.STORE_ADDRESS2.getSnakeCase(), lottoResultStoreDTO.getStoreAddress2());
        jsonObject.put(NewsResultStoreField.STORE_ADDRESS3.getSnakeCase(), lottoResultStoreDTO.getStoreAddress3());
        jsonObject.put(NewsResultStoreField.STORE_MAP_ID.getSnakeCase(), lottoResultStoreDTO.getStoreMapId());
        JSONObject location = new JSONObject();
        location.put(NewsResultStoreField.STORE_LOCATION_LAT.getSnakeCase(), lottoResultStoreDTO.getStoreLatitude());
        location.put(NewsResultStoreField.STORE_LOCATION_LON.getSnakeCase(), lottoResultStoreDTO.getStoreLongitude());
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
    public void createLottoResultJsonFile(LottoResultDTO lottoResultDTO) {
        JSONObject json = lottoResultDTOToJSONObject(lottoResultDTO);
        String RESULT_FILE_PATH = "result";
        final String filePath = JSON_FILE_PATH + File.separator + RESULT_FILE_PATH;
        final String fileName = String.format(fourDigitIntegerWithZeroFillFormat, lottoResultDTO.getTurn()) + FILE_EXT;
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
    public void createLottoResultDetailListJsonFile(List<LottoResultDetailDTO> lottoResultDetailDTOList) {
        JSONArray json = new JSONArray();
        lottoResultDetailDTOList.forEach(lottoResultDetailDTO -> json.add(lottoResultDetailDTOToJSONObject(lottoResultDetailDTO)));
        String DETAIL_FILE_PATH = "detail";
        final String filePath = JSON_FILE_PATH + File.separator + DETAIL_FILE_PATH;
        final String fileName = String.format(fourDigitIntegerWithZeroFillFormat, lottoResultDetailDTOList.get(0).getTurn()) + FILE_EXT;
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
    public void createLottoResultStoreListJsonFile(List<LottoResultStoreDTO> lottoResultStoreDTOList) {
        JSONArray json = new JSONArray();
        lottoResultStoreDTOList.forEach(lottoResultStoreDTO -> json.add(lottoResultStoreDTOToJSONObject(lottoResultStoreDTO)));
        String STORE_FILE_PATH = "store";
        final String filePath = JSON_FILE_PATH + File.separator + STORE_FILE_PATH;
        final String fileName = String.format(fourDigitIntegerWithZeroFillFormat, lottoResultStoreDTOList.get(0).getTurn()) + FILE_EXT;
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
