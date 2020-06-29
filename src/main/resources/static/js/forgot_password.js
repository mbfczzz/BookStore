$(function () {//页面加载完成
    $("#changepassword").bootstrapValidator({
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
            password: { // name="password" 密码不能与账户相同
                validators: {
                    notEmpty: {
                        message: "密码不能为空！"
                    },
                    regexp: {
                        message: "密码由小写字母、数字组成,长度应该在6到10之间！",
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
                        message: "确认密码由小写字母、数字组成,长度应该在6到10之间！！",
                        regexp: /^[a-z0-9]{6,10}$/
                    },
                    identical: { // identical：确认密码与密码必须一致
                        message: "两次密码不一致！",
                        field: "password" // 比较的字段值
                    }
                }
            },
        }
    })
    var message = $("#mes").text();
    console.log(message);
    if(message !=""){
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
    $.ajax({
        type: "GET",
        url: "/getcheckcode?email="+email,
        dataType: "JSON",
        success: function(result) {
            if(result !=null &&result != ""){
                checkcode = result;
            }
        }
    });
}