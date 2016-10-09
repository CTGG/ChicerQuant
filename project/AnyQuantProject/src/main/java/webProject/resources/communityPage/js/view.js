var app=angular.module('myApp',[]);
app.controller("SearchCtrl", function($scope, $http, MyCache) {
    $scope.url = 'http://115.159.97.98/php/serviceController.php'; // The url of our search
    
        $http.post($scope.url, {
            "method": "getShareStrategyService"
        }).success(function (data, status) {
            var temp = data;
            console.log(temp);
            var tmp=data;
            var str_cnt=0;
            $scope.usrmessages=[];
            for(item in tmp){
                if (tmp[item]!="success") {
                    if (tmp[item].strategytype) {
                        $scope.usrmessages[str_cnt++] = {
                            "textalign": "right",
                            "side": "other",
                            "userName": tmp[item].username,
                            "strName": tmp[item].strategyname,
                            "strType": tmp[item].strategytype,
                            "buypoint": tmp[item].buypoint+"%",
                            "sellpoint": tmp[item].sellpoint+"%"
                        };
                    } else {
                        $scope.usrmessages[str_cnt++] = {
                            "side": "you",
                            "textalign": "left",
                            "userName": tmp[item].username,
                            "strName": tmp[item].strategyname,
                            "strType": tmp[item].crossstr,
                            "buypoint": "黄金交叉点",
                            "sellpoint": "死亡交叉点"
                        };
                    }
                }
            }
        })
            .error(function (data, status) {
                $scope.data = data || "Request failed";
                $scope.status = status;
            });
    // }
});
app.factory('MyCache', function ($cacheFactory) {
    return $cacheFactory('myCache');
});

$('#gotoStrategyBtn').click(function (e) {
    $('#competetionBoard').fadeOut(300);
    $('#strategyBoard').fadeTo(1, 300);
});

$('#gotoCompetetionBtn').click(function (e) {
    $('#strategyBoard').fadeOut(300);
    $('#competetionBoard').fadeTo(1, 300);
});

var strList;
var selectedBtnID;

$('.insertBtn').click(function(){
    // 下面这行代码就是你要的ID属性
    selectedBtnID=$(this).attr("value");
    console.log(selectedBtnID);
     conductBuyStrategy(1);
});

function conductBuyStrategy(strIndex){
    swal({
        title: "您将支付"+2+"元",
        text: "您确认付费查看本策略吗?",
        type: "warning",
        showCancelButton: true,
        cancelButtonText: "取消",
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定" ,
        closeOnConfirm: false }, function(){
        swal("支付成功!", "您可以查看该策略内容!", "success");
    });
}
