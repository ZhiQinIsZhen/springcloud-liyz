package com.liyz.cloud.common.export.constant;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/5 15:48
 */
public interface ExportConstant {

    String OFFICE_EXCEL_2003_POSTFIX = ".xls";
    String OFFICE_EXCEL_2007_POSTFIX = ".xlsx";
    String OFFICE_CSV_POSTFIX = ".csv";
    String FILE_EXTENSION_SPLITER = ".";

    int COLUMN_NUM = 8;
    /**
     * 默认字体
     */
    String DEFAULT_FONT = "宋体";

    String EXCEPTION_MSG_TYPE = "类型传入异常";

    String EXCEPTION_MSG_COLUM_REPEAT = "列的顺序重复";

    String EXCEPTION_MSG_NO_INFO = "没有设置导出注解";

    String EXCEPTION_MSG_PARAM_ERROR = "导出参数错误";

    String EXCEPTION_MSG_EXPORT = "导出异常";
}
