<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/5/4
 * Time: 上午12:58
 */
include_once ('try.php');
//    function getNameByAgeService(){
//        $age = $_GET['age'];
//        $jsonstring = getNameByAge($age);
//        echo $jsonstring;
//    }
    /**
     *
     * 数据说明:
     *
     * 所有date为xxxx_xx_xx的string,或是你们提供一个固定的类型我自己转也可以~
     * 股票和大盘的name是英文和代号
     * industry_name是行业名(中文)
     *
     * 图表类!!------每次调用是得到某个图表在某一天的值(也就是图中的一个点)~
     *
     * 输出的格式只是主要数据的格式--暂时用于说明一下可以得到什么,至于jsonstring里面具体的格式会有不同,比如加上状态信息什么的~
     * 事实上网络异常什么的很有可能出现,所以所有的方法都会返回操作是否成功的信息= =
     *
     *
     * 有欠缺的可以加,多余的也可以删╰(￣▽￣)╮
     *
    */
    /**
     *
     * 模块一:  股票关注
     *
     */
    //输入: 用户名
    //输出: 操作是否成功的信息以及所有喜爱的股票名
    function getMyFavorService(){
        $username = $_GET['username'];
        $jsonstring = getMyFavor($username);
        echo $jsonstring;
    }

    //输入: 股票名
    //输出: 操作是否成功的信息
    function cancelMyFavorService(){
        $username = $_GET['username'];
        $name = $_GET['name'];
        $jsonstring = cancelMyFavor($name, $username);
        echo $jsonstring;
    }
    /*
     *
     * 模块二:  得到股票基本属性
     *
    */

    //输入:股票名和日期
    //输出: 操作是否成功的信息以及该日该股票的属性--各种表格项信息
    function getStockByNameService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = getStockByName($name, $date);
        echo $jsonstring;
    }

    //输入: 股票名和日期区间
    //输出: 操作是否成功的信息以及该日区间内的每日该股票的属性--各种表格项信息
    function getStockAmongDateService(){
        $name = $_GET['name'];
        $startdate = $_GET['startdate'];
        $enddate = $_GET['enddate'];
        $jsonstring = getStockAmongDate($name, $startdate, $enddate);
        echo $jsonstring;
    }

    //输入: 股票名和日期
    //输出: 操作是否成功的信息以及该日所有股票名或者是返回所有股票的属性--各种表格项信息!!!------这里你们来选一个~
    function getAllStocksService(){
        $date = $_GET['date'];
        $jsonstring = getAllStocks($date);
        echo $jsonstring;
    }

    //输入: 股票名和日期区间
    //输出: 操作是否成功的信息以及该区间内每日所有股票的属性--各种表格项信息
    function getAllStocksAmongDateService(){
        $startdate = $_GET['startdate'];
        $enddate = $_GET['enddate'];
        $jsonstring = getStockAmongDate($startdate, $enddate);
        echo $jsonstring;
    }

    /*
     *
     * 模块三:    得到大盘基本属性
     *
     */

    //输入: 大盘名和日期
    //输出: 操作是否成功的信息以及该日该大盘的属性--各种表格项信息
    function getBenchMarkByNameService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = getBenchMarkByName($name, $date);
        echo $jsonstring;
    }

    //输入: 大盘名和日期区间
    //输出: 操作是否成功的信息以及该区间内每日该大盘的属性--各种表格项信息
    function getBenchMarkAmongDateService(){
        $name = $_GET['name'];
        $startdate = $_GET['startdate'];
        $enddate = $_GET['enddate'];
        $jsonstring = getBenchMarkAmongDate($name, $startdate, $enddate);
        echo $jsonstring;
    }


    /*
     *
     * 模块四:    得到图表数据
     *
     */
    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的5日均线--日线的值
    function get5PMA_dayService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get5PMA_day($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的5日均线--周线的值
    function get5PMA_weekService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get5PMA_week($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的5日均线--月线的值
    function get5PMA_monthService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get5PMA_month($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的10日均线--日线的值
    function get10PMA_dayService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get10PMA_day($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的10日均线--周线的值
    function get10PMA_weekService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get10PMA_week($name, $date);
        echo $jsonstring;
    }
    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的10日均线--月线的值
    function get10PMA_monthService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get10PMA_month($name, $date);
        echo $jsonstring;
    }


    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的30日均线--日线的值
    function get30PMA_dayService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get10PMA_day($name, $date);
        echo $jsonstring;
    }
    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的30日均线--周线的值
    function get30PMA_weekService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get30PMA_week($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的30日均线--月线的值
    function get30PMA_monthService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get30PMA_month($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的6RSI的值
    function get6RSIService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get6RSI($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的12RSI的值
    function get12RSIService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get12RSI($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的24RSI的值
    function get24RSIService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get24RSI($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的6BIAS的值
    function get6BIASService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get6RSI($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的12BIAS的值
    function get12BIASService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get12RSI($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的24BIAS的值
    function get24BIASService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = get24RSI($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的KDJ中K的值
    function getKService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = getK($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的KDJ中D的值
    function getDService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = getD($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的KDJ中J的值
    function getJService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = getJ($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的MACD中DIF的值
    function getDIFService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = getDIF($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的MACD中DEA的值
    function getDEAService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = getDEA($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的MACD中MACDBar的值
    function getMACDBarService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = getMACDBar($name, $date);
        echo $jsonstring;
    }

    //输入: 股票或大盘名和日期
    //输出: 操作是否成功的信息以及该日该股票或大盘的多项式拟合的值
    function getpolyService(){
        $name = $_GET['name'];
        $date = $_GET['date'];
        $jsonstring = getpoly($name, $date);
        echo $jsonstring;
    }

    /**
     *
     * 模块五:   得到行业数据
     *
     */

    //输入: 无
    //输出: 操作是否成功的信息以及所有行业的名称
    function getAllIndustriesService(){
        $jsonstring = getAllIndustries();
        echo $jsonstring;
    }


    //输入: 行业名称
    //输出: 操作是否成功的信息以及该行业的所有股票名(最新)
    function getStocksByIndustryService(){
        $industry_name = $_GET['industry_name'];
        $jsonstring = getStocksByIndustry($industry_name);
        echo $jsonstring;
    }


    //输入: 行业名称和日期
    //输出: 操作是否成功的信息以及该日该行业的所有属性--表格项信息
    function getIndustryService(){
        $industry_name = $_GET['industry_name'];
        $date = $_GET['date'];
        $jsonstring = getIndustry($industry_name,$date);
        echo $jsonstring;
    }
    

?>