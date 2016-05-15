
function SearchCtrl($scope, $http) {

    $scope.test={"0":{"date":"2015-01-01","stock_name":"浦发银行","open":"15.69","high":"15.69","low":"15.69","close":"15.69","volumn":"0","adj_price":"14.9942","turnover":"0","pe_ttm":"0","pb":"0","industry":"银行"},
        "1":{"date":"2015-01-04","stock_name":"浦发银行","open":"15.94","high":"16.25","low":"15.56","close":"16.07","volumn":"513568700","adj_price":"15.35735","turnover":"0","pe_ttm":"6.530156","pb":"1.295751","industry":"银行"},
        "2":{"date":"2015-01-05","stock_name":"浦发银行","open":"16","high":"16.68","low":"15.82","close":"16.13","volumn":"511684500","adj_price":"15.41469","turnover":"0","pe_ttm":"6.55454","pb":"1.300589","industry":"银行"},
        "3":{"date":"2015-01-06","stock_name":"浦发银行","open":"15.9","high":"16.17","low":"15.53","close":"15.81","volumn":"385716800","adj_price":"15.10888","turnover":"0","pe_ttm":"6.424504","pb":"1.274787","industry":"银行"},
        "4":{"date":"2015-01-07","stock_name":"浦发银行","open":"15.87","high":"15.88","low":"15.2","close":"15.25","volumn":"330627100","adj_price":"14.57371","turnover":"0","pe_ttm":"6.196942","pb":"1.229632","industry":"银行"},
        "5":{"date":"2015-01-08","stock_name":"浦发银行","open":"15.2","high":"16.25","low":"15.11","close":"15.43","volumn":"491999900","adj_price":"14.74573","turnover":"0","pe_ttm":"6.270084","pb":"1.244146","industry":"银行"},
        "retmsg":"success"};


    $scope.url = 'http://115.159.106.212/php/serviceController.php'; // The url of our search
    //
    //// The function that will be executed on button click (ng-click="search()")
    //$scope.singleStockInfo = function() {



    $http.post($scope.url, {"date":"---", "name" :localStorage.singleStockID, "method": "getStockByNameService"}).

            success(function(data, status) {
                $scope.status = status;
                $scope.data = data;
                //data=eval("("+data+")");
                var stockInfo=data;
                //stockInfo= $.parseJSON(stockInfo);
                $scope.result = stockInfo[0]; // Show result from server in our <pre></pre> element


        })
            .
            error(function(data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    //};

    //$scope.KlineDataList = function() {
        //localStorage.singleStockID

        //$scope.str;

        $http.post($scope.url, {"name" : localStorage.singleStockID,"startdate":"2015-01-01","enddate":"2015-01-10", "method": "getStockAmongDateService"}).
            success(function(data, status) {
                $scope.status = status;
                $scope.data = data;
                $scope.kLineResult=data; // Show result from server in our <pre></pre> element
                $scope.testStr=data;
                $scope.str=data;
                //var stockInfo=data;
                localStorageService.add( "result", $scope.kLineResult) ;
                //document.write( localStorageService);
                console.log(  localStorageService("result"));
            })
            .
            error(function(data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
        //return $scope.str;
    //};

}

