/**
 * Created by QiHan on 2016/5/17.
 */
var app = angular.module('myFavorApp', []);
    app.controller('myFavorCtrl', function ($scope, $http) {
        $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search
        var favorStocks=[];
       // addFavorStock("sh600006");
        var singleStock=[];
        var array=new Array();
        var countOther=0;
        var length=0;
        var count =-1;
        if( localStorage.userName==""){
            $.extend($.gritter.options, {
                time: 4000,
            });
            // clean the wrapper position class
            $('#gritter-notice-wrapper').attr('class', '');
            // global setting override
            $.extend($.gritter.options, {
                position: '' + $(this).attr('id') + '' // possibilities: bottom-left, bottom-right, top-left, top-right
            });
            $.gritter.options.position = "bottom-right";
            $.gritter.add({
                title: $(this).find('span.title').text(), // could be simpler, just for demo purposes
                text: "您好！" + "</br>" + "请先返回主页登录",
            });

      //      alert("请先登录");
        }else {
            $http.post($scope.url, {
                "username": localStorage.userName,
                "method": "getMyFavorService"
            }).success(function (data, status) {
                    $scope.status = status;
                    $scope.data = data;
                    $scope.favorStocks = data;
                    console.log($scope.favorStocks);


                    for (var item in  $scope.favorStocks) {
                        length++;
                    }

                    for (var item in  $scope.favorStocks) {
                        if (item < length - 1) {
                            console.log($scope.favorStocks[item].stock_id);
                            getSingleDetail($scope.favorStocks[item].stock_id);
                        }

                    }

                })
                .error(function (data, status) {
                    $scope.data = data || "Request failed";
                    $scope.status = status;
                });

            function GetDateStr(AddDayCount) {
                var dd = new Date();
                dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
                var y = dd.getFullYear();
                var m = dd.getMonth() + 1;//获取当前月份的日期
                var d = dd.getDate();
                var day = dd.getDay();
                var show_day = new Array('星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日');
                var week = "";
                if (day == 0) {
                    week = "星期日";
                } else {
                    week = show_day[day - 1];
                }
                //  console.log(week);
                if(week=='星期日'){
                    d=d-2;
                    if(d<=0){
                        m--;
                        d=30+d;
                    }
                }else if(week=='星期一'){
                    d=d-3;
                    if(d<=0){
                        m--;
                        d=30+d;
                    }
                }else if(week=='星期六'){
                    d=d-1;
                    if(d<=0){
                        m--;
                        d=30+d;
                    }
                }
                console.log(y+"-"+m+"-"+d);
                return y+"-"+m+"-"+d;
            }

            function getSingleDetail(stockName) {
                $http.post($scope.url, {
                    "name": stockName,
                    "date": GetDateStr(-1),
                    "method": "getStockByNameService"
                }).success(function (data, status) {
                        $scope.status = status;
                        $scope.data = data;
                        $scope.singleStock = data;

                    console.log( $scope.singleStock);

                    var temp  =Object.keys(  $scope.singleStock[0] );
                    //        console.log( $scope.singleStock);
                           console.log(temp.length);

                    if(temp.length>2) {
                        array[count + 1] = new Array;
                        array[count + 1][0] = stockName;
                        array[count + 1][1] = $scope.singleStock[0].stock_name;

                        array[count + 1][2] = $scope.singleStock[0].open;
                        array[count + 1][3] = $scope.singleStock[0].high;
                        array[count + 1][4] = $scope.singleStock[0].low;
                        array[count + 1][5] = $scope.singleStock[0].close;
                        array[count + 1][6] = $scope.singleStock[0].volumn;
                        array[count + 1][7] = $scope.singleStock[0].adj_price;
                        array[count + 1][8] = $scope.singleStock[0].pe_ttm;
                        array[count + 1][9] = $scope.singleStock[0].pb;
                        array[count + 1][10] = $scope.singleStock[0].industry;

              //          count++;
                    } else{
                        array[count + 1] = new Array;
                        array[count + 1][0] = stockName;
                        array[count + 1][1] = "-";

                        array[count + 1][2] =  "-";
                        array[count + 1][3] =  "-";
                        array[count + 1][4] =  "-";
                        array[count + 1][5] =  "-";
                        array[count + 1][6] =  "-";
                        array[count + 1][7] =  "-";
                        array[count + 1][8] =  "-";
                        array[count + 1][9] =  "-";
                        array[count + 1][10] =  "-";
               //     countOther++;
                 }
                        count++;

                if(count==length-2){
                            var dataSet = array;

                            $(document).ready(function () {
                                var selected = [];
                                $('#table').DataTable({
                                    // 设置红绿
                                    /*              "createdRow": function ( row, data, index ) {
                                     if ( data[5].replace(/[\$,]/g, '') * 1 < 150000 ) {
                                     $('td', row).eq(5).css( "color", "red");
                                     }
                                     },
                                     */
                                    "rowCallback": function (row, data) {
                                        if ($.inArray(data.DT_RowId, selected) !== -1) {
                                            $(row).addClass('selected');
                                        }
                                    },
                                    data: dataSet,
                                    columns: [
                                        {title: "股票代码"},
                                        {title: "股票简称"},
                                        {title: "开盘价"},
                                        {title: "最高价"},
                                        {title: "最低价"},
                                        {title: "收盘价"},
                                        {title: "成交量"},
                                        {title: "后复权价"},
                                        {title: "市盈率"},
                                        {title: "市净率"},
                                        {title: "所属行业"},
                                    ]
                                });
                                $('#table tbody').on('click', 'tr', function (row) {
                                    var id = this.id;
                                    var index = $.inArray(id, selected);
                                    console.log(index);

                                    if (index === -1) {
                                        selected.push(id);

                                        var rowIndex = $(this).index();
                                        console.log(rowIndex);

                                        localStorage.singleStockID = $(this).eq(0)[0].firstChild.textContent;
                                        console.log(localStorage.singleStockID);
                                        window.location.href = "../singleStockPage/singleStockPage.html";
                                    } else {
                                        selected.splice(index, 1);
                                    }

                                    $(this).toggleClass('selected');
                                });
                            });
                        }
                    })
                    .error(function (data, status) {
                        $scope.data = data || "Request failed";
                        $scope.status = status;
                    });
            }
        }
            function addFavorStock(stockName) {
                $http.post($scope.url, {
                    "name": stockName,
                    "username": "cx",
                    "method": "addMyFavorService"
                }).success(function (data, status) {
                        $scope.status = status;
                        $scope.data = data;

                        console.log($scope.data);
                    })
                    .error(function (data, status) {
                        $scope.data = data || "Request failed";
                        $scope.status = status;
                    });
            }


});