var app=angular.module('myApp',[]);
app.controller("SearchCtrl", function($scope, $http, MyCache) {
    //$scope.url = 'http://115.159.106.212/php/serviceController.php';
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search

    //当前日期
    var myDate = new Date();
    myDate.getFullYear();    //获取完整的年份(4位,1970-????)
    myDate.getMonth();       //获取当前月份(0-11,0代表1月)
    myDate.getDate();        //获取当前日(1-31)
    var currentDate=""+myDate.getFullYear()+"-0"+(myDate.getMonth()+1)+"-"+myDate.getDate();
    var d=new Date();
    d.setDate(d.getDate()-30);
    var month=d.getMonth()+1;
    var day = d.getDate();
    if(month<10){
            month = "0"+month;
        }
    if(day<10){
            day = "0"+day;
    }
   var startDate = d.getFullYear()+"-"+month+"-"+day;

    $http.post($scope.url, {
        "name": localStorage.singleStockID,
        "startdate": startDate,
        "enddate":currentDate,
        "method": "getStockAmongDateService"
    }).success(function(data) {
        $scope.error = false;
        $scope.data = data;
        $scope.tableData =data;

        //console.log($scope.tableData);
        var array=new Array();
        var count=0;
        var tableData=[];

        for(var item in $scope.tableData) {
            count++;
        }
        for(var item in  $scope.tableData) {
            if (item < count-1) {
                array[item] = new Array;
                //array[item][0] = $scope.tableData[item].stock_id;
                //array[item][1] = $scope.tableData[item].stock_name;
                array[item][0] = $scope.tableData[item].date;
                array[item][1] = $scope.tableData[item].open;
                array[item][2] = $scope.tableData[item].high;
                array[item][3] = $scope.tableData[item].low;
                array[item][4] = $scope.tableData[item].close;
                array[item][5] = $scope.tableData[item].volumn;
                array[item][6] = $scope.tableData[item].adj_price;
                //array[item][7] = $scope.tableData[item].pe_ttm;
                array[item][7] = $scope.tableData[item].pb;
            }
        }

        var dataSet = array;
        $(document).ready(function () {
            var selected = [];
            $('#mytable').DataTable({
                data: dataSet,
                columns: [
                    //{title: "股票代码"},
                    //{title: "股票简称"},
                    {title: "日期"},
                    {title: "开盘价"},
                    {title: "最高价"},
                    {title: "最低价"},
                    {title: "收盘价"},
                    {title: "成交量"},
                    {title: "后复权价"},
                    //{title: "市盈率"},
                    {title: "市净率"},
                ]
        });});});


    $scope.stockName;

    //$http.post($scope.url, {
    //    "date": "---",
    //    "name": localStorage.singleStockID,
    //    "method": "getStockByNameService"
    //}).success(function (data, status) {
    //        $scope.status = status;
    //        $scope.data = data;
    //        var stockInfo = data;
    //        $scope.result = stockInfo[0];
    //        console.log(stockInfo);
    //    })
    //    .error(function (data, status) {
    //        $scope.data = data || "Request failed";
    //        $scope.status = status;
    //    });


    $http.post($scope.url, {// function getDayLineService($name, $startdate, $enddate){
        "startdate": startDate,
        "enddate":currentDate,
        "name": localStorage.singleStockID,
        "method": "getDayLineService"
    }).success(function (data, status) {
            $scope.status = status;
            $scope.data = data;
            $scope.dayKLineResult=data;
            var content=data;
            $scope.result=content[19];
            $scope.stockName=$scope.result.stock_name;
        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });

    $http.post($scope.url, {// function getDayLineService($name, $startdate, $enddate){
        "startdate": startDate,
        "enddate":currentDate,
        "name": localStorage.singleStockID,
        "method": "getWeekLineService"
    }).success(function (data, status) {
            $scope.weekKLineResult=data;
            //console.log($scope.weekKLineResult);
        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });


    d=new Date();
    d.setDate(d.getDate()-200);
    month=d.getMonth()+1;
    day = d.getDate();
    if(month<10){
        month = "0"+month;
    }
    if(day<10){
        day = "0"+day;
    }
    startDate = d.getFullYear()+"-"+month+"-"+day;

    $http.post($scope.url, {// function getDayLineService($name, $startdate, $enddate){
        "startdate": startDate,
        "enddate":currentDate,
        "name": localStorage.singleStockID,
        "method": "getMonthLineService"
    }).success(function (data, status) {
            $scope.monthKLineResult=data;
            //console.log($scope.monthKLineResult);
        })
        .error(function (data, status) {
            $scope.data = data || "Request failed";
            $scope.status = status;
        });



    //$http.post($scope.url, {
    //    "name": localStorage.singleStockID,
    //    "startdate": startDate,
    //    "enddate":currentDate,
    //    "method": "getStockAmongDateService"
    //}).success(function (data, status) {
    //        $scope.status = status;
    //        $scope.data = data;
    //        $scope.kLineResult=data;
    //    })
    //    .error(function (data, status) {
    //        $scope.data = data || "Request failed";
    //        $scope.status = status;
    //    });

    $scope.favorStateContent="关注";

    $scope.favorButtonHandle=function(){
        if($scope.favorStateContent=="关注"){
           document.getElementById("favorState").innerText="取消关注";
            $scope.favorStateContent="取消关注";

            $http.post($scope.url, {
                "username": "hmy14",
                //"username": localStorage.userName,
                "name": localStorage.singleStockID,
                "method": "addMyFavorService"
            }).success(function (data, status) {
                    $scope.status = status;
                    $scope.data = data;

                })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });
        }else{
            document.getElementById("favorState").innerText="关注";
            $scope.favorStateContent="关注";
            $http.post($scope.url, {
                "username": localStorage.userName,
                "name": localStorage.singleStockID,
                "method": "cancelMyFavorService"
            }).success(function (data, status) {
                    $scope.status = status;
                    $scope.data = data;

                })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });
        }
    };
    $scope.test = {
        "0": {
            "date": "2015-01-01",
            "stock_name": "浦发银行",
            "open": "15.69",
            "high": "15.69",
            "low": "15.69",
            "close": "15.69",
            "volumn": "0",
            "adj_price": "14.9942",
            "turnover": "0",
            "pe_ttm": "0",
            "pb": "0",
            "industry": "银行"
        },
        "1": {
            "date": "2015-01-04",
            "stock_name": "浦发银行",
            "open": "15.94",
            "high": "16.25",
            "low": "15.56",
            "close": "16.07",
            "volumn": "513568700",
            "adj_price": "15.35735",
            "turnover": "0",
            "pe_ttm": "6.530156",
            "pb": "1.295751",
            "industry": "银行"
        },
        "2": {
            "date": "2015-01-05",
            "stock_name": "浦发银行",
            "open": "16",
            "high": "16.68",
            "low": "15.82",
            "close": "16.13",
            "volumn": "511684500",
            "adj_price": "15.41469",
            "turnover": "0",
            "pe_ttm": "6.55454",
            "pb": "1.300589",
            "industry": "银行"
        },
        "3": {
            "date": "2015-01-06",
            "stock_name": "浦发银行",
            "open": "15.9",
            "high": "16.17",
            "low": "15.53",
            "close": "15.81",
            "volumn": "385716800",
            "adj_price": "15.10888",
            "turnover": "0",
            "pe_ttm": "6.424504",
            "pb": "1.274787",
            "industry": "银行"
        },
        "4": {
            "date": "2015-01-07",
            "stock_name": "浦发银行",
            "open": "15.87",
            "high": "15.88",
            "low": "15.2",
            "close": "15.25",
            "volumn": "330627100",
            "adj_price": "14.57371",
            "turnover": "0",
            "pe_ttm": "6.196942",
            "pb": "1.229632",
            "industry": "银行"
        },
        "5": {
            "date": "2015-01-08",
            "stock_name": "浦发银行",
            "open": "15.2",
            "high": "16.25",
            "low": "15.11",
            "close": "15.43",
            "volumn": "491999900",
            "adj_price": "14.74573",
            "turnover": "0",
            "pe_ttm": "6.270084",
            "pb": "1.244146",
            "industry": "银行"
        },
        "retmsg": "success"
    };
});

app.factory('MyCache', function ($cacheFactory) {
    return $cacheFactory('myCache');
})




