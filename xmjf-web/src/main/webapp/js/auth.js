$(function () {
    $("#identityNext").click(function () {
        var realName=$("#realName").val();
        var card=$("#card").val();
        var password=$("#_ocx_password").val();
        var confirmPassword=$("#_ocx_password1").val();
        if(isEmpty(realName)){
            layer.tips("真实名称不能为空!","#realName");
            return;
        }
        // 身份证号  长度  密码 是否为数子进行校验
        if(isEmpty(card)){
            layer.tips("身份证号不能为空!","#card");
            return;
        }
        if(isEmpty(password)){
            layer.tips("交易密码不能为空!","#_ocx_password");
            return;
        }
        if(isEmpty(confirmPassword)){
            layer.tips("确认密码不能为空!","#_ocx_password1");
            return;
        }

        if(password!=confirmPassword){
            layer.tips("交易密码不一致!","#_ocx_password1");
            return;
        }

        var params={};
        params.realName=realName;
        params.card=card;
        params.password=password;
        $.ajax({
            type:"post",
            url:ctx+"/account/userAuth",
            data:params,
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    window.location.href=ctx+"/account/setting";
                }else{
                    layer.tips(data.msg,"#identityNext");
                }
            }
        })





    })
})