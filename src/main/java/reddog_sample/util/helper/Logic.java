package reddog_sample.util.helper;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.upload.FormFile;
import org.json.simple.JSONObject;
import org.seasar.fisshplate.template.FPTemplate;
import org.seasar.framework.container.SingletonS2Container;

import au.com.bytecode.opencsv.CSVReader;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import reddog_sample.util.helper.enums.Encode;
import reddog_sample.util.helper.enums.Extension;

/**
 * Logicヘルパークラス
 *   action, form, validatorで使用する便利関数。
 *   業務ロジックをサポートする
 */
public class Logic {

    /**
     * システムのエンコーディング での文字列のバイト数を取得
     *
     * @param value 処理対象となる文字列
     * @return 文字列のバイト数
     */
    public static int getByte(String value) {

        String enc = System.getProperty("file.encoding");

        return getByte(value, enc);
    }

    /**
     * 指定した文字エンコーディングでの文字列のバイト数を取得
     *
     * @param value 処理対象となる文字列
     * @param enc 文字エンコード("Shift_JIS", "UTF-8" etc...)
     * @return 文字列のバイト数
     */
    public static int getByte(String value, String enc) {

        if ( value == null || value.length() == 0 ) {
            return 0;
        }

        int ret;
        try {
            ret = value.getBytes(enc).length;
        } catch ( UnsupportedEncodingException e ) {
            ret = 0;
        }

        return ret;
    }

    /**
     * <pre>
     * key,valueを持つmapを、「key1=value1,key2=value2...」形式の文字列に変換する。
     * jqueryで必要なエスケープも行う。.:[]
     * </pre>
     *
     * @param map
     * @return
     */
    public static String map2IdNameString(Map<String, String> map) {

        String re = "";

         for (Map.Entry<String, String> m : map.entrySet()) {

             // jQueryで必要なエスケープを行う
             String key = m.getKey()
                     .replace(".", "\\.")
                     .replace(":", "\\:")
                     .replace("[", "\\[")
                     .replace("]", "\\]");

             String val = m.getValue()
                .replace(".", "\\.")
                .replace(":", "\\:")
                .replace("[", "\\[")
                .replace("]", "\\]");

             re += String.format("%s=%s", key, val) + ",";
         }

         // 最後のカンマを削除
         re = re.substring(0, re.length()-1);

        return re;
    }

    /**
     * <pre>
     * Excelファイルを作成し、ストリームへ書き込む
     * 対応Excelファイル⇒ xls, xlsx, xlsm
     *
     * 1シートに書き込む場合だけに使用できる。
     * 複数シートに値を入れたい場合は、別途Workbookクラスを自前で作成して
     * writeResponseExcelメソッドでストリーム出力する必要がある。
     * </pre>
     *
     * @param realPath Excelテンプレートの絶対パス
     * @param params   埋め込む値
     * @param fileName ファイル名
     * @throws Exception
     */
    public static void writeResponseExcel(
            String realPath,
            Map<String, Object> params,
            String fileName) throws Exception {

        // ストリームを取得
        InputStream is = new FileInputStream(realPath);

        // テンプレートに値を書き込む
        FPTemplate template = new FPTemplate();
        HSSFWorkbook wb = template.process(is, params);

        // 拡張子を取得
        Extension ext = Extension.getValue( Logic.getSuffix(realPath) );
        if (ext == null) {
            throw new Exception("Excel以外の拡張子が指定されています");
        }

        // ストリームへ書き込む
        Logic.writeResponseExcel(wb, fileName, ext);
    }

    /**
     * ファイル名から拡張子を取得
     *
     * @param fileName ファイル名
     * @return ファイルの拡張子
     */
    public static String getSuffix(String fileName) {

        if (fileName == null) {
            return "";
        }

        int point = fileName.lastIndexOf(".");
        if (point != -1) {
            return fileName.substring(point + 1);
        }

        return fileName;
    }
    /**
     * Excelファイルをストリームへ書き込む
     *
     * @param wb        Excelテンプレート
     * @param fileName  ファイル名
     * @param ext 拡張子名 [xls, xlsx, xlsm]
     * @throws Exception
     */
    public static void writeResponseExcel(
    		HSSFWorkbook wb,
            String fileName,
            Extension ext) throws Exception {

        HttpServletRequest  req = SingletonS2Container.getComponent(HttpServletRequest.class);
        HttpServletResponse res = SingletonS2Container.getComponent(HttpServletResponse.class);

        // ---------------------------------------------
        // ファイル名をエンコーディング
        // ---------------------------------------------
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

        // IE8-10は、通常のファイル名もエンコーディングする
        String agent = req.getHeader("User-Agent");
        if (agent.indexOf("IE ") >= 0) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }

        // ---------------------------------------------
        // 出力設定
        // ---------------------------------------------
        res.setContentType("application/vnd.ms-excel");

        String extStr = ext.toString().toLowerCase();
        res.setHeader(
                "Content-Disposition",
                "attachment; filename="+ fileName+ "."+ extStr+
                           ";filename*=utf-8''"+ encodedFileName+ "."+ extStr);

