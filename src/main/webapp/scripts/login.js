$(function() {
	/*console.log("hello world")*/
	$('#login').click(loginAction);
	$('#count').blur(checkName);
	$('#password').blur(checkPassword);
	//注册会功能
	$('#regist_button').click(registAction);
	$('#regist_username').blur(checkRegistName);
	$('#regist_password').blur(checkRegistPassword);
	$('#final_password').blur(checkconfirm);

});

function registAction() {
	console.log('registAction');
	//检验界面参数
	var n = checkRegistName()+checkRegistPassword()+checkconfirm();
	if(n!=3){
		return; 
	}
	//获取表单数据
	var name= $('#regist_username').val().trim();
	var nick = $('#nickname').val();
	var password=$('#regist_password').val();
	var confirm = $('#final_password').val();
	var data={"name":name,"nick":nick,"password":password,"confirm":confirm};
	var url="user/regist.do";
	//发送ajax请求
	$.post(url,data,function(result){
		if(result.stata==0){
			$('#back').click();
			var name = result.data.name;
			$('#count').val(name);
			$('#password').focus();
			$('#final_password').val("");
			$('#regist_username').val("");
			$('#nickname').val("");
			$('#regist_password').val("");
		}else if(result.stata==4){
			$('#regist_username').next().show().find('span').html(result.message);
		}else{
			alert(result.message)
		}
	});
	//得到响应
}
function checkconfirm() {
	var pwd2=$('#final_password').val();
	var pwd=$('#regist_password').val();
	if(pwd && pwd2==pwd){
		$('#final_password').next().hide();
		return true;
	}
	$('#final_password').next().show().find('span').html("密码前后不一致");
	return true;
}
function checkRegistPassword() {
	var pwd = $('#regist_password').val().trim();
	var rule=/^\w{4,10}$/;
	if(rule.test(pwd)){
		$('#regist_password').next().hide();
		return true;
	}
	$('#regist_password').next().show().find('span').html("4-10个字符");
	return false;
}
function checkRegistName() {
	var name=$('#regist_username').val().trim();
	var rule=/^\w{4,10}$/;
	
	if(rule.test(name)){
		$('#regist_username').next().find('span').empty();
		return true;
	}
	$('#regist_username').next().show().find('span').html("4-10个字符");
	return false;
}
function checkName() {
	var name=$('#count').val();
	var rule =/^\w{4,10}$/;
	if(!rule.test(name)){
		$('#count').next().html("4-10个字符");
		return false;
	}
	$('#count').next().empty();
	return true;
	
}
function checkPassword() {
	var password=$('#password').val();
	var rule =/^\w{4,12}$/;
	if(!rule.test(password)){
		$('#password').next().html("4-12个字符");
		return false;
	}
	$('#password').next().empty();
	return true;
}
function loginAction() {
	/*console.log('loginAction');*/
	//获取用户输入的的帐号和密码
	var name=$('#count').val();
	var pwd=$('#password').val();
	var n = checkName()+checkPassword();
	if(n!=2){
		return;
	}
	//data 对象中的key的属性名要与服务器控制器的参数名一致
	var data = {"name":name,"password":pwd};
	$.ajax({
		url:'user/login.do',
		data:data,
		type:'post',
		dataType:'json',
		success:function(result){
			console.log(result);
			if(result.stata==0){
				//登录成功
				var user = result.data;
				//登录成功后将userId保存到cookie中
				addCookie("userId",user.id);
				
				//跳转edit.html
				location.href="edit.html";
				console.log(user);
			}else{
				var msg = result.message;
				if(result.stata==2){
					$('#count').next().html(msg);
				}else if(result.stata==3){
					$('#password').next().html(msg);
				}else{
					alert(msg);
				}
				
			}
		},
		error:function(e){
			alert('通讯失败!')
		}
	});
	
}




