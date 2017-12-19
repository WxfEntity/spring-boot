/**
 * Created by TYZ027 on 2017/7/28.
 */
$(function() {
    $('#new_password').blur(checkNewPwd);
    $('#changePassword').click(changePwd);
    $('#final_password').blur(checkConfirm);
    $('#back').click(back);
});
function back() {
    window.history.back();
}
function checkNewPwd() {
    var pwd= $('#new_password').val().trim();
    var rule=/^\w{4,10}$/;
    if(rule.test(pwd)){
        $('#new_password').next().hide();
        return true;
    }
    $('#new_password').next().show().find('span').html("4-10个字符");
    return false;
}
function checkConfirm() {
    var pwd2=$('#final_password').val();
    var pwd=$('#new_password').val();
    if(pwd && pwd2==pwd){
        $('#final_password').next().hide();
        return true;
    }
    $('#final_password').next().show().find('span').html("密码前后不一致");
    return true;
}
function changePwd() {

    var userId=getCookie("userId");
    var password = $('#last_password').val();
    var newPassword=$('#new_password').val();
    var finalPassword=$('#final_password').val();
    var n = checkConfirm()+checkNewPwd();
    if(n!=2){
            return;
    }
    var url="user/changePwd.do";
    var data={password:password,userId:userId,newPassword:newPassword,finalPassword:finalPassword};
    $.post(url,data,function (result) {
        if(result.stata==0){
            alert("密码修改成功，请重新登录");
            location.href="log_in.html";
        }else {
            $('#last_password').next().show().find('span').html(result.message);
        }
    });
}