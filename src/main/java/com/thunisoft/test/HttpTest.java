package com.thunisoft.test;

import com.thunisoft.summer.component.auim.client.IUIMBusiness;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * @author zhangliujie
 * @Description
 * @date 2020/3/26.
 */
public class HttpTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String result = StringUtils.EMPTY;
        String url = "http://tap-dev.thunisoft.com/uim-api/uimRight.do?action=getUserGnRightsByGuid&userGuid=212992010";
        IUIMBusiness uimBusiness = new UIMBusiness();
        int TIMEOUT = 10000;
        try {
            GetMethod httpGet = new GetMethod(url);
            HttpClient client = new HttpClient();
            //dingjs added in 20150327 增加超时时间
            //TODO 效果如何
            int httpTimeout = 0;
            if((httpTimeout=uimBusiness.getApiRequestTimeOut())==0){
                httpTimeout = TIMEOUT;
            }
            client.getHttpConnectionManager().getParams().setSoTimeout(httpTimeout);
            client.getHttpConnectionManager().getParams().setConnectionTimeout(httpTimeout);
            client.executeMethod(httpGet);
            result = httpGet.getResponseBodyAsString();
            long duraction = System.currentTimeMillis() - start;
            System.out.println("调用UIM接口【" + url + "】结束，耗时【" + duraction + "ms】");
            System.out.println("返回值【" + result + "】");
        } catch (HttpException e) {
            System.out.println("调用UIM接口【" + url + "】出错:网络访问异常");
        } catch (IOException e) {
            System.out.println("调用UIM接口【" + url + "】出错");
        }
    }



}