        // ---------------------------------------------
        // ダウンロードをさせるため「OutputStream」に書き込む
        // ---------------------------------------------
        OutputStream out = res.getOutputStream();
        wb.write(out);
        out.close();
    }

    /**
     * ContentType[application/json] でJsonを書き込む
     * @param response
     * @param msg
     * @throws Exception
     */
    public static void writeResponseJson(JSONObject json) throws Exception {

        HttpServletResponse res = SingletonS2Container.getComponent(HttpServletResponse.class);

        res.setContentType("application/json; charset=UTF-8");
        PrintWriter w = res.getWriter();
        w.print(json.toString());
        w.close();
    }

    /**
     * PDF出力処理
     *
     * @param jasperPath
     * @param list
     * @param params
     * @param fileName
     * @throws Exception
     */
    public static void writeResponsePDF(
        String jasperPath,
        List<?> list,
        Map<String, Object> params,
        String fileName) throws Exception {

        HttpServletRequest  req = SingletonS2Container.getComponent(HttpServletRequest.class);
        HttpServletResponse res = SingletonS2Container.getComponent(HttpServletResponse.class);

        // SSL認証の為レスポンスをクリア
        res.reset();

        // ---------------------------------------------
        // byte[]としてPDFを生成
        // ---------------------------------------------
        byte[] bytes = JasperRunManager.runReportToPdf(jasperPath, params,
            new JRBeanCollectionDataSource(list));

        // ---------------------------------------------
        // ファイル名をエンコーディング
        // ---------------------------------------------
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

        // IE8-10は、通常のファイル名もエンコーディングする
        String agent = req.getHeader("User-Agent");
        if (agent.indexOf("IE ") >= 0) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }

        // ---------------------------------------------
        // 出力設定
        // ---------------------------------------------
        //response.setContentType("application/pdf");
        res.setContentLength(bytes.length);
        res.setContentType("application/pdf; charset=utf-8");
        res.setHeader(
                "Content-Disposition",
                "inline; filename="+ fileName+ ".pdf"+
                       ";filename*=utf-8''"+ encodedFileName+ ".pdf");

        // ---------------------------------------------
        // 生成したbyte[]をストリームに出力
        // ---------------------------------------------
        ServletOutputStream ouputStream = res.getOutputStream();
        ouputStream.write(bytes, 0, bytes.length);
        ouputStream.flush();
        ouputStream.close();
    }

    /**
     * <pre>
     * [OverLoad] PDF出力処理
     * 値がListではなくても受け取れるようにする
     * </pre>
     *
     * @param jasperPath
     * @param obj
     * @param params
     * @param fileName
     * @throws Exception
     */
    public static <T> void writeResponsePDF(
            String jasperPath,
            T obj,
            Map<String, Object> params,
            String fileName) throws Exception {

        List<T> list = new ArrayList<T>();
        list.add(obj);

        Logic.writeResponsePDF(jasperPath, list, params, fileName);
    }

    /**
     * CSVファイルを文字列配列リストに変換する
     * @param file
     * @param csvEncode
     * @return
     */
    public static List<String[]> convertFile2CsvLines(FormFile file, Encode csvEncode) {

        List<String[]> csvLines = new ArrayList<String[]>();

        try {
            InputStreamReader isr = new InputStreamReader(file.getInputStream(), csvEncode.getString());
            CSVReader reader = new CSVReader(isr, ',', '"', 0);

            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                csvLines.add(nextLine);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvLines;
    }

    /**
     * CSV形式に変換する(ダブルクォーテーション括り、カンマ区切り)
     * @param line
     * @return
     */
    public static String formatCSV(String[] line) {

        List<String> tmpList = new ArrayList<String>();

        // 値のダブルクォーテションをエスケープ 「" → ""」
        for (String l : line) {

            if (l == null) {
                tmpList.add( l );

            } else {
                tmpList.add( l.replace("\"", "\"\"") );
            }
        }

        String[] escapeLine = (String[]) tmpList.toArray(new String[tmpList.size()]);

        return "\""+ StringUtils.join(escapeLine, "\",\"")+ "\"";
    }

    /**
     * [Overload] CSV形式に変換する(ダブルクォーテーション括り、カンマ区切り)
     * @param line
     * @return
     */
    public static String formatCSV(List<String> line) {

        String[] tmp = (String[]) line.toArray(new String[line.size()]);

        return Logic.formatCSV(tmp);
    }

    /**
     * CSV出力処理
     *
     * @param list
     * @param title
     * @throws UnsupportedEncodingException
     */
    public static void writeResponseCSV(
            List<String> list,
            String fileName) throws Exception {

        HttpServletRequest  req = SingletonS2Container.getComponent(HttpServletRequest.class);
        HttpServletResponse res = SingletonS2Container.getComponent(HttpServletResponse.class);

        // SSL認証の為レスポンスをクリア
        res.reset();

        // ---------------------------------------------
        // ファイル名をエンコーディング
        // ---------------------------------------------
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

        // IE8-10は、通常のファイル名もエンコーディングする
        String agent = req.getHeader("User-Agent");
        if (agent.indexOf("IE ") >= 0) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }

        // ---------------------------------------------
        // 出力設定
        // ---------------------------------------------
        res.setContentType("text/plain; charset=Windows-31J");
        res.setHeader(
                "Content-Disposition",
                "attachment; filename="+ fileName+ ".csv"+
                           ";filename*=utf-8''"+ encodedFileName+ ".csv");

        // ---------------------------------------------
        // ファイル内容の出力
        // ---------------------------------------------
        OutputStream stream;
        try {
            stream = res.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Writer streamWriter;
        try {
            streamWriter = new BufferedWriter(new OutputStreamWriter(stream, "Windows-31J"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String lineCls = "\r\n";

        try {
            for (String s : list) {
                streamWriter.write(s + lineCls);
            }
            streamWriter.flush();
            streamWriter.close();
            stream.flush();
            stream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        stream = null;
        streamWriter = null;
    }
}
