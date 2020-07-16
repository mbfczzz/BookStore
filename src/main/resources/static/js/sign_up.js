$(function () {//页面加载完成
    $("#bt_captcha").click(function(){
        let count = 15 ; //倒计时的时间
        const countDown = setInterval(() => {
            if(count == 0){
                $("#bt_captcha").text('重新发送').removeAttr('disabled');
                clearInterval(countDown);
            }else{
                $('#bt_captcha').attr('disabled', true);
                $('#bt_captcha').css({
                    background: '#d8d8d8',
                    color: '#707070',
                });  //按钮的
                $('#bt_captcha').text('重新发送('+count+')');
            }
            count--;
        }, 1000);
    });
    $("#registerform").bootstrapValidator({
        // 默认的提示消息
        message: "this is not valid field",
        // 表单框里右侧的icon
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            email: { // name="username"
                validators: {
                    notEmpty: {
                        message: "邮箱不能为空！"
                    },
                    emailAddress: {
                        message: "邮箱格式不对！"
                    }
                }
            },
            captcha:{
                validators: {
                    notEmpty: {
                        message: "验证码不能为空！"
                    },
                    regexp: {
                        message: "验证码格式不对！",
                        regexp:/^\d{6}$/
                    }
                }
            },
            username:{
                validators: {
                    notEmpty: {
                        message: "用户名不能为空！"
                    },
                    regexp: {
                        message: "只能包括数字字母的组合，长度为4-15位！",
                        regexp:/^[A-Za-z0-9]{4,15}$/
                    }
                }
            },
            password: { // name="password" 密码不能与账户相同
                validators: {
                    notEmpty: {
                        message: "密码不能为空！"
                    },
                    regexp: {
                        message: "密码由小写字母、数字组成,长度应在6到10之间！",
                        regexp: /^[a-z0-9]{6,10}$/
                    },
                }
            },
            repassword: { // name="repassword" 确认密码与密码必须一致
                validators: {
                    notEmpty: {
                        message: "确认密码不能为空！"
                    },
                    regexp: {
                        message: "确认由小写字母、数字组成,长度应在6到10之间",
                        regexp: /^[a-z0-9]{6,10}$/
                    },
                    identical: { // identical：确认密码与密码必须一致
                        message: "两次密码不一致！",
                        field: "password" // 比较的字段值
                    }
                }
            },
            phone: { // name="password" 密码不能与账户相同
                validators: {
                    notEmpty: {
                        message: "手机号不能为空！"
                    },
                    regexp: {
                        message: "手机号格式错误！",
                        regexp: /^1[3456789]\d{9}$/
                    },
                }
            },
        }
    })
    var message = $("#mes").text();
    console.log(message);
    if(message !=""){;
        $(".form-control").focus(function(){
            $("#mes").addClass("none");
        });
    }
});
var checkcode="";
function onSubmit() {

var inputCheckcode = $("#captcha").val();
console.log(inputCheckcode);
if(inputCheckcode == checkcode){
return true;
}
else {
return false;
}
}
function getCheckCode() {
var email = $("#email").val();
var patt1=new RegExp("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
var result = patt1.test(email)
if(!result)
{
return false;
}
console.log(email);
}