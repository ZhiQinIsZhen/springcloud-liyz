package com.liyz.cloud.common.export.util;

import com.liyz.cloud.common.export.annotation.Export;
import com.liyz.cloud.common.export.column.ColumnProperties;
import com.liyz.cloud.common.export.constant.ExportConstant;
import com.liyz.cloud.common.export.exception.ExportException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.output.FileWriterWithEncoding;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 15:49
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExportUtil {

    private static ThreadLocal<SortedMap<Integer, ColumnProperties>> fieldMap = new ThreadLocal<>();

    public static <T> void exportCsv(HttpServletResponse response, String title, String savePath, List<T> list,
                                     Class<T> cls) throws ExportException {
        if (title == null || savePath == null || list == null || list.size() == 0
                || cls == null || cls == String.class) {
            throw new ExportException(ExportConstant.EXCEPTION_MSG_PARAM_ERROR);
        }
        if (!savePath.endsWith("\\") || !savePath.endsWith("/")) {
            savePath = savePath + "\\";
        }
        initClass(cls);
        List<String> result = dealList(title, list);
        try {
            exportByStream(response, title, savePath, result);
        } catch (Exception e) {
            if (e instanceof ExportException) {
                throw (ExportException)e;
            } else {
                throw new ExportException(ExportConstant.EXCEPTION_MSG_EXPORT);
            }
        }
    }

    public static <T> void exportCsv(String title, String savePath, List<T> list, Class<T> cls) throws Exception {
        if (title == null || savePath == null || list == null || list.size() == 0
                || cls == null || cls == String.class) {
            throw new ExportException(ExportConstant.EXCEPTION_MSG_PARAM_ERROR);
        }
        if (!savePath.endsWith("\\") || !savePath.endsWith("/")) {
            savePath = savePath + "\\";
        }
        initClass(cls);
        List<String> result = dealList(title, list);
        File file = new File(savePath + title + ExportConstant.OFFICE_CSV_POSTFIX);
        OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "gbk");
        for (String str : result) {
            ow.write(str);
            ow.write("\r\n");
        }
        ow.flush();
        ow.close();
    }

    private static void initClass(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            throw new ExportException(ExportConstant.EXCEPTION_MSG_TYPE);
        }
        SortedMap<Integer, ColumnProperties> map = new TreeMap<>(Comparator.naturalOrder());
        for (int i = 0, length = fields.length; i < length; i++) {
            Field field = fields[i];
            if (!field.isAnnotationPresent(Export.class)) {
                continue;
            }
            Export csvExport = field.getAnnotation(Export.class);
            if (map.containsKey(csvExport.sort())) {
                throw new ExportException(ExportConstant.EXCEPTION_MSG_COLUM_REPEAT);
            }
            ColumnProperties columnProperties = ColumnProperties.builder()
                    .colName(csvExport.name())
                    .colType(csvExport.type())
                    .colField(field.getName())
                    .build();
            map.put(csvExport.sort(), columnProperties);
        }
        if (map.size() == 0) {
            throw new ExportException(ExportConstant.EXCEPTION_MSG_NO_INFO);
        }
        fieldMap.set(map);
    }

    private static List<String> dealList(String title, List<?> list) {
        SortedMap<Integer, ColumnProperties> map = fieldMap.get();
        List<String> result = new ArrayList<>(list.size()+1);
        StringBuilder builder = new StringBuilder("序号");
        for (ColumnProperties properties : map.values()) {
            builder = builder.append(",").append(properties.getColName().replaceAll(",", ";"));
        }
        result.add(builder.toString());
        Object obj;
        String methodName;
        for (int i = 0, size = list.size(); i < size; i++) {
            obj = list.get(i);
            builder.delete(0, builder.length());
            builder = builder.append(i + 1);
            for (ColumnProperties properties : map.values()) {
                try {
                    methodName = "get" + properties.getColField().substring(0, 1).toUpperCase() +
                            properties.getColField().substring(1);
                    Object columnObj = obj.getClass().getMethod(methodName).invoke(obj);
                    if (columnObj == null) {
                        builder = builder.append(",");
                    } else {
                        builder = builder.append(",").append(columnObj.toString().replaceAll(",", ";"));
                    }
                } catch (Exception e) {

                }
            }
            result.add(builder.toString());
        }
        return result;
    }

    private static void exportByStream(HttpServletResponse response, String title, String savePath,
                                       List<String> result) throws Exception {
        response.setCharacterEncoding("GBK");
        String filename = title + ExportConstant.OFFICE_CSV_POSTFIX;
        response.setHeader("contentType", "text/html; charset=GBK");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename="+filename);
        String path = savePath + filename;
        File file = new File(path);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        FileWriterWithEncoding fwwe =new FileWriterWithEncoding(file,"GBK");
        BufferedWriter bw = new BufferedWriter(fwwe);
        if (result != null && result.size() > 0) {
            for (String str : result) {
                bw.write(str);
                bw.write("\r\n");
            }
        }
        bw.close();
        fwwe.close();
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            while (true) {
                int bytesRead;
                if (-1 == (bytesRead = bis.read(buff, 0, buff.length))){
                    break;
                }
                bos.write(buff, 0, bytesRead);
            }
            file.deleteOnExit();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if(bis != null){
                    bis.close();
                }
                if(bos != null){
                    bos.flush();
                    bos.close();
                }
            }
            catch (IOException e) {
                throw e;
            }
        }
    }

    public static void delAllFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                }
            }
        }
    }
}
