package com.thunisoft.test;

/*
 * @(#)UIMBusiness.java 2015-2-6 下午06:08:25 uim-server Copyright 2015 Thuisoft,
 * Inc. All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */

import org.apache.commons.lang.StringUtils;

import com.thunisoft.summer.component.auim.client.IUIMBusiness;

public class UIMBusiness implements IUIMBusiness {

    @Override
    public String getUimServerUrl() {
        return "http://tap-dev.thunisoft.com/uim-api";
        // return ArteryConfigUtil.getProperty("uim.url");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.thunisoft.summer.component.auim.client.IUIMBusiness#isSyncUIMOrganToDB
     * ()
     */
    @Override
    public boolean isSyncUIMOrganToDB() {
        boolean syncUimToDB = true;
        String configValue = "false";
        // String configValue = ArteryConfigUtil.getProperty("uim.syncUIMOrganToDB");
        if (StringUtils.isNotBlank(configValue)) {
            syncUimToDB = Boolean.parseBoolean(configValue);
        }
        return syncUimToDB;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.thunisoft.summer.component.auim.client.IUIMBusiness#isSyncUIMRightToDB
     * ()
     */
    @Override
    public boolean isSyncUIMRightToDB() {
        boolean syncUimToDB = true;
        // String configValue = ArteryConfigUtil.getProperty("uim.syncUIMRightToDB");
        String configValue = "false";
        if (StringUtils.isNotBlank(configValue)) {
            syncUimToDB = Boolean.parseBoolean(configValue);
        }
        return syncUimToDB;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.thunisoft.summer.component.auim.client.IUIMBusiness#
     * getSyncUIMOrganInterval()
     */
    @Override
    public int getSyncUIMOrganInterval() {
        int inerval = DEFAULT_SYNC_UIM_ORGAN_INTERVAL;
        String configValue = "";
        // String configValue = ArteryConfigUtil.getProperty("uim.syncUIMOrgan.interval");
        if (StringUtils.isNotBlank(configValue)) {
            inerval = Integer.parseInt(configValue);
        }
        return inerval;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.thunisoft.summer.component.auim.client.IUIMBusiness#
     * getSyncUIMRightInterval()
     */
    @Override
    public int getSyncUIMRightInterval() {
        int inerval = DEFAULT_SYNC_UIM_RIGHT_INTERVAL;
        String configValue = "";
        // String configValue = ArteryConfigUtil.getProperty("uim.syncUIMRight.interval");
        if (StringUtils.isNotBlank(configValue)) {
            inerval = Integer.parseInt(configValue);
        }
        return inerval;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.thunisoft.summer.component.auim.client.IUIMBusiness#isNeedSyncUIMOrgan
     * ()
     */
    @Override
    public boolean isNeedSyncUIMOrgan() {
        boolean isNeedSyncUIMOrgan = false;
        String configValue = "false";
        // String configValue = ArteryConfigUtil.getProperty("uim.needSyncUIMOrgan");
        if (StringUtils.isNotBlank(configValue)) {
            isNeedSyncUIMOrgan = Boolean.parseBoolean(configValue);
        }
        return isNeedSyncUIMOrgan;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.thunisoft.summer.component.auim.client.IUIMBusiness#isNeedSyncUIMRight
     * ()
     */
    @Override
    public boolean isNeedSyncUIMRight() {
        boolean isNeedSyncUIMRight = false;
        // String configValue = ArteryConfigUtil.getProperty("uim.needSyncUIMRight");
        String configValue = "false";
        if (StringUtils.isNotBlank(configValue)) {
            isNeedSyncUIMRight = Boolean.parseBoolean(configValue);
        }
        return isNeedSyncUIMRight;
    }

    /*
     * @see com.thunisoft.summer.component.auim.client#getApiRequestTimeOut()
     */
    @Override
    public int getApiRequestTimeOut() {
        int inerval = 0;
        String timeout = "";
        // String timeout = ArteryConfigUtil.getProperty("uim.timeout");
        if (StringUtils.isNotBlank(timeout)) {
            inerval = Integer.parseInt(timeout);
        }
        return inerval;
    }
}
