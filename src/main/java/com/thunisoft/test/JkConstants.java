package com.thunisoft.test;

/**
 * @author zhangliujie
 * @Description
 * @date 2020/3/23.
 */
public interface JkConstants {

    String LCMB_SP = "1";

    String LCMB_ZX = "2";

    String ETL_STATUS_RUNNING = "1";

    String ETL_STATUS_SUCCESS = "2";

    String ETL_STATUS_FAIL = "3";

    //分组失败,本任务之前的关键节点任务运行失败
    String ETL_STATUS_IMFAIL = "4";

    String ETL_STATUS_WAIT = "0";

    int EXPORT_STATUS_NORUN = 1;

    int EXPORT_STATUS_FAIL = 2;

    int EXPORT_STATUS_SUCCESS = 3;

    int IMPORT_STATUS_WAIT = 0;

    int IMPORT_STATUS_RUNNING = 1;

    int IMPORT_STATUS_SUCCESS = 2;

    int IMPORT_STATUS_FAIL = 3;

    //首页任务状态展示
    //运行中
    int TASK_RUNNING = 1;

    //成功
    int TASK_SUCCESS = 2;

    //失败
    int TASK_FAIL = 3;

    //未运行
    int TASK_NORUN = 4;

    /**
     * 批量插入每次插入数据量
     */
    int N_BATCH_INSERT_COUNT = 1000;

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     */
    String STR_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd
     */
    String STR_FORMAT_PATTERN1 = "yyyy-MM-dd";

    /**
     * 时间格式：HH:mm:ss
     */
    String STR_FORMAT_TIME = "HH:mm:ss";

    /**
	 * name
	 */
	String STR_NAME = "name";
	/**
	 * value
	 */
	String STR_VALUE = "value";
	/**
	 * dataList
	 */
	String STR_DATALIST = "dataList";

	/**
	 * fydm
	 */
	String STR_FYDM = "fydm";

	//比对任务-自动执行
	Integer BDRW_ZDZX = 1;

	//比对任务-手动执行
    Integer BDRW_SDZX = 2;


    //导出任务状态展示

    //成功
    int DCQK_SUCCESS = 3;

    //失败
    int DCQK_FAIL = 2;

    //np>fb
    String CYLX_NPGRFB = "1";

    //np<fb
    String CYLX_NPLEFB = "1";






}
