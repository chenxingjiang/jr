$(function () {
    $("#rechargeBut").click(function () {
        var rechargeAmount=$("#rechargeAmount").val();
        var picCode=$("#pictureCode").val();
        var password=$("#password").val();

        if(isEmpty(rechargeAmount)){
            layer.tips("充值金额非法!","#rechargeAmount");
            return;
        }
        if(isEmpty(picCode)){
            layer.tips("验证码非空!","#pictureCode");
            return;
        }
        if(isEmpty(password)){
            layer.tips("校验密码不能为空!","#password");
            return;
        }
        document.forms['fm'].submit();
    })
});