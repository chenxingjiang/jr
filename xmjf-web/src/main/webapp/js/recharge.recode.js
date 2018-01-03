$(function () {
    loadRechargeRecodesList();
});


function loadRechargeRecodesList(pageNum) {
    var params={};
    params.pageNum=1;
    if(!isEmpty(pageNum)){
        params.pageNum=pageNum;
    }


    $.ajax({
        type:"post",
        url:ctx+"/account/queryBusAccountRechargesByParams",
        data:params,
        dataType:"json",
        success:function (data) {
            var recodeList=data.list;
            var paginator=data.paginator;
            if(recodeList.length>0){
                initDivsHtml(recodeList);
                initNavigatePages(paginator);
            }
        }
    })
}

/**
 * @param recodeList
 */
function initDivsHtml(recodeList) {
    if(recodeList.length>0){
        /**
         *  <script id="rechargeListTemp" type="text/x-handlebars-template">
         {{#each this}}
         <div class="table-content-first">
         {{addtime}}
         </div>
         <div class="table-content-center">
         {{actualAmount}}元
         </div>
         <div class="table-content-first">
         {{status}}
         </div>
         {{/each}}
         </script>
         */
        var divs="";
        for(var i=0;i<recodeList.length;i++){
            var tempData=recodeList[i];
            divs=divs+"<div class='table-content-first'>";
            divs=divs+tempData.auditTime+"</div>";
            divs=divs+" <div class='table-content-center'>";
            divs=divs+tempData.actualAmount+"</div>";
            divs=divs+"<div class='table-content-first'>";
            if(tempData.status==0){
                divs=divs+"失败";
            }
            if(tempData.status==1){
                divs=divs+"成功";
            }
            if(tempData.status==2){
                divs=divs+"审核中";
            }
            divs=divs+"</div>";
        }
        $("#rechargeList").html(divs);
    }

}


function  initNavigatePages(paginator) {
    var navigatepageNums=paginator.navigatepageNums;// 数组
    if(navigatepageNums.length>0){
        /**
         * 拼接导航页内容
         */
        var lis="";
        //var href=
        /**
         * 首页
         * 上一页
         * 下一页
         * 末页
         */
        if(!paginator.isFirstPage){
            lis=lis+"<li ><a href='javascript:getCurrentPageData(1)' title='首页' >首页</a></li>";
        }
        if(paginator.hasPreviousPage){
            lis=lis+"<li ><a href='javascript:getCurrentPageData("+(paginator.pageNum-1)+")' title='上一页' >上一页</a></li>";
        }
        for(var i=0;i<navigatepageNums.length;i++){
            var page=navigatepageNums[i];
            var href="javascript:getCurrentPageData("+page+")";
            if(paginator.pageNum==page){
                lis=lis+"<li class='active'><a  href='"+href+"' title='第"+page+"页' >"+page+"</a></li>";
            }else{
                lis=lis+"<li ><a href='"+href+"' title='第"+page+"页' >"+page+"</a></li>";
            }
        }
        if(paginator.hasNextPage){
            lis=lis+"<li ><a href='javascript:getCurrentPageData("+(paginator.pageNum+1)+")' title='下一页' >下一页</a></li>";
        }

        if(!paginator.isLastPage){
            lis=lis+"<li ><a href='javascript:getCurrentPageData("+(paginator.lastPage)+")' title='末页' >末页</a></li>";
        }
        $("#pages").html(lis);
    }
}


function  getCurrentPageData(pageNum) {
    loadRechargeRecodesList(pageNum);
}