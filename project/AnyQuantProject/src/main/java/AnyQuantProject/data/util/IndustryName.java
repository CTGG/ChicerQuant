package AnyQuantProject.data.util;

import AnyQuantProject.dataService.realDATAService.IndustryNameDATAService;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.method.IOHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;


import java.util.*;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by G on 16/3/24.
 */
public class IndustryName implements IndustryNameDATAService{

    APIHelper aHelper = new APIHelper();


    private static CloseableHttpClient httpClient = createHttpsClient();
    private static final String ACCESS_TOKEN = "a426949d7db2b7a49da5d4a65d171eeb687ecdb8c07ab1dad6a38ffcd7818f2a";
    private static IndustryName industryName;

    public static void main(String[] args) {
        IndustryName i = new IndustryName();
//        System.out.println(i.getStockByIndustry("综合"));
        getMktIndustryFlow();
//		i.iniIndustry();
//        System.out.println(i.getIndustryName("sh601186"));

//        List<String> list = i.getAllIndustries();
//        i.getStockByIndustry("银行");


    }

    private IndustryName(){}

    public static IndustryName getInstance(){
        if ( industryName == null) {
            industryName = new IndustryName();
        }
        return industryName;
    }


    public OperationResult iniIndustry() {
        OperationResult result = new OperationResult();
        try {
            Map<String, String> list = getIndustryNameArray();
            result = IOHelper.save(R.CachePath, R.IndustryNameFile, (Serializable) list);
        } catch (IOException e) {
            return result = new OperationResult(false, "Industry IOEXCEPTION ");
        }

        return result;
    }

    private Map<String, String> readIndustryFile() {
        Map<String, String> list = (Map<String, String>) IOHelper.read(R.CachePath, R.IndustryNameFile);
        if (list == null) {
            iniIndustry();
            list = (Map<String, String>) IOHelper.read(R.CachePath, R.IndustryNameFile);
        }
        return list;
    }

    /**
     * 从缓存的文件由单只股票的代号名得到对应的行业
     * @param name
     * @return
     */
    public String getIndustryName(String name){
        Map<String, String> list = readIndustryFile();
        return list.get(name);
    }



    /**
     * 得到所有股票的代号和行业对应的map
     * @return
     * @throws IOException
     */
    public Map<String, String> getIndustryNameArray() throws IOException	{

        Map<String, String> resultList = new HashMap<String, String>();

        List<String> stockNameList = (List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
        for (int i = 0; i < stockNameList.size(); i++) {
            String temp = stockNameList.get(i);
            String subName = temp.substring(2);
            String url = "https://api.wmcloud.com:443/data/v1/api/equity/getEquIndustry.json?field=&industryVersionCD=010303&industry=&secID=&ticker="+subName+"&intoDate=";
            HttpGet httpGet = new HttpGet(url);
            //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
            httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity);
            System.out.println(body);

            //解析json格式的数据得到行业名字
            JSONObject jo = JSONObject.fromObject(body);
            JSONArray arr = JSONArray.fromObject(jo.get("data"));
            if (! arr.get(0).getClass().equals(net.sf.json.JSONNull.class)) {
                String industryName = (String) ((JSONObject) arr.get(0)).get("industryName1");
                resultList.put(stockNameList.get(i), industryName);
            }
        }




        return resultList;
    }

    /**
     * 行业日资金流向
     */
    public static void getMktIndustryFlow() {
        String url = "https://api.wmcloud.com:443/data/v1/api/market/getMktIndustryFlow.json?field=&tradeDate=20151023&beginDate=&endDate=";
        HttpGet httpGet = new HttpGet(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity);
            System.out.println(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    //创建http client
    public static CloseableHttpClient createHttpsClient() {
        X509TrustManager x509mgr = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        //因为java客户端要进行安全证书的认证，这里我们设置ALLOW_ALL_HOSTNAME_VERIFIER来跳过认证，否则将报错
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509mgr}, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 测试有多少个行业类别
     * @throws IOException
     */
    private void getIndustryNum() throws IOException{
        List<String> stockNameList = (List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
        Set<String> count = new HashSet<String>();
        for (int i = 0; i < stockNameList.size(); i++) {
            String name = getIndustryName(stockNameList.get(i));
            count.add(name);
        }
        System.out.println(count.size());

    }

	@Override
	public List<String> getAllIndustries() {
        Map<String, String> list = readIndustryFile();
        Set<String> resultSet = new HashSet<String>();

        Set entries = list.entrySet();
        if (entries != null) {
            Iterator iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                resultSet.add((String)entry.getValue());
            }
        }
        List<String> resultList = new ArrayList<>(resultSet);
        return resultList;

	}

	@Override
	public List<String> getStockByIndustry(String industry) {
        Map<String, String> stockNameList = readIndustryFile();
        Set set = stockNameList.entrySet();
        List<String> resultList = new ArrayList<>();

        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue().equals(industry)) {
                resultList.add((String) entry.getKey());
            }
        }

        return resultList;
    }


}
